package com.poc.apisignaturedoc.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Signatures {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_db;
    @JsonProperty("id_document")
    private String id_document;
    @JsonProperty("email")
    private String email;
    private Boolean signature;

    public Integer getId() {
        return id_db;
    }

    public void setId(Integer id) {
        this.id_db = id;
    }

    public String getId_document() {
        return id_document;
    }

    public void setId_document(String id_document) {
        this.id_document = id_document;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Boolean getSignature() {
        return signature;
    }

    public void setSignature(Boolean signature) {
        this.signature = signature;
    }
}
