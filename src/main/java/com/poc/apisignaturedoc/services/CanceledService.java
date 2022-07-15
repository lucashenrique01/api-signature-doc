package com.poc.apisignaturedoc.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.poc.apisignaturedoc.contexts.KafkaProperties;
import com.poc.apisignaturedoc.converters.Mapper;
import com.poc.apisignaturedoc.converters.ObjectToGson;
import com.poc.apisignaturedoc.dto.CancelCommandDto;
import com.poc.apisignaturedoc.dto.CanceledSignatureEventDto;
import com.poc.apisignaturedoc.dto.DataCancelCommandDto;
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
import java.util.concurrent.ExecutionException;

@Service
public class CanceledService {
    private DocumentService documentService;
    private SignatureService signatureService;


    public CanceledService(SignatureService signatureService, DocumentService documentService
                           ){
        this.signatureService = signatureService;
        this.documentService = documentService;
    }


    public void getMessages(){

        String topic = "br.com.example.orchestrator.cancel";
        KafkaProperties kafkaProperties = new KafkaProperties();
        Mapper objectMapper = new Mapper();

        final Consumer<String, String> consumer = new KafkaConsumer<>(kafkaProperties.getProperties());
        consumer.subscribe(Arrays.asList(topic));
        try {
            while (true) {
                ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(100));
                for (ConsumerRecord<String, String> record : records) {
                    String value = record.value();
                    CancelCommandDto idDocument = objectMapper.getMapper()
                            .readValue(new String(value.getBytes(StandardCharsets.UTF_8)), CancelCommandDto.class);
                    deleteSignaturesAndSendEvent(idDocument.getData().getIdDocument());
                }
            }
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            consumer.close();
        }
    }

    private void deleteSignaturesAndSendEvent(String idDocument) throws ExecutionException, InterruptedException {
        signatureService.deleteSignatures(idDocument);
        documentService.deleteDocument(idDocument);
        CanceledSignatureEventDto canceledSignatureEventDto = new CanceledSignatureEventDto();
        UUID uuid = UUID.randomUUID();
        String uuidAsString = uuid.toString();
        canceledSignatureEventDto.setId(uuidAsString);
        LocalDateTime localDate = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
        canceledSignatureEventDto.setTime(localDate.format(formatter));
        canceledSignatureEventDto.setType("br.com.example.signature.canceled");
        canceledSignatureEventDto.setSpecVersion("1.0");
        canceledSignatureEventDto.setSubject("Signatures canceled");
        DataCancelCommandDto dataCancelCommandDto = new DataCancelCommandDto();
        dataCancelCommandDto.setIdDocument(idDocument);
        canceledSignatureEventDto.setData(dataCancelCommandDto);
        KafkaDispatcher kafkaDispatcher = new KafkaDispatcher();
        kafkaDispatcher.send(canceledSignatureEventDto);
    }
}
