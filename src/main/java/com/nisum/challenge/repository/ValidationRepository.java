package com.nisum.challenge.repository;

import com.nisum.challenge.entity.Validation;
import com.nisum.challenge.enumerator.ValidationEnum;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ValidationRepository extends CrudRepository<Validation, UUID> {

    Optional<Validation> findByName(ValidationEnum name);

}
