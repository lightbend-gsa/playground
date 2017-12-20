organization in ThisBuild := "com.example"
version in ThisBuild := "1.0-SNAPSHOT"

// the Scala version that will be used for cross-compiled libraries
scalaVersion in ThisBuild := "2.11.8"

lagomCassandraEnabled in ThisBuild := false
lagomKafkaEnabled in ThisBuild := false

lazy val cinnamonDependencies = Seq(
  Cinnamon.library.cinnamonCHMetrics,
  Cinnamon.library.cinnamonLagom,
  Cinnamon.library.cinnamonOpsClarity
)

lazy val `employee` = (project in file("."))
  .aggregate(`employee-api`, `employee-impl`)

lazy val `employee-api` = (project in file("employee-api"))
  .settings(common: _*)
  .settings(
    resolvers += Cinnamon.resolver.commercial,
    libraryDependencies ++= Seq(
      lagomJavadslApi,
      lombok,
      "org.mongodb" % "mongodb-driver-reactivestreams" % "1.6.0"
    ) ++ cinnamonDependencies
  )

lazy val `employee-impl` = (project in file("employee-impl"))
  .enablePlugins(LagomJava)
  .enablePlugins(SbtReactiveAppPlugin)
  //.enablePlugins(Cinnamon)
  .settings(common: _*)
  .settings(
    name := "reactive-crud-mongo",
    libraryDependencies ++= Seq(
      lagomJavadslPersistenceCassandra,
      lagomJavadslKafkaBroker,
      lagomJavadslTestKit,
      lombok
    ),
    dockerRepository := Some("265690947920.dkr.ecr.us-west-1.amazonaws.com"),
    packageName in Docker := packageName.value,
    dockerUpdateLatest := true
  )
  .settings(libraryDependencies += "com.typesafe" % "config" % "1.3.1")
  .settings(lagomForkedTestSettings: _*)
  .dependsOn(`employee-api`)

val lombok = "org.projectlombok" % "lombok" % "1.16.10"

def common = Seq(
  javacOptions in compile += "-parameters"
)
