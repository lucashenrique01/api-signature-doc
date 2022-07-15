package com.poc.apisignaturedoc.controllers;


import com.poc.apisignaturedoc.services.SignatureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;
import java.util.concurrent.ExecutionException;


@RestController
@RequestMapping("/signatures")
public class SignatureController {

    @Autowired
    SignatureService signatureService;


    @GetMapping
    public ResponseEntity getSignatures(@PathVariable String id_document){
        if (signatureService.getSignatures(id_document).isEmpty()) {
            return ResponseEntity.status(204).build();
        }
        return ResponseEntity.status(200).body(signatureService.getSignatures(id_document));
    }

    @PutMapping("/{id_document}/{email}/{doc}")
    public ResponseEntity putSignature(@PathVariable String id_document,
                                        @PathVariable String email,
                                        @PathVariable String doc) throws ExecutionException, InterruptedException {

        if(signatureService.putSignature(id_document, email, doc)){
            return ResponseEntity.status(200).build();
        } else if (Objects.isNull(signatureService.putSignature(id_document, email, doc))){
            return ResponseEntity.status(404).build();
        }
        return ResponseEntity.status(401).build();
    }

    @DeleteMapping("/{idDocument}")
    public ResponseEntity deleteSignatures(String idDocument){
        if(signatureService.deleteSignatures(idDocument)){
            return ResponseEntity.status(200).build();
        }
        return ResponseEntity.status(404).build();
    }
}
