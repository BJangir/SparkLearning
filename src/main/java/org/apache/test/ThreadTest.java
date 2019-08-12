package org.apache.test;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ThreadTest {


  public static void main(String[] args) throws InterruptedException {

    ExecutorService executorService = Executors.newCachedThreadPool();
    for(int i=0;i<10;i++){
      RunMe runMe=new RunMe("babu");
      executorService.submit(runMe);
    }
    executorService.shutdown();
    executorService.awaitTermination(1, TimeUnit.DAYS);
  }
  static class RunMe implements Runnable{
    String name;
  RunMe(String name){
    this.name=name;
  }

  @Override public void run() {
    LockHolder lockHolder=new LockHolder();
    Object o = lockHolder.objectMap.get(name);
    if(null==o){
      lockHolder.objectMap.put(name,new Object());
    }
    else{
      synchronized (o){
        System.out.println("i am "+name);
      }
    }

  }
}

static final class LockHolder {
   static Map<String,Object> objectMap=new ConcurrentHashMap<>();


}

}
