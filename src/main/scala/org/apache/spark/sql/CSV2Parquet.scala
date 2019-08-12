package org.apache.spark.sql

import java.text.SimpleDateFormat
import java.util
import java.util.Date

import scala.io.Source

import org.apache.spark.sql.types._

/**
 * Created by Administrator on 8/16/2018.
 */
object CSV2Parquet {
  import scala.collection.JavaConverters._
  var spark: SparkSession = _

  def main(args: Array[String]): Unit = {
     spark = SparkSession.builder().master("local[1]").enableHiveSupport().getOrCreate()
    //import spark.sqlContext.implicits._

    //spark.sql("CREATE TABLE  orders_csv ('o_orderkey String,o_custkey String,o_orderstatus String,o_totalprice String,o_orderdate String,o_orderpriority String,o_clerk String,o_shippriority String,o_comment String) using parquet ")
    //val pRdd=spark.read.textFile("D:/tpch_data/tpch/1gb/orders.tbl")
    val pRdd=spark.read.textFile("D:/tpch_data/tpch/1gb/orders_10records.tbl")
    val totalCounter=pRdd.count()
    var counter=0;
    val BUFF_LINE=5;
    val filename = "D:/tpch_data/tpch/1gb/orders_10records.tbl"
    val temLines =new java.util.ArrayList[String]()
    val schema = new StructType()
      .add(StructField("O_ORDERKEY", IntegerType, true))
      .add(StructField("O_CUSTKEY", StringType, true))
      .add(StructField("O_ORDERSTATUS",StringType , true))
      .add(StructField("O_TOTALPRICE", DoubleType))
      .add(StructField("O_ORDERDATE", DateType))
      .add(StructField("O_ORDERPRIORITY", StringType))
      .add(StructField("O_CLERK", StringType))
      .add(StructField("O_SHIPPRIORITY", IntegerType))
      .add(StructField("O_COMMENT", StringType))


    for (line <- Source.fromFile(filename).getLines) {
      counter += 1;
      temLines.add(line)
      if (counter == BUFF_LINE) {
        /*val rddd=spark.sparkContext.parallelize(temLines.asScala)
        println("--all--"+rddd.count())*/
        generateParquet(temLines,schema)
        counter = 0;
        temLines.clear();
      }
    }

  if(!temLines.isEmpty){
   // val rddd=spark.sparkContext.parallelize(temLines.asScala)
    generateParquet(temLines,schema)
   // println("--all--"+rddd.count())
    temLines.clear();
  }


   /* val schema = new StructType()
      .add(StructField("O_ORDERKEY", IntegerType, true))
      .add(StructField("O_CUSTKEY", StringType, true))
      .add(StructField("O_ORDERSTATUS",StringType , true))
      .add(StructField("O_TOTALPRICE", DoubleType))
      .add(StructField("O_ORDERDATE", DateType))
      .add(StructField("O_ORDERPRIORITY", StringType))
      .add(StructField("O_CLERK", StringType))
      .add(StructField("O_SHIPPRIORITY", IntegerType))
      .add(StructField("O_COMMENT", StringType))
*/    //val temLines =new java.util.ArrayList[String]()

    /*pRdd.foreach { line =>
      if (counter == BUFF_LINE) {
        //generateParquet(temLines, schema);
        /*val rddd=spark.sparkContext.parallelize(temLines.asScala)
        println("--all--"+rddd.count())*/
        counter = 0;
        temLines.clear();
      }
      else {
        counter += 1;
        temLines.add(line)
      }
    }*/

    spark.stop()
  }

  def generateParquet(temLines :java.util.ArrayList[String], schema :StructType): Unit ={
    val temRDD=spark.sparkContext.parallelize(temLines.asScala)

    val allschema=schema.mkString(",").split(",")
    val name_dtypeMap :java.util.Map[String,DataType]= new java.util.HashMap[String,DataType]

    schema.foreach{ e=>
      name_dtypeMap.put(e.name,e.dataType)
    }

    val rowRDD=temRDD.map { temLine =>
      val rec=temLine.split("|")
      var builder=new StringBuilder
      var rowData :String=""
      val keys=name_dtypeMap.keySet().iterator()
      var index=0;
       while(keys.hasNext){
         val value=rec(index)
         val dtype=name_dtypeMap.get(keys.next())
         dtype match {
           case StringType =>builder.append("\"").append(value).append("\"").append(",")
           case IntegerType =>builder.append(value).append(",")
           case DoubleType =>builder.append(value).append(",")
           case DateType =>builder.append("\"").append(value).append("\"").append(",")
         }
         index=index+1;
          rowData=builder.toString()
          rowData.substring(0,rowData.length-1)

       }
      Row(rowData)
    }
    val df=spark.createDataFrame(rowRDD,schema);
    df.write.format("parquet").save("D:/data/myparquetnew/order")
    println("ii")
    //df.count()
  }
}
