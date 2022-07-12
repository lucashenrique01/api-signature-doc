package com.poc.apisignaturedoc.repositorys;

import com.poc.apisignaturedoc.models.Document;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDate;

public interface DocumentRepository extends JpaRepository<Document, Integer> {

    Document findByIdDocument(String id_document);
    Boolean existsByIdDocument(String id_document);
    Document findByIdDocumentAndLimitDateAfter(String id_document, LocalDate date);
    void deleteByIdDocument(String idDocument);

}
