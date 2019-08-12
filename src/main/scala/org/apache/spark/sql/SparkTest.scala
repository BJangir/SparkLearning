package org.apache.spark.sql

object SparkTest{



  def main(args: Array[String]): Unit = {
    val spark=SparkSession.builder().enableHiveSupport().master("local").getOrCreate()
    val rdd1=spark.sparkContext.parallelize(1 to 10,3)
    import spark.implicits._
    /*val df1=rdd1.toDF
    df1.printSchema()
    */
    /*val df2=df1.select("value1")
    df2.show()
    val ds1=rdd1.toDS
    ds1.select("aaa")
    ds1.show()*/
    /*val ds1=rdd1.toDS()
    val ds2=rdd1.toDS()
    val ds3=ds1.join(ds2,"value1")
    ds3.show(false)*/
   /* val rdd2=spark.sparkContext.parallelize(1 to 10).map(x=> {
      Person("babu"+x,x)
    });

   val ds3=spark.createDataset(rdd2)
    val dd=ds3("aa")
    ds3.printSchema()
    ds3.collect()*/

    //test1(spark);
    test3(spark);
    spark.stop()
  }
  case class Person(name:String,age:Int)

  def test1(spark: SparkSession) = {
    import spark.implicits._
    import spark.sqlContext.implicits
    val path="D:/logs/logs.log"

    val readRdd=spark.sparkContext.textFile(path)
    val r2=readRdd.map(x=> x.split(" "))
    val r3=r2.flatMap(x=> x).filter(x=> x.contains("ERROR")|| x.contains("AUDIT")|| x.contains("Administrator")).map(x=>(x,1))

    val r4=r3.reduceByKey((a,b)=> (a+b))
      r4.collect().foreach(println(_))

  }


  def test2(spark: SparkSession) = {
    import spark.implicits._
    import spark.sqlContext.implicits


    val ds1=spark.sparkContext.parallelize(1 to 12,3).toDS()
   val ds2= ds1.selectExpr(" 'city1' as name","value as age").as[Person]
    val ff=ds2.groupByKey(_.name).reduceGroups((a,b)=>Person(a.name,1))
    ff.explain()
    ff.show(false)

    val rdd1=spark.sparkContext.parallelize(1 to 12,3)
    val input = spark.sparkContext.parallelize(1 to 10000000, 42).map(x => (x % 42, x))

  }

  def test3(spark: SparkSession) = {
    import spark.implicits._
    import spark.sqlContext.implicits



    val rdd1=spark.sparkContext.parallelize(1 to 12,3).keyBy(_%4)
    rdd1.collect().foreach(println(_))

  }






}
