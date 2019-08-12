package org.apache.spark.sql

/**
 * Created by Administrator on 3/20/2018.
 */
import scala.util.control.Breaks

object SounakTest_2 {

  def main(args: Array[String]): Unit = {

    //val cc = SparkSession.builder().master("yarn-client").appName("aaa").getOrCreateCarbonSession()
    val cc = SparkSession.builder().master("yarn-client").appName("aaa").getOrCreate()

try{
  println("111111111");
  var randomNmber = 5
  cc.sql("select count(*) from public.c_compact3").show(100,false)
  cc.sql(s"DROP TABLE IF EXISTS cache_compact3").show;
  cc.sql(s"cache table  cache_compact3  as select * from   public.c_compact3  where pmod(cast(id as int),1000)=$randomNmber").show(100, false);
  cc.sql("select count(*) from cache_compact3").show;
  cc.sql("update public.c_compact3 a set (a.id,a.qqnum,a.nick,a.age,a.gender,a.auth,a.qunnum,a.mvcc)=(select b.id,b.qqnum,b.nick,b.age,b.gender,b.auth,b.qunnum,b.mvcc from   cache_compact3 b where b.id=a.id)").show;
  println("222222222");
  Thread.sleep(30000);

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
