package org.apache.spark.sql.test

import org.apache.hadoop.conf.Configuration
import org.apache.hadoop.fs.FileStatus
import org.apache.hadoop.mapreduce._
import org.apache.hadoop.mapreduce.task.TaskAttemptContextImpl
import org.apache.spark.SparkConf
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.carbon.{CustomReader, VectorCustomReader}
import org.apache.spark.sql.catalyst.InternalRow
import org.apache.spark.sql.execution.datasources.{FileFormat, OutputWriterFactory, PartitionedFile, RecordReaderIterator}
import org.apache.spark.sql.execution.vectorized.{OffHeapColumnVector, OnHeapColumnVector}
import org.apache.spark.sql.internal.SQLConf
import org.apache.spark.sql.sources.{DataSourceRegister, Filter}
import org.apache.spark.sql.types.{StringType, StructField, StructType}
import org.apache.spark.util.SerializableConfiguration

class CustomFileFormatBatch extends FileFormat with Serializable with DataSourceRegister{
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


  /*override protected def buildReader(sparkSession: SparkSession,
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
  }*/


  override def buildReaderWithPartitionValues(sparkSession: SparkSession,
      dataSchema: StructType,
      partitionSchema: StructType,
      requiredSchema: StructType,
      filters: Seq[Filter],
      options: Map[String, String],
      hadoopConf: Configuration): PartitionedFile => Iterator[InternalRow] = { pfile=>{
    val columns = Integer.parseInt(options.getOrElse("columns", 1).asInstanceOf[String])
    val parts = Integer.parseInt(options.getOrElse("parts", 1).asInstanceOf[String])
    val eachParData = Integer
      .parseInt(options.getOrElse("eachParData", 1).asInstanceOf[String])
    val attemptId = new TaskAttemptID(new TaskID(new JobID(), TaskType.MAP, 0), 0)

    val myit:RecordReader[Void,Object]={
      if(supportBatch(sparkSession,dataSchema)){
        val myvecotre= new VectorCustomReader(requiredSchema,eachParData,pfile,supportBatch(sparkSession,dataSchema))
        myvecotre.initBatch()
        myvecotre
      }
      else{
        new CustomReader(pfile ,requiredSchema.length,requiredSchema)
      }
    }
    val iter = new RecordReaderIterator(myit)
    iter.asInstanceOf[Iterator[InternalRow]]
  }
  }

  /*override protected def buildReader(sparkSession: SparkSession,
      dataSchema: StructType,
      partitionSchema: StructType,
      requiredSchema: StructType,
      filters: Seq[Filter],
      options: Map[String, String],
      hadoopConf: Configuration): PartitionedFile => Iterator[InternalRow] = {
    pfile=>{
      val columns = Integer.parseInt(options.getOrElse("columns", 1).asInstanceOf[String])
      val parts = Integer.parseInt(options.getOrElse("parts", 1).asInstanceOf[String])
      val eachParData = Integer
        .parseInt(options.getOrElse("eachParData", 1).asInstanceOf[String])
      val myvecotre= new VectorCustomReader(requiredSchema,eachParData,pfile,supportBatch(sparkSession,dataSchema))
      myvecotre.initBatch()
      val iter = new RecordReaderIterator(myvecotre)
      iter.asInstanceOf[Iterator[InternalRow]]
    }
  }*/


  override def vectorTypes(requiredSchema: StructType,
      partitionSchema: StructType,
      sqlConf: SQLConf): Option[Seq[String]] = {
    Option(Seq.fill(requiredSchema.fields.length + partitionSchema.fields.length)(
      classOf[OnHeapColumnVector].getName
    ))
  }
  override def supportBatch(sparkSession: SparkSession,
      dataSchema: StructType): Boolean = {
    val conf = sparkSession.sessionState.conf
    false
  }

  override def shortName(): String = "babu"
}



object TestCustomFileFormat{


  def main(args: Array[String]): Unit = {
    val spark = getOrSetSparkSession()
/*    spark.conf.set("spark.sql.codegen.wholeStage",false)
    spark.conf.set("spark.sql.codegen.wholeStage","false")*/
    spark.sql("drop table if exists babu2")
    spark.sql("create table babu2 using org.apache.spark.sql.test.CustomFileFormatBatch options" +
              "('path'='D:/data/uri','columns'='2' ,'parts'='2','eachParData'='3')")
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


