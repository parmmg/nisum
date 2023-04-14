package com.nisum.challenge.repository;

import com.nisum.challenge.entity.Phone;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PhoneRepository  extends CrudRepository<Phone, UUID> {

}