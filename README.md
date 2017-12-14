#mlspark



bin/zkServer.sh start conf/zoo_sample.cfg 
bin/kafka-server-start.sh config/server.properties
bin/kafka-topics.sh --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic raw_uber_pickup
bin/cqlsh < uber.csv
bin/cassandra


export SPARK_MASTER_HOST="localhost"
sbin/start-master.sh
sbin/start-slave.sh spark://localhost:7077
bin/kafka-console-producer.sh --broker-list localhost:9092 --topic raw_uber_pickup < uber.csv  
