package org.apache.spark.sql

/**
 * Created by Administrator on 5/12/2018.
 */
import org.apache.spark.mllib.clustering.{KMeans, KMeansModel}
import org.apache.spark.mllib.linalg.Vectors
object SparkMLLearning {

  def main(args: Array[String]): Unit = {

    val sparkSession = SparkSession.builder().master("local").appName("aaa").enableHiveSupport().getOrCreate()
    import sparkSession.implicits._
    kmeansTest(sparkSession)
    while(true){
      Thread.sleep(1000);
    }
  }


  def kmeansTest(sparkSession: SparkSession): Unit ={
    import sparkSession.implicits._
   // val mydata=sparkSession.range(100)
   // Create a dense vector (1.0, 0.0, 3.0).

   val dv = Vectors.dense(1.0, 0.0, 3.0)
    val dv2=Vectors.dense(4.0, 5.0, 7.0)
    val topredeit=Vectors.dense(100.0)
    val data = sparkSession.sparkContext.textFile("D:/code/spark/spark/data/mllib/kmeans_data.txt")
    val parsedData = data.map(s => Vectors.dense(s.split(' ').map(_.toDouble))).cache()


    //val data=sparkSession.sparkContext.parallelize(Seq(dv,dv2))

    val numClusters = 3
    val numIterations = 20
    val clusters = KMeans.train(parsedData,numClusters,numIterations);
    println("Computing--------------Cost"+clusters.computeCost(parsedData))
    clusters.save(sparkSession.sparkContext, "D:/code/spark/spark/data/mllib/output")
    val sameModel = KMeansModel.load(sparkSession.sparkContext, "D:/code/spark/spark/data/mllib/output")
    println(sameModel.k)
  }
}
