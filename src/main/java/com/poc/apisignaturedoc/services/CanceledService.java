package com.poc.apisignaturedoc.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.poc.apisignaturedoc.contexts.KafkaProperties;
import com.poc.apisignaturedoc.converters.Mapper;
import com.poc.apisignaturedoc.converters.ObjectToGson;
import com.poc.apisignaturedoc.dto.CancelCommand;
import com.poc.apisignaturedoc.dto.CanceledSignatureEvent;
import com.poc.apisignaturedoc.dto.DataCancelCommand;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.UUID;

@Service
public class CanceledService {
    private DocumentService documentService;
    private SignatureService signatureService;
    private KafkaProducerService kafkaProducerService;

    public CanceledService(SignatureService signatureService, DocumentService documentService,
                           KafkaProducerService kafkaProducerService){
        this.signatureService = signatureService;
        this.documentService = documentService;
        this.kafkaProducerService = kafkaProducerService;
    }


    public void getMessages(){

        String topic = "br.com.example.orchestrator.cancel";
        KafkaProperties kafkaProperties = new KafkaProperties();
        Mapper objectMapper = new Mapper();

        // Add additional required properties for this consumer app
        final Consumer<String, String> consumer = new KafkaConsumer<>(kafkaProperties.getProperties());
        consumer.subscribe(Arrays.asList(topic));
        try {
            while (true) {
                ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(100));
                for (ConsumerRecord<String, String> record : records) {
                    String value = record.value();
                    CancelCommand idDocument = objectMapper.getMapper()
                            .readValue(new String(value.getBytes(StandardCharsets.UTF_8)), CancelCommand.class);
                    deleteSignaturesAndSendEvent(idDocument.getData().getIdDocument());
                }
            }
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        } finally {
            consumer.close();
        }
    }

    private void deleteSignaturesAndSendEvent(String idDocument){
        signatureService.deleteSignatures(idDocument);
        documentService.deleteDocument(idDocument);
        CanceledSignatureEvent canceledSignatureEvent = new CanceledSignatureEvent();
        UUID uuid = UUID.randomUUID();
        String uuidAsString = uuid.toString();
        canceledSignatureEvent.setId(uuidAsString);
        LocalDateTime localDate = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
        canceledSignatureEvent.setTime(localDate.format(formatter));
        canceledSignatureEvent.setType("br.com.example.signature.canceled");
        canceledSignatureEvent.setSpecVersion("1.0");
        canceledSignatureEvent.setSubject("Signatures canceled");
        DataCancelCommand dataCancelCommand = new DataCancelCommand();
        dataCancelCommand.setIdDocument(idDocument);
        canceledSignatureEvent.setData(dataCancelCommand);
        ObjectToGson<CanceledSignatureEvent> objectToGson = new ObjectToGson<>();
        kafkaProducerService.sendMessage(uuidAsString, objectToGson.eventToJson(canceledSignatureEvent));
    }
}
