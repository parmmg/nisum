package com.nisum.challenge.service;


import com.nisum.challenge.entity.Configuration;
import com.nisum.challenge.enumerator.ConfigurationNameEnum;
import com.nisum.challenge.presenter.ConfigurationPresenter;

import java.util.List;


public interface ConfigurationService {

    Configuration getConfigurationByName(ConfigurationNameEnum name);

    ConfigurationPresenter saveConfiguration(ConfigurationPresenter configurationPresenter);

    List<ConfigurationPresenter> getConfigurations();

}
