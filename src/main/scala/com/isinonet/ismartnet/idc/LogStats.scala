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
//      .master("local[*]")
      .appName("LogStats")
      .getOrCreate()

    //导入spark的隐式转换
//    import sparkSession.implicits._
    import sparkSession.implicits._
    //scala  转为java 的集合
//    import scala.collection.JavaConversions._
    import scala.collection.JavaConversions._
    val date = args(0)
    //读取日志文件
    val logToday = sparkSession.read.json("hdfs://192.168.1.213:9000/ismartnet/" + date + "*")
//    val logToday = sparkSession.read.json("d:/sdc.gz")

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

    pv_uv.show(false)
    println(s"===broad_tb_static_uatype======$broad_tb_static_uatype")
    println(s"-------==${broad_tb_static_uatype.value}")
    println(s"===broad_tb_idc_website======$broad_tb_idc_website")
    println(s"++++++===${broad_tb_idc_website.value}")

    //网站详情关联
    val pv_uv_ua_website = pv_uv.join(broad_tb_static_uatype.value, $"ua" === $"p_type", "left")
      .join(broad_tb_idc_website.value, $"host" === $"domain", "left")
//    pv_uv_ua_website.show(false)
//    +------+-----+---+-----+----+------+---------+-------+------------+----------+-------+
//    |host  |sip  |ua |atm  |id  |p_type|is_mobile|os_type|browser_type|website_id|domain |
//    +------+-----+---+-----+----+------+---------+-------+------------+----------+-------+
    //确定ip归属地
    val finalResult = pv_uv_ua_website.mapPartitions(it => {
      val ipDB = broad_tb_static_ip.value
      val list = ListBuffer[IdcDaily]()

      it.foreach(row => {
        val e = new IdcDaily
        val sip = row.getLong(1)

        //查找对应的IP归属地
        ipDB.filter(_ip => sip >= _ip.getLong(1) && sip <= _ip.getLong(2)).take(1).foreach(real_ip => {
          e.setProvinceId(real_ip.getString(3).toShort)
          e.setIsp(real_ip.getString(4))
        })
        e.setWebsiteId(row.getAs[Int](9))
        e.setIsMobile(row.getAs[Short](6))
        e.setSip(sip)
        e.setAtm(row.getAs[Long](3).toString)
        list.+=(e)

      })
      list.toIterator
    })(Encoders.bean(classOf[IdcDaily])).toDF()
      .groupBy($"isMobile", $"isp", $"provinceId", $"websiteId")
      .agg(countDistinct($"sip").as("uv"), count($"sip").as("pv"), concat_ws(",", collect_set($"atm")).as("atm_string"))
//      .select($"isMobile", $"isp", $"provinceId", $"websiteId", $"uv", $"pv", $"atm_string")

    //保存到pg数据库
    finalResult.foreachPartition(it => {

      val session = JDBCHelper.getSession
      val mapper = session.getMapper(classOf[IdcDailyMapper])
      val list = ListBuffer[IdcDaily]()
      val sdf = new SimpleDateFormat("yyyy-MM-dd")

      //循环partition中的所有记录
      it.filter(_.getString(6) != "").foreach(row => {

        var current = 0l
        var timeSum = 0l
        var viewCount = 0
        row.getAs[String](6).split(",").map(_.toLong).sorted.foreach(now => {
          val result = now - current
          //两次时间间隔小于 5分钟(300s), 计入时长统计
          if(result <300) {
            timeSum += result
          } else {
            //否则访问次数+1
            viewCount += 1
          }
          current = now
        })
        val unit = new IdcDaily
        unit.setIsMobile(row.getShort(0))
        unit.setIsp(row.getString(1))
        unit.setProvinceId(row.getShort(2))
        unit.setWebsiteId(row.getInt(3))
        unit.setUv(row.getLong(4))
        unit.setPv(row.getLong(5))
        unit.setVn(viewCount)
        unit.setVt((timeSum/60).toInt)
        unit.setStatDate(sdf.parse(date))
        list+=(unit)
        if(list.size >0) {
          mapper.insertBatch(list)
          list.clear()
          session.commit
        }
      })

    })
    sparkSession.stop()
  }
}

