package org.apache.spark.sql.carbon

import org.apache.hadoop.conf.Configuration
import org.apache.hadoop.fs.FileStatus
import org.apache.hadoop.mapreduce.Job
import org.apache.spark.SparkConf
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.catalyst.InternalRow
import org.apache.spark.sql.catalyst.expressions.GenericInternalRow
import org.apache.spark.sql.execution.datasources.{FileFormat, OutputWriterFactory, PartitionedFile}
import org.apache.spark.sql.sources.Filter
import org.apache.spark.sql.types.{StringType, StructField, StructType}
import org.apache.spark.unsafe.types.UTF8String

class CustomFileFormat extends FileFormat{
  override def inferSchema(spark: SparkSession,
      options: Map[String, String],
      files: Seq[FileStatus]): Option[StructType] = {

      val columns = Integer.parseInt(options.getOrElse("columns", 1).asInstanceOf[String])
    val sF = (0 until columns).map { i =>
      new StructField("c" + i, StringType, true)
    }.toSeq
    Some(StructType(sF))
  }

  override def prepareWrite(sparkSession: SparkSession,
      job: Job,
      options: Map[String, String],
      dataSchema: StructType): OutputWriterFactory = {
    null
  }


  override protected def buildReader(sparkSession: SparkSession,
      dataSchema: StructType,
      partitionSchema: StructType,
      requiredSchema: StructType,
      filters: Seq[Filter],
      options: Map[String, String],
      hadoopConf: Configuration): PartitionedFile => Iterator[InternalRow] = { pFile =>
    val columns = Integer.parseInt(options.getOrElse("columns", 1).asInstanceOf[String])
    val parts = Integer.parseInt(options.getOrElse("parts", 1).asInstanceOf[String])
    val eachParData = Integer
      .parseInt(options.getOrElse("eachParData", 1).asInstanceOf[String])
    var counter=0
    val myiterator = new Iterator[InternalRow] {
      override def hasNext: Boolean = {
        counter < eachParData
      }

      override def next(): InternalRow = {
        counter = counter + 1
        var values: Array[Any] = new Array[Any](columns)
        (0 until columns).map{i=>
          values(i) = UTF8String.fromString("babu")
        }
        new GenericInternalRow(values)
      }
    }
    myiterator
  }
}



object TestCustomFileFormat{


  def main(args: Array[String]): Unit = {
    val spark = getOrSetSparkSession()
    spark.sql("drop table if exists babu2")
    spark.sql("create table babu2 using org.apache.spark.sql.carbon.CustomFileFormat options('path'='D:/data/uri','columns'='4' ,'parts'='2','eachParData'='3')")
    spark.sql("show tables").show(200,false)
    spark.sql("select * from babu2").show(100,false)

    while (true){
      Thread.sleep(2*1000)
    }
    spark.stop()

  }
  def getOrSetSparkSession():SparkSession ={
    val sparkConf=new SparkConf(true);
    SparkSession
      .builder()
      .master("local")
      .config(sparkConf)
      .appName("SparkCaronQueryTest")
      .enableHiveSupport().getOrCreate()
  }
}


