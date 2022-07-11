package com.poc.apisignaturedoc;

import com.poc.apisignaturedoc.controllers.KafkaController;
import com.poc.apisignaturedoc.repositorys.DocumentRepository;
import com.poc.apisignaturedoc.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;

@SpringBootApplication
public class ApiSignatureDocApplication {
	public static void main(String[] args) {
		SpringApplication.run(ApiSignatureDocApplication.class, args);
	}
}
