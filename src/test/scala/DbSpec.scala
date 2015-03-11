
import org.scalatest._
import eu.unicredit.sophia._

class DbSpec extends FlatSpec with SequentialNestedSuiteExecution {
  
  "DB " should "be created and destroyed" in {
	  val si = new SophiaInterface()
	  
	  val env = si.sp_env()
	  val ctl = si.sp_ctl(env)
	  
	  val destroyed = si.sp_destroy(env)
    
	  println(s"Destroyed ${destroyed}")
	  
	  assert { destroyed === 0 }
  }

}