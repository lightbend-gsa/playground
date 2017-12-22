
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

lazy val `rx-rabbit` = (project in file("."))
  .aggregate(`rx-rabbit-api`, `rx-rabbit-impl`)

lazy val `rx-rabbit-api` = (project in file("rx-rabbit-api"))
  .settings(common: _*)
  .settings(
    resolvers += Cinnamon.resolver.commercial,
    libraryDependencies ++= Seq(
      lagomJavadslApi) ++ cinnamonDependencies
  )

lazy val `rx-rabbit-impl` = (project in file("rx-rabbit-impl"))
  .enablePlugins(LagomJava)
  .enablePlugins(SbtReactiveAppPlugin)
  .settings(common: _*)
  .settings(
    name := "reactive-rabbitmq",
    libraryDependencies ++= Seq(
      lagomJavadslTestKit,
      "com.rabbitmq" % "amqp-client" % "5.0.0",
      "com.lightbend.akka" %% "akka-stream-alpakka-amqp" % "0.14"
    ),
    dockerRepository := Some("265690947920.dkr.ecr.us-west-1.amazonaws.com"),
    packageName in Docker := packageName.value,
    dockerUpdateLatest := true
  )
  .settings(lagomForkedTestSettings: _*)
  .dependsOn(`rx-rabbit-api`)

def common = Seq(
  javacOptions in compile += "-parameters"
)
