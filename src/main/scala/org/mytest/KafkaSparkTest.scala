package org.mytest

import java.util
import java.util.{Date, Properties}

import kafka.javaapi.producer.Producer
import kafka.producer.KeyedMessage
import kafka.producer.ProducerConfig
import org.apache.kafka.clients.producer.{KafkaProducer, ProducerRecord}

import scala.util.Random;
/**
  * Created by Babu on 11/12/2017.
  */
object KafkaSparkTest {

  def main(args: Array[String]): Unit = {
    var events=10
    var topic="test"
    var brokers="master:9092"
    if(args.length>=0){
      val events = args(0).toInt
      val topic = args(1)
      val brokers = args(2)
    }
    val rnd = new Random()

    val props = new Properties()
    props.put("bootstrap.servers", "master:9092,slave1:9092,slave2:9092")
    props.put("client.id", "ScalaProducerExample")
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


}

