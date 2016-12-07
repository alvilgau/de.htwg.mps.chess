name := "de.htwg.mps.chess"

version := "1.0"

scalaVersion := "2.11.8"

resolvers += "Akka Snapshot Repository" at "http://repo.akka.io/snapshots/"

libraryDependencies ++= {
  val scalaTestV = "3.0.0-M15"
  val scalaMockV = "3.2.2"
  Seq(
    "org.scalatest" %% "scalatest" % scalaTestV % "test"
      exclude("org.scala-lang", "scala-reflect")
      exclude("org.scala-lang.modules", "scala-xml_2.11"),
    "org.scalamock" %% "scalamock-scalatest-support" % scalaMockV % "test"
      exclude("org.scala-lang", "scala-reflect")
  )
}

libraryDependencies += "com.typesafe.akka" %% "akka-actor" % "2.4-SNAPSHOT"