
name := "Sophia_DB_JNI"

organization := "unicredit"

scalaVersion := "2.11.5"

fork in run := true

javaOptions += "-Djava.library.path=./"

//"eu.unicredit" %% "reactive-aerospike" % "0.1.6",
//"com.h2database" % "h2" % "1.4.186",
      

libraryDependencies ++= Seq(
      "org.scalatest" % "scalatest_2.11" % "2.2.1" % "test"
)
