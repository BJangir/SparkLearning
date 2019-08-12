package org.apache.spark.sql.relation

import java.util

import org.apache.spark.sql.sources.{BaseRelation, InsertableRelation, SchemaRelationProvider}
import org.apache.spark.sql.{Row, SQLContext, SparkSession}
import org.apache.spark.sql.sources.v2.reader.{DataReader, DataReaderFactory, DataSourceReader}
import org.apache.spark.sql.sources.v2.{DataSourceOptions, DataSourceV2, ReadSupport, ReadSupportWithSchema}
import org.apache.spark.sql.types.StructType

class CustomV2Source extends SchemaRelationProvider with DataSourceV2 with ReadSupportWithSchema {

  override def createRelation(sqlContext: SQLContext,
      parameters: Map[String, String],
      schema: StructType): BaseRelation = {
    MyRelationTest(schema)(sqlContext.sparkSession)
  }

  override def createReader(structType: StructType,
      dataSourceOptions: DataSourceOptions): DataSourceReader = {
    MyDSource(structType)
  }


}

case class MyRelationTest(userSpecifiedSchema : StructType)(@transient val sparkSession: SparkSession) extends BaseRelation  with InsertableRelation{
  override def sqlContext: SQLContext = sparkSession.sqlContext
  override def schema: StructType = userSpecifiedSchema
  override def insert(input: org.apache.spark.sql.DataFrame, overwrite: Boolean): Unit = {
    input.collect
  }
}
case class MyDSource(userSpecifiedSchema : StructType) extends DataSourceReader {
  override def readSchema(): StructType = userSpecifiedSchema
  override def createDataReaderFactories(): util.List[DataReaderFactory[Row]] = {
    val factoryList = new java.util.ArrayList[DataReaderFactory[Row]]
    factoryList.add(new MyDataSourceReaderFactory())
    factoryList
  }
}

class MyDataSourceReaderFactory extends DataReaderFactory[Row] with DataReader[Row]{
  val values=Seq((12,"b"),(23,"a"))
  var counter=0;
  def createDataReader = new MyDataSourceReaderFactory()
  override def next(): Boolean = counter < values.length

  override def get(): Row = {
    val seq=values(counter)
    counter +=1
     Row(seq._1.toLong,seq._2)
  }

  override def close(): Unit = {
    print("Hiiiii,i am in close")
  }
}
