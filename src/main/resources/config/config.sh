#export KAFKA_HOME=
$KAFKA_HOME/bin/zookeeper-server-start.sh $KAFKA_HOME/config/zookeeper.properties
$KAFKA_HOME/bin/kafka-server-start.sh $KAFKA_HOME/config/server.properties
$KAFKA_HOME/bin/connect-standalone.sh config/connect-standalone.properties config/s3-connector.properties
spark-submit --class StreamingPipelineApp chemin/vers/monapplication.jar