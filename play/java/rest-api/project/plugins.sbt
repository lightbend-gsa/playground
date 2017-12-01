// The Play plugin
addSbtPlugin("com.typesafe.play" % "sbt-plugin" % "2.6.6")

addSbtPlugin("com.lightbend.cinnamon" % "sbt-cinnamon" % "2.6.1")

addSbtPlugin("com.typesafe.sbt" % "sbt-native-packager" % "1.3.2")

credentials += Credentials(Path.userHome / ".lightbend" / "commercial.credentials")

resolvers += Resolver.url("lightbend-commercial",
  url("https://repo.lightbend.com/commercial-releases"))(Resolver.ivyStylePatterns)
