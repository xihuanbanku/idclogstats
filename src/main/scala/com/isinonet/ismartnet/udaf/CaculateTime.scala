package com.isinonet.ismartnet.udaf

import org.apache.spark.sql.Row
import org.apache.spark.sql.expressions.{MutableAggregationBuffer, UserDefinedAggregateFunction}
import org.apache.spark.sql.types._

class CaculateTime extends UserDefinedAggregateFunction {
  // 该方法指定具体输入数据的结构Schema
  override def inputSchema: StructType = StructType(StructField("input", LongType, true)::Nil)
  //在进行聚合操作的时候所要处理的数据的结果的结构Schema
  override def bufferSchema: StructType = StructType(StructField("sum", LongType, true)
    :: StructField("last", LongType, true)
    :: StructField("break_count", IntegerType, true) :: Nil)
  //指定UDAF函数计算后返回的结果类型
  override def dataType: DataType = StringType
  // 确保一致性 一般用true
  override def deterministic: Boolean = true
  //在Aggregate之前每组数据的初始化结果
  override def initialize(buffer: MutableAggregationBuffer): Unit = {
    buffer(0) = 0l
    buffer(1) = 0l
    buffer(2) = 0
  }
  // 在进行聚合的时候，每当有新的值进来，对分组后的聚合如何进行计算
  // 本地的聚合操作，相当于Hadoop MapReduce模型中的Combiner
  override def update(buffer: MutableAggregationBuffer, input: Row): Unit = {
    println(s"++++++++$input, [$buffer]")
    val next = input.getAs[Long](0)
    val result = next - buffer.getAs[Long](1)
    if(result < 300 ) {
      buffer(0) = buffer.getAs[Long](0) + result
    }
    buffer(2) = buffer.getAs[Int](2) + 1
    buffer(1) = next
  }
  //最后在分布式节点进行Local Reduce完成后需要进行全局级别的Merge操作
  override def merge(buffer1: MutableAggregationBuffer, buffer2: Row): Unit = {
    buffer1(0) = buffer1.getAs[Long](0) + buffer2.getAs[Long](0)
    buffer1(2) = buffer1.getAs[Int](2) + buffer2.getAs[Int](2)
  }
  //返回UDAF最后的计算结果
  override def evaluate(buffer: Row): Any = buffer.getAs[Long](0) +"#"+ buffer.getAs[Int](2)
}