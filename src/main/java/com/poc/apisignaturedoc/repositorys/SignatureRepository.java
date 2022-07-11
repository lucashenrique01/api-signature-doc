package com.poc.apisignaturedoc.repositorys;

import com.poc.apisignaturedoc.models.Document;
import com.poc.apisignaturedoc.models.Signature;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SignatureRepository extends JpaRepository<Signature, Integer> {

    Signature findByDocumentAndEmailAndDocIdentificacao(Document document, String email, String doc);

    void deleteAllByDocument(Document document);

    List<Signature> findByDocument(Document id);
}
