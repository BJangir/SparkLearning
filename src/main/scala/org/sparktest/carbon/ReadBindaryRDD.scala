package org.sparktest.carbon

import org.apache.spark.SparkConf
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.hive.thriftserver.HiveThriftServer2

object ReadBinaryRDD {

  def main(args: Array[String]): Unit = {
    val spark=getOrSetSparkSession()
    Thread.sleep(5*1000)
    HiveThriftServer2.startWithContext(spark.sqlContext)

   /* spark.sparkContext.setLogLevel("DEBUG")
    spark.sql("show tables").show(false)*/
  }

  def getOrSetSparkSession():SparkSession ={
   /* val sparkConf = new SparkConf(loadDefaults = true)
    import org.apache.spark.sql.CarbonSession.CarbonBuilder
    SparkSession
      .builder()
      .config(sparkConf)
      .appName("SparkCaronQueryTest")
      .enableHiveSupport().getOrCreateCarbonSession()*/
    null
  }


}
