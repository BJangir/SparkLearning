package org.apache.spark.sql.carbon

import java.io.{BufferedInputStream, BufferedOutputStream, FileInputStream, FileOutputStream}

import scala.reflect.ClassTag

import org.apache.hadoop.conf.Configuration
import org.apache.hadoop.fs.{FileSystem, Path}
import org.apache.spark.{Partition, SparkConf, TaskContext}
import org.apache.spark.rdd.RDD
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.hive.thriftserver.HiveThriftServer2

class CarbonReadRDD[T :ClassTag] (@transient val spark:SparkSession,@transient val inputPath:String) extends RDD[T](spark.sparkContext,Nil){

  override def compute(split: Partition,
      context: TaskContext): Iterator[T] = {
    var time:Long=0;
    var fileSize:Long=0;
    if(split.isInstanceOf[MyPerFilePartitioner]){
      val file=new Path(split.asInstanceOf[MyPerFilePartitioner].files)
      val conf=new Configuration()
      val fs=file.getFileSystem(conf);
      val filests=fs.getFileStatus(file);
      fileSize=filests.getLen
      var os:BufferedInputStream=null
      val start=System.currentTimeMillis()
      try{
        val  is = fs.open(file);
        os = new BufferedInputStream(is)
        val readSize=1024;
        val loop=fileSize/readSize.toInt
        val remain=fileSize%readSize
        var readByte=new Array[Byte](readSize.toInt);
        for(i<-0 to loop.toInt-1){
          os.read(readByte,i.toInt*readSize,readSize)
        }
        if(remain!=0){
          var remaionByte=new Array[Byte](remain.toInt);
          os.read(remaionByte, loop.toInt*readSize,remain.toInt)
          //anynonZero
          var matched=false
          for(i<-0 to remaionByte.length-1){
            if(!matched){
              if(remaionByte(i)!=0){
                println("Sometime not zero"+remaionByte(i))
                matched=true
              }
            }
          }
        }
      }
       catch {
         case e:Exception=>{
           e.printStackTrace()
         }
           Seq("FAIL,"+fileSize+","+time).toIterator.asInstanceOf[Iterator[T]]
       }
      finally {
        if(null!=os){
          os.close()
        }
      }
      val end=System.currentTimeMillis()
      time= (end-start);
    }
    Seq("Pass,"+fileSize+","+time).toIterator.asInstanceOf[Iterator[T]]

  }

  override protected def getPartitions: Array[Partition] = {
    val conf=spark.sparkContext.hadoopConfiguration
    val path=new Path(inputPath)
    val fs=path.getFileSystem(conf);
    val listfiles=fs.listFiles(path,true);
    var i=0;
    var arra :Seq[MyPerFilePartitioner]=Seq.empty
      while (listfiles.hasNext){
        val file=listfiles.next();
        val f=file.getPath.toUri.toString
        if(f.endsWith("carbondata")){
          arra= arra :+ new MyPerFilePartitioner(f,i)
          i = i+1
        }
    }
    arra.toArray
  }

  class MyPerFilePartitioner(file:String,i:Int) extends Partition{
    override def index: Int = i;
    val files:String=file

  }
}

object TestCarbonReader {

  def main(args: Array[String]): Unit = {
   val spark= getOrSetSparkSession()
    val path="D:/data/select/t1/testfact";
    val rdd1=new CarbonReadRDD[String](spark,path);
   val result= rdd1.collect()
    for(s<-result){
      println(s)
    }

  }

  def getOrSetSparkSession():SparkSession ={
    val sparkConf=new SparkConf(true);
    SparkSession
      .builder()
      .master("local")
      .config(sparkConf)
      .appName("SparkCaronQueryTest")
      .enableHiveSupport().getOrCreate()
  }

}
