package org.apache.spark.sql

import org.apache.hadoop.conf.Configuration
import org.apache.hadoop.fs.{FileStatus, FileSystem, Path}

/**
 * Created by Administrator on 8/14/2018.
 */
object HadoopTest {

  var action :String="ls";
  var pathString :String="s3a://cstest519/babu/carbondata/5g/orders"
  def main(args: Array[String]): Unit = {
    val conf = new Configuration();
    conf.set("fs.defaultFS","s3a://cstest519")
    conf.set("fs.s3a.access.key", "LGCUKNYSCPHOLEH5UPZP")
    conf.set("fs.s3a.secret.key", "1g7ttzgdelafd1v6teb1qR2iKqRzJpYQuOwl8rgw")
    conf.set("fs.s3a.endpoint", "obs.cn-north-1.myhwclouds.com")
    conf.set("fs.s3a.impl","org.apache.hadoop.fs.s3a.S3AFileSystem")
    val path=new Path("s3a://cstest519/babu/carbondata/usinghive2")
    val fileSystem =path.getFileSystem(conf)
    val length=fileSystem.getContentSummary(path)
    println("Length is :-"+length.getLength)
    fileSystem.close()
    /*if(args.length>0){
      val keyvalue=args(0).split(" ")
      val actionkey=keyvalue(0)
      val pathkey=keyvalue(1)
      action match {
        case "ls" => {
          action="ls"
          pathString=pathString
        }
      }
    }

    // Action
    action match {
      case "ls" => {
        action="ls"
        listFiles(new Path(pathString),fileSystem)

      }
    }*/

    /*val path = new Path("s3a://cstest519/babu");
   // val d=fileSystem.getFileStatus(path)
    val d=fileSystem.listLocatedStatus(path)
     //println("file"+d.getPath.getName)
    //val files=fileSystem.listFiles(path,false);
    while(d.hasNext){
      val f=d.next()
      println("file "+f.getPath.getName)
    }*/
    fileSystem.close()

  }

  def listFiles(path :Path,fs :FileSystem) : Unit = {
    val d=fs.listLocatedStatus(path)
    while(d.hasNext){
      val f=d.next()

        println(f.getPath.toUri + "size is "+fs.getContentSummary(f.getPath))

    }
  }

  def listFilesRecursive(path :Path,fs :FileSystem) : Unit = {
    val d=fs.listLocatedStatus(path)
    while(d.hasNext){
      val f=d.next()
      if(f.isDirectory){
        listFiles(f.getPath,fs)
      }
      else {
        println(f.getPath.toUri)
        return
      }

    }
  }



}
