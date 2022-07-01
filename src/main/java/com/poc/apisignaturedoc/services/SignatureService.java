package com.poc.apisignaturedoc.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.poc.apisignaturedoc.models.Data;
import com.poc.apisignaturedoc.models.Message;
import com.poc.apisignaturedoc.models.Signatures;
import com.poc.apisignaturedoc.repositorys.SignatureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;

@Component
public class SignatureService {

    @Autowired
    private SignatureRepository signatureRepository;

    public void saveSignature(String value) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        Message signatures = objectMapper.readValue(new String(value.getBytes(StandardCharsets.UTF_8)), Message.class);
        System.out.println("PRINTOU: " + signatures);
    }
}
