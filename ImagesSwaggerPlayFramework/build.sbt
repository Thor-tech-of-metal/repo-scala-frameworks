import sbt.Keys._

scalaVersion := "2.11.11"


resolvers ++= Seq(
  Resolver.sonatypeRepo("releases"),
  Resolver.typesafeRepo("releases"),
  Resolver.bintrayRepo("websudos", "oss-releases")
)


libraryDependencies += "net.codingwell" %% "scala-guice" % "4.1.0"
libraryDependencies += "io.swagger" %% "swagger-play2" % "1.5.3"
dependencyOverrides += "com.fasterxml.jackson.core" % "jackson-core" % "2.8.7"
dependencyOverrides += "com.fasterxml.jackson.core" % "jackson-databind" % "2.8.7"
dependencyOverrides += "com.fasterxml.jackson.module" % "jackson-module-scala_2.11" % "2.8.7"
libraryDependencies += "ai.x" %% "play-json-extensions" % "0.10.0"


// Dependecies comming from the project/Dependencies.scala file.

libraryDependencies ++= {
  import Dependencies._
  Seq(filters, scalaTestPlus, cats, phantomCassandra, scalaGuice)
}


// The Play project itself
lazy val root = (project in file("."))
  .enablePlugins(PlayScala)
  .settings(
    name := """ImagesSwaggerPlayFramework"""
  )

