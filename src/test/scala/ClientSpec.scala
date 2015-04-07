import org.scalatest._
import eu.unicredit.sophia._
import eu.unicredit.sophia.client._

class ClientSpec extends FlatSpec with SequentialNestedSuiteExecution {
  
  val client = new SophiaClient()
  
  client.start
  
  client.setSophiaPath("./storage")
  
  val db = client.use("test")
  
  "The Sophia client " should "amazingly ease the use of the db!" in {
    
      client.put(db, "hello", "world")
  
      val ret = client.get(db, "hello")
  
      val value = getCString(ret.array)
      
      assert { value === "world" }
  }
  
  it should "make use of transactions..." in {

      val transaction = client.startTransaction
  
      client.put(db, "hello", "moon", transaction)
  
      val before = client.get(db, "hello")
      
      val tResult = client.commitTransaction(transaction)
      
      val after = client.get(db, "hello")
      
      val valueBefore = getCString(before.array)
      
      val valueAfter = getCString(after.array)
      
      assert { valueBefore === "world" }
      
      assert { valueAfter === "moon" }
      
	  assert { tResult === 0 }
  }
  
  it should "end gracefully the db" in {
    val stop = client.stop
    
    assert {stop === 0}
  }
}