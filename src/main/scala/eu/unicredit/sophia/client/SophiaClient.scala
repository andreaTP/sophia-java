package eu.unicredit.sophia.client

import eu.unicredit.sophia.client._
import scala.concurrent.Promise
import eu.unicredit.sophia.SophiaInterface

class SophiaClient extends ReusableStrings {
  
  val si = new SophiaInterface()
  
  val prom_env = Promise[Long]
  lazy val env = prom_env.future.value.get.get
  
  val prom_ctl = Promise[Long]
  lazy val ctl = prom_ctl.future.value.get.get
   
  def start = {
	prom_env.success(si.sp_env())
	prom_ctl.success(si.sp_ctl(env))
  }
  
  def setSophiaPath(path: String) = {
    sophiaUse(all("sophia.path"), all(path)) {map => {
    	si.sp_set(ctl, map("sophia.path"), map(path))
    }}(si)
  }
  
  def use(dbName: String): Long = {
    sophiaUse(all("db"), all(dbName), all(s"db.$dbName")) {map => {
    	si.sp_set(ctl, map("db"), map(dbName))
    	val ret = si.sp_get(ctl, map(s"db.$dbName"))
    	val check = si.sp_open(env)
    	if (check!=0) throw new Exception("Cannot open requested table")
    	else ret
    }}(si)
  }
  
  def put(db: Long, key: Allocable, value: Allocable): Int =
    put(db, key, value, db)
  def put(db: Long, key: Allocable, value: Allocable, transaction: Long): Int = {
    sophiaUse(key, value) {map => {
       	val o = si.sp_object(db)
    	
    	si.sp_set(o, keyPtr, map(key), key.length)
    	si.sp_set(o, valuePtr, map(value), value.length)
    	
    	val res = si.sp_set(transaction, o);
    	if (res != 0) throw new Exception("Cannot insert object in db "+res)
    	res
    }}(si)
  }
  
  def get(db: Long, key: Allocable): Allocable =
    get(db, key, db)
  def get(db: Long, key: Allocable, transaction: Long): Allocable = {
    sophiaUse(key) {map => {
    	val valuesize = si.allocate_mem(4)
       	val o = si.sp_object(db)
       	
    	si.sp_set(o, keyPtr, map(key), key.length)
    	
    	val res = si.sp_get(transaction, o)
    	val resultPtr = si.sp_get(res, valuePtr, valuesize.getAddress())
  
    	if (resultPtr == -1) throw new Exception("Cannot read result "+resultPtr) 
    
       	val size =  valuesize.buffer.get() + (valuesize.buffer.get()<<8)
       	
       	val resultArr = si.get_mem(resultPtr, size)
  
       	val arr: Array[Byte] = new Array(size)
       	resultArr.buffer.get(arr)
       	     	
    	val result = AllocableByteArray(arr)
    	
    	result
    }}(si)
  }
  
  def delete(db: Long, key: Allocable): Int =
    delete(db, key, db)
  def delete(db: Long, key: Allocable, transaction: Long): Int = {
    sophiaUse(key) {map => {
       	val o = si.sp_object(db)
       	
    	si.sp_set(o, keyPtr, map(key), key.length)
    	
    	val res = si.sp_delete(transaction, o)
    	
    	res
    }}(si)
  }
  
  def startTransaction: Long = {
    val transaction = si.sp_begin(env)
    if (!transaction.isValidLong)
      throw new Exception("Cannot initialize transaction")
    
    transaction
  }

  /*
   	Possible returns from commit
	case -1: /* error */
    case  0: /* ok */
    case  1: /* rollback */
    case  2: /* lock */
   */
  def commitTransaction(transaction: Long): Int = {
    si.sp_commit(transaction)
  }
  
  def stop = {
    si.sp_destroy(env)
  }
  
  
  def all(str: String) = AllocableString(str)
}