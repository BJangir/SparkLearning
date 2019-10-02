package org.sparktest.carbon

import java.sql.SQLException

import org.apache.spark.SparkConf
import org.apache.spark.sql.{DataFrame, SparkSession}
import org.apache.test.JdbcQueryRun

object SparkCarbonTest {

  var spark:SparkSession=null;
  def main(args: Array[String]): Unit = {
    System.setProperty("conf.path","D:\\redbook\\config.properties")
    val spark:SparkSession=getOrSetSparkSession()
    val jdbc=new JdbcQueryRun
    jdbc.loadConfig()
    val queries=jdbc.loadQueries(null)
    executeQueries(queries,jdbc);
    spark.stop()
  }

  def getOrSetSparkSession():SparkSession ={
    val sparkConf=new SparkConf();
    /*import org.apache.spark.sql.CarbonSession.CarbonBuilder
    SparkSession
      .builder()
      .config(sparkConf)
      .appName("SparkCaronQueryTest")
      .enableHiveSupport().getOrCreateCarbonSession()*/
    null
  }

  def  executeQueries(strings:java.util.List[String] ,jdbc:JdbcQueryRun) {
    val repeat = Integer.parseInt(JdbcQueryRun.properties.getProperty("repeat", "1"));
    val resultWriter = jdbc.getResultWriterWithHeader(repeat);
    try {
      var quid = 0;

      for (p<-0 to strings.size()) {
        resultWriter.append(quid + " ,");
        for (j <- 0 to   repeat) {
          val startTime = System.currentTimeMillis();
          val res = spark.sql(strings.get(p));
          if(j==0){
            processResult(res, true, quid + "");
          }else {
            processResult(res, false, quid + "");
          }
          val endTime = System.currentTimeMillis();
          resultWriter.append((endTime - startTime) + ",");
          resultWriter.append("\n");
          resultWriter.flush();
        }
        quid=quid+1;
      }
    } catch  {
      case e:Exception=>{
        e.printStackTrace()
      }
    }
    finally {
      resultWriter.close();
    }

  }
  def processResult(df:DataFrame,toBeprint:Boolean,qId:String): Unit ={
    val columns=df.columns
    val columnCount=columns.length
    var totalRows = 0;
    if (toBeprint) {
      for (col<-columns) {
        print(col + " , ")
      }
      println();
    }
    df.show()
    val res=df.collect()
    if (toBeprint) {
      for(row <-res){
        val size=row.size;
        for(i<-0 to size){
         print(row.apply(i)+"");
        }
        println();
        totalRows=totalRows+1;;
      }
    } else {
      totalRows=res.length
    }
    println();
    println(qId + "," + totalRows + " records");
    println("===========End===="+qId);
  }

}
