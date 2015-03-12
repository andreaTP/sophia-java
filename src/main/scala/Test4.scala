object Test4 extends App {

  import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import org.h2.tools.DeleteDbFiles;

//org.h2.tools.DeleteDbFile.execute("~", "test", true);

        Class.forName("org.h2.Driver");
        val conn = DriverManager.getConnection("jdbc:h2:./test");
        val stat = conn.createStatement();

        stat.execute("create table test(key varchar(255) primary key, value varchar(255))");
        //stat.execute("insert into test values(1, 'Hello')");
  var counter = 0
  
  val timeBefore = System.currentTimeMillis()
  
  while (true) {
    
    import scala.util.Random
    
    val randKey = Random.nextLong+"ciao"
    val randValue = Random.nextLong+"ciao"
    
    val res = stat.execute(s"insert into test values('${randKey}', '${randValue}')");
    
    /*if (res != true) {
      println("Errore!!")
    } else {
      counter+=1
    }*/
    counter+=1
    val timeAfter = System.currentTimeMillis()
    if (timeAfter-timeBefore > 10000) {
      println(s"Inserts per second: ${counter/10}")
      System.exit(0)
    }
  }
  
}