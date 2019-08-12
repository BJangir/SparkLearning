package org.apache.spark.sql.relation

import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.catalyst.TableIdentifier
import org.apache.spark.sql.catalyst.catalog.{CatalogStorageFormat, CatalogTable, CatalogTableType}
import org.apache.spark.sql.types.{LongType, StringType, StructType}

object CustomSourceV2Suite {


  def main(args: Array[String]): Unit = {
    val spark=SparkSession.builder().enableHiveSupport().master("local").getOrCreate()
    val schema = new StructType()
      .add("i", LongType, true)
      .add("s", StringType, true)

   /* val df=spark.read.format("org.apache.spark.sql.relation.CustomV2Source").schema(schema).load();
    df.show(false)*/

    spark.sql("show tables").show(false)
    spark.sql("drop table if exists babu").show(false)

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
      provider = Some(classOf[CustomV2Source].getName))
    spark.sessionState.catalog.createTable(newTable, false)

    spark.sql("select * from babu").show(false)

    /*spark.sql("insert into babu select 1,'s'")
    spark.sql("insert into babu select 1,'f'")
    spark.sql("desc formatted babu").show(false)
    spark.sql("select * from babu").show(false)*/
    //spark.sessionState.catalog.dropTable(newTable.identifier,true,false)

  }



}
