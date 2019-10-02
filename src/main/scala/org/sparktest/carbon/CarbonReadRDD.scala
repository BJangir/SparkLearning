package org.sparktest.carbon

import java.io.{BufferedInputStream, FileReader}
import java.util.Properties

import scala.reflect.ClassTag

import org.apache.hadoop.conf.Configuration
import org.apache.hadoop.fs.Path
import org.apache.spark.rdd.RDD
import org.apache.spark.sql.SparkSession
import org.apache.spark.{Partition, SparkConf, TaskContext}

import org.apache.carbondata.core.datastore.impl.FileFactory

class CarbonReadRDD[T :ClassTag] (@transient val spark:SparkSession,@transient val inputPath:String
    ,ak:String,sk:String,endpoint:String,properties:java.util.Properties) extends RDD[T](spark.sparkContext,Nil){

  override def compute(split: Partition,
      context: TaskContext): Iterator[T] = {
    var time:Long=0;
    var fileSize:Long=0;
    if(split.isInstanceOf[MyPerFilePartitioner]){
      val fname=FileFactory.getUpdatedFilePath(split.asInstanceOf[MyPerFilePartitioner].files)
      val file=new Path(fname)
      val conf=new Configuration()
      if (fname.trim.startsWith("s3a")) {
        println("------Setting AK and SK in Executor-----")
        conf.set("fs.s3a.access.key", ak)
        conf.set("fs.s3a.secret.key", sk)
        conf.set("fs.s3a.endpoint", endpoint)
      }
      val fs=file.getFileSystem(conf);
      var filests=fs.getFileStatus(file);
      fileSize=filests.getLen
      val startTime=System.currentTimeMillis()
      if (properties.getProperty("readType", "seq").equalsIgnoreCase("random")) {
        val fileSizeRemaing= fileSize *0.97;
        val footerOffset = fileSizeRemaing.toInt
        val footerLen = fileSize - footerOffset
        val blockletSize = Integer.parseInt(properties.getProperty("blocklets", "1"))
        val blockletsEachSize = (footerOffset /blockletSize).intValue()
        var range :Array[Int] = new Array[Int](blockletSize + 1);
        var runningSize = 0
        range = range.zipWithIndex.map{ s =>
          if (s._2 == 0) {
            runningSize
          } else {
            runningSize = runningSize + blockletsEachSize
          }
          runningSize
        }
        range(range.length - 1) = footerOffset

        val reader = FileFactory.getFileHolder(FileFactory.getFileType(fname), conf)
        reader.readByteArray(fname, footerOffset, footerLen.toInt)

        var i = 1
        var curLen = if (range.length > 1) {
          range(i)
        } else {
          fileSizeRemaing.toInt
        }
        range.foreach{ r =>
          if (i != range.length) {
            reader.readByteArray(fname, r, curLen)
            i = i + 1
            if (i < range.length) {
              curLen = range(i) - range(i - 1)
            }
          }
        }
        reader.finish()
        time=System.currentTimeMillis() - startTime
      }
      else{
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


    }
    Seq("Pass,"+fileSize+","+time).toIterator.asInstanceOf[Iterator[T]]

  }

  override protected def getPartitions: Array[Partition] = {
    val conf=spark.sparkContext.hadoopConfiguration
    if (inputPath.trim.startsWith("s3a")) {
      println("------Setting AK and SK in Partition-----")
      conf.set("fs.s3a.access.key", ak)
      conf.set("fs.s3a.secret.key", sk)
      conf.set("fs.s3a.endpoint", endpoint)
    }

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
    var path = "D:/data/select/t1/testfact";
    var configPath = "D:/code/leo/SparkCarbonTest/config_spark.properties";
    val ak="NQBSPB5EOCTEWIHB5ACR"
    val sk="EZEfjZchwaWc3WGOaUGCMiPZkyCfJUejXbZ1Hrwr"
    val endpoint="obs.cn-east-2.myhuaweicloud.com"
    var properties=new Properties();
    if (args.length > 0) {
      path = args(0)
      if (path.trim.startsWith("s3a")) {
        println("------Setting AK and SK-----")
        spark.conf.set("spark.hadoop.fs.s3a.access.key", ak)
        spark.conf.set("spark.hadoop.fs.s3a.secret.key", sk)
        spark.conf.set("spark.hadoop.fs.s3a.endpoint", endpoint)

        spark.conf.set("fs.s3a.access.key", ak)
        spark.conf.set("fs.s3a.secret.key", sk)
        spark.conf.set("fs.s3a.endpoint", endpoint)
      }
      configPath = args(1)
    }
    properties=new java.util.Properties()
    properties.load(new FileReader(configPath))
    val rdd1=new CarbonReadRDD[String](spark,path,ak,sk,endpoint,properties);
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
