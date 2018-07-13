package com.isinonet.ismartnet.idc

import java.io.FileNotFoundException
import java.text.SimpleDateFormat
import java.util.{Calendar, Date, Properties}

import com.isinonet.ismartnet.beans.{IdcDaily, IdcDailyExample, StaticUAtype, Website}
import com.isinonet.ismartnet.mapper.{IdcDailyMapper, StaticUAtypeMapper, WebsiteMapper}
import com.isinonet.ismartnet.utils.{JDBCHelper, PropUtils}
import org.apache.hadoop.conf.Configuration
import org.apache.hadoop.fs.FileSystem
import org.apache.hadoop.fs.Path
import org.apache.spark.sql.functions._
import org.apache.spark.sql.{DataFrame, Encoders, SparkSession}

import scala.collection.mutable.{ArrayBuffer, ListBuffer}

/**
  * idc日志分析
  * 每小时执行一次, 执行前先清空当天的记录
  * 例如: 20180705 08:10执行时, 清空数据库中statdate=20180705的记录, 读取文件 20180705*, 统计statdate=20180705的记录
  *      20180706 00:10执行时, 清空数据库中statdate=20180705的记录, 读取文件 20180705*, 统计statdate=20180705的记录
  *      20180706 01:10执行时, 清空数据库中statdate=20180706的记录, 读取文件 20180706*, 统计statdate=20180706的记录
  * Created by Administrator on 2018-06-06.
  * 2018-06-06
  */
object LogStats {

