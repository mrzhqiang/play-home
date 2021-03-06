name := "core"

// https://mvnrepository.com/artifact/junit/junit
libraryDependencies += "junit" % "junit" % "4.12" % Test
// https://mvnrepository.com/artifact/redis.clients/jedis
libraryDependencies += "redis.clients" % "jedis" % "2.9.0"
libraryDependencies += "mysql" % "mysql-connector-java" % "5.1.41"

libraryDependencies += guice
