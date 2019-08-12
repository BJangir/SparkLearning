package org.apache.spark.sql.relation

import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.catalyst.TableIdentifier
import org.apache.spark.sql.catalyst.catalog.{CatalogStorageFormat, CatalogTable, CatalogTableType, CatalogTypes}
import org.apache.spark.sql.types.{LongType, StringType, StructType}

object CustomSourceSuite {


  def main(args: Array[String]): Unit = {
    val spark=SparkSession.builder().enableHiveSupport().master("local").getOrCreate()

    val schema = new StructType()
      .add("i", LongType, true)
      .add("s", StringType, true)
    spark.sql("show tables").show(false)
    spark.sql("drop table if exists babu").show(false)
    spark.sql("drop table if exists c").show(false)

    /*val x= new CatalogTable(identifier = new TableIdentifier("c",None),tableType = CatalogTableType.EXTERNAL,schema=schema,
      storage = CatalogStorageFormat(
        locationUri = None,
        inputFormat = None,
        outputFormat = None,
        serde = None,
        compressed = false,
        properties = Map.empty),provider = None)

    spark.sessionState.catalog.createTable(x, false)
    spark.sql("show tables").show(false)
    spark.sql("desc formatted c").show(false)*/

    val newTable = CatalogTable(
      identifier = TableIdentifier("babu", None),
      tableType = CatalogTableType.EXTERNAL,
      storage = CatalogStorageFormat(
        locationUri = None,
        inputFormat = None,
        outputFormat = None,
        serde = None,
        compressed = false,
        properties = Map.empty),
      schema = schema,
      provider = Some(classOf[CustomSource].getName))
    spark.sessionState.catalog.createTable(newTable, false)
   /* spark.sql("insert into babu select 1,'s'")
    spark.sql("insert into babu select 1,'f'")*/
   // spark.sql("select * from babu").show(false)
   val df= spark.read.format("org.apache.spark.sql.relation.CustomSource").schema(schema).load()
    df.show()
    //spark.sessionState.catalog.dropTable(newTable.identifier,true,false)

  }



}
