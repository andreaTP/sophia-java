package eu.unicredit.sophia.client

trait ReusableStrings {
  self: SophiaClient =>
    
  lazy val keyPtr = allocate(AllocableString("key"))(si).getAddress()
    
  lazy val valuePtr = allocate(AllocableString("value"))(si).getAddress()

}