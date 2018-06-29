package com.isinonet.tests

import org.apache.spark.{SparkConf, SparkContext}

/**
  * Created by Administrator on 2018-06-29.
  * 2018-06-29
  */
object AccumulatorTest {

  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setMaster("local[*]").setAppName("AccumulatorTest")
    val sc = new SparkContext(conf)

    var sum_normal = 0
    val accumulator = sc.longAccumulator

    val array = Array(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
    val parr = sc.parallelize(array, 5)
    parr.foreach(x => {
      sum_normal += x
      accumulator.add(x)
    })

    println(sum_normal)
    println(accumulator)


  }

}
