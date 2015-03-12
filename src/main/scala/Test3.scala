
import eu.unicredit.reactive_aerospike.client._
import eu.unicredit.reactive_aerospike.data._
import eu.unicredit.reactive_aerospike.data.AerospikeValue._
import eu.unicredit.reactive_aerospike.future.ScalaFactory.Helpers._

import scala.util.{ Success, Failure }
import scala.concurrent._
import scala.concurrent.duration._
import scala.concurrent.ExecutionContext.Implicits.global

import scala.util.Random

object Test3 extends App {
  
  val client = new AerospikeClient("localhost", 3000)

  val timeBefore = System.currentTimeMillis()
  
  var counter = 0
  
  while (true) {
  val randKey = Random.nextLong+"ciao"
  val randValue = Random.nextLong+"ciao"
  
  val key = AerospikeKey("test", "demokey", randKey)

  val bin1 = AerospikeBin("value", randValue)
    
  Await.result(client.put(key, Seq(bin1)), 100 millis)
  counter+=1
  val timeAfter = System.currentTimeMillis()
  if (timeAfter-timeBefore > 10000) {
      println(s"Inserts per second: ${counter/10}")
      System.exit(0)
    }
  }


}