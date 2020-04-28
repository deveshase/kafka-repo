package app;

import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;

public class ProducerDemoWithCallback {
    public static void main(String[] args) {

        final Logger logger = LoggerFactory.getLogger(ProducerDemoWithCallback.class);

        //create producer properties
        Properties properties = new Properties();
        properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

        //create producer
        KafkaProducer<String, String> producer = new KafkaProducer<String, String>(properties);


        for (int i=0; i<10; i++) {
            ProducerRecord<String, String> producerRecord = new ProducerRecord<String,
                    String>("sixth.topic",
                    "Message "+ i +" with callback");


            producer.send(producerRecord, new Callback() {
                public void onCompletion(RecordMetadata recordMetadata, Exception e) {
                    if (e == null) {
                        logger.info("Message metadata: \n" +
                                "Topic: " + recordMetadata.topic() + "\n " +
                                "Partition: " + recordMetadata.partition() + "\n " +
                                "Offset: " + recordMetadata.offset() + "\n " +
                                "Timestamp: " + recordMetadata.timestamp());
                    } else {
                        logger.error(e.getMessage());

                    }

                }
            });
        }

        producer.flush();
        producer.close();
        System.out.println("Message Sent and ack");



        //Send data


    }
}
