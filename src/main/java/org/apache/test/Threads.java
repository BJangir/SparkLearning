package org.apache.test;

import java.util.ArrayDeque;
import java.util.Comparator;
import java.util.Deque;
import java.util.Iterator;
import java.util.Queue;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Threads {

  public static void main(String[] args) {

    Set<Person> strings=new TreeSet<Person>();
    strings.add(new Person("babu",9));
    strings.add(new Person("a",10));
    strings.add(new Person("a",1));

    Iterator<Person> iterator = strings.iterator();
    while (iterator.hasNext()){
      System.out.println(iterator.next().age);
    }

  }


  static class Person implements Comparable<Person>{

    String name;
    int age;
    Person(String name,int age){
      this.name=name;
      this.age=age;
    }


    @Override public int compareTo(Person o) {
      return this.age-o.age;
    }
  }
}

