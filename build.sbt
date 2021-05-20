name := "StreamingPipeline"

version := "0.1"

scalaVersion := "2.12.0"


libraryDependencies ++= Seq (
  "org.apache.spark" %% "spark-core" % "2.4.5",
  "org.apache.spark" %% "spark-sql" % "2.4.5",
  "org.apache.spark" %% "spark-streaming" % "2.4.5",
  "com.typesafe" % "config" % "1.3.1",
  "org.apache.spark" %% "spark-sql-kafka-0-10" % "2.4.5"

)
//resolvers += "Spark Packages Repo" at "http://dl.bintray.com/spark-packages/maven"