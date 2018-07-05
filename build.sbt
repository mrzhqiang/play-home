name := """play-home"""
organization := "com.github.mrzhqiang"
version := "1.0-SNAPSHOT"
scalaVersion := "2.12.6"

lazy val util = project in file("modules/util")

lazy val core = (project in file("modules/core")).dependsOn(util)

lazy val framework = (project in file("modules/framework")).enablePlugins(PlayJava).dependsOn(util)

lazy val service = (project in file("modules/service")).enablePlugins(PlayJava).dependsOn(framework, core)

lazy val rest = (project in file("modules/rest")).enablePlugins(PlayJava).dependsOn(service)

lazy val root = (project in file(".")).enablePlugins(PlayJava).dependsOn(rest).aggregate(util, core, framework, service, rest)

// lazy val third = (project in file("modules/3rd")).dependsOn(core)
