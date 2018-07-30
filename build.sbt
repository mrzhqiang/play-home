name := """play-home"""

lazy val commonSetting = Seq(
  organization := "com.github.mrzhqiang",
  version := "1.0-SNAPSHOT",
  scalaVersion := "2.12.6",
)

lazy val util = (project in file("modules/util"))
  .settings(commonSetting)

lazy val core = (project in file("modules/core"))
  .settings(commonSetting)
  .enablePlugins(PlayMinimalJava, PlayEbean)
  .dependsOn(util)

lazy val framework = (project in file("modules/framework"))
  .settings(commonSetting)
  .enablePlugins(PlayMinimalJava)
  .dependsOn(core)

lazy val service = (project in file("modules/service"))
  .settings(commonSetting)
  .enablePlugins(PlayMinimalJava)
  .dependsOn(framework)

lazy val rest = (project in file("modules/rest"))
  .settings(commonSetting)
  .enablePlugins(PlayMinimalJava)
  .dependsOn(service)

lazy val root = (project in file("."))
  .settings(commonSetting)
  .enablePlugins(PlayJava)
  .dependsOn(rest)
  .aggregate(util, core, framework, service, rest)

lazy val third = (project in file("modules/3rd"))
  .settings(commonSetting)
  .dependsOn(core)
