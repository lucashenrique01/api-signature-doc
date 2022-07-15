package com.poc.apisignaturedoc.controllers;

import com.poc.apisignaturedoc.services.DocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

@RestController
@RequestMapping("/documents")
public class    DocumentController {

    @Autowired
    private DocumentService documentService;

    @GetMapping
    public ResponseEntity getDocument(@PathVariable Integer id){
        if (Objects.isNull(documentService.getDocument(id))) {
            return ResponseEntity.status(404).build();
        }
        return ResponseEntity.status(200).body(documentService.getDocument(id));
    }

    @GetMapping("/{id_document}")
    public ResponseEntity verifyDocument(@PathVariable String id_document){
        Boolean result = documentService.verifyDocument(id_document);
        if(result) return ResponseEntity.status(200).build();
        return ResponseEntity.status(404).build();
    }
}
