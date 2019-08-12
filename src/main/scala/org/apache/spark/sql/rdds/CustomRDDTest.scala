package org.apache.spark.sql.rdds

import org.apache.spark.Partitioner
import org.apache.spark.sql.SparkSession

object CustomRDDTest {

  def main(args: Array[String]): Unit = {
    var master="local"
    if(args.length>1){
      master=args(0)
    }
    println("--------------------Master is "+master)
    val spark=SparkSession.builder().master(master).getOrCreate()
    spark.sparkContext.setLogLevel("INFO")
    val customRDD=new CustomRDD[Emp](4,spark.sparkContext)
    customRDD.collect().foreach(println(_))
   /* val parRDD=customRDD.map(x=>(x.name,x.age))
    //val newRdd=parRDD.partitionBy(new MyPartitioner())
    val ss=parRDD.groupByKey()

      val gg=ss.mapValues(x=>{
      var sum=0;
      for(i<-x){
        sum=sum+i
      }
      sum
    })*/

    //val newRdd=parRDD.repartitionAndSortWithinPartitions(new MyPartitioner())

    //newRdd.saveAsTextFile("D:/data/mydd/s1")
    //newRdd.collect().foreach(println(_))
    //customRDD.collect().foreach(println(_))


  }
  class MyPartitioner extends Partitioner{
    override def numPartitions: Int = 5

    override def getPartition(key: Any): Int = key.asInstanceOf[String].charAt(0)%5

  }
}
