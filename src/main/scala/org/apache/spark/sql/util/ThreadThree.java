package org.apache.spark.sql.util;

/**
 * Created by Administrator on 3/4/2018.
 */
public class ThreadThree {
  static int MAX_VCOUNT=10;
   boolean isodd;
  int counter;
  public static void main(String[] args) throws InterruptedException {
   final ThreadThree three=new ThreadThree();
   Thread even=new Thread(new Runnable() {
      public void run() {
        three.printEven();
      }
    },"Even");

    Thread odd=new Thread(new Runnable() {
      public void run() {
        three.printOdd();
      }
    },"odd");

    even.start();
    odd.start();
    even.join();
    odd.join();


  }

  private  void printOdd() {
    synchronized (this){
      while (counter<MAX_VCOUNT){
        if (!isodd){
          try {
            wait();
          } catch (InterruptedException e) {
            e.printStackTrace();
          }
        }
        System.out.println(Thread.currentThread().getName()+"--"+counter);
        counter=counter+1;
        isodd=false;
        notify();
      }
    }
  }

  private  void printEven() {
    try {
      Thread.sleep(1000);
    } catch (InterruptedException e1) {
      e1.printStackTrace();
    }
    synchronized (this){
      while (counter<MAX_VCOUNT){
        if (isodd){
          try {
            wait();
          } catch (InterruptedException e) {
            e.printStackTrace();
          }
        }
        System.out.println(Thread.currentThread().getName()+"--"+counter);
        counter=counter+1;
        isodd=true;
        notify();
      }
    }
  }

}
