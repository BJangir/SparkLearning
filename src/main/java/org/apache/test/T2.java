package org.apache.test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class T2 {
  public static void main(String[] args) throws InterruptedException {
    T2 object = new T2();
    ExecutorService executorService = Executors.newFixedThreadPool(5);
    executorService.submit(new tt("Thread1", object));
    executorService.submit(new tt("Thread2", object));
    executorService.submit(new tt("Thread3", object));
    executorService.shutdown();
    executorService.awaitTermination(1, TimeUnit.DAYS);
    System.out.println("HI Done");
  }



  synchronized public void justwait(String name) throws InterruptedException {
    System.out.println(" i am inside"+name);
    Thread.sleep(2*100000);
  }
}
class tt implements Runnable {
  String name;
  T2 object;
  public tt(String tname,T2 object){
    this.name=tname;
    this.object=object;
    Thread.currentThread().setName(name);
  }

  @Override public void run() {
      try {
        object.justwait(name);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
  }
}
