package com.poc.apisignaturedoc.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.poc.apisignaturedoc.contexts.KafkaProperties;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.springframework.stereotype.Component;
import java.time.Duration;
import java.util.Arrays;
@Component
public class KafkaService {

    private DocumentService documentService;

    public KafkaService(DocumentService documentService){
        this.documentService = documentService;
    }

    public void getMessages(String topic){
        KafkaProperties kafkaProperties = new KafkaProperties();

        // Add additional required properties for this consumer app
        final Consumer<String, String> consumer = new KafkaConsumer<>(kafkaProperties.getProperties());
        consumer.subscribe(Arrays.asList(topic));
        try {
            while (true) {
                ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(100));
                for (ConsumerRecord<String, String> record : records) {
                    String value = record.value();
                    documentService.saveDocument(value);
                }
            }
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        } finally {
            consumer.close();
        }
    }
}
