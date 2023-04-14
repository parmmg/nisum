package com.nisum.challenge.service;


import com.nisum.challenge.entity.Configuration;
import com.nisum.challenge.config.enumerator.ConfigurationNameEnum;
import com.nisum.challenge.exception.ValidationException;
import com.nisum.challenge.presenter.ConfigurationPresenter;
import com.nisum.challenge.repository.ConfigurationRepository;
import com.nisum.challenge.service.impl.ConfigurationServiceImpl;
import com.nisum.challenge.util.TestData;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class ConfigurationServiceTest {
    @Mock
    private ConfigurationRepository configurationRepository;

    private final ConfigurationService configurationService = new ConfigurationServiceImpl();
    private final TestData testData = new TestData();

    @Test
    public void shouldGetConfigurations(){
        List<Configuration> configurations = new ArrayList<>();
        configurations.add(testData.configurationFake(ConfigurationNameEnum.EMAIL_VALIDATION));
        when(configurationRepository.findAll()).thenReturn(configurations);
        List<ConfigurationPresenter> configurationPresenters = configurationService.getConfigurations();
        Assertions.assertThat(configurationPresenters).isNotEmpty();
    }

    @Test
    public void shouldGetConfigurationByName() {
        Configuration configuration = testData.configurationFake(ConfigurationNameEnum.EMAIL_VALIDATION);
        when(configurationRepository.findByName(ConfigurationNameEnum.EMAIL_VALIDATION)).thenReturn(Optional.of(configuration));
        Configuration configurationSearched = configurationService.getConfigurationByName(ConfigurationNameEnum.EMAIL_VALIDATION);
        Assertions.assertThat(configuration.getName()).isEqualTo(configurationSearched.getName());
    }

    @Test
    public void shouldThrowExceptionWhenConfigurationByNameNotExist() {
        when(configurationRepository.findByName(ConfigurationNameEnum.EMAIL_VALIDATION)).thenReturn(Optional.empty());
        Assertions.assertThatThrownBy(() ->configurationService.getConfigurationByName(ConfigurationNameEnum.EMAIL_VALIDATION)).isInstanceOf(ValidationException.class)
                .hasMessageContaining("Configuration");
    }

    @Test
    public void shouldSaveConfiguration() {
        ConfigurationPresenter configurationPresenter = testData.configurationPresenterFake(ConfigurationNameEnum.EMAIL_VALIDATION);
        Configuration configuration = testData.configurationFake(ConfigurationNameEnum.EMAIL_VALIDATION);
        when(configurationRepository.findByName(ConfigurationNameEnum.EMAIL_VALIDATION)).thenReturn(Optional.of(configuration));
        ConfigurationPresenter configurationPresenterSaved = configurationService.saveConfiguration(configurationPresenter);
        Assertions.assertThat(configurationPresenterSaved.getId()).isEqualTo(configurationPresenter.getId());
    }

    @Test
    public void shouldThrowExceptionWhenSaveConfigurationWithNameNotExist() {
        ConfigurationPresenter configurationPresenter = testData.configurationPresenterFake(ConfigurationNameEnum.EMAIL_VALIDATION);
        configurationPresenter.setName("nameNotExist");
        Assertions.assertThatThrownBy(() ->configurationService.saveConfiguration(configurationPresenter)).isInstanceOf(ValidationException.class)
                .hasMessageContaining("Configuration");
    }

}
