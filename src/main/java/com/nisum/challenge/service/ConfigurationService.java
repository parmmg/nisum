package com.nisum.challenge.service;


import com.nisum.challenge.entity.Configuration;
import com.nisum.challenge.config.enumerator.ConfigurationNameEnum;
import com.nisum.challenge.presenter.ConfigurationPresenter;


public interface ConfigurationService {

    Configuration getConfigurationByName(ConfigurationNameEnum name);

    ConfigurationPresenter saveConfiguration(ConfigurationPresenter configurationPresenter);

    Iterable<Configuration> getConfigurations();

}
