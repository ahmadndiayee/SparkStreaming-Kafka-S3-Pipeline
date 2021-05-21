import StreamingPipelineApp.{path, spark}
import org.apache.spark
import org.apache.spark.sql.{DataFrame, SparkSession}
import org.apache.spark.sql.streaming.Trigger
import org.apache.spark.sql.types.{DoubleType, IntegerType, StringType, StructField, StructType}

val KAFKA_TOPIC = "s3-kafka"
val KAFKA_SERVER = "localhost:9092"
val KAFKA_CHECKPOINT = "src/main/resources/checkpoint/"

object StreamingPipeline {

  val schema = StructType(List(
    StructField("id", StringType, true),
    StructField("first_name", StringType, true),
    StructField("last_name", StringType, true),
    StructField("date_of_birth", StringType, true)
  ))

  def checkDirectory(spark: SparkSession, path: String): DataFrame = {
    val streamingDF = spark
      .readStream
      .option("inferSchema", true)
      .schema(schema)
      .csv(path)
    streamingDF
  }

  def sendToKafka(df: DataFrame) : Unit = {
    df
      .selectExpr("id AS key", "CONCAT_WS(',', id, first_name, last_name, date_of_birth) AS value")
      .writeStream
      .format("kafka")
      .option("kafka.bootstrap.servers", KAFKA_SERVER)
      .option("topic", KAFKA_TOPIC)
      .option("checkpointLocation", KAFKA_CHECKPOINT)
      .start()
      .awaitTermination()
  }

}
