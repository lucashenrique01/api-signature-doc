package com.poc.apisignaturedoc.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Message {
    private Data data;


    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }
}
