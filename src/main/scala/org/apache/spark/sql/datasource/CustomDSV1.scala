package org.apache.spark.sql.datasource

import java.util.Random

import org.apache.spark.{Partition, TaskContext}
import org.apache.spark.rdd.RDD
import org.apache.spark.sql.{Row, SQLContext, SparkSession}
import org.apache.spark.sql.sources.{BaseRelation, RelationProvider, TableScan}
import org.apache.spark.sql.types.{IntegerType, StringType, StructField, StructType}

class CustomDSV1  extends RelationProvider{
  override def createRelation(sqlContext: SQLContext,
      parameters: Map[String, String]): BaseRelation = {
    new MyJDBCRelation(parameters("url"),sqlContext.sparkSession)

  }



}

class MyJDBCRelation (url:String,spark:SparkSession)extends BaseRelation  with TableScan{

  override def schema: StructType = {
    StructType(Seq(new StructField("name",StringType,true),
      new StructField("age",IntegerType,true)));
  }

  override def sizeInBytes: Long = {
    val random=new Random()
    val intV=1+random.nextInt(100);
    println("Calculating Size: "+intV)
    intV
  }
  override def buildScan(): RDD[Row] = {
    new MyData(spark)
  }

  override def sqlContext: SQLContext =spark.sqlContext

}

class MyData(sc:SparkSession) extends RDD[Row](sc.sparkContext,Nil) {
  override def compute(split: Partition,
      context: TaskContext): Iterator[Row] = {
    var counter=4;
     val itr=new Iterator[Row] {
       override def hasNext: Boolean = counter>0

       override def next(): Row = {
         counter=counter-1;
         Row("babu"+split.index,counter)
       }
     }
    itr
  }

  override protected def getPartitions: Array[Partition] = {
    Seq(new MyPart(0),new MyPart(1)).toArray
  }
}

class MyPart(id:Int) extends Partition{
  override def index: Int = id
}
