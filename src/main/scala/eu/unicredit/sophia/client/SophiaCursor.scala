package eu.unicredit.sophia.client

import java.io.Closeable

class SophiaCursor(
                    client: SophiaClient,
                    db: Long, 
                    valuesizemem: Option[Long] = None, 
                    cursoraddr: Option[Long] = None,
                    dbobj: Option[Long] = None) extends Closeable {
    import client._
  
    val valuesize =
      if (valuesizemem.isDefined) 
        valuesizemem.get
      else 
        si.allocate_mem(4).getAddress
  
    val obj =
      if (dbobj.isDefined) 
        dbobj.get
      else
        si.sp_object(db)
  
    val cursor = 
      if (cursoraddr.isDefined) 
        cursoraddr.get
      else
        si.sp_cursor(db, obj)
    
    val elem: Option[Long] = 
      try {
        val ret = si.sp_get(cursor, obj)
        assert {ret != 0}
        Some(ret)
      } catch {
        case err :Throwable =>
          this.close()
          None
      } 
  
    val hasNext: Boolean = 
      elem.isDefined
    
    lazy val nextPtr =
       elem.get
    
    lazy val nextKey =
       getElem(keyPtr, elem.get)
       
    lazy val nextValue =
       getElem(valuePtr, elem.get)
       
    lazy val nextCursor = 
      new SophiaCursor(client, db, Some(valuesize), Some(cursor), Some(obj))
    
    lazy val nextOptCursor = {
      val ret = nextCursor
      if (ret.hasNext) Some(ret)
      else None
    }
  
    override def close() = {
      si.sp_destroy(cursor)
      si.free_mem(valuesize)
    }
  }
  