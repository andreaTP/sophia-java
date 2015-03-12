import eu.unicredit.sophia._

object Test /*extends App*/ {
  
  println("Starting!!!")
  
  val si = new SophiaInterface()
	  
  val env = si.sp_env() 
  val ctl = si.sp_ctl(env)
	  
  val sophiaPath = "sophia.path".getBytes()
  
  val storagePath = "/home/andrea/workspace/sophia-java/storage".getBytes()
    
  //have to terminate with 0 strings in java....argggghhh
  val bufferPath = si.allocate_mem(sophiaPath.length+1)
  val bufferStorPath = si.allocate_mem(storagePath.length+1)
  
  bufferPath.buffer.put(sophiaPath)
  bufferPath.buffer.put(0.toByte)
  bufferStorPath.buffer.put(storagePath)
  bufferStorPath.buffer.put(0.toByte)
  
  val setted = 
    si.sp_set(ctl, bufferPath.getAddress(), bufferStorPath.getAddress())
  
  si.free_mem(bufferPath.getAddress())
  si.free_mem(bufferStorPath.getAddress())
  
  println(s"Setted is ${setted}")
  
  //Creating database
  val dbStr = "db".getBytes()
  val testStr = "test".getBytes()
  
  val bufferDb = si.allocate_mem(dbStr.length+1)
  val bufferTest = si.allocate_mem(testStr.length+1)
  
  bufferDb.buffer.put(dbStr)
  bufferDb.buffer.put(0.toByte)
  bufferTest.buffer.put(testStr)
  bufferTest.buffer.put(0.toByte)
  
  val setDb = 
    si.sp_set(ctl, bufferDb.getAddress(), bufferTest.getAddress())
  
  println("SetDB is "+setDb)
  
  si.free_mem(bufferDb.getAddress())
  si.free_mem(bufferTest.getAddress())
  
  
  
  //void *db = sp_get(ctl, "db.test");
  val dbName = "db.test".getBytes()
  
  val bufferDBName = si.allocate_mem(dbName.length+1)
  
  bufferDBName.buffer.put(dbName)
  bufferDBName.buffer.put(0.toByte)
  
  val db = si.sp_get(ctl, bufferDBName.getAddress())
  
  println("Db address is "+db)
  
  si.free_mem(bufferDBName.getAddress())
    
  val rc = si.sp_open(env)
  
  println("Env opened: "+rc)
  
  
  
  /* insert */
  val keyStr = "key".getBytes()
  val valueStr = "value".getBytes()
  
  val bufferKey = si.allocate_mem(keyStr.length+1)
  val bufferValue = si.allocate_mem(valueStr.length+1)
  
  bufferKey.buffer.put(keyStr)
  bufferKey.buffer.put(0.toByte)
  bufferValue.buffer.put(valueStr)
  bufferValue.buffer.put(0.toByte)
  
  //val helloStr = setStr("hello")
  //val worldStr = setStr("world")
  val helloStr = "hello".getBytes()
  val worldStr = "world".getBytes()
  
  val bufferHello = si.allocate_mem(helloStr.length+1)
  val bufferWorld = si.allocate_mem(worldStr.length+1)
  
  bufferHello.buffer.put(helloStr)
  bufferHello.buffer.put(0.toByte)
  bufferWorld.buffer.put(worldStr)
  bufferWorld.buffer.put(0.toByte)
  
  
    
  val o = si.sp_object(db)
  
  val s1 = si.sp_set(o, bufferKey.getAddress(), bufferHello.getAddress(), 6)
  val s2 = si.sp_set(o, bufferValue.getAddress(), bufferWorld.getAddress(), 6)
  
  println("S1 --> "+s1)
  println("S2 --> "+s2)
  
  val rc2 = si.sp_set(db, o)
  
  println("Setted result is "+rc2)
  
  //si.free_mem(bufferKey.getAddress())
  //si.free_mem(bufferValue.getAddress())
  //si.free_mem(bufferHello.getAddress())
  //si.free_mem(bufferWorld.getAddress())
  
  // Getting back value
  
  val o2 = si.sp_object(db)
  
  println("Object is "+o2)
  
  
  val keyStr2 = "key".getBytes()
    
  val bufferKey2 = si.allocate_mem(keyStr.length+1)
    
  bufferKey2.buffer.put(keyStr)
  bufferKey2.buffer.put(0.toByte)
  
  val helloStr2 = "hello".getBytes()
  
  val bufferHello2 = si.allocate_mem(helloStr.length+1)
  
  bufferHello2.buffer.put(helloStr)
  bufferHello2.buffer.put(0.toByte)
  
  val sixNum = si.allocate_mem(4)
  
  sixNum.buffer.putInt(6)
  
  //val sett = si.sp_set(o, bufferKey2.getAddress(), bufferHello2.getAddress(), sixNum.getAddress())
  val sett = si.sp_set(o, bufferKey2.getAddress(), bufferHello2.getAddress(), 6)
  
  println("Il set è --> "+sett)
  
  val result = si.sp_get(db, o2)

  println("Result is "+result)
  val valuesize = si.allocate_mem(4)
  
  val valueStr2 = setStr("value")
  
  println("5")
  
  val stringaPtr = si.sp_get(result, valueStr2, valuesize.getAddress())
  
  println("Stringa is "+stringaPtr)
  if (stringaPtr == -1) {
    println("ok")
    System.exit(0)
  }
    
  println("6")

  val dimensione =  valuesize.buffer.get() + (valuesize.buffer.get()<<8)
  
  println("Dimensione della stringa è --> "+dimensione)
  
  val resultStr = si.get_mem(stringaPtr, dimensione)
  
  println("Prima")
  var acc: Array[Byte] = new Array(dimensione)
  
  for {i <- 0.to(dimensione-1)}
  	acc(i) = resultStr.buffer.get()
	  
  println("Array is "+acc)
  
  println("Dopo")
  
  val stringati = new String(acc.map(_.toChar))
  
  println("Result della stringa è -->  "+stringati)
    //printf("%s\n", value);
    //sp_destroy(result);
  
  val destroyed = si.sp_destroy(env)
  println(s"Destroyed ${destroyed}")
  /*
  val si2 = new SophiaInterface();
  
  val capacity = 4
  
  val buffer1 = si1.allocate_mem(capacity);
  
  println(s"Buffer1 --> ${buffer1.getAddress()} , ${buffer1.getCapacity()} , ${buffer1.buffer}")
  
  buffer1.buffer.putInt(111)
  buffer1.buffer.rewind()
  
  val value1 = buffer1.buffer.asIntBuffer().get()
  
  println(s"Setting Value now $value1")
  
  val buffer2 = si2.get_mem(buffer1.getAddress(), capacity)
  
  val value2 = buffer2.buffer.asIntBuffer().get()
  
  println(s"Getting Value now $value2")
  
  val address = si1.get_mem_address(buffer2.buffer);
  
  println(s"Address $address is the same? ${address==buffer1.getAddress()}")
  
  val retCapacity = si1.get_mem_capacity(buffer2.buffer);
  
  println(s"Capacity is $retCapacity and the same? ${retCapacity==buffer1.getCapacity()}")
  */
  println("Ending!!!")

  
  def setStr(_str: String): Long = {
      val str = _str.getBytes()
      val bufferStr = si.allocate_mem(str.length+1)
        
      bufferStr.buffer.put(str)
      bufferStr.buffer.put(0.toByte)
      
      bufferStr.getAddress()
  } 
    
}