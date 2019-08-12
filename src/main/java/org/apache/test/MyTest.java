package org.apache.test;

import java.nio.ByteBuffer;
import java.util.BitSet;
import java.util.Random;
import java.util.UUID;

import com.fasterxml.uuid.Generators;
import com.fasterxml.uuid.UUIDGenerator;
import com.fasterxml.uuid.impl.RandomBasedGenerator;
import com.fasterxml.uuid.impl.TimeBasedGenerator;

public class MyTest {

  public static void main(String[] args) {
    //bytebufferTest();

    //2DArra
     /*array2d();
    array2d_cache();
    arrayRandomAccess();*/
    // xorUsageCase1();
    //xorUsageCase2();
    //xorUsageCase3();
    //uuid();
    //aboutInt();
    d();



  }

  private static void d(){
    java.lang.String[] descriptorData = {
        "\n\025ResourceTracker.proto\022\013hadoop.yarn\032\'ya" +
            "rn_server_common_service_protos.proto2\350\002" +
            "\n\026ResourceTrackerService\022r\n\023registerNode" +
            "Manager\022,.hadoop.yarn.RegisterNodeManage" +
            "rRequestProto\032-.hadoop.yarn.RegisterNode" +
            "ManagerResponseProto\022`\n\rnodeHeartbeat\022&." +
            "hadoop.yarn.NodeHeartbeatRequestProto\032\'." +
            "hadoop.yarn.NodeHeartbeatResponseProto\022x" +
            "\n\025unRegisterNodeManager\022..hadoop.yarn.Un" +
            "RegisterNodeManagerRequestProto\032/.hadoop",
        ".yarn.UnRegisterNodeManagerResponseProto" +
            "B5\n\034org.apache.hadoop.yarn.protoB\017Resour" +
            "ceTracker\210\001\001\240\001\001"
    };
    for(String g:descriptorData){
      System.out.println(g);
    }


  }
  private static void aboutInt() {
    int a=-100;
    int b=8;
    System.out.println(Integer.toBinaryString(a));
    System.out.println(Integer.toBinaryString(b));
    System.out.println(a^b);

    System.out.println(b&1);
    b=b>>1;
    System.out.println(Integer.toBinaryString(b));
    System.out.println(b&1);
    BitSet s=new BitSet(b);
    System.out.println(s.cardinality());

    int counter=0;
    while(a>0){
      counter=counter+(a&1);
      a=a>>1;
    }
    System.out.println("Bits ON "+counter);

    for(int i=0;i<=10;i++){
      System.out.println(i+"->"+(i-1)+"-Ori->"+Integer.toBinaryString(i)+"-(Ori-1)->"+Integer.toBinaryString(i-1)+" -AND "+(i&(i-1))+"-->OR"+(i|(i-1))+"-->XOR"+(i^(i-1)));
    }
  }

  private static void xorUsageCase3() {
  int a=8;
  int b=2;

    String sBit = Integer.toBinaryString(a);
    String dBit = Integer.toBinaryString(b);
    ByteBuffer buffer=ByteBuffer.allocate(4);
    BitSet bitSet = BitSet.valueOf(buffer.putInt(a ^ b).array());
    System.out.println(a^b);
    System.out.println(Integer.toBinaryString(a^b));
    System.out.println(bitSet.cardinality());
    System.out.println(a&(a-1));
    System.out.println(Integer.toBinaryString(a-1));
    System.out.println(Integer.toBinaryString(a));
    System.out.println(Integer.toBinaryString(a&(a-1)));



  }

  private static void uuid() {
  /*  TimeBasedGenerator timeBasedGenerator = Generators.timeBasedGenerator();
    RandomBasedGenerator randomBasedGenerator = Generators.randomBasedGenerator();
    Generators.nameBasedGenerator();
    UUID*/
    UUID s = UUID.fromString("c81d4e2e-bcf2-11e6-869b-7df92533d2db");
    System.out.println(s.timestamp());

  }

  private static void xorUsageCase2() {
    int[] a=new int[]{1,0};
    a[0] = a[a[0]];
    a[1] = a[0];
    System.out.println(a[0]+"-->"+a[1]);

  }

  // Find the element which appeared only once
  private static void xorUsageCase1() {

    int[] aa=new int[]{12, 1, 12, 3, 12, 1, 1, 2, 3, 3};
 int result=0;
    for(int i=0;i<aa.length-1;i++){
    int j=i+1;
    if(i==0){
      result=aa[i];
    }
    result=result^aa[i];
    }

    System.out.println(result);



  }

  private static void arrayRandomAccess() {
    int max=1024;
    int col=1024;
    byte[] a=new byte[max];
    long start = System.nanoTime();
    Random as=new Random();
    for(int i=0;i<max;i++){
      byte c=a[as.nextInt(max-1)];
    }
    System.out.println(("Random: "+(System.nanoTime()-start)/1000));

     start = System.nanoTime();
    for(int i=0;i<max;i++){
      byte c=a[i];
    }
    System.out.println(("Seq: "+(System.nanoTime()-start)/1000));


  }

  private static void array2d() {
    int row=1024;
    int col=1024;
    byte[][] a=new byte[row][col];
    long start = System.nanoTime();
    for(int i=0;i<row;i++){
      for(int j=0;j<col;j++){
       byte x= a[i][j];
      }
    }
    System.out.println((System.nanoTime()-start)/1000);

  }
  private static void array2d_cache() {
    int row=1024;
    int col=1024;
    byte[][] a=new byte[row][col];
    long start = System.nanoTime();
    for(int i=0;i<row;i++){
      for(int j=0;j<col;j++){
        byte x= a[j][i];
      }
    }
    System.out.println((System.nanoTime()-start)/1000);

  }

  private static void bytebufferTest() {
    //raw data
    int a =120;
    String s="baub";

    //Byte Array
    byte[] bytes_Stirng = s.getBytes();

    ByteBuffer buffer=ByteBuffer.allocate(4);
    buffer.putInt(a);
    byte[] array = buffer.array();

    //decoder

    System.out.println(new String(bytes_Stirng));

    ByteBuffer wrap = ByteBuffer.wrap(array);
    System.out.println(wrap.getShort());
  }

  private static void xorUsageCase() {


  }
}
