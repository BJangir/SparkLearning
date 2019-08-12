package org.apache.spark.sql

import scala.util.control.Breaks


/**
 * Created by Administrator on 3/20/2018.
 */
object QueryTest {



  def main(args: Array[String]): Unit = {/*

    //val cc = SparkSession.builder().master("yarn-client").appName("aaa").getOrCreateCarbonSession("hdfs://master:9000/carbonstore","/opt/babu/metasore_db")
    val cc = SparkSession.builder().master("yarn-client").appName("aaa").getOrCreateCarbonSession()

    cc.sql("select count(*) from  public.c_compact3").show;
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
          println("Just Ignore")
        }

      }
    }

    cc.stop()
*/
  }


}
