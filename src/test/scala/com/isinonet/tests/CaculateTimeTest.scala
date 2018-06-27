package com.isinonet.tests

import com.isinonet.ismartnet.udaf.{CaculateTime, NumsAvg}
import org.apache.spark.sql.types.{DoubleType, LongType, StructField, StructType}
import org.apache.spark.sql.{Row, SparkSession}

object CaculateTimeTest {


  def main(args: Array[String]): Unit = {

    val sparkSession = SparkSession.builder().appName("CaculateTimeTest").master("local").getOrCreate
    val sc = sparkSession.sparkContext

//    val nums = List(1530100918, 1530095854, 1530107403, 1530093926, 1530101653,
//      1530099209, 1530102939, 1530105467, 1530105096, 1530097163,
//      1530097987, 1530101575, 1530105199, 1530096520, 1530106135,
//      1530102488, 1530100376, 1530094915, 1530099631, 1530101633,
//      1530101202, 1530102249, 1530098468, 1530094786, 1530105838,
//      1530101799, 1530102402, 1530103762, 1530103272, 1530100996,
//      1530104247, 1530105157, 1530100384, 1530097415, 1530107379,
//      1530098145, 1530103940, 1530106208, 1530097979, 1530094033,
//      1530106957, 1530096908, 1530106065, 1530094255, 1530093973,
//      1530099475, 1530098317, 1530094694, 1530093699, 1530103552,
//      1530105702, 1530107009, 1530097048, 1530094778, 1530093996,
//      1530106140, 1530103834, 1530095899, 1530102292, 1530096794,
//      1530103468, 1530101195, 1530103158, 1530098298, 1530096205,
//      1530106532, 1530097479, 1530093969, 1530100911, 1530102636,
//      1530099136, 1530105877, 1530100155, 1530094690, 1530101883,
//      1530103580, 1530094839, 1530094444, 1530094033, 1530095980,
//      1530102880, 1530107595, 1530095389, 1530099230, 1530098586,
//      1530094649, 1530095915, 1530096898, 1530105998, 1530100714,
//      1530105026, 1530094749, 1530105433, 1530107799, 1530096567,
//      1530096146, 1530102384, 1530103954, 1530101781, 1530094173
//    )
    val nums = List(1530093699, 1530093926, 1530093969, 1530093973, 1530093996)
    val numsRDD = sc.parallelize(nums, 1);

    val numsRowRDD = numsRDD.map { x => Row(x.toLong) }

    val structType = StructType(Array(StructField("num", LongType, true)))

    val numsDF = sparkSession.createDataFrame(numsRowRDD, structType)

    numsDF.createOrReplaceTempView("numtest")
//    sparkSession.sql("select avg(num) from numtest ").collect().foreach { x => println(x) }

    sparkSession.udf.register("caculateTime", new CaculateTime)
    val dataFrame = sparkSession.sql("select * from numtest order by num")
    dataFrame.createOrReplaceTempView("numOrdered")
    sparkSession.sql("select caculateTime(num) from numOrdered").show()


    sparkSession.udf.register("numsAvg", new NumsAvg)
    sparkSession.sql("select numsAvg(num) from numtest").show()
  }
}
