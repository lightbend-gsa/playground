// The Lagom plugin
addSbtPlugin("com.lightbend.lagom" % "lagom-sbt-plugin" % "1.3.10")
// Needed for importing the project into Eclipse
addSbtPlugin("com.typesafe.sbteclipse" % "sbteclipse-plugin" % "5.1.0")

addSbtPlugin("com.lightbend.rp" % "sbt-reactive-app" % "0.4.4")

addSbtPlugin("com.lightbend.cinnamon" % "sbt-cinnamon" % "2.6.2")

// Credentials and resolver to download the Cinnamon Telemetry libraries
credentials += Credentials(Path.userHome / ".lightbend" / "commercial.credentials")
resolvers += Resolver.url("lightbend-commercial",
  url("https://repo.lightbend.com/commercial-releases"))(Resolver.ivyStylePatterns)
