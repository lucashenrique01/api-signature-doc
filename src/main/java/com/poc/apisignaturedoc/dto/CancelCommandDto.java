package com.poc.apisignaturedoc.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CancelCommandDto {
    @JsonProperty("id")
    private String id;
    @JsonProperty("specVersion")
    private String specVersion;
    @JsonProperty("source")
    private String source;
    @JsonProperty("type")
    private String type;
    @JsonProperty("subject")
    private String subject;
    @JsonProperty("time")
    private String time;
    @JsonProperty("correlationId")
    private String correlationId;
    @JsonProperty("dataContentType")
    private String dataContentType;
    @JsonProperty("data")
    private DataCancelCommandDto data;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSpecVersion() {
        return specVersion;
    }

    public void setSpecVersion(String specVersion) {
        this.specVersion = specVersion;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getCorrelationId() {
        return correlationId;
    }

    public void setCorrelationId(String correlationId) {
        this.correlationId = correlationId;
    }

    public String getDataContentType() {
        return dataContentType;
    }

    public void setDataContentType(String dataContentType) {
        this.dataContentType = dataContentType;
    }

    public DataCancelCommandDto getData() {
        return data;
    }

    public void setData(DataCancelCommandDto data) {
        this.data = data;
    }
}
