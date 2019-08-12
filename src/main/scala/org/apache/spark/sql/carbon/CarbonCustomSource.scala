package org.apache.spark.sql.carbon

import scala.reflect.ClassTag

import org.apache.spark.{Partition, SparkConf, TaskContext}
import org.apache.spark.rdd.RDD
import org.apache.spark.sql.{Row, SQLContext, SparkSession}
import org.apache.spark.sql.catalyst.expressions.GenericRow
import org.apache.spark.sql.sources.{BaseRelation, RelationProvider, TableScan}
import org.apache.spark.sql.types.{StringType, StructField, StructType}
import org.apache.spark.util.TaskCompletionListener


class CarbonCustomSource extends RelationProvider {
  override def createRelation(sqlContext: SQLContext,
      parameters: Map[String, String]): BaseRelation = {
    {
      new CarbonCustomBaseRelation(sqlContext.sparkSession, parameters)
    }
  }
}

  class CarbonCustomBaseRelation(spark: SparkSession, parameters: Map[String, String]) extends
    BaseRelation with TableScan {
    override def sqlContext: SQLContext = spark.sqlContext

    override def schema: StructType = {
      val columns = Integer.parseInt(parameters.getOrElse("columns", 1).asInstanceOf[String])
      val sF = (0 until columns).map { i =>
        new StructField("c" + i, StringType, true)
      }.toSeq
      StructType(sF)
    }

    override def buildScan(): RDD[Row] = {
      val columns = Integer.parseInt(parameters.getOrElse("columns", 1).asInstanceOf[String])
      val parts = Integer.parseInt(parameters.getOrElse("parts", 1).asInstanceOf[String])
      val eachParData = Integer
        .parseInt(parameters.getOrElse("eachParData", 1).asInstanceOf[String])
      new CarbonCustomSourceRDD[Row](spark, columns, parts, eachParData);
    }
  }


  class CarbonCustomSourceRDD[T: ClassTag](@transient val spark: SparkSession, colums: Int,
      parts: Int, eachParData: Int) extends RDD[T](spark
    .sparkContext, Nil) {
    override def compute(split: Partition,
        context: TaskContext): Iterator[T] = {

      var counter = 0;
      var time = 0L
      val startTime=System.currentTimeMillis();
      context.addTaskCompletionListener{
        new TaskCompletionListener{
          override def onTaskCompletion(context: TaskContext): Unit = {
            println("-------------total Time is : "+context.taskAttemptId() +" :" +(time/1000/1000))
            logInfo("-------------total Time is : "+context.taskAttemptId() +":" +(time/1000/1000))
          }
        }
      }
      val myiterator = new Iterator[Any] {
        override def hasNext: Boolean = {
          counter < eachParData
        }

        override def next(): Any = {
          val t1 = System.nanoTime()
          counter = counter + 1
          var values: Array[Any] = new Array[Any](colums)
          (0 until colums).map{i=>
            values(i) = "babu"
          }
          val row = new GenericRow(values)
          time += (System.nanoTime() - t1)
          row
        }
      }
      myiterator.asInstanceOf[Iterator[T]]
    }

    override protected def getPartitions: Array[Partition] = {
      var i = 0;
      var arra: Seq[MyPerFilePartitioner] = Seq.empty
      while (i < parts) {
        arra = arra :+ new MyPerFilePartitioner(i)
        i = i + 1
      }
      arra.toArray
    }

    class MyPerFilePartitioner(i: Int) extends Partition {
      override def index: Int = i;

    }

  }



object TestCarbonCustomSource{

  def main(args: Array[String]): Unit = {
    val spark = getOrSetSparkSession()
    spark.sql("drop table if exists babu2")
    spark.sql("create table babu2 using org.apache.spark.sql.carbon.CarbonCustomSource options('columns'='4' ,'parts'='2','eachParData'='3')")
    spark.sql("show tables").show(200,false)
    spark.sql("select * from babu2").show(100,false)
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


