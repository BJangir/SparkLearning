package org.apache.spark.sql.test

import org.apache.spark.sql.util.SparkSessionTestUtil

object MapFunctions extends SparkSessionTestUtil {


  def main(args: Array[String]): Unit = {
    instance();
    val rdd=spark.sparkContext.parallelize(1 to 10);

     var rt=rdd.map(mutipler(_)).collect()
    rt.foreach(println(_))
    print("---------------------")
    rt=rdd.mapPartitions(mutiplerIterator(_)).collect()
    rt.foreach(println(_))
    destroyIndance()
  }

  def mutipler(ele :Int) :Int={
    ele * 10
  }

  def mutiplerIterator(ele :Iterator[Int]) :Iterator[Int]={
    val multi=10;
    var result :List[Int]=List()
    while (ele.hasNext){
      val value=ele.next();
      result=result :+ (multi*value)
    }
    result.toIterator
  }



}
