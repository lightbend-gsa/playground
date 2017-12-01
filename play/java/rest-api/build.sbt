name := """rest-api"""
organization := "com.example"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayJava, Cinnamon, JavaAppPackaging)

scalaVersion := "2.12.2"

libraryDependencies += guice

libraryDependencies ++= Seq(
  Cinnamon.library.cinnamonCHMetrics,
  Cinnamon.library.cinnamonAkka,
  Cinnamon.library.cinnamonOpsClarity
)

dockerRepository := Some("265690947920.dkr.ecr.us-west-1.amazonaws.com")
dockerUpdateLatest := true
