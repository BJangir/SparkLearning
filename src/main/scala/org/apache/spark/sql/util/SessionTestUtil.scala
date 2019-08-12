package org.apache.spark.sql.util

import org.apache.spark.SparkConf
import org.apache.spark.sql.SparkSession

class SparkSessionTestUtil {
  var spark:SparkSession=null;
  def instance():SparkSession={
    if(spark==null){
      val conf=new SparkConf(true).setMaster("local").setAppName("Test")
          spark= SparkSession.builder().enableHiveSupport().config(conf).getOrCreate();
    }
    spark
  }

  def destroyIndance(): Unit ={
    spark.stop()
  }


}
