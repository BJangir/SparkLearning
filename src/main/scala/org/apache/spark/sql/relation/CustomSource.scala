package org.apache.spark.sql.relation

import org.apache.spark.rdd.RDD
import org.apache.spark.sql.sources.{BaseRelation, InsertableRelation, SchemaRelationProvider, TableScan}
import org.apache.spark.sql.types.StructType
import org.apache.spark.sql._

class CustomSource extends SchemaRelationProvider {

  override def createRelation(sqlContext: SQLContext,
      parameters: Map[String, String],
      schema: StructType): BaseRelation = {
    CustomRelation(schema)(sqlContext.sparkSession)
  }

}

case class CustomRelation(userSpecifiedSchema : StructType) (@transient val sparkSession: SparkSession)
  extends BaseRelation  with TableScan {
  override def sqlContext: SQLContext = sparkSession.sqlContext
  override def schema: StructType = userSpecifiedSchema
  /*override def insert(input: org.apache.spark.sql.DataFrame, overwrite: Boolean): Unit = {
    input.collect
  }
  override def sizeInBytes: Long = super.sizeInBytes
*/
  override def buildScan(): RDD[Row] = {
    import sparkSession.implicits;
    /*val rdd=sparkSession.sparkContext.parallelize(Seq((12,"b"),(23,"a"))).map(x => Row(x._1.toLong,x._2));
    rdd*/
    //sparkSession.range(10).toDF("id").select("id as a,id as b").rdd
    //sparkSession.range(10).toDF("id").selectExpr("id as c1"," id as c2").rdd
    sparkSession.range(10).toDF("id").selectExpr("cast(id as Long)","cast(id as String)").rdd
  }

}


