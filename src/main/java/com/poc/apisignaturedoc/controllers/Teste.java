package com.poc.apisignaturedoc.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.poc.apisignaturedoc.services.SignatureService;
import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.Duration;
import java.util.Arrays;
import java.util.Properties;

public class Teste {


    /*public Teste(SignatureService signatureService){
        this.signatureService = signatureService;
    }*/

    public void teste() {
        final String topic = "br.com.example.correctTopic";

        // Load consumer configuration settings from a local file
        final Properties props = new Properties();

        // Add additional properties.
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "kafka-java-getting-started");
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());

        // Add additional required properties for this consumer app
        final Consumer<String, String> consumer = new KafkaConsumer<>(props);
        consumer.subscribe(Arrays.asList(topic));

        try {
            while (true) {
                ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(100));
                for (ConsumerRecord<String, String> record : records) {
                    String key = record.key();
                    String value = record.value();
                    System.out.println(
                    String.format("Consumed event from topic %s: key = %-10s value = %s", topic, key, value));

                }
            }
        }
        finally {
            consumer.close();
        }
    }
}
