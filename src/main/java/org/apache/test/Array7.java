package org.apache.test;

import java.util.Comparator;
import java.util.Iterator;
import java.util.PriorityQueue;

public class Array7 {

  public static void main(String[] args) {
   test1();


  }

  private static void test1() {

    PriorityQueue<ArrayTest> strings=new PriorityQueue<ArrayTest>(10,new ArrayTest());

    strings.add(new ArrayTest("babu",3));
    strings.add(new ArrayTest("lal",2));
    strings.add(new ArrayTest("a",0));
    strings.add(new ArrayTest("ba",1234));
    strings.add(new ArrayTest("bu",46));
    strings.add(new ArrayTest("zabu",80));
    System.out.println(strings.peek());

    int sizez=strings.size();
    for(int i=0;i<sizez;i++){
      ArrayTest poll = strings.poll();
      System.out.println(poll.name+"-->"+poll.getAge());

    }


  }

  static class ArrayTest implements Comparator<ArrayTest> {
    String name;
    int age;
    ArrayTest( String name,int age){
      this.name=name;
      this.age=age;
    }
    ArrayTest(){

    }

    public String getName() {
      return name;
    }

    public int getAge() {
      return age;
    }

    public void setName(String name) {
      this.name = name;
    }

    public void setAge(int age) {
      this.age = age;
    }

    @Override public int compare(ArrayTest o1, ArrayTest o2) {
      return o1.age-o2.age;
    }
  }

}
