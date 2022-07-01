package com.poc.apisignaturedoc.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Data {

    private Signatures signatures;
    @JsonProperty("uuid")
    private String uuid;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public Signatures getSignatures() {
        return signatures;
    }

    public void setSignatures(Signatures signatures) {
        this.signatures = signatures;
    }
}
