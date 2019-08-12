package org.apache.test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

import static org.apache.test.Location.*;

public class Compa {

  public static void main(String[] args) {

    //test1();
    testComparator();
  }

  private static void testComparator() {

    List<MyDep> empList=new ArrayList<MyDep>();
    for(int i=0;i<10;i++){
      MyDep emp=new MyDep();
      emp.setName("Babu");
      emp.setAge(new Random().nextInt(100));
      empList.add(emp);
    }
    Collections.sort(empList, new Comparator<MyDep>() {
      @Override public int compare(MyDep o1, MyDep o2) {
        if(o1.getAge()<o2.getAge()){
          return -1;
        }
        if(o1.getAge()>o2.getAge()){
          return 1;
        }
        return 0;
      }
    });
    for(MyDep e:empList){
      System.out.println(e.getAge());
    }

  }



  private static void test1() {
    MyDep myDep=new MyDep();
    myDep.age=1;
    myDep.name="babu";
    Location location=new Location();
    location.setState("Raj");

    //Location test = test(location);
    //System.out.println(test.getState());
    Person person=new Person();
    String detail = person.getDetail(location);
    System.out.println(detail);
  }

  protected static <E> E test(E myvalue){
    if(myvalue instanceof MyDep){
        ((MyDep) myvalue).age=123;

      return myvalue;
    }
    if(myvalue instanceof Location){
      ((Location) myvalue).setState("Kar");

      return myvalue;
    }
    return null;

  }
}


class MyDep implements Comparable<MyDep> {
  String name;
  int age;

  public void setAge(int age) {
    this.age = age;
  }

  public void setName(String name) {
    this.name = name;
  }

  public int getAge() {
    return age;
  }

  public String getName() {
    return name;
  }

  @Override public int compareTo(MyDep o) {
    if(this.getAge()<o.getAge()){
      return -1;
    }
    if(this.getAge()>o.getAge()){
      return 1;
    }
    return  0;
  }
}

class Location {
  String state;
  String temp;

  public String getState() {
    return state;
  }

  public String getTemp() {
    return temp;
  }

  public void setState(String state) {
    this.state = state;
  }

  public void setTemp(String temp) {
    this.temp = temp;
  }


}

class Person<T>{
  String state = null;
  String temp=null;

  public String getDetail( T value) {
    if(value instanceof Location){
      return ((Location) value).getState();
    }
    if (value instanceof Dep){
      return "NA";
    }
    return null;
  }
}




