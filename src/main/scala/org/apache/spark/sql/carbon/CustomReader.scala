package org.apache.spark.sql.carbon
import org.apache.hadoop.mapreduce.{InputSplit, RecordReader, TaskAttemptContext}
import org.apache.spark.sql.catalyst.expressions.{GenericInternalRow, UnsafeProjection}
import org.apache.spark.sql.execution.datasources.PartitionedFile
import org.apache.spark.sql.types.StructType
import org.apache.spark.unsafe.types.UTF8String

class CustomReader(file : PartitionedFile,columns:Int,requiredSchema:StructType)  extends RecordReader [Void,Object] {

  var iterapre :java.util.Iterator[String]=null;
  var totalRecords=0;
  var currentBatch=0;
  private val unsafeProjection = UnsafeProjection.create(requiredSchema)
  override def initialize(split: InputSplit,
      context: TaskAttemptContext): Unit = {
  }



  override def nextKeyValue(): Boolean = {
    if(iterapre==null){
      processData()
    }
    iterapre.hasNext
  }

  override def getCurrentKey: Void = {
  null
  }

  override def getCurrentValue: AnyRef = {
    var values: Array[Any] = new Array[Any](columns)
    var index:Int=0;
    iterapre.next()
      .split(",").map{i =>
      values(index) = UTF8String.fromString(i)
      index=index+1;
    }
    unsafeProjection(new GenericInternalRow(values))

  }

  override def getProgress: Float = {
    0
  }

  override def close(): Unit = {

  }

  def processData() = {
    val data=new VectorCustomReader(file).readCustomFile(file)
     iterapre=data.iterator()
  }
}
