package org.apache.spark.sql.util;

/**
 * Created by Administrator on 3/3/2018.
 */
public class ThreadTwo {
static  Object object=new Object();
static int counter;
static int MAX_COUNTER=10;
  public static void main(String[] args) throws InterruptedException {

    Thread even=new Thread(new OddEven(),"even");
    Thread odd=new Thread(new OddEven(),"odd");
    even.start();
    odd.start();
    even.join();
    odd.join();



  }



}
class OddEven implements Runnable{
 static   int MAX_COUNT=10;
 static int counter;
 static  Object  lck=new Object();
  public void run() {
   while(counter <MAX_COUNT){
String currentthreadName=Thread.currentThread().getName();
 if (counter %2==0 && currentthreadName.equalsIgnoreCase("even")){
   synchronized (lck){
     System.out.println(currentthreadName+"---"+counter);
     counter=counter+1;
     try {
       lck.wait();
     } catch (InterruptedException e) {
       e.printStackTrace();
     }
   }
 }

 if(counter %2 !=0 && currentthreadName.equalsIgnoreCase("odd")){
synchronized (lck){
  System.out.println(currentthreadName+"---"+counter);
  counter=counter+1;
  lck.notify();
}

     }

   }

  }
}
