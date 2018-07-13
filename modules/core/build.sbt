name := "core"
scalaVersion := "2.12.6"


libraryDependencies += guice
// https://mvnrepository.com/artifact/junit/junit
libraryDependencies += "junit" % "junit" % "4.12" % Test
// https://mvnrepository.com/artifact/org.slf4j/slf4j-nop
libraryDependencies += "org.slf4j" % "slf4j-nop" % "1.7.25" % Test
// https://mvnrepository.com/artifact/redis.clients/jedis
libraryDependencies += "redis.clients" % "jedis" % "2.9.0"
// https://mvnrepository.com/artifact/com.datastax.cassandra/cassandra-driver-core
libraryDependencies += "com.datastax.cassandra" % "cassandra-driver-core" % "3.5.1"
// https://mvnrepository.com/artifact/com.datastax.cassandra/cassandra-driver-mapping
libraryDependencies += "com.datastax.cassandra" % "cassandra-driver-mapping" % "3.5.1"
// https://mvnrepository.com/artifact/com.datastax.cassandra/cassandra-driver-extras
libraryDependencies += "com.datastax.cassandra" % "cassandra-driver-extras" % "3.5.1"
