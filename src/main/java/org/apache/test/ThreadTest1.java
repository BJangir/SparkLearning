package org.apache.test;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.ReentrantLock;

public class ThreadTest1 {

  public static void main(String[] args) {
    final Holder holder=new Holder(10);
    ExecutorService executorService =
        Executors.newFixedThreadPool(4);
    final Random random=new Random();
    executorService.submit(new Runnable() {
      @Override public void run() {
        Thread.currentThread().setName("Producer P1");
        while (true){
          Emp emp=new Emp();
          emp.setAge(random.nextInt(100));
          emp.setName("ba"+random.nextInt(12));
          try {
            holder.put(emp);
            Thread.sleep(500);
          } catch (InterruptedException e) {
            e.printStackTrace();
          }
          System.out.println("added P2 Emp Current Size"+holder.emps.size());
        }
      }
    });
    executorService.submit(new Runnable() {
      @Override public void run() {
        Thread.currentThread().setName("Producer P2");
        while (true){
          Emp emp=new Emp();
          emp.setAge(random.nextInt(700));
          emp.setName("ba"+random.nextInt(12));
          try {
            holder.put(emp);
            Thread.sleep(1*1000);
          } catch (InterruptedException e) {
            e.printStackTrace();
          }
          System.out.println("added P2 Emp Current Size"+holder.emps.size());
        }
      }
    });
    executorService.submit(new Runnable() {
      @Override public void run() {
        Thread.currentThread().setName("Consumer 1");
        while (true){
          Emp emp = null;
          try {
            emp = holder.get();
            Thread.sleep(500);
          } catch (InterruptedException e) {
            e.printStackTrace();
          }

          System.out.println("GotEmp "+emp.getName()+" "+emp.getAge());
        }
      }
    });


  }




}

class Holder{

  int size=0;
  Queue<Emp> emps;
  ReentrantLock reentrantLock=new ReentrantLock();
  ReentrantLock waitLockP=new ReentrantLock();
  ReentrantLock waitLockC=new ReentrantLock();
  Object waitObj=new Object();
  Semaphore semaphoreP=new Semaphore(0);
  Semaphore semaphoreC=new Semaphore(0);

  Holder(int size){
    this.size=size;
    emps=new LinkedList<Emp>();
  }

  void put(Emp emp) throws InterruptedException {
    try{
      /* while (size==emps.size()){
        notify();
        wait();
      }*/
      reentrantLock.lock();
      if(size==emps.size()){
        reentrantLock.unlock();
        semaphoreC.release();
        semaphoreP.acquire();
        reentrantLock.lock();
      }

      emps.add(emp);
      semaphoreC.release();
    } catch (Exception e){
      e.printStackTrace();
    } finally {
      reentrantLock.unlock();
    }


  }

  Emp get() throws InterruptedException {
    try{
      /* while (emps.size()==0){
        notify();
        wait();
        }*/
      reentrantLock.lock();
      if(0==emps.size()){
        reentrantLock.unlock();
        semaphoreP.release();
        semaphoreC.acquire();
        reentrantLock.lock();
      }
      Emp poll = emps.poll();
      //notify();
      semaphoreP.release();

      return poll;
    } catch (Exception e){
      e.printStackTrace();
    } finally {
      reentrantLock.unlock();
    }
    return null;
  }
}