  def main(args: Array[String]): Unit = {
    if(args.length % 2 != 0) {
      throw new IllegalArgumentException(
        s"""
           |Usage:
           | ./video_start.sh [date] [hdfs] <expect date> <expect hdfs>
           |
           | date:        要计算的日期
           | hdfs:        输入文件路径, 以/结尾
           | expect date: 期望计算的日期
           | expect hdfs: 期望计算的文件
           |
         """.stripMargin)
    }

    val sparkSession = SparkSession.builder()
      .config("spark.sql.shuffle.partitions", "10")
//      .master("local[*]")
      .appName("LogStats")
      .getOrCreate()

    val sparkContext = sparkSession.sparkContext
    // pickup config files off classpath// pickup config files off classpath

    //导入spark的隐式转换
//    import sparkSession.implicits._
    import sparkSession.implicits._
    //scala  转为java 的集合
//    import scala.collection.JavaConversions._
    import scala.collection.JavaConversions._
    val date = if(args.length > 2) args(2) else args(0)
    val hdfsPath = if(args.length > 2) args(3) else args(1)
    println(s"${"="*50}start [${date}] hdfs path [$hdfsPath]")
    //准备前7天的ip文件, 为判断新老用户使用
    val format = new SimpleDateFormat("yyyy-MM-dd")
    val calendar = Calendar.getInstance()
    calendar.setTime(format.parse(date))
    val dates = new ArrayBuffer[String]
    //创建hdfs的fs, 来检测目录是否存在
    val conf = new Configuration
    conf.addResource(new Path("core-site.xml"))
    val fileSystem = FileSystem.get(conf)
    var _7daysUsers :DataFrame = null
    //检查今天的日志文件是否已经生成
    val todayFilePath = hdfsPath + date+"*"
    val fileStatuses = fileSystem.globStatus(new Path(todayFilePath))
    if(fileStatuses != null && fileStatuses.length>0) {
      println(s"${"="*50}reading file path ${todayFilePath}")
      dates += todayFilePath
    } else {
      println(s"${"="*50}log file of ${todayFilePath} does not exist, exiting...")
      throw new FileNotFoundException("today's log file is not ready")
    }
    //检查前7天的cookie文件是否已经生成, 如果没有, 需要重新生成
    val cookieFilePath = hdfsPath + date+"_cookie_7_csv"
    val cookiefileStatuses = fileSystem.globStatus(new Path(cookieFilePath))
    if(cookiefileStatuses != null && cookiefileStatuses.length>0) {
      println(s"${"="*50}reading cookieFilePath ${cookieFilePath}")
      _7daysUsers = sparkSession.read.csv(cookieFilePath).select($"_c0".as("cook_7"))
    } else {
      println(s"${"="*50}cookieFilePath of ${cookieFilePath} does not exist, genarating...")
      for (elem <- 1 to 7) {
        calendar.add(Calendar.DAY_OF_MONTH, -1)
        //先判断路径是否存在
        val filePath = hdfsPath + format.format(calendar.getTime) + "*"
        if(fileSystem.globStatus(new Path(filePath)).length>0) {
          println(s"${"="*50}reading file path ${filePath}")
          dates += filePath
        }
      }
      println(s"${"="*50}hdfs length ${dates.length}")
      if(dates.length > 0) {
        dates.length match {
          case 1 => _7daysUsers = sparkSession.read.json(dates(0)).select($"cook".as("cook_7")).distinct()
          case 2 => _7daysUsers = sparkSession.read.json(dates(0), dates(1)).select($"cook".as("cook_7")).distinct()
          case 3 => _7daysUsers = sparkSession.read.json(dates(0), dates(1), dates(2)).select($"cook".as("cook_7")).distinct()
          case 4 => _7daysUsers = sparkSession.read.json(dates(0), dates(1), dates(2), dates(3)).select($"cook".as("cook_7")).distinct()
          case 5 => _7daysUsers = sparkSession.read.json(dates(0), dates(1), dates(2), dates(3), dates(4)).select($"cook".as("cook_7")).distinct()
          case 6 => _7daysUsers = sparkSession.read.json(dates(0), dates(1), dates(2), dates(3), dates(4), dates(5)).select($"cook".as("cook_7")).distinct()
          case 7 => _7daysUsers = sparkSession.read.json(dates(0), dates(1), dates(2), dates(3), dates(4), dates(5), dates(6)).select($"cook".as("cook_7")).distinct()
        }
        _7daysUsers.write.csv(cookieFilePath)
        println(s"${"="*50}write file path ${cookieFilePath} success")
      } else {
        throw new FileNotFoundException("7 days File does not exist")
      }
      calendar.clear()
    }
    //读取日志文件
    val logToday = sparkSession.read.json(hdfsPath + date + "*")
//    val logToday = sparkSession.read.json("d:/sdc.gz")
//println(s"line===============logToday===line${logToday.count()}")
    val props: Properties = PropUtils.loadProps("jdbc.properties")
    //读取ip RDD
    val tb_static_ip = sparkSession.read.jdbc(props.getProperty("url"),
      "tb_static_ip",
      props).collect()
    //广播
    val broad_tb_static_ip = sparkContext.broadcast(tb_static_ip)
    //读取ua RDD
    val tb_static_uatype = sparkSession.read.jdbc(props.getProperty("url"),
      "tb_static_uatype",
      props)
//    tb_static_uatype.show(false)
    //广播
//    val broad_tb_static_uatype = sparkContext.broadcast(tb_static_uatype)
    //读取website RDD
    val tb_idc_website = sparkSession.read.jdbc(props.getProperty("url"),
      "tb_idc_website",
      props).select($"website_id", $"domain")
//    tb_idc_website.show(false)
    //广播
//    val broad_tb_idc_website = sparkContext.broadcast(tb_idc_website)

    //统计pv, uv, 过滤掉 jpg, png, bmp, js, css, xml, swf, xls, rar, zip, gif, woff, ttf, eot, otf, svg, json
    val pv_uv = logToday.filter(" host!= '' and not url rlike '\\.(ico|js|jpg|png|bmp|css|xml|swf|xls|rar|zip|gif|ttf|eot|otf|svg|woff|json)'")
      .select($"url", $"host", $"sip", $"ua", $"atm", $"ref", $"cook")

//    println(s"line===============pv_uv===line${pv_uv.count()}")
//    pv_uv.show(false)
//    println(s"===broad_tb_static_uatype======$broad_tb_static_uatype")
//    println(s"-------==${broad_tb_static_uatype.value}")
//    println(s"===broad_tb_idc_website======$broad_tb_idc_website")
//    println(s"++++++$tb_idc_website===${broad_tb_idc_website.value}")

    //网站详情关联
    val pv_uv_ua_website = pv_uv.join(tb_static_uatype, $"ua" === $"ua_type", "left")
      .join(tb_idc_website, $"host" === $"domain", "left")
//      .where("website_id is null")
//    pv_uv_ua_website.show()
//    +------+-----+---+-----+----+----+-------+------+---------+-------+------------+----------+-------+
//    |host  |sip  |ua |atm  |ref |id  |cook   |ua_type|is_mobile|os_type|browser_type|website_id|domain |
//    +------+-----+---+-----+----+----+-------+------+---------+-------+------------+----------+-------+
    //与7天前的用户关联判断是否是新用户
    val pv_uv_ua_website_7user = pv_uv_ua_website.join(_7daysUsers, $"cook" === $"cook_7", "left")
    //确定ip归属地
    val finalResult = pv_uv_ua_website_7user.mapPartitions(it => {
      val ipDB = broad_tb_static_ip.value
      val list = ListBuffer[IdcDaily]()

      it.foreach(row => {
        val e = new IdcDaily
        val sip = row.getAs[Long]("sip")

        //查找对应的IP归属地
        ipDB.filter(_ip => sip >= _ip.getLong(1) && sip <= _ip.getLong(2)).take(1).foreach(real_ip => {
          e.setProvinceId(real_ip.getString(3).toShort)
          e.setIsp(real_ip.getString(4))
        })
        //通过ref确认来源
        val ref = row.getAs[String]("ref")
        if(ref == null) {
          e.setComeFrom(1.toShort)
        } else if(ref.indexOf("www.baidu.com") > 0
          || ref.indexOf("www.so.com") > 0
          || ref.indexOf("www.soso.com") > 0
          || ref.indexOf("www.sogou.com") > 0
          || ref.indexOf("www.yahoo.com") > 0
          || ref.indexOf("www.google.com") > 0) {
          e.setComeFrom(2.toShort)
        } else {
          e.setComeFrom(3.toShort)
        }
        if(row.getAs[String]("cook_7") != null) {
          e.setIsNewComer(0.toShort)
        } else {
          e.setIsNewComer(1.toShort)
        }
        e.setWebsiteId(row.getAs[Int]("website_id"))
        e.setIsMobile(row.getAs[Short]("is_mobile"))
        e.setSip(sip)
        e.setHost(row.getAs[String]("host"))
        e.setAtm(row.getAs[Long]("atm").toString)
        list.+=(e)

      })
      list.toIterator
    })(Encoders.bean(classOf[IdcDaily])).toDF()
      .groupBy($"isMobile", $"isp", $"provinceId", $"host", $"websiteId", $"comeFrom", $"isNewComer")
      .agg(countDistinct($"sip").as("uv"), count($"sip").as("pv")
        , concat_ws(",", collect_set($"atm")).as("atm_string"))
//      .select($"isMobile", $"isp", $"provinceId", $"websiteId", $"uv", $"pv", $"atm_string")

//    finalResult.show()
    //先清空表
    println(s"${"="*50}delete pg data ${date}")
    val session = JDBCHelper.getSession
    val mapper = session.getMapper(classOf[IdcDailyMapper])
    val idcDaily = new IdcDailyExample
    idcDaily.createCriteria().andStatDateEqualTo(format.parse(date))
    mapper.deleteByExample(idcDaily)
    session.commit
    //保存到pg数据库
    finalResult.rdd.mapPartitionsWithIndex((index, it) => {

      val session = JDBCHelper.getSession
      val mapper = session.getMapper(classOf[IdcDailyMapper])
      val list = ListBuffer[IdcDaily]()
      val sdf = new SimpleDateFormat("yyyy-MM-dd")


      val mapperWebsite = session.getMapper(classOf[WebsiteMapper])
      val listWebsite = ListBuffer[Website]()
      val sdf2 = new SimpleDateFormat("MMddHH")
      var websiteID = (sdf2.format(new Date) + index + "000").toInt

      //循环partition中的所有记录
      it.filter(_.getAs[String]("atm_string") != "").foreach(row => {

        var current = 0l
        var timeSum = 0l
        var viewCount = 0
        var singlePageCount = 0
        var lastPeriod = false
        //计算时长
        row.getAs[String]("atm_string").split(",").map(_.toLong).sorted.foreach(now => {
          val result = now - current
          //两次时间间隔小于 30分钟(1800s), 计入时长统计
          if(result <1800) {
            timeSum += result
            lastPeriod = false
          } else {
            //否则访问次数+1
            viewCount += 1
            //如果是连续两次间隔(lastPeriod)都大于阈值, spn+1
            if(lastPeriod) {
              singlePageCount += 1
            } else {
              lastPeriod = true
            }
          }
          current = now
        })
        val unit = new IdcDaily
        unit.setIsMobile(row.getAs[Short]("isMobile"))
        unit.setIsp(row.getAs[String]("isp"))
        unit.setProvinceId(row.getAs[Short]("provinceId"))
        if(row.getAs[Int]("websiteId") == 0 || row.getAs[Int]("websiteId") == null) {
          websiteID += 1
          val website = new Website
          website.setWebsiteID(websiteID)
          website.setWebsiteName(row.getAs[String]("host"))
          website.setDomain(row.getAs[String]("host"))
          listWebsite+=website
          unit.setWebsiteId(websiteID)

        } else {
          unit.setWebsiteId(row.getAs[Int]("websiteId"))
        }
        unit.setComeFrom(row.getAs[Short]("comeFrom"))
        unit.setIsNewComer(row.getAs[Short]("isNewComer"))
        unit.setUv(row.getAs[Long]("uv"))
        unit.setPv(row.getAs[Long]("pv"))
        unit.setVn(viewCount)
        unit.setVt(timeSum.toInt)
        unit.setSpn(singlePageCount)
        unit.setStatDate(sdf.parse(date))
        list+=(unit)
      })
      if(list.size >0) {
        println(s"[${new Date}][IdcDaily]${mapper.insertBatch(list)}")
        list.clear()
        session.commit
      } else {
        println(s"[${new Date}][IdcDaily]no data...")
      }
      if(listWebsite.size >0) {
        println(s"[${new Date}][WebSite]${mapperWebsite.insertBatchWithID(listWebsite)}")
        listWebsite.clear()
        session.commit
      } else {
        println(s"[${new Date}][WebSite]no data...")
      }
      it
    }).collect

    //统计新UA 入库
//    +------+-----+---+-----+----+----+-------+------+---------+-------+------------+----------+-------+
//    |host  |sip  |ua |atm  |ref |id  |cook   |ua_type|is_mobile|os_type|browser_type|website_id|domain |
//    +------+-----+---+-----+----+----+-------+------+---------+-------+------------+----------+-------+
    pv_uv_ua_website
      .select($"ua")
      .where("ua_type == null").rdd.mapPartitionsWithIndex((index, it) => {

      val session = JDBCHelper.getSession
      val mapperStaticUAtype = session.getMapper(classOf[StaticUAtypeMapper])
      val listStaticUAtype = ListBuffer[StaticUAtype]()
      val sdf = new SimpleDateFormat("yyyy-MM-dd")
      val sdf2 = new SimpleDateFormat("MMdd")
      var uaID = (sdf2.format(sdf.parse(date)) + index + "000").toInt

      it.foreach(row => {
          uaID += 1
          val staticUAtype = new StaticUAtype
          staticUAtype.setId(uaID)
          staticUAtype.setUaType(row.getAs[String]("ua"))
          listStaticUAtype +=(staticUAtype)
      })
      //新UA type 入库
      if(listStaticUAtype.size >0) {
        println(s"[${new Date}][UA_type]${mapperStaticUAtype.insertBatch(listStaticUAtype)}")
        session.commit
        listStaticUAtype.clear()
      } else {
        println(s"[${new Date}][UA_type]no data...")
      }
      it
    }).collect
    sparkSession.stop()
  }
}

