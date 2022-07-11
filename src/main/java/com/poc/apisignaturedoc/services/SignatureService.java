package com.poc.apisignaturedoc.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.poc.apisignaturedoc.converters.Mapper;
import com.poc.apisignaturedoc.dto.Event;
import com.poc.apisignaturedoc.models.Document;
import com.poc.apisignaturedoc.models.Signature;
import com.poc.apisignaturedoc.repositorys.DocumentRepository;
import com.poc.apisignaturedoc.repositorys.SignatureRepository;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.*;

@Component
public class SignatureService {
    private SignatureRepository signatureRepository;
    private DocumentRepository documentRepository;
    private DocumentService documentService;
    public SignatureService(SignatureRepository signatureRepository, DocumentRepository documentRepository, @Lazy
    DocumentService documentService) {
        this.signatureRepository = signatureRepository;
        this.documentRepository = documentRepository;
        this.documentService = documentService;
    }

    public void saveSignature(String value) throws JsonProcessingException {
        Mapper objectMapper = new Mapper();
        Event event = objectMapper.getMapper().readValue(new String(value.getBytes(StandardCharsets.UTF_8)), Event.class);
        for(int i = 0; i < event.getData().getSignatures_number(); i++){
            Signature signature = new Signature();
            signature.setEmail(event.getData().getSignatures().get(i).getEmail());
            signature.setDocIdentificacao(event.getData().getSignatures().get(i).getDocIdentificacao());
            signature.setDocument(event.getData());
            signature.setSignature(false);
            signatureRepository.save(signature);
            System.out.println("Create signatures");
        }
    }


    public List<Signature> getSignatures(String idDocument){
        Document doc = documentRepository.findByIdDocument(idDocument);
        List<Signature> signatures = signatureRepository.findByDocument(doc);
        if(!signatures.isEmpty()){
            return signatures;
        }
        return null;
    }

    public Boolean putSignature(String id_document, String email, String doc){
        Document document = documentRepository.findByIdDocument(id_document);
        Signature signature = signatureRepository.findByDocumentAndEmailAndDocIdentificacao(document, email, doc);
        if(!Objects.isNull(document) && !Objects.isNull(signature)){
            if(documentService.verifyDocument(id_document) && signature.getEmail().equals(email) &&
                    signature.getDocIdentificacao().equals(doc)){
                signature.setSignature(true);
                signature.setSingature_date(LocalDate.now());
                signatureRepository.save(signature);
                return true;
            }
            return false;
        }
        return null;
    }

    public Boolean deleteSignatures(String idDocument){
        Document doc = documentRepository.findByIdDocument(idDocument);
        if (Objects.isNull(doc)) {
            return null;
        }
        try {
            List<Signature> signatures = signatureRepository.findByDocument(doc);
            for(int i = 0; i < signatures.size(); i++){
                signatureRepository.deleteById(signatures.get(i).getId());
            }
            return true;
        }catch (Exception ex){
            System.out.println(ex.getMessage());
        }
        return false;
    }


}
