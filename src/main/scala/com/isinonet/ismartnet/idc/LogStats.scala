package com.isinonet.ismartnet.idc

import java.text.SimpleDateFormat
import java.util.Properties

import com.isinonet.ismartnet.beans.IdcDaily
import com.isinonet.ismartnet.mapper.IdcDailyMapper
import com.isinonet.ismartnet.utils.{JDBCHelper, PropUtils}
import org.apache.spark.sql.functions._
import org.apache.spark.sql.{Encoders, SparkSession}

import scala.collection.mutable.ListBuffer

/**
  * idc日志分析
  * Created by Administrator on 2018-06-06.
  * 2018-06-06
  */
object LogStats {


  def main(args: Array[String]): Unit = {

    val sparkSession = SparkSession.builder()
      .config("spark.sql.shuffle.partitions", "10")
      .master("local[*]").appName("LogStats").getOrCreate()

    //导入spark的隐式转换
//    import sparkSession.implicits._
    import sparkSession.implicits._
    //scala  转为java 的集合
//    import scala.collection.JavaConversions._
    import scala.collection.JavaConversions._
    val date = args(0)
    //读取日志文件
    val logToday = sparkSession.read.json("hdfs://192.168.1.200:9000/lhk/" + date + "*")


//    logToday.filter($"host" =!= "")
//      .select($"host").distinct().rdd.foreachPartition((it) => {
//        val session = JDBCHelper.getSession
//        val mapper = session.getMapper(classOf[WebsiteMapper])
//        var i = 0
//        val list = ListBuffer[Website]()
//        while (it.hasNext) {
//          val row = it.next()
//
//          val unit = new Website
//          unit.setDomain(row.getString(0))
//
//          list+=(unit)
//          println(i)
//          i += 1
//        }
//      mapper.insertBatch(list)
//      session.commit
//    })
    val props: Properties = PropUtils.loadProps("jdbc.properties")
    //读取ip RDD
    val tb_static_ip = sparkSession.read.jdbc(props.getProperty("url"),
      "tb_static_ip",
      props).collect()
    //广播
    val broad_tb_static_ip = sparkSession.sparkContext.broadcast(tb_static_ip)
    //读取ua RDD
    val tb_static_uatype = sparkSession.read.jdbc(props.getProperty("url"),
      "tb_static_uatype",
      props)
    //广播
    val broad_tb_static_uatype = sparkSession.sparkContext.broadcast(tb_static_uatype)
    //读取website RDD
    val tb_idc_website = sparkSession.read.jdbc(props.getProperty("url"),
      "tb_idc_website",
      props).select($"website_id", $"domain")
    //广播
    val broad_tb_idc_website = sparkSession.sparkContext.broadcast(tb_idc_website)

    //统计pv, uv
    val pv_uv = logToday.filter($"host" =!= "")
      .select($"host", $"sip", $"ua", $"atm")

    //自定义时间的统计函数
    def makeDT(date: String, time: String, tz: String) = {s"$date $time $tz"}
    val makeDt = udf(makeDT(_:String,_:String,_:String))

    val pv_uv_ua_website = pv_uv.join(broad_tb_static_uatype.value, $"ua" === $"p_type", "left")
      .join(broad_tb_idc_website.value, $"host" === $"domain", "left")

    //确定ip归属地
    val finalResult = pv_uv_ua_website.mapPartitions((it) => {
      val ipDB = broad_tb_static_ip.value
      val list = ListBuffer[IdcDaily]()

      while (it.hasNext) {
        val e = new IdcDaily
        val row = it.next()
        val sip = row.getLong(1)

        //查找对应的IP归属地
        ipDB.filter(row => sip >= row.getLong(1) && sip <= row.getLong(2)).take(1).foreach(x => {
          e.setProvinceId(x.getString(3).toShort)
          e.setIsp(x.getString(4))
        })
        e.setWebsiteId(row.getInt(8))
        e.setIsMobile(row.getAs[Short](5))
        e.setSip(sip)
        list.+=(e)

      }
      list.toIterator
    })(Encoders.bean(classOf[IdcDaily])).toDF().groupBy($"isMobile", $"isp", $"provinceId", $"websiteId")
      .agg(countDistinct($"sip").as("uv"), count($"sip").as("pv"))
      .select($"isMobile", $"isp", $"provinceId", $"websiteId", $"uv", $"pv")

    //保存到pg数据库
    finalResult.foreachPartition((it) => {
      val session = JDBCHelper.getSession
      val mapper = session.getMapper(classOf[IdcDailyMapper])
      var i = 0
      val list = ListBuffer[IdcDaily]()
      val sdf = new SimpleDateFormat("yyyy-MM-dd")
      while (it.hasNext) {
        val row = it.next()

        val unit = new IdcDaily
        unit.setIsMobile(row.getShort(0))
        unit.setIsp(row.getString(1))
        unit.setProvinceId(row.getShort(2))
        unit.setWebsiteId(row.getInt(3))
        unit.setUv(row.getLong(4))
        unit.setPv(row.getLong(5))
        unit.setStatDate(sdf.parse(date))

        list+=(unit)
        println(i)
        i += 1
      }
      if(list.size >0) {
        mapper.insertBatch(list)
        session.commit
      }
    })
    sparkSession.stop()
  }
}

