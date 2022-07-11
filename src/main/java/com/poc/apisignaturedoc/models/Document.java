package com.poc.apisignaturedoc.models;

import com.fasterxml.jackson.annotation.JsonProperty;


import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Document {

    @OneToMany(mappedBy = "document")
    @JsonProperty("signatures")
    private List<Signature> signatures = new ArrayList<>();
    @JsonProperty("signatures_number")
    private Integer signatures_number;
    @Id
    @JsonProperty("id_document")
    private String idDocument;
    @JsonProperty("limit_date")
    private String limitDate;


    public List<Signature> getSignatures() {
        return signatures;
    }

    public void setSignatures(List<Signature> signatures) {
        this.signatures = signatures;
    }

    public Integer getSignatures_number() {
        return signatures_number;
    }

    public void setSignatures_number(Integer signatures_number) {
        this.signatures_number = signatures_number;
    }

    public String getIdDocument() {
        return idDocument;
    }

    public void setIdDocument(String idDocument) {
        this.idDocument = idDocument;
    }

    public String getLimitDate() {
        return limitDate;
    }

    public void setLimitDate(String limitDate) {
        this.limitDate = limitDate;
    }

}
