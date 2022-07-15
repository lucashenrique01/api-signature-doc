package com.poc.apisignaturedoc.services;

import com.poc.apisignaturedoc.contexts.KafkaProperties;
import com.poc.apisignaturedoc.converters.ObjectToGson;
import com.poc.apisignaturedoc.dto.CanceledSignatureEventDto;
import com.poc.apisignaturedoc.dto.EventReadyDto;
import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

import java.io.Closeable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class KafkaDispatcher implements Closeable {

    private final KafkaProducer<String, String> producer;

    private KafkaProperties kafkaProperties;

    public KafkaDispatcher() {
        this.kafkaProperties = new KafkaProperties();
        this.producer = new KafkaProducer<>(this.kafkaProperties.getPropertiesDispatcher());
    }

    public void send(EventReadyDto payload) throws ExecutionException, InterruptedException {
        Future<RecordMetadata> future = sendAsync(payload);
        future.get();
    }
    public void send(CanceledSignatureEventDto payload) throws ExecutionException, InterruptedException {
        Future<RecordMetadata> future = sendAsync(payload);
        future.get();
    }

    public Future<RecordMetadata> sendAsync(EventReadyDto payload) {
        ObjectToGson<EventReadyDto> objectToGson = new ObjectToGson();
        ProducerRecord record = new ProducerRecord<>(payload.getType(),
                payload.getId(),
                objectToGson.eventToJson(payload));
        Callback callback = (data, ex) -> {
            if (ex != null) {
                ex.printStackTrace();
                return;
            }
            System.out.println("sucesso enviando " + data.topic() + ":::partition " + data.partition() + "/ offset " + data.offset() + "/ timestamp " + data.timestamp());
        };
        return producer.send(record, callback);
    }

    public Future<RecordMetadata> sendAsync(CanceledSignatureEventDto payload) {
        ObjectToGson<CanceledSignatureEventDto> objectToGson = new ObjectToGson();
        ProducerRecord record = new ProducerRecord<>(payload.getType(),
                payload.getId(),
                objectToGson.eventToJson(payload));
        Callback callback = (data, ex) -> {
            if (ex != null) {
                ex.printStackTrace();
                return;
            }
            System.out.println("sucesso enviando " + data.topic() + ":::partition " + data.partition() + "/ offset " + data.offset() + "/ timestamp " + data.timestamp());
        };
        return producer.send(record, callback);
    }

    @Override
    public void close() {
        producer.close();
    }
}
