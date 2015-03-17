package eu.unicredit.sophia

import java.nio.ByteBuffer

package object client {
  
  trait Allocable {
    def length: Long
    def writeByteArray: (ByteBuffer => Unit)
    
    def array: Array[Byte] 
  }
  
  def getCString(array: Array[Byte]) =
    new String(
      (for {i <- 0.to(array.length-2)}
      yield {array(i).toChar}).toArray)    
  
  case class AllocableString(str: String) extends Allocable {
    def length: Long = str.length()+1
    def writeByteArray: (ByteBuffer => Unit) = (buffer: ByteBuffer) => {
      buffer.put(array)
      buffer.put(0.toByte)
    }
    
    def array = str.getBytes()
    
    override def equals(x: Any) = x match {
      case as: AllocableString => as.str == str
      case s: String => s == str
    }
    
    override def toString = 
      new String(
      (for {i <- 0.to(array.length-2)}
      yield {array(i).toChar}).toArray)
  }
  
  case class AllocableByteArray(ba: Array[Byte]) extends Allocable {
    def length: Long = ba.length
    def writeByteArray: (ByteBuffer => Unit) = (buffer: ByteBuffer) => {
      buffer.put(ba)
    }
    def array = ba
    
    override def toString = 
      new String(
      (for {i <- 0.to(array.length-1)}
      yield {array(i).toChar}).toArray)
  }
  
  /*
  def sophiaUseStr[String, R](vals: String*)(f: (Map[String, Long]) => R): (SophiaInterface) => R = 
    sophiaUseStr[String,R](vals.toList)(f)
  def sophiaUseStr[String, R](vals: List[String])(f: (Map[String, Long]) => R): (SophiaInterface) => R = {
    (si: SophiaInterface) => {
      
      val map = 
        vals.map(x => {
    		x -> allocate(AllocableString(x.toString))(si).getAddress()
    	}).toMap
    	
      val ret = f(map)
      
      map.values.map(si.free_mem(_))
      
      ret
    }
  }  
  */
  implicit def fromStringToAllocable(str: String) = 
    AllocableString(str)
   
//  def sophiaUse[T <: Allocable, R](vals: T*)(f: Map[T, Long] => R): (SophiaInterface) => R = 
//    sophiaUse[T,R](vals.toList)(f)
  def sophiaUse[T <: Allocable, R](vals: T*)(f: Map[T, Long] => R): (SophiaInterface) => R = {
    (si: SophiaInterface) => {
      val map = 
        vals.map(x => {
    		x -> allocate(x)(si).getAddress()
    	}).toMap
    	
      val ret = f(map)
      
      map.values.map(si.free_mem(_))
      
      ret
    }
  }
  
  
//  def sophiaUse[T <: Allocable, R](vals: T*)(f: Map[T, Long] => Unit): (SophiaInterface) => Unit =
//    sophiaUse[T](vals.toList)(f)
  /*
  def sophiaUse[T <: Allocable](vals: T*)(f: Map[T, Long] => Unit): (SophiaInterface) => Unit = {
    (si: SophiaInterface) => {
      val map = 
        vals.map(x => {
    		x -> allocate(x)(si).getAddress()
    	}).toMap
    	
      f(map)
      
      map.values.map(si.free_mem(_))
    }
  }
  */
  def allocate[T <: Allocable](x: T): (SophiaInterface) => MemoryArea = (si) => {
	val ma = si.allocate_mem(x.length)
	x.writeByteArray(ma.buffer)
	ma
  }
    

}