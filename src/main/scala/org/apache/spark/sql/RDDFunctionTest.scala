package org.apache.spark.sql

import scala.collection
import scala.collection.Seq
import scala.reflect.{ClassTag, classTag}

import org.apache.spark.sql.rdds.{CustomRDD, Emp}

object RDDFunctionTest {

  def main(args: Array[String]): Unit = {

    val spark=SparkSession.builder().master("local").getOrCreate()
    import spark.implicits._
    import org.apache.spark.sql.functions._
    //customsortTest(spark)
    customsortTestWithintPart(spark)
    spark.stop()


    /**
     * https://www.quora.com/What-does-Closure-cleaner-func-mean-in-Spark
     * MapInternals are Closure
     */
  }

  def customsortTest(spark: SparkSession): Unit = {
    val ran = new java.util.Random()
    val rdd1 = spark.sparkContext.parallelize(Array.tabulate[Person](20) { i =>
      new Person("name" + i, ran.nextInt(100))
    }, 3)
    val sortedRdd = rdd1.sortBy(x => x, true, 2)(new CustomOrder, classTag[Person])
    println(sortedRdd.toDebugString)
    val res = sortedRdd.mapPartitionsWithIndexInternal((index, itr) => {
      while (itr.hasNext) {
        val p = itr.next()
        println(index + "," + p)
      }
      "".toIterator
    })
    res.collect()
  }

  def customsortTestWithintPart(spark: SparkSession): Unit = {
    val ran = new java.util.Random()
    import spark.implicits._
    val ds1 = spark.sparkContext.parallelize(Array.tabulate[Person](20) { i =>
      new Person("name" + i, ran.nextInt(100))
    }, 3).toDS
    ds1.sortWithinPartitions("age")
  }


  class CustomOrder[T :ClassTag] extends Ordering[T]{
    override def compare(x: T, y: T): Int = {
      if(x.isInstanceOf[Person]&& y.isInstanceOf[Person]){
        val x1=x.asInstanceOf[Person]
        val x2=y.asInstanceOf[Person]
        x1.age-x2.age
      }
      else
        0
    }
  }

  case class Person(name:String,age :Int)


}
