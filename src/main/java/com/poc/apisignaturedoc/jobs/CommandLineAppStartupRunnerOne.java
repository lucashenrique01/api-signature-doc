package com.poc.apisignaturedoc.jobs;

import com.poc.apisignaturedoc.services.CanceledService;
import com.poc.apisignaturedoc.services.KafkaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class CommandLineAppStartupRunnerOne implements ApplicationRunner {
    @Autowired
    private KafkaService kafkaService;
    @Autowired
    private CanceledService canceledService;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        KafkaThread kafkaThread = new KafkaThread(kafkaService);
        CanceledServiceThread canceledServiceThread = new CanceledServiceThread(canceledService);
        kafkaThread.start();
        canceledServiceThread.start();
    }
}
