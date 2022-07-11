package com.poc.apisignaturedoc.controllers;

import com.poc.apisignaturedoc.services.CanceledService;
import com.poc.apisignaturedoc.services.KafkaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/kafka")
public class KafkaController {

    @Autowired
    private KafkaService kafkaService;

    @Autowired
    private CanceledService canceledService;

    @GetMapping("/{topic}")
    public void getMessages(@PathVariable String topic){
        kafkaService.getMessages(topic);
    }

    @GetMapping
    public void getMessages(){
        canceledService.getMessages();
    }

}
