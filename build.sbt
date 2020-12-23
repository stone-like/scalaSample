name := """scalaH2Test"""
organization := "com.example"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.13.3"

libraryDependencies += guice
libraryDependencies += "org.scalatestplus.play" %% "scalatestplus-play" % "5.0.0" % "test"
libraryDependencies += "org.scalatest" %% "scalatest" % "3.2.0"  % "test"

libraryDependencies ++= Seq(
  jdbc,
  evolutions,

  "org.scalikejdbc" %% "scalikejdbc"        % "3.5.0",
  "org.scalikejdbc" %% "scalikejdbc-test"   % "3.5.0"   % "test",
  "org.scalikejdbc" %% "scalikejdbc-config"  % "3.5.0",
  "com.h2database"  %  "h2"                 % "1.4.200",
  "ch.qos.logback"  %  "logback-classic"    % "1.2.3",
  "org.scalikejdbc" %% "scalikejdbc-play-initializer" % "2.8.0-scalikejdbc-3.5",
)
// Adds additional packages into Twirl
//TwirlKeys.templateImports += "com.example.controllers._"

// Adds additional packages into conf/routes
// play.sbt.routes.RoutesKeys.routesImport += "com.example.binders._"
