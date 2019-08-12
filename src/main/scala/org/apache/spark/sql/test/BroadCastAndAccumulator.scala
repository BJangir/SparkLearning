package org.apache.spark.sql.test

import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.rdds.{CustomRDD, Emp}
import org.apache.spark.sql.util.SparkSessionTestUtil

object BroadCastAndAccumulator extends SparkSessionTestUtil{



  def main(args: Array[String]): Unit = {
    val spark=instance()
    //broadCastTest(spark)
    accumulator(spark)
    while(true){
      Thread.sleep(2*1000)
    }
    destroyIndance()

  }
  def accumulator(spark:SparkSession):Unit={
    val emp=Emp("x",20)
    val listAcc= spark.sparkContext.collectionAccumulator[Emp]("name")
    val longac=spark.sparkContext.longAccumulator("Long")
    val customRDD=new CustomRDD[Emp](4,spark.sparkContext);
    val newrdd=customRDD.mapPartitionsWithIndex((index,itr)=>{
      var rr:List[Emp]=List.empty
       while(itr.hasNext){
        val emp=itr.next()
         listAcc.add(emp)
        rr=rr :+emp
      }
      rr.toIterator
    })
    newrdd.collect().foreach(println(_))
    println("Acc vaue s"+listAcc.value)
  }

  def broadCastTest(spark: SparkSession): Unit = {
    val myvalue = spark.sparkContext.broadcast(Emp("lalu",23))
    val customRDD=new CustomRDD[Emp](4,spark.sparkContext,Some(myvalue),Some(2));
    customRDD.collect().foreach(println(_))
  }

}
