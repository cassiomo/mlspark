
name := "mlspark"

version := "1.0"

//assemblyOption in assembly := (assemblyOption in assembly).value.copy(includeScala = false)
//
//assemblyMergeStrategy in assembly := {
//  case PathList("org", "apache", "spark", "unused", "UnusedStubClass.class") => MergeStrategy.first
//  case PathList(pl @ _*) if pl.contains("log4j.properties") => MergeStrategy.concat
//  case PathList("META-INF", "io.netty.versions.properties") => MergeStrategy.last
//  case x =>
//    val oldStrategy = (assemblyMergeStrategy in assembly).value
//    oldStrategy(x)
//}

//fork := true

fork in run := true
javaOptions in run ++= Seq(
  "-Dlog4j.debug=true",
  "-Dlog4j.configuration=log4j.properties")

// only relevant for Java sources --
javacOptions ++= Seq("-source", "1.8", "-target", "1.8")

scalaVersion := "2.11.8"

scalacOptions ++= Seq("-unchecked", "-deprecation")

libraryDependencies += "org.apache.spark" %% "spark-core" % "2.2.0"
libraryDependencies += "org.apache.spark" %% "spark-streaming" % "2.2.0"
libraryDependencies += "org.apache.spark" %% "spark-sql" % "2.2.0"
libraryDependencies += "org.apache.spark" %% "spark-hive" % "2.2.0"
libraryDependencies += "org.apache.spark" %% "spark-graphx" % "2.2.0"
libraryDependencies += "org.apache.spark" %% "spark-mllib" % "2.2.0"
libraryDependencies += "org.apache.spark" %% "spark-streaming-kafka-0-8" % "2.2.0"
//libraryDependencies += "org.apache.spark" %% "spark-streaming-kafka-0-8_2.11" % "2.2.0"
libraryDependencies += "com.github.scopt" %% "scopt" % "3.3.0"
libraryDependencies += "org.apache.spark" %% "spark-streaming-flume" % "2.2.0"
libraryDependencies += "org.apache.hadoop" % "hadoop-aws" % "2.8.0"
libraryDependencies += "com.amazonaws" % "aws-java-sdk" % "1.11.46"
libraryDependencies += "joda-time" % "joda-time" % "2.9.9"
libraryDependencies += "org.apache.httpcomponents" % "httpclient" % "4.5.3"
libraryDependencies += "com.fasterxml.jackson.core" % "jackson-databind" % "2.4.0"
libraryDependencies += "org.apache.crunch" % "crunch-core" % "0.15.0"
//libraryDependencies += "org.apache.hbase" % "hbase-server" % "3.0.0"
//libraryDependencies += "org.apache.hbase" % "hbase-client" % "1.2.1"
//libraryDependencies += "org.apache.hbase" % "hbase-common" % "1.2.1"
libraryDependencies += "org.apache.hadoop" % "hadoop-common" % "2.7.3"
libraryDependencies +=  "org.apache.spark" %% "spark-streaming" % "1.3.1"
libraryDependencies += "com.github.scopt" %% "scopt" % "3.2.0"
libraryDependencies +=  "com.datastax.spark" % "spark-cassandra-connector_2.11" % "2.0.0-RC1"

// needed to make the hiveql examples run at least on Linux
javaOptions in run += "-XX:MaxPermSize=128M"

scalacOptions += "-target:jvm-1.8"

// note: tested directly using sbt with -java-home pointing to a JDK 1.8