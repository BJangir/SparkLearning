package org.apache.spark.sql

import org.apache.spark.sql.SparkSession.builder
import org.apache.spark.sql.internal.SQLConf
import org.scalatest.{BeforeAndAfterAll, FunSuite}

/**
 * Created by Administrator on 11/20/2017.
 */
class SparkTestAllStrategy extends FunSuite with BeforeAndAfterAll{

  var spark:SparkSession=_

  override def beforeAll(){
    spark=builder().master("local").appName("Test").enableHiveSupport().getOrCreate()
    spark.sql("show tables").show()
    spark.sql("drop table if exists nation")
    spark.sql("drop table if exists region")
    spark.sql("drop table if exists part_naation")
    spark.sql("drop table if exists part_region")

    spark.sql("create table if not exists  nation(n_nationname String,n_nationkey int,n_regionkey int,n_comment String) using csv options('path' 'D:/data/spark/nation.csv')")
    spark.sql("create table if not exists  region(r_regionname string,r_regionkey int,r_comment String) using csv options('path' 'D:/data/spark/region.csv')")
    spark.sql("create table if not exists  part_naation using parquet as select * from nation")
    spark.sql("create table if not exists  part_region using parquet as select * from region")

  }

  override def afterAll(){
    spark.close();
  }


  test("CollectLimit"){
    spark.sql("explain  select * from region limit 10").show(false)
  }


  test("TakeOrderedAndProject"){
    spark.range(10).toDF("id").orderBy("id").limit(10).explain
  }

  test("Gobal limit "){
    spark.range(10).toDF("id").limit(10).orderBy("id").explain()
  }


  test("BroadcastExchange and BroadcastHashJoin "){
    spark.range(10).join(spark.range(20),"id").explain
  }

  test("Sort MergeJoin "){
    spark.range(10000000).join(spark.range(2000000).sort("id"),"id").explain
  }

  test("LeftsemiJoin "){
    spark.sql("SELECT * FROM nation LEFT SEMI JOIN region ON nation.n_nationkey = region.n_regionkey")
  }
  test("innerjoin "){
    spark.sql("SELECT * FROM nation  JOIN region ON nation.n_nationkey = region.n_regionkey and  nation.n_nationname='BRAZIL' ")
  }
  test("LEFTJoin "){
    withConfSupport(SQLConf.CROSS_JOINS_ENABLED.key,"true"){
      spark.sql("SELECT * FROM nation  LEFT JOIN region  ")
    }

  }
  test("RightJoin "){
    spark.sql("SELECT * FROM nation  right JOIN region ON nation.n_nationkey = region.n_regionkey ")
  }
  test("LEFTJoin "){
    withConfSupport(SQLConf.CROSS_JOINS_ENABLED.key,"true"){
      spark.sql("SELECT * FROM nation  FULL OUTER JOIN  region  ")
    }

  }
  test("LEFTJoin with filter"){
    withConfSupport(SQLConf.CROSS_JOINS_ENABLED.key,"true"){
      spark.sql("SELECT * FROM nation  LEFT JOIN region where nation.n_nationkey = region.n_regionkey ")
    }

  }
  test("RIGHT JOIN with fiter  "){
    withConfSupport(SQLConf.CROSS_JOINS_ENABLED.key,"true"){
      spark.sql("SELECT * FROM nation  Right JOIN region where nation.n_nationkey = region.n_regionkey ")
    }

  }

  test("BroadcastNestedLoopJoinExec"){
    withConfSupport(SQLConf.CROSS_JOINS_ENABLED.key,"true"){
      spark.sql("SELECT * FROM nation  FULL OUTER JOIN  region  where nation.n_nationkey = region.n_regionkey ")
    }

  }

  test("CartesianProductExec "){
    withConfSupport(SQLConf.CROSS_JOINS_ENABLED.key,"true"){
      spark.sql("SELECT * FROM nation   JOIN  region WHERE nation.n_nationkey = region.n_regionkey ")
    }

  }

  test("CartesianProductExec withouterjoin "){
    withConfSupport(SQLConf.CROSS_JOINS_ENABLED.key,"true"){
      spark.sql("SELECT * FROM nation  FULL OUTER JOIN  region  ")
    }

  }






  def withConfSupport(key:String,value:String)(f: =>Unit): Unit ={
   val currentvalue= spark.conf.get(key)
    spark.conf.set(key,value)
    f
    spark.conf.set(key,currentvalue)
  }
}
