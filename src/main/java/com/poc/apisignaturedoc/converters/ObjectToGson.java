package com.poc.apisignaturedoc.converters;

import com.google.gson.Gson;

public class ObjectToGson<T> {

    public String eventToJson(T event){
        Gson gson = new Gson();
        String jsonString = gson.toJson(event);
        return jsonString;
    }
}
