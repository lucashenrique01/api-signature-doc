package com.poc.apisignaturedoc.repositorys;

import com.poc.apisignaturedoc.models.Signatures;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SignatureRepository extends JpaRepository<Signatures, Integer> {
}
