package org.apache.spark.sql.datasource

import org.apache.spark.sql.SparkSession

object CustomDSTest {


  def main(args: Array[String]): Unit = {
    val spark=SparkSession.builder().master("local").getOrCreate()

    test1(spark);
    spark.stop()

  }

  def test1(spark: SparkSession): Unit ={
    val df=spark.read.format("org.apache.spark.sql.datasource.CustomDSV1").option("url","babu").load()
    println(df.queryExecution.stringWithStats)
    df.show(2,false)
    println(df.queryExecution.stringWithStats)
    df.show(2,false)
  }
}
