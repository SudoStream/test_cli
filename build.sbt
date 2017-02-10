name := """aeh-cli"""

organization := "io.sudostream.api-antagonist"

version := "1.0.0-SNAPSHOT"

scalaVersion := "2.11.8"

resolvers += "Typesafe Repo" at "http://repo.typesafe.com/typesafe/releases/"

libraryDependencies ++= {
  val akkaV = "2.4.12"
  val akkaHttpVersion = "2.4.11"
  val scalaTestV = "2.2.6"

  Seq(
    "io.sudostream.api-antagonist" %% "messages" % "0.0.1-SNAPSHOT",
    "io.sudostream.api-antagonist" %% "screenplay-guild" % "0.0.1-SNAPSHOT",

    "com.github.scopt" %% "scopt" % "3.5.0",
    "com.typesafe.akka" %% "akka-actor" % akkaV,
    "com.typesafe.akka" %% "akka-stream" % akkaV,
    "com.typesafe.akka" %% "akka-http-core" % akkaHttpVersion,
    "com.typesafe.akka" %% "akka-http-experimental" % akkaHttpVersion,
    "com.typesafe.akka" %% "akka-http-spray-json-experimental" % akkaHttpVersion,
    "com.typesafe.akka" %% "akka-http-testkit" % akkaHttpVersion,
    "com.typesafe.akka" %% "akka-stream-kafka" % "0.13",

    "org.scalatest" %% "scalatest" % "2.2.4" % "test"
  )
}

enablePlugins(JavaAppPackaging)
