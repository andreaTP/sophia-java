
import org.scalatest._
import eu.unicredit.sophia._

class MemorySpec extends FlatSpec with SequentialNestedSuiteExecution {

  
  "The Sophia interface " should "be created and memory can be managed from java to C seemlessly" in {
      val si1 = new SophiaInterface();
      val si2 = new SophiaInterface();
  
      val capacity = 4
  
      val buffer1 = si1.allocate_mem(capacity);
  
      buffer1.buffer.putInt(111)
      buffer1.buffer.rewind()
       
      val value1 = buffer1.buffer.asIntBuffer().get()
  
      val buffer2 = si2.get_mem(buffer1.getAddress(), capacity)
  
      val value2 = buffer2.buffer.asIntBuffer().get()

      assert {value1 === value2}
  
      val address = si1.get_mem_address(buffer2.buffer);
  
      assert {address === buffer1.getAddress()}
  
      val retCapacity = si1.get_mem_capacity(buffer2.buffer);
      
      assert {retCapacity === buffer1.getCapacity()}
   
  }
}