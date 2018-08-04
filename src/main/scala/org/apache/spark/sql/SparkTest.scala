package org.apache.spark.sql

import org.apache.spark.rdd.RDD
import org.apache.spark.sql.types.{DataTypes, StructField, StructType}
  
/**
 * Created by Administrator on 12/17/2017.
 */
object SparkTest {
  var spark:SparkSession=null



  def main(args: Array[String]): Unit = {
    /* spark = SparkSession.builder().master("local[1]").enableHiveSupport().getOrCreate()
    val df=spark.range(10).filter("id >10").orderBy("id").agg("id" -> "sum")
    val df2=spark.range(10).as("babu").filter("id >10").orderBy("id").agg("id" -> "sum")
    val re=df.queryExecution.logical.sameResult( df2.queryExecution.logical)
    println(re)*/
    /*//val rdd4=rdd3.count()
    println(rdd2.explain(true))

    println(rdd2.rdd.toDebugString)
    println(rdd2.rdd.getStorageLevel)
    println(rdd2.rdd.toString())
*/
   // testReparitionAndCoalesce()
    //testCreateDataFrmae()
   /* val result = rdd1.coalesce(2,true)
    println("No. of parition after:- "+result.getNumPartitions)
    result.foreach(println)*/
    spark.stop()

  }















  /**
   * Ori :- jan-> 0
feb-> 0
mar-> 1
april-> 1
may-> 2
jun-> 2

   Coalesce(2,true) and Reparition(2) is same
   jan-> 0
mar-> 0
may-> 0
feb-> 1
april-> 1
jun-> 1

   Coalesce(2,false)
   jan-> 0
mar-> 0
may-> 0
feb-> 1
april-> 1
jun-> 1
   *
   */
  def testReparitionAndCoalesce(): Unit ={
    val rdd1 = spark.sparkContext.parallelize(List("jan","feb","mar","april","may","jun"),3)
    printrddparition(rdd1)
    var result = rdd1.coalesce(2,true)
    printrddparition(result)
    result = rdd1.coalesce(2,false)
    printrddparition(result)
    result=rdd1.repartition(2)
    printrddparition(result)


  }

  def printrddparition(rdd :RDD[String]) = {
    println("No. of parition :- "+rdd.getNumPartitions)
    val newrdd = rdd.mapPartitionsWithIndex(
      (index, iterator) => {
        println("Called in Partition -> " + index)
        val mlist = iterator.toList
        mlist.map(x => x + "-> " + index).toIterator
      });
    newrdd.collect().foreach(x => println(x))
  }


  def testCreateDataFrmae() : Unit ={
    val rdd=spark.sparkContext.parallelize(1 to 10,3).map(x => Row(x,"city_"+x))
    //val listodscheam=List( StructField("id",DataTypes.IntegerType,false),StructField("cityid",DataTypes.StringType,false))
    val schema= new StructType().add(StructField("id",DataTypes.IntegerType,false)).add(StructField("cityid",DataTypes.StringType,false))
   val df= spark.createDataFrame(rdd,schema)
    df.show()

  }


}
