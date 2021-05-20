import java.io.File

import com.typesafe.config.ConfigFactory
import org.apache.spark.sql.SparkSession

object StreamingPipelineApp extends App {
  val configFile = ConfigFactory.parseFile(new File("src/main/resources/stp.conf"))

  val spark : SparkSession = SparkSession.
    builder().
    master("local[*]").
    appName("StreamingPipeline").
    getOrCreate()
  spark.sparkContext.setLogLevel("WARN")


  val path = configFile.getString("STP.SOURCES.PATH")
  val df = StreamingPipeline.checkDirectory(spark, path)
//  df.writeStream
//    .format("console")
//    .outputMode("append")
//    .start()             // Start the computation
//    .awaitTermination()
  StreamingPipeline.sendToKafka(df)



}