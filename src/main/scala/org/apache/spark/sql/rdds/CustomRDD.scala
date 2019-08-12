package org.apache.spark.sql.rdds

import scala.reflect.ClassTag

import org.apache.spark.broadcast.Broadcast
import org.apache.spark.rdd.RDD
import org.apache.spark.{Partition, SparkContext, TaskContext}


class CustomRDD[T :ClassTag]( @transient counter:Int,
    @transient sparkContext: SparkContext,bb:Option[Broadcast[Emp]]=None,x:Option[Int]=None)
  extends  RDD[T] (sparkContext,Nil){


  override protected def getPartitions: Array[Partition] = {
    (0 until counter).map(x=>{
      new MyPart(x)
    }).toArray
  }
  override def compute(split: Partition,
      context: TaskContext): Iterator[T] = {
    var coun=counter;
    if(bb.isDefined){
      val em=bb.get.value
      println("In Compute "+em.name +" age is "+em.age)
    }
    if(x.isDefined){
      println("X is " +x.get)
    }
    val rand=new java.util.Random()
    val ff=new Iterator[T] {
      override def hasNext: Boolean = coun>0

      override def next(): T = {
        coun=coun-1
         Emp("babu"+coun,rand.nextInt(100)).asInstanceOf[T]
      }
    }
    ff
  }
}


class MyPart(i:Int) extends Partition {
  override def index: Int = i
}

case class Emp(name:String,age:Int)


