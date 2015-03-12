
import eu.unicredit.sophia.client.SophiaClient

object Test2 extends App {
/*
  println("Ci riproviamo!")
  
  val client = new SophiaClient()
  
  client.start
  
  client.setSophiaPath("./storage")
  
  val db = client.use("test")
  
  client.put(db, "hello", "world")
  
  val ret = client.get(db, "hello")
  
  val stringa = new String(ret.array.map(_.toChar))
  
  println("Value found is "+stringa)
  
  client.stop
  
  println("fine")
*/  
  
  println("Performance test")
  
  
  
  val client = new SophiaClient()
  
  client.start
  
  client.setSophiaPath("./storage")
  
  val db = client.use("test")
  
  var counter = 0
  
  val timeBefore = System.currentTimeMillis()
  
  while (true) {
    
    import scala.util.Random
    
    val randKey = Random.nextLong+"ciao"
    val randValue = Random.nextLong+"ciao"
    
    val res = client.put(db, randKey, randValue)
    
    if (res != 0) {
      println("Errore!!")
    } else {
      counter+=1
    }
    
    val timeAfter = System.currentTimeMillis()
    if (timeAfter-timeBefore > 10000) {
      println(s"Inserts per second: ${counter/10}")
      System.exit(0)
    }
  }
  
}