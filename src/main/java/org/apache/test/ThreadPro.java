package org.apache.test;

import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ThreadPro {

  static ArrayBlockingQueue<String> queues=new ArrayBlockingQueue<>(10);

  public static void main(String[] args) throws InterruptedException {

    ExecutorService executorService =
        Executors.newFixedThreadPool(3, Executors.defaultThreadFactory());
    executorService.submit(new Producer1());
    executorService.submit(new Producer2());
    executorService.submit(new Consumer());
    executorService.shutdown();
    executorService.awaitTermination(4, TimeUnit.HOURS);



  }


  static class Producer1  implements Runnable{
    @Override public void run() {
      Thread.currentThread().setName("Producer1");
      while (true){
        for (int i=0;i<10;i++){
          try {
            queues.put("babuP1 "+i);
            System.out.println("Added ");
            Thread.sleep(1*1000);
          } catch (InterruptedException e) {
            e.printStackTrace();
          }
        }
        break;
      }
      System.out.println("P1 Done");

    }
  }

  static class Producer2  implements Runnable{
    @Override public void run() {
      Thread.currentThread().setName("Producer2");
      while (true){
        for (int i=0;i<10;i++){
          try {
            queues.put("babuP2"+i);
            System.out.println("Added ");
            Thread.sleep(1*1000);
          } catch (InterruptedException e) {
            e.printStackTrace();
          }
        }
        break;
      }
      System.out.println("P1 Done");
    }
  }

  static class Consumer  implements Runnable{

    @Override public void run() {
      Thread.currentThread().setName("Consumer");
      while (true){
        try {
          String take = queues.take();
          System.out.println("Got "+take);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }

      }

    }
  }

}








class PCTest{
  public Queue<String> queue;




}