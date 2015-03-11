
name := "Sophia_DB_JNI"

organization := "unicredit"

scalaVersion := "2.11.5"

fork in run := true

javaOptions += "-Djava.library.path=./"

libraryDependencies ++= Seq(
      "org.scalatest" % "scalatest_2.11" % "2.2.1" % "test"
)
