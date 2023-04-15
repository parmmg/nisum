package com.nisum.challenge.service.impl;


import com.nisum.challenge.entity.Configuration;
import com.nisum.challenge.enumerator.ConfigurationNameEnum;
import com.nisum.challenge.exception.ValidationException;
import com.nisum.challenge.presenter.ConfigurationPresenter;
import com.nisum.challenge.repository.ConfigurationRepository;
import com.nisum.challenge.service.ConfigurationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class ConfigurationServiceImpl implements ConfigurationService {
    @Autowired
    private ConfigurationRepository configurationRepository;

    @Override
    public Configuration getConfigurationByName(ConfigurationNameEnum name) {
        return configurationRepository.findByName(name).orElseThrow(() -> new ValidationException("Configuration " + name + " Not Found"));
    }

    @Override
    public ConfigurationPresenter saveConfiguration(ConfigurationPresenter configurationPresenter) {
        try {
            ConfigurationNameEnum configurationName = ConfigurationNameEnum.valueOf(configurationPresenter.getName());
            Configuration configuration = configurationRepository.findByName(configurationName).orElse(new Configuration());
            configuration.setName(configurationName);
            configuration.setMessage(configurationPresenter.getMessage());
            configuration.setPattern(configurationPresenter.getPattern());
            configurationRepository.save(configuration);
            configurationPresenter.setId(configuration.getId());
            return configurationPresenter;
        } catch (IllegalArgumentException e) {
            throw new ValidationException("Configuration " + configurationPresenter.getName() + " not found");
        }
    }

    @Override
    public List<ConfigurationPresenter> getConfigurations() {
        List<ConfigurationPresenter> configurationPresenters = new ArrayList<>();
        configurationRepository.findAll().forEach(configuration -> configurationPresenters.add(toPresenter(configuration)));
        return configurationPresenters;
    }

    private ConfigurationPresenter toPresenter (Configuration configuration) {
        return ConfigurationPresenter.builder()
                .id(configuration.getId())
                .name(configuration.getName().toString())
                .message(configuration.getMessage())
                .pattern(configuration.getPattern())
                .build();
    }
}
