package com.poc.apisignaturedoc.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.poc.apisignaturedoc.converters.Mapper;
import com.poc.apisignaturedoc.dto.Event;
import com.poc.apisignaturedoc.models.Document;
import com.poc.apisignaturedoc.repositorys.DocumentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.Objects;
import java.util.Optional;

@Component
public class DocumentService {

    private DocumentRepository documentRepository;

    @Autowired
    private SignatureService signatureService;

    private KafkaProducerService kafkaProducerService;

    public DocumentService (DocumentRepository documentRepository, @Lazy KafkaProducerService kafkaProducerService) {
        this.documentRepository = documentRepository;
        this.kafkaProducerService = kafkaProducerService;
    }

    public void saveDocument(String value) throws JsonProcessingException {
        Mapper objectMapper = new Mapper();
        Event event = objectMapper.getMapper().readValue(new String(value.getBytes(StandardCharsets.UTF_8)), Event.class);
        if(!existDocument(event.getData().getIdDocument())){
            Document document = new Document();
            document.setIdDocument(event.getData().getIdDocument());
            document.setLimitDate(event.getData().getLimitDate());
            document.setSignatures_number(event.getData().getSignatures_number());
            documentRepository.save(document);
            signatureService.saveSignature(value);
            System.out.println("Create document");
        } else {
            System.out.println("Documento já existe na base de dados");
        }
    }

    private Boolean existDocument(String id_document){
        return documentRepository.existsByIdDocument(id_document);
    }

    public Document getDocument(Integer id){
        Optional<Document> document = documentRepository.findById(id);
        return document.get();
    }

    public Boolean verifyDocument(String id_document){
        Document doc = documentRepository.findByIdDocumentAndLimitDateAfter(id_document,LocalDate.now());
        return !Objects.isNull(doc);
    }
    @Transactional
    public Boolean deleteDocument(String idDocument) {
        Document doc = documentRepository.findByIdDocument(idDocument);
        if (Objects.isNull(doc)) {
            return null;
        }
        try{
            documentRepository.deleteByIdDocument(doc.getIdDocument());
        } catch (Exception ex){
            System.out.println(ex.getMessage());
        }
        return true;
    }
}
