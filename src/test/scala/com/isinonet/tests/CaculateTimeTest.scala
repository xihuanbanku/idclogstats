package com.isinonet.tests

import com.isinonet.ismartnet.udaf.{CaculateTime, NumsAvg}
import org.apache.spark.sql.types._
import org.apache.spark.sql.{Row, SparkSession}

object CaculateTimeTest {


  def main(args: Array[String]): Unit = {

    val sparkSession = SparkSession.builder()
      .config("spark.sql.shuffle.partitions", "10")
      .appName("CaculateTimeTest").master("local").getOrCreate
    val sc = sparkSession.sparkContext

    val nums = List((1, 1530100918), (2, 1530095854), (3, 1530107403), (4, 1530093926), (5, 1530101653),
      (1,1530099209), (2,1530102939), (3,1530105467), (4, 1530105096), (5, 1530097163),
      (1,1530097987), (2,1530101575), (3,1530105199), (4, 1530096520), (5, 1530106135),
      (1,1530102488), (2,1530100376), (3,1530094915), (4, 1530099631), (5, 1530101633),
      (1,1530101202), (2,1530102249), (3,1530098468), (4, 1530094786), (5, 1530105838),
      (1,1530101799), (2,1530102402), (3,1530103762), (4, 1530103272), (5, 1530100996),
      (1,1530104247), (2,1530105157), (3,1530100384), (4, 1530097415), (5, 1530107379),
      (1,1530098145), (2,1530103940), (3,1530106208), (4, 1530097979), (5, 1530094033),
      (1,1530106957), (2,1530096908), (3,1530106065), (4, 1530094255), (5, 1530093973),
      (1,1530099475), (2,1530098317), (3,1530094694), (4, 1530093699), (5, 1530103552),
      (1,1530105702), (2,1530107009), (3,1530097048), (4, 1530094778), (5, 1530093996),
      (1,1530106140), (2,1530103834), (3,1530095899), (4, 1530102292), (5, 1530096794),
      (1,1530103468), (2,1530101195), (3,1530103158), (4, 1530098298), (5, 1530096205),
      (1,1530106532), (2,1530097479), (3,1530093969), (4, 1530100911), (5, 1530102636),
      (1,1530099136), (2,1530105877), (3,1530100155), (4, 1530094690), (5, 1530101883),
      (1,1530103580), (2,1530094839), (3,1530094444), (4, 1530094033), (5, 1530095980),
      (1,1530102880), (2,1530107595), (3,1530095389), (4, 1530099230), (5, 1530098586),
      (1,1530094649), (2,1530095915), (3,1530096898), (4, 1530105998), (5, 1530100714),
      (1,1530105026), (2,1530094749), (3,1530105433), (4, 1530107799), (5, 1530096567),
      (1,1530096146), (2,1530102384), (3,1530103954), (4, 1530101781), (5, 1530094173)
    )
//    val nums = List(1530093699, 1530093926, 1530093969, 1530093973, 1530093996)
    val numsRDD = sc.parallelize(nums);

    val numsRowRDD = numsRDD.map { x => Row(x._1.toInt, x._2.toString) }

    val structType = StructType(StructField("user_id", IntegerType, true) :: StructField("num", StringType, true) :: Nil)

    val numsDF = sparkSession.createDataFrame(numsRowRDD, structType)

    numsDF.createOrReplaceTempView("numtest")
//    sparkSession.sql("select avg(num) from numtest ").collect().foreach { x => println(x) }

    sparkSession.udf.register("caculateTime", new CaculateTime)

    //分组聚合时长 形如:(user_id , (time1, time2, time3...)
    sparkSession.sql("select concat_ws(',',collect_set(num)), user_id from numtest group by user_id")
      .foreach(x => {
        val userId = x.getAs[Int](1)
        //对聚合后的结果分解为 数字数组, 排序
        val longs = x.getAs[String](0).split(",").map(_.toLong).sorted
        var current = 0l
        var timeSum = 0l
        var viewCount = 0
        longs.foreach(now => {
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

        println(s"[$userId, ----$timeSum ===$viewCount]")
      })


//    sparkSession.udf.register("numsAvg", new NumsAvg)
//    sparkSession.sql("select numsAvg(num) from numOrdered").show()
  }
}
