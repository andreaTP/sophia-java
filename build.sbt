
name := "Sophia_Java"

organization := "unicredit"

scalaVersion := "2.11.6"

scalacOptions ++= Seq(
  "-deprecation",
  "-feature",
  "-language:implicitConversions"
)

fork in run := true

javaOptions += "-Djava.library.path=./"

libraryDependencies ++= Seq(
      "org.scalatest" % "scalatest_2.11" % "2.2.1" % "test"
)
