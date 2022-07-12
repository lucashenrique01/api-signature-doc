package com.poc.apisignaturedoc.dto;

import java.util.List;

public class DocumentReadyDto {

    private String idDocument;
    private List<String> email;

    public String getIdDocument() {
        return idDocument;
    }

    public void setIdDocument(String idDocument) {
        this.idDocument = idDocument;
    }

    public List<String> getEmail() {
        return email;
    }

    public void setEmail(List<String> email) {
        this.email = email;
    }
}
