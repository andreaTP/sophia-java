import org.scalatest._
import eu.unicredit.sophia._
import eu.unicredit.sophia.client._
import scala.annotation.tailrec

import scala.util.Random

class CursorSpec extends FlatSpec with SequentialNestedSuiteExecution {
  
   
  val client = new SophiaClient()
  
  client.start
  
  client.setSophiaPath("./storage")
  
  val db = client.use("cursorTest")
  
  def randomKey = s"key-${new String(Random.alphanumeric.take(10).toArray)}"
  
  def randomSize = Random.nextInt(500)
  
  def randomValue = s"$randomSize-${new String(Random.alphanumeric.take(randomSize).toArray)}"
  
  val inserts = 101
  
  val allKeys =
    for (_<-1.to(inserts)) 
    yield randomKey
  
  "The Sophia client " should "insert a bunch of elements" in {
      
      for (key <- allKeys) {
        //println(s"Inserting $randomKey -> $randomValue")
        client.put(db, key, randomValue)
      }  
  }
  
  @tailrec
  private def iterate(cursor: SophiaCursor, f: (String,String) => Unit): Unit = {
      if (!cursor.hasNext)
        return
      else {
         val key = cursor.nextKey
         val value = cursor.nextValue
         
         f(getCString(key.array), getCString(value.array))
         
         val nextOpt = cursor.nextOptCursor
         if (!nextOpt.isDefined)
           return
         else
           iterate(nextOpt.get,f)
      }
    }
  
  it should "cursor over them" in {
    
    val cursor = new SophiaCursor(client, db)
    
    var keyInserted = allKeys
    
    val checkPresence: (String,String) => Unit = 
      (key, value) => {
         //println(s"Key: $key, value: $value")
         keyInserted = keyInserted.filterNot { x => x === key }
      }
    
    assert { allKeys.size == keyInserted.size}
    
    iterate(cursor, checkPresence)
    
    assert { keyInserted.isEmpty == true } 
    
  }

}