package org.apache.spark.sql.relation

object KL {
  def main(args: Array[String]): Unit = {
/*    val colums=34;
    val size=5000000;
    val startTime=System.currentTimeMillis()
    for(i<-0 until size-1 ){
      var values: Array[Any] = new Array[Any](colums)
      (0 until colums).map{i=>
        values(i) = "babu"
      }
    }
    println("Time is "+(System.currentTimeMillis()-startTime))*/
    val start=System.nanoTime()
    Thread.sleep(2*1000)
    println((System.nanoTime()-start)/1000/1000)

  }

}
