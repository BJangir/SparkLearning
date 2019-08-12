package org.apache.test;

import java.lang.management.ManagementFactory;
import java.util.ArrayList;
import java.util.List;

public class MemTest {
  int[] a=new int[10];
  List<String> values=null;
  public static void main(String[] args) throws InterruptedException {
    MemTest s=new MemTest();
    System.out.println(s.a.length);
    System.out.println("PID is "+ ManagementFactory.getRuntimeMXBean().getName());
    Thread.sleep(30*1000);
    System.out.println("Waiting");
    for(int i=0 ;i<1000000000;i++){
      s.values= getValues();
    }
    Thread.sleep(300*1000);
  }

  private static List<String> getValues() throws InterruptedException {
    List<String> alllist=new ArrayList<>();
    alllist.add("1");
    alllist.add("1");
    alllist.add("1");
    alllist.add("1");
    return alllist;
  }
}
