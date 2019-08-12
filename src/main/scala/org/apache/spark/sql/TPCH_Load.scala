package org.apache.spark.sql

import scala.util.control.Breaks

import org.apache.spark.SparkConf
/**
 * Created by Administrator on 3/27/2018.
 */
object TPCH_Load {
  def main(args: Array[String]): Unit = {
    val conf=new SparkConf().setMaster("yarn-client").setAppName("TPCH")

   // val cc = SparkSession.builder().config(conf).getOrCreateCarbonSession()
   val cc = SparkSession.builder().config(conf).getOrCreate()

    try{
      println("start");
      //cc.sql("create database if not exists raghu")
      //cc.sql("drop table if exists raghu.LINEITEM")
      //cc.sql("create table if not exists raghu.LINEITEM(  L_SHIPDATE date,  L_SHIPMODE string,  L_SHIPINSTRUCT string,  L_RETURNFLAG string,  L_RECEIPTDATE date,  L_ORDERKEY INT ,  L_PARTKEY INT ,  L_SUPPKEY   string,  L_LINENUMBER int,  L_QUANTITY double,  L_EXTENDEDPRICE double,  L_DISCOUNT double,  L_TAX double,  L_LINESTATUS string,  L_COMMITDATE date,  L_COMMENT  string) STORED BY 'org.apache.carbondata.format'TBLPROPERTIES ('table_blocksize'='64') ")
    } catch {
      case e: Exception => {
        e.printStackTrace()
        println("Just Ignore")
      }

    }

    val scanner = new java.util.Scanner(System.in)
    while(true){
      val input = scanner.nextLine()
      if(input.equalsIgnoreCase("break")){
        Breaks
      }
      print(s"SQl Qeuery is  $input")
      try{
        cc.sql(input).show(100,false)

      }catch {
        case e: Exception => {
          e.printStackTrace()
          println("Just Ignore")
        }

      }
    }
  }


}
