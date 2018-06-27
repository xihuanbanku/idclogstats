package com.isinonet.ismartnet.video

import java.text.SimpleDateFormat
import java.util.{Date, Properties}

import com.isinonet.ismartnet.beans._
import com.isinonet.ismartnet.constant.Constants
import com.isinonet.ismartnet.mapper._
import com.isinonet.ismartnet.utils.{JDBCHelper, PropUtils}
import org.apache.spark.SparkContext
import org.apache.spark.sql._
import org.apache.spark.sql.functions._

import scala.collection.mutable.ListBuffer
;

/**
  * 读取hdfs文件， 统计视频类型, ua类型, 用户观看视频时长, 用户性别
  * 结果入库postgresql
  * Created by Administrator on 2018-05-04.
  * 2018-05-04
  */
object VideoLogStats {

  def main(args: Array[String]): Unit = {
    val sparkSession = SparkSession.builder().appName("VideoLogStats")
      .config("spark.sql.shuffle.partitions", "10").getOrCreate()
    sparkSession.sparkContext.setLogLevel("WARN")

    import sparkSession.implicits._

    import scala.collection.JavaConversions._
    val date = args(0)
    val aids = Array(9,10,13,14,16,32,5,6,1012)
    val cacheToday = sparkSession.read.json(args(1))
        .filter(x => {
            aids.contains(x.getString(0).toInt)
        }).cache

    val props: Properties = PropUtils.loadProps("jdbc.properties")
    //1. 统计UA, 需要按照 ip排重
    //读取现有的UA类型
    val tb_static_uatype = sparkSession.read.jdbc(props.getProperty(Constants.URL), "tb_static_uatype", props).select("id", "p_type").cache()
    val broadcast_ua = sparkSession.sparkContext.broadcast(tb_static_uatype)
    //日志先group by 然后与ua表 left join
    val dataset: Dataset[Row] = cacheToday.where("ua is not null").select($"sip",$"ua").distinct().groupBy($"ua").agg(count($"ua").as("c_ua"))
    val joinedDataframe = dataset.join(broadcast_ua.value, $"ua" === $"p_type", "left")

    //入库
//    dataset.foreachPartition(data2Postgres(_, "tb_video_daily_uatype", "ua_type, ua_count", date))

    joinedDataframe.rdd.mapPartitionsWithIndex((index, it) => {
      val session = JDBCHelper.getSession
      val mapper = session.getMapper(classOf[VideoDailyUAtypeMapper])
      val mapperStaticUAtype = session.getMapper(classOf[StaticUAtypeMapper])
      val list = ListBuffer[VideoDailyUAtype]()
      val listStaticUAtype = ListBuffer[StaticUAtype]()
      val sdf = new SimpleDateFormat("yyyy-MM-dd")
      val sdf2 = new SimpleDateFormat("MMdd")
      var uaID = (sdf2.format(sdf.parse(date)) + index + "000").toInt
      it.foreach(row => {
        val unit = new VideoDailyUAtype
        if(row.isNullAt(2)) {
          uaID += 1
          val staticUAtype = new StaticUAtype
          staticUAtype.setId(uaID)
          staticUAtype.setpType(row.getString(0))
          listStaticUAtype +=(staticUAtype)
          unit.setUaType(uaID.toString)
        } else {
          unit.setUaType(row.getInt(2).toString)
        }
        unit.setUaCount(row.getLong(1).toInt)
        unit.setAtime(sdf.parse(date))

        list+=(unit)

      })
      //UA统计入库
      if(list.size >0) {
        mapper.insertBatch(list)
        session.commit
        println(s"[${new Date}][UA]${list.size}")
      } else {
        println(s"[${new Date}][UA]no data...")
      }
      //新UA type 入库
      if(listStaticUAtype.size >0) {
        mapperStaticUAtype.insertBatch(listStaticUAtype)
        session.commit
        println(s"[${new Date}][UA_type]${listStaticUAtype.size}")
      } else {
        println(s"[${new Date}][UA_type]no data...")
      }
      it
    }).collect()
    // 2.统计观看视频时长
    cacheToday.select($"sip", $"atm").where("atm is not null").groupByKey(_.get(0).toString)
      .mapGroups((a, b) => (a, b.map[Long](r => r.getAs[Long](1)).toList))
      .map(x => {
        val list = x._2.sortWith(_ > _)
        var d = 0l
        for(i <- 0 until list.size-1) {
          val temp = list(i + 1) - list(i)
          if(temp < 600)
            d += temp
        }
        val duration_min = (d / 60.0).toInt
        (x._1, duration_min match {
          case duration_min if(duration_min <= 10) => 1
          case duration_min if(duration_min > 10 && duration_min <= 30) => 2
          case duration_min if(duration_min > 30 && duration_min <= 60) => 3
          case duration_min if(duration_min > 60) => 4
          case _ => 5
        })
    }).groupBy("_2").count()
//      .foreachPartition(data2Postgres(_, "tb_video_daily_duration", "duration_type, duration_count", date))
      .foreachPartition((it) => {
      val session = JDBCHelper.getSession
      val mapper = session.getMapper(classOf[VideoDailyDurationMapper])

      val list = ListBuffer[VideoDailyDuration]()
      val sdf = new SimpleDateFormat("yyyy-MM-dd")
      while (it.hasNext) {
        val row = it.next()

        val unit = new VideoDailyDuration
        unit.setDurationType(String.valueOf(row.getInt(0)))
        unit.setDurationCount(row.getLong(1).toInt)
        unit.setAtime(sdf.parse(date))

        list+=(unit)

      }
      if(list.size >0) {
        mapper.insertBatch(list)
        session.commit
        println(s"[${new Date}][Duration]${list.size}")
      } else {
        println(s"[${new Date}][Duration]no data...")
      }
    })


    //3. 视频类型分布
    //读取上报的url1
    val tb_data2 = sparkSession.read.jdbc(props.getProperty(Constants.URL), "tb_iprobe_data2", Array[String]("create_time > '"+date+" 00:00:00'", "create_time <= '"+date+" 23:59:59'", "rflag in(1, 3)"), props).select("url1")
    //读取豆瓣中的url1
    val tb_media_meta_url_map = sparkSession.read.jdbc(props.getProperty(Constants.URL), "tb_media_meta_url_map", props).select("url", "media_uid").cache()
    //读取豆瓣中的视频类型
    val tb_media_meta_data2 = sparkSession.read.jdbc(props.getProperty(Constants.URL), "tb_media_meta_data2", props).select($"media_uid".as("media_uid1"), $"media_type2").cache()
    //连接 取类型, 按照斜线分割, 做 word count
    tb_data2.join(tb_media_meta_url_map, $"url1" === $"url")
      .join(tb_media_meta_data2, $"media_uid" === $"media_uid1").select($"media_type2")
      .flatMap(_.getString(0).split("/")).map((_, 1)).groupBy($"_1").count
//      .foreachPartition(data2Postgres(_, "tb_video_daily_vtype", "v_type, v_count", date))
      .foreachPartition((it) => {
      val session = JDBCHelper.getSession
      val mapper = session.getMapper(classOf[VideoDailyVtypeMapper])

      val list = ListBuffer[VideoDailyVtype]()
      val sdf = new SimpleDateFormat("yyyy-MM-dd")
      while (it.hasNext) {
        val row = it.next()

        val unit = new VideoDailyVtype
        unit.setvType(row.getString(0))
        unit.setvCount(row.getLong(1).toInt)
        unit.setAtime(sdf.parse(date))

        list+=(unit)

      }
      if(list.size >0) {
        mapper.insertBatch(list)
        session.commit
        println(s"[${new Date}][Vtype]${list.size}")
      } else {
        println(s"[${new Date}][Vtype]no data...")
      }
    })

    //4. 性别分布
    //读取当天  18-22点上报的sip, url1

    val sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
    val s_date = sdf.parse(date+" 18:00:00").getTime
    val e_date = sdf.parse(date+" 22:00:00").getTime

    val tb_data2_18_22 = cacheToday.select("sip", "aid", "url", "cid")
      .where("atm >= "+s_date+" and atm < "+e_date)
      .map(row => {
        val url = row.getString(2)
        val aid = row.getLong(1)
        (row.getLong(0), aid match {
          //iqiyi
          case 1012  => {
            val str = getUrlParam(url, "lc")
            if(str.length>0) {
              str.split("?")(0)
            }
            "1012"
          }
            //youku
          case 14 => {
            val vid = getUrlParam(url, "vid")
            "http://v.youku.com/v_show/id_" + vid + ".html"
          }
            //QQ
          case 13 => {
            val cid = "_"+row.get(3).toString+"."
            val vid = cid.substring(cid.indexOf("_") + 1, cid.indexOf("."))
            "https://v.qq.com/x/page/" + vid+".html"
          }
            //中国网络电视台
          case 10 => {
            val cid = "_"+row.get(3)
              // cid 非空时 decode cid, cid 形式cntv_xxxxxx_xxx.ts, 按照"_"分割, 取第2个结果
              "http://tv.cntv.cn/video/default/" + cid.split("_")(1)
          }
            //letv
          case 9 => {
            val vid = getUrlParam(url, "vid")
            "http://www.le.com/ptv/vplay/" + vid + ".html"
          }
            //sohu
          case 16 => {
            val vid = getUrlParam(url, "vid")
            "http://m.tv.sohu.com/v" + vid +".shtml"

          }
            //huashu
          case 32 => {
            val vid = getUrlParam(url, "vid")
            "https://www.wasu.cn/Play/show/id/" + vid

          }
          case aid if(aid==5 || aid==6) => {
            "56"
          }
          case _ => "error"
        })
      })(Encoders.tuple(Encoders.scalaLong, Encoders.STRING)).toDF("sip", "url1").distinct()
    //连接 取类型, 包含 "爱情" 的标记为1,  返回结果 (ip, 1) (ip, 0)
    val cacheSip = tb_data2_18_22.join(tb_media_meta_url_map, $"url1" === $"url")
      .join(tb_media_meta_data2, $"media_uid" === $"media_uid1")
      .select($"sip", $"media_type2").map(r => {
      val i = if(r.getString(1).contains("爱情")) 1 else 0
      (r.getString(0), i)
      })(Encoders.tuple(Encoders.STRING, Encoders.scalaInt)).toDF("_1", "_2").groupBy($"_1").agg(count($"_1").as("c_sip"), sum($"_2").as("s_love")).cache()


    val total = cacheSip.count()
    val female = cacheSip.where("s_love/c_sip > 0.5").count()

    List(Row("male", total-female), Row("female", female)).foreach(
      row => {
        val session = JDBCHelper.getSession
        val mapper = session.getMapper(classOf[VideoDailyGenderMapper])

        val sdf = new SimpleDateFormat("yyyy-MM-dd")

          val unit = new VideoDailyGender
          unit.setGender(row.getString(0))
          unit.setGenderCount(row.getLong(1).toInt)
          unit.setAtime(sdf.parse(date))

          mapper.insert(unit)
          session.commit
          println(s"[${new Date}][Gender]${row}")
      }
    )//.foreachiterator, "tb_video_daily_gender", "gender, gender_count", date)

    //删除缓存
    cacheSip.unpersist
    cacheToday.unpersist
    tb_media_meta_url_map.unpersist()
    tb_media_meta_data2.unpersist()
    sparkSession.stop()

  }

  /**
    * 从URL 中获取需要的参数字段
    * @param url
    * @param param
    * @return
    */
  def getUrlParam(url: String, param:String): String = {
    val strings = url.split(param + "=")
    if(strings.length>1) {
      strings(0).split("&")(0)
    }
    ""
  }
}
