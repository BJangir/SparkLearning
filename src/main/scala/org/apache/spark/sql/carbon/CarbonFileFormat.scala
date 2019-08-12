package org.apache.spark.sql.carbon

import org.apache.hadoop.fs.{FileSystem, Path}
import org.apache.spark.sql.SparkSession

/**
 * Created by Administrator on 8/26/2018.
 */
object CarbonFileFormat {


  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder().master("local[1]").enableHiveSupport().getOrCreate()
    import spark.implicits._;
    spark.sql("show tables").show()
    val path1 = new Path("D:/data/carbonFormat/", "second")
    println("------------------------->"+path1.toString)
    /*val rawDF=spark.sparkContext.parallelize(1 to 12,3).map(x => ("c_"+x,x))
    rawDF.toDF("a","b").write.format("carbon").save(path1.toString)*/
    val df=spark.read.format("carbon").load(path1.toString)
    df.printSchema();
    val f=df.select("*")
    f.explain(true)
    f.show(false)
    spark.stop();

  }
}
