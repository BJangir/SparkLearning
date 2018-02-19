package org.apache.spark.sql
import java.util
import java.util.{Date, Properties}

import kafka.javaapi.producer.Producer
import kafka.producer.KeyedMessage
import kafka.producer.ProducerConfig
import org.apache.kafka.clients.producer.{KafkaProducer, ProducerRecord}
import scala.util.Random;
/**
 * Created by Babu on 11/19/2017.
 */
object KafkaSparkTest {
  def main(args: Array[String]): Unit = withException {
    var events=10
    var topic="test"
    var brokers="master:9092"
    if(args.length>=0){
       events = args(0).toInt
       topic = args(1)
       brokers = args(2)
    }
    val rnd = new Random()

    val props = new Properties()
    //props.put("bootstrap.servers", "master:9092,slave1:9092,slave2:9092")
    props.put("bootstrap.servers", brokers)
    props.put("client.id", "Test Example")
    props.put("topic", topic)
    props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer")
    props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer")

    val producer = new KafkaProducer[String, String](props)
    val t = System.currentTimeMillis()

    for (nEvents <- Range(0, events)) {
      val runtime = new Date().getTime()
      val ip = "192.168.2." + rnd.nextInt(255)
      val msg = runtime + "," + nEvents + ",www.example.com," + ip
      val data = new ProducerRecord[String, String](topic, ip, msg)

      //async
      //producer.send(data, (m,e) => {})
      //sync
      producer.send(data)
    }

    System.out.println("sent per second: " + events * 1000 / (System.currentTimeMillis() - t))
    producer.close()
  }

  def withException(f: Unit): Unit ={
    try{
      f
    }catch {
      case e:Throwable=>{
        e.printStackTrace()
      }

    }
  }

}
