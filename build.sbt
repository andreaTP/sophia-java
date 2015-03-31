
name := "Sophia_Java"

organization := "unicredit"

scalaVersion := "2.11.6"

version := "0.0.2-SNAPSHOT"

scalacOptions ++= Seq(
  "-deprecation",
  "-feature",
  "-target:jvm-1.7",
  "-language:implicitConversions"
)

fork in run := true

javaOptions += "-Djava.library.path=./"

javacOptions ++= Seq("-source", "1.7", "-target", "1.7")

libraryDependencies ++= Seq(
      "org.scalatest" % "scalatest_2.11" % "2.2.1" % "test"
)
