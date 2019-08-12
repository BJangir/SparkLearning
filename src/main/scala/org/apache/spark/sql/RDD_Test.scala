package org.apache.spark.sql

import org.apache.hadoop.io.Text
import org.apache.hadoop.mapreduce.lib.input.KeyValueTextInputFormat
import org.apache.spark.SparkConf

/**
 * Created by Administrator on 8/6/2018.
 */
object RDD_Test {
  var spark :SparkSession=null;



  def main(args: Array[String]): Unit = {
    val conf=new SparkConf(true).setAppName("Test");
   spark=SparkSession.builder().config(conf).master("local").enableHiveSupport().getOrCreate();

     val rDD= spark.sparkContext.parallelize(Seq((1,"A"),(6,"B"),(2,"A")))
    val allsort=rDD.sortByKey()
    allsort.foreach( println)
    // RDD

  spark.stop()
  }

  /*def myRDD() = {
    val input = spark.sparkContext.newAPIHadoopFile("D:/data/keyvalue_data/keyvalue.txt",classOf[KeyValueTextInputFormat],classOf[Text],classOf[Text])
    println("Total Parttition"+input.getNumPartitions)
    input.map{ case (key, value) => (key.toString, value.copyBytes) }.collect().foreach(println)

  }*/


}
