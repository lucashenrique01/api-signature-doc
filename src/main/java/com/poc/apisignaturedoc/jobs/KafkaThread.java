package com.poc.apisignaturedoc.jobs;

import com.poc.apisignaturedoc.services.KafkaService;

public class KafkaThread extends Thread{
    KafkaService kafkaService;

    public KafkaThread(KafkaService kafkaService){
        this.kafkaService=kafkaService;
    }

    @Override
    public void run() {
        kafkaService.getMessages("br.com.example.document.created");
    }
}
