package org.apache.spark.sql.carbon;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.mapreduce.InputSplit;
import org.apache.hadoop.mapreduce.RecordReader;
import org.apache.hadoop.mapreduce.TaskAttemptContext;
import org.apache.spark.memory.MemoryMode;
import org.apache.spark.sql.catalyst.InternalRow;
import org.apache.spark.sql.execution.datasources.PartitionedFile;
import org.apache.spark.sql.execution.vectorized.OnHeapColumnVector;
import org.apache.spark.sql.execution.vectorized.WritableColumnVector;
import org.apache.spark.sql.types.StructType;
import org.apache.spark.sql.vectorized.ColumnarBatch;

public class VectorCustomReader extends RecordReader<Void,Object> {

  private ColumnarBatch columnarBatch=null;
  private WritableColumnVector[] columnVectors=null;
  private StructType schema=null;

  private boolean isbatch=false;
  private int batchIdx = 0;
  private int numBatched = 0;
  private static final int CAPACITY = 4 * 1024;
  private int totalRowCount=4;
  private PartitionedFile file=null;
  public VectorCustomReader(StructType schema,int totalRowCount,PartitionedFile file,boolean isbatch){
    this.schema=schema;
    this.totalRowCount=totalRowCount;
    this.file=file;
    this.isbatch=isbatch;
  }
  public VectorCustomReader(PartitionedFile file){
    this.file=file;
  }

  @Override public void initialize(InputSplit split, TaskAttemptContext context)
      throws IOException, InterruptedException {
  }

  @Override public boolean nextKeyValue() throws IOException, InterruptedException {
    resultBatch();
    if (isbatch) return nextBatch();

    if(batchIdx>=numBatched)
      if (!nextBatch()) return false;
    ++batchIdx;
   return true;
  }

  @Override public Void getCurrentKey() throws IOException, InterruptedException {
    return null;
  }

  @Override public Object getCurrentValue() throws IOException, InterruptedException {
    if(isbatch) return columnarBatch;
    return columnarBatch.getRow(batchIdx-1);


  }

  @Override public float getProgress() throws IOException, InterruptedException {
    return 0;
  }

  @Override public void close() throws IOException {
  if(columnarBatch!=null){
    columnarBatch.close();
    columnarBatch=null;
  }
  }
  public ColumnarBatch resultBatch() {
    if (columnarBatch == null) initBatch();
    return columnarBatch;
  }

  public void initBatch() {
    initBatch(MemoryMode.ON_HEAP, null, null);
  }

  private void initBatch(MemoryMode mode, StructType partitionColumns, InternalRow partitionValues){
    columnVectors = OnHeapColumnVector.allocateColumns(CAPACITY,schema);
    columnarBatch = new ColumnarBatch(columnVectors);
  }
  private int rowsReturned;
  public boolean nextBatch() throws IOException {
    for (WritableColumnVector vector : columnVectors) {
      vector.reset();
    }
    columnarBatch.setNumRows(0);
    if (rowsReturned >= totalRowCount) return false;
    List<String> rowsInString = readCustomFile(file);

    for(int row=0;row<rowsInString.size();row++){
     String rowValue=rowsInString.get(row);
      String[] split = rowValue.split(",");
      for(int i=0;i<split.length;i++){
        columnVectors[i].putByteArray(row,split[i].getBytes());
      }
    }
    rowsReturned += rowsInString.size();
    columnarBatch.setNumRows(rowsReturned);
    numBatched = totalRowCount;
    batchIdx = 0;
    return true;
  }

  public List<String> readCustomFile(PartitionedFile file) throws IOException {
    String s = file.filePath();
    Configuration conf=new Configuration();
    FileSystem fs = FileSystem.get(conf);
    Path filePath=new Path(s);
    System.out.println("configured filesystem = " + conf.get("fs.defaultFS"));
    InputStream is = fs.open(filePath);
    BufferedReader reader=new BufferedReader(new InputStreamReader(is));
    List<String> allvalues=new ArrayList<>();
    try {
      String line="";
      while ((line=reader.readLine()) !=null){
        allvalues.add(line);
      }
    } catch (Exception e){
      e.printStackTrace();
    }
    finally {
      reader.close();
    }
    return  allvalues;
  }

  private void addvalue(WritableColumnVector columnVector,int rowid,String rowValue) {
    String[] split = rowValue.split(",");
    for(int i=0;i<split.length;i++){
      columnVector.putByteArray(rowid,split[i].getBytes());
    }
  }
}
