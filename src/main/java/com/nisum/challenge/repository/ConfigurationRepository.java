package com.nisum.challenge.repository;

import com.nisum.challenge.entity.Configuration;
import com.nisum.challenge.config.enumerator.ConfigurationNameEnum;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ConfigurationRepository extends CrudRepository<Configuration, UUID> {

    Optional<Configuration> findByName(ConfigurationNameEnum name);

}
