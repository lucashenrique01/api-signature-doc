package com.poc.apisignaturedoc.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DataCancelCommand {
    @JsonProperty("idDocument")
    private String idDocument;

    public String getIdDocument() {
        return idDocument;
    }

    public void setIdDocument(String idDocument) {
        this.idDocument = idDocument;
    }
}
