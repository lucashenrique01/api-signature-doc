package com.poc.apisignaturedoc.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
public class Signature {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @JsonProperty("email")
    private String email;
    @JsonProperty("doc_identificacao")
    private String docIdentificacao;
    private Boolean signature;
    @JsonProperty("singature_date")
    private LocalDate singature_date;

    @ManyToOne
    @JoinColumn(name="fkDocument", referencedColumnName="idDocument")
    private Document document;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Document getDocument() {
        return document;
    }

    public void setDocument(Document document) {
        this.document = document;
    }

    public Integer getId_db() {
        return id;
    }

    public void setId_db(Integer id_db) {
        this.id = id_db;
    }

    public LocalDate getSingature_date() {
        return singature_date;
    }

    public void setSingature_date(LocalDate singature_date) {
        this.singature_date = singature_date;
    }

    public String getDocIdentificacao() {
        return docIdentificacao;
    }

    public void setDocIdentificacao(String doc_identificacao) {
        this.docIdentificacao = doc_identificacao;
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
