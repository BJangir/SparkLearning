package org.apache.test;

import java.io.Serializable;
import java.util.BitSet;

/**
 * Created by Administrator on 3/25/2018.
 */
public class L1 implements Serializable{

  //

  public static void main(String[] args) {
   // printnowithoutLoopwithIF(10);
   // count1sFromBitSe3(9);
    String value = Long.toBinaryString(10);
    System.out.println(value);
    BitSet bitSet=new BitSet();

  }

  private static void count1sFromBitSet(int i) {
    byte s=(byte) i;
    System.out.println(s);
    final BitSet set = BitSet.valueOf(new byte[] { s });
    System.out.println(set);
    long l = set.toLongArray()[0];

    System.out.println(set.cardinality());
  }
  private static void count1sFromBitSet2(int n) {
    System.out.println(Integer.toBinaryString(n));
    int count = 0;
    while (n > 0)
    {
      System.out.println("Binary:"+Integer.toBinaryString(n)+" num:"+n);
      count += n & 1;
      System.out.println("count:"+count);
     n =n>>1;
    }
    System.out.println(count);
  }
  //Brian Kernighan’s Algorithm:
  private static void count1sFromBitSe3(int n) {
    System.out.println(Integer.toBinaryString(n));
    int count = 0;
    while (n > 0)
    {

      System.out.println("Binary:"+Integer.toBinaryString(n)+" num:"+n);
      System.out.println("Binary:"+Integer.toBinaryString(n-1)+" num:"+(n-1));
      n=n &(n-1);
      System.out.println("Binary Result:-"+Integer.toBinaryString(n)+" num:"+(n));
      count++;
      System.out.println("count:"+count);
    }
    System.out.println(count);
  }

  public static void printnowithoutLoop(int size){

    if(size>0){
      printnowithoutLoop(size-1);
      System.out.println(size);
    }
  }
  public static void printnowithoutLoopwithIF(int size){
     int[] a =new int[size];
     printcounter(a,0);
  }

  private static void printcounter(int[] a,int start) {
    try {
      int value=a[start];
      System.out.println(start);
      printcounter(a,start+1);
    }
    catch (Exception e){
      System.out.println("just ignored");
    }
  }

}
