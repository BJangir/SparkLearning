package org.apache.spark.sql.relation

import org.apache.hadoop.log.LogLevel
import org.apache.spark.RangePartitioner
import org.apache.spark.sql.SparkSession

object PartionTest {

  def main(args: Array[String]): Unit = {

    val spark=SparkSession.builder().enableHiveSupport().master("local").getOrCreate()
    spark.sparkContext.setLogLevel("ERROR")
    val datapart = List(0, 50, 100, 150)
    val rdd = spark.sparkContext.parallelize(datapart).map((_, 1))
    val rangePart = new RangePartitioner(4, rdd)
    val afterpart=rdd.partitionBy(rangePart)
    afterpart.mapPartitionsWithIndex{
      (index :Int,it:Iterator[(Int,Int)]) =>
        println("Index is "+index)
        while(it.hasNext){
          val v=it.next()
          println(v._1+"  --- "+v._2)
        }
        it
    }.collect()

  spark.stop()
  }


}
