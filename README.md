

Kafka Demo

Content
•	Basics of Kafka
•	Twitter Producer
•	ElasticSearch Consumer
•	Kafka Connect Example

Setup Kafka and Zookeeper
	Min. JDK8 needed
	Set Path C:\kafka\bin\windows after copying binary for Kafka
o	tar -xzf kafka_2.12-2.5.0.tgz
o	set PATH=C -> this will refresh the environment variables without rebooting windows

Start Zookeeper:
	zookeeper-server-start.bat C:\kafka\config\zookeeper.properties

Start Kafka:
	kafka-server-start.bat c:\kafka\config\server.properties

Example commands:

	kafka-topics --bootstrap-server localhost:9092  --topic first_topic --create    

	kafka-topics --bootstrap-server localhost:9092 --topic first_topic --create --partitions 3 --replication-factor 2                                               


	kafka-topics --bootstrap-server localhost:9092  --list                                                                                                           

	kafka-topics --bootstrap-server localhost:9092 --topic first_topic --describe                                                                                                                                                                    

	kafka-console-producer.bat --bootstrap-server localhost:9092 --topic fourth.topic                                                                        

	kafka-console-consumer.bat --bootstrap-server localhost:9092 --topic first_topic --from-beginning

https://app.bonsai.io/clusters/kafka-bonsai-elastic-5711035019



Kafka Connect

Source  => Kafka              --- Producer API			(Kafka Connect Source)
Kafka	=> Kafka	-- Consumer, Producer API	(Kafka Streams)
Kafka	=> Sink		-- Consumer API		(Kafka Connect Sink)
Kafka	=> App		-- Consumer API


