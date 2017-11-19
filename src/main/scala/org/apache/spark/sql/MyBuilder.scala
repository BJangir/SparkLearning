package org.apache.spark.sql

import org.apache.spark.internal.Logging
import org.apache.spark.sql._
import org.apache.spark.sql.catalyst.{FunctionIdentifier, TableIdentifier}
import org.apache.spark.sql.catalyst.expressions.Expression
import org.apache.spark.sql.catalyst.parser.{CatalystSqlParser, ParserInterface}
import org.apache.spark.sql.catalyst.plans.logical.LogicalPlan
import org.apache.spark.sql.execution.command.RunnableCommand
import org.apache.spark.sql.types.{DataType, StructType}
/**
 * Created by Administrator on 11/20/2017.
 */
class MyBuilder {
  type ExtensionsBuilder = SparkSessionExtensions => Unit
  private def create(builder: ExtensionsBuilder): ExtensionsBuilder = builder

  private def withSession(builder: ExtensionsBuilder)(f: SparkSession => Unit): Unit = {
    val spark = SparkSession.builder().master("local[1]").withExtensions(builder).getOrCreate()
    try f(spark) finally {
      stop(spark)
    }
  }
  private def stop(spark: SparkSession): Unit = {
    spark.stop()
    SparkSession.clearActiveSession()
    SparkSession.clearDefaultSession()
  }
}
object MyBuilder{
  val myb=new MyBuilder()
  var spark :SparkSession=null
  def main(args: Array[String]): Unit = {
    spark=SparkSession.builder().appName("x").master("local").enableHiveSupport().getOrCreate();
    val extension = myb.create { extensions =>
      extensions.injectParser((_, _) => CatalystSqlParser)
      extensions.injectParser(MyParser)
    }
    myb.withSession(extension) { session =>
      assert(session.sessionState.sqlParser == CatalystSqlParser)
    }

  }
}

case class MyParser(spark: SparkSession, delegate: ParserInterface) extends ParserInterface {
  override def parsePlan(sqlText: String): LogicalPlan ={
    MyCommand
  }
  //delegate.parsePlan(sqlText)

  override def parseExpression(sqlText: String): Expression =
    delegate.parseExpression(sqlText)

  override def parseTableIdentifier(sqlText: String): TableIdentifier =
    delegate.parseTableIdentifier(sqlText)

  override def parseFunctionIdentifier(sqlText: String): FunctionIdentifier =
    delegate.parseFunctionIdentifier(sqlText)

  override def parseTableSchema(sqlText: String): StructType =
    delegate.parseTableSchema(sqlText)

  override def parseDataType(sqlText: String): DataType =
    delegate.parseDataType(sqlText)
}


case object MyCommand extends RunnableCommand with Logging {

  override def run(sparkSession: SparkSession): Seq[Row] = {
    print("Hi i am in the RunCommand")
    Seq.empty[Row]
  }
}
