package org.apache.test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 9/7/2018.
 */
public class Size {
  public static void main(String[] args) throws IOException {

   //adaptiveTest();
   //just1();
   just2();
   //readBinaryFile();


  }

  private static void just2() {
    int[] rowids=new int[]{1,2,3,4,6,8,10,11,12,13};
    //output
    //arr1=1,4,6,8,10,13   arr2=0,6
    //encode
    int[] startend=new int[rowids.length];
    int startkey=0;
    int[] conti_aray=new int[rowids.length];
    for(int i=0;i<rowids.length-1;i++){

      if(rowids[i]==rowids[i+1]){
        startend[startkey]=rowids[i];
      }
      else{
        startend[startkey]=rowids[i];
      }

    }




  }

  private static void readBinaryFile() throws IOException {
    String file="D:/data/babu_1.bin";
    FileInputStream stream=new FileInputStream(file);
    byte[] buffer=new byte[4];
    int nRead=0;
    while((nRead = stream.read(buffer)) != -1){
      ByteBuffer buffer1=ByteBuffer.wrap(buffer);
      System.out.println(buffer1.getInt());
    }
  }

  private static void just1() {
/*
If you are given an Array where every number is repeated twice(or even number of times) and except one. which appears exactly once.
 int[]{1,1,2,2,3}
 */
boolean[] booleans=new boolean[]{false,true,false,true,true};
boolean temp=booleans[0];

for(int i=1;i<booleans.length;i++){
  temp=temp^booleans[i];

}
int i=5;
    System.out.println((i^3)+"-->"+temp);


    int[] ints=new int[]{1,1,2,2,33,33,5,5,5};

    int tempint=ints[0];

    for(int j=1;j<ints.length;j++){
      int a =tempint^ints[j];
      tempint=a;
    }
    System.out.println("is "+(1^1)+"--"+(3^3^8)+"---->"+tempint);
  }

  private static void adaptiveTest() throws IOException {

    List<Integer> integers = new ArrayList<>();
    int TOTAL_SIZE = 1000;
    ByteBuffer buffer = null;
    DATA_TYPE targetType = DATA_TYPE.INT;
    for (int i = 0; i < TOTAL_SIZE; i++) {
      integers.add(i);
    }
    int[] minMax = getMinMax(integers);
    System.out.println("Min is "+minMax[0]+"Max is "+minMax[1]);
    if (minMax[0] > Byte.MIN_VALUE && minMax[1] < Byte.MAX_VALUE) {
      targetType = DATA_TYPE.BYTE;
    } else{
      if (minMax[0] > Integer.MIN_VALUE && minMax[1] < Integer.MAX_VALUE) {
        targetType = DATA_TYPE.INT;
      }
    }

    switch (targetType) {
      case SHORT:
      case BYTE:
        buffer = ByteBuffer.allocate(integers.size());
        for (int i : integers) {
          buffer.put((byte) i);
        }
      case INT:
        buffer = ByteBuffer.allocate((integers.size()*4)+1);
        for (int i : integers) {
          buffer.putInt(i);
        }
        buffer.put((byte) 2);
    }

    OutputStream fileInputStream = new FileOutputStream("D:/data/babu_1.bin");
    fileInputStream.write(buffer.array());
    fileInputStream.close();
    // Reading
    System.out.println("Toal size"+buffer.array().length);


    ByteBuffer wrap = ByteBuffer.wrap(buffer.array());
    byte readtargetType= wrap.get(buffer.array().length-1);
    wrap.limit(buffer.array().length-1);
    /*ByteBuffer buffer1 = wrap.get(buffer.array(), buffer.array().length - 1, 1);
    ByteBuffer buffer2 = wrap.get(buffer.array(),0, buffer.array().length - 1);*/
    //byte readtargetType=buffer1.wrap(buffer1.array()).get();
    if(readtargetType==2){
      for(int i=0;i<(buffer.array().length/4);i++){
        System.out.println(wrap.getInt());
      }
    }
  }

  private static int[] getMinMax(List<Integer> integers) {
    int min = Integer.MAX_VALUE;
    int max = Integer.MIN_VALUE;

    for (int i : integers) {
      if (i < min) {
        min = i;
      }
      if (i > max) {
        max = i;
      }
    }
    return new int[] { min, max };
  }
}
enum DATA_TYPE{
  INT,BYTE,SHORT,SHORT_INT
}
