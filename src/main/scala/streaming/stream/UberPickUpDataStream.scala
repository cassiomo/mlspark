package streaming.stream

import com.datastax.spark.connector.streaming.DStreamFunctions
import kafka.serializer.StringDecoder
import org.apache.log4j.Logger
import org.apache.spark.SparkConf
import org.apache.spark.streaming.dstream.{DStream, InputDStream}
import org.apache.spark.streaming.kafka.KafkaUtils
import org.apache.spark.streaming.{Seconds, StreamingContext}
import streaming.data.UberPickup.RawUberPickupData

/**
  * Stream from Kafka
  */
object UberPickUpDataStream {

  val localLogger = Logger.getLogger("UberPickupDataStream")

  def main(args: Array[String]) {

    val sparkConf = new SparkConf().setAppName("Raw Uber Pickup")
    sparkConf.setIfMissing("spark.master", "local[5]")
    sparkConf.setIfMissing("spark.cassandra.connection.host", "127.0.0.1")
    sparkConf.setIfMissing("spark.driver.maxResultSize", "2g")
    sparkConf.setIfMissing("spark.driver.extraJavaOptions", "-Xss10M")

    val ssc = new StreamingContext(sparkConf, Seconds(2))

    val kafkaTopicRaw = "raw_uber_pickup"
    val kafkaBroker = "127.0.0.1:9092"

    val cassandraKeyspace = "uber_pickup_data"
    val cassandraTableRaw = "raw_uber_hourly_data"
    val cassandraTableDailyPrediction = "daily_aggregate_kmean_prediction"

    println(s"using cassandraTableDailyPrediction $cassandraTableDailyPrediction")

    val topics: Set[String] = kafkaTopicRaw.split(",").map(_.trim).toSet
    val kafkaParams = Map[String, String]("metadata.broker.list" -> kafkaBroker)

    localLogger.info(s"connecting to brokers: $kafkaBroker")
    localLogger.info(s"kafkaParams: $kafkaParams")
    localLogger.info(s"topics: $topics")

    val rawUberPickupStream = KafkaUtils.createDirectStream[String, String, StringDecoder, StringDecoder](ssc, kafkaParams, topics)
    val parsedUberPickupStream: DStream[RawUberPickupData] = ingestStream(rawUberPickupStream)

    persist(cassandraKeyspace, cassandraTableRaw, cassandraTableDailyPrediction, parsedUberPickupStream)


    localLogger.info("printing output")

    println(parsedUberPickupStream.print(10)) // for demo purposes only

    //Kick off
    ssc.start()

    ssc.awaitTermination()

    ssc.stop()
  }

  def persist(CassandraKeyspace: String, CassandraTableRaw: String,
              cassandraTableDailyPrediction: String,
              parsedUberPickupStream: DStreamFunctions[RawUberPickupData]): Unit = {

    /** Saves the raw data to Cassandra - raw table. */
    parsedUberPickupStream.saveToCassandra(CassandraKeyspace, CassandraTableRaw)

//    parsedUberPickupStream.map { uberpickup =>
//      (uberpickup.dt, uberpickup.lat, uberpickup.lon, uberpickup.base)
//    }.saveToCassandra(CassandraKeyspace, cassandraTableDailyPrediction)
  }

  def ingestStream(rawUberPickupStream: InputDStream[(String, String)]): DStream[RawUberPickupData] = {
    val parsedUberPickupStream = rawUberPickupStream.map(_._2.split(","))
      .map(RawUberPickupData(_))
    parsedUberPickupStream
  }
}
