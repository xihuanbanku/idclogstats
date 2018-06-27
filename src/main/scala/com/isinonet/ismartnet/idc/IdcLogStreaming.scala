package com.isinonet.ismartnet.idc

import java.text.SimpleDateFormat
import java.util.Date

import com.alibaba.fastjson.JSON
import com.isinonet.ismartnet.beans.{Rtpvuv, Website}
import com.isinonet.ismartnet.mapper.{RtpvuvMapper, WebsiteMapper}
import com.isinonet.ismartnet.utils.JDBCHelper
import kafka.serializer.StringDecoder
import org.apache.spark.SparkConf
import org.apache.spark.streaming.kafka.KafkaUtils
import org.apache.spark.streaming.{Durations, StreamingContext}

import scala.collection.mutable.ListBuffer

object IdcLogStreaming {

  def main(args: Array[String]): Unit = {

    val conf: SparkConf = new SparkConf()
      .setAppName("IdcLogStreaming")
//      .setMaster("local[*]")
    val ssc = new StreamingContext(conf, Durations.minutes(1))
    ssc.sparkContext.setLogLevel("ERROR")

    val brokers = "docker5:9092, docker6:9092, docker7:9092";
//    val brokers = "localhost:9095, localhost:9096, localhost:9097";
    val topics = "ismartnet.iprobe";
    val topicSet = topics.split(",").toSet
    val kafkaParams = Map[String, String]("metadata.broker.list" -> brokers)
    val messages = KafkaUtils.createDirectStream[String, String, StringDecoder, StringDecoder](ssc, kafkaParams, topicSet)

    val lines = messages.map(_._2).map(JSON.parseObject(_)).filter(_.containsKey("host"))

    val pv = lines.map(x => (x.getString("host"), 1)).reduceByKey(_+_)
    //构成 (host_ip, 1)的形式
    val uv = lines.map(x => (x.getString("host")+"_"+ x.getString("sip"), 1))
      //构成 (host_ip, sum)的形式
      .reduceByKey(_+_)
      //只取出host
      .map(x => (x._1.split("_")(0), 1))
      //累加数量
      .reduceByKey(_+_)

    //scala  转为java 的集合
    //    import scala.collection.JavaConversions._
    import scala.collection.JavaConversions._

    val host_pv_uv = pv.join(uv)

    val host_pv_uv_joined = host_pv_uv.transform(rdd => {
      val session = JDBCHelper.getSession
      val mapper = session.getMapper(classOf[WebsiteMapper])
      val websites = mapper.findAll()
      val list1 = scala.collection.mutable.ArrayBuffer[Tuple2[String, String]]()
      for(x:Website <- websites) {
        list1 += ((x.getDomain(), x.getWebsiteID().toString))
      }
      val websiteRdd = ssc.sparkContext.parallelize(list1)

      rdd.leftOuterJoin(websiteRdd)
    })

    host_pv_uv_joined.foreachRDD(_.foreachPartition(it => {
      val list = ListBuffer[Rtpvuv]()
      val listWebsite = ListBuffer[Website]()
      val now = new Date()

      it.foreach(row => {
        val unit = new Rtpvuv
        if(row._2._2.isEmpty) {
          val website = new Website
          website.setWebsiteName(row._1)
          website.setDomain(row._1)
          unit.setDomain(row._1)
          listWebsite += website
        } else {
          unit.setWebsiteId(row._2._2.get.toInt)
        }
        val pv_uv = row._2._1

        unit.setPv(pv_uv._1)
        unit.setUv(pv_uv._2)
        unit.setStatDate(now)

        list+=unit

      })
      val session = JDBCHelper.getSession
      if(listWebsite.size > 0) {
        val mapperWebsite = session.getMapper(classOf[WebsiteMapper])
        mapperWebsite.insertBatch(listWebsite)
        session.commit
        println(s"[${new Date}][Website]${listWebsite.size}")
      } else {
        println(s"[${new Date}][Website]no data...")
      }
      if(list.size > 0) {
        val mapper = session.getMapper(classOf[RtpvuvMapper])
        mapper.insertBatch(list)
        session.commit
        println(s"[${new Date}][PV_UV]${list.size}")
      } else {
        println(s"[${new Date}][PV_UV]no data...")
      }
      session.close
    }))
    ssc.start()
    ssc.awaitTermination()
    ssc.stop()
  }
}