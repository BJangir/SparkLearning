package org.apache.spark.sql.util;

/**
 * Created by Administrator on 3/3/2018.
 */
public class ThreadOne {

  public static void main(String[] args) {

  Thread odd=new Thread(new OddEvenTest(0),"Even");
  Thread even=new Thread(new OddEvenTest(1),"Odd");
  odd.start();
  even.start();
  try{
    odd.join();
    even.join();
  }catch (Exception e){
    e.printStackTrace();
  }

  }


}


class OddEvenTest implements Runnable{

 static int counter=0;
 static int MAX_COUNTER=10;
 static Object lock=new Object();
 int rem;
 OddEvenTest(int remider){
   this.rem=remider;
 }

  public void run() {
  while(counter<MAX_COUNTER){
    synchronized (lock){
      if(counter%2 != rem){
        try {
          lock.wait();
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }
        System.out.println(Thread.currentThread().getName() + "--" + counter);
      lock.notifyAll();
      counter =counter+1;


    }

  }
  }
}