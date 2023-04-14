package com.nisum.challenge.controller;

import com.nisum.challenge.config.enumerator.ConfigurationNameEnum;
import com.nisum.challenge.exception.ValidationException;
import com.nisum.challenge.infraestructure.Response;
import com.nisum.challenge.presenter.ConfigurationPresenter;
import com.nisum.challenge.service.ConfigurationService;
import com.nisum.challenge.util.TestData;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ConfigurationControllerTest {

    @Mock
    private ConfigurationService configurationService;

    private final ConfigurationController configurationController = new ConfigurationController();
    private final TestData testData = new TestData();

    @Test
    public void shouldResponseOKGetConfigurations() {
        when(configurationService.getConfigurations()).thenReturn(Collections.singletonList(testData.configurationPresenterFake(ConfigurationNameEnum.EMAIL_VALIDATION)));
        Response response = configurationController.listConfigurations();
        Assertions.assertThat(response.getMessage()).isEqualTo("SUCCESS");
    }
    @Test
    public void shouldResponseOKSaveConfiguration() {
        ConfigurationPresenter configurationPresenter = testData.configurationPresenterFake(ConfigurationNameEnum.EMAIL_VALIDATION);
        when(configurationService.saveConfiguration(configurationPresenter)).thenReturn(configurationPresenter);
        Response response = configurationController.saveConfiguration(configurationPresenter);
        Assertions.assertThat(response.getMessage()).isEqualTo("SUCCESS");
    }

    @Test
    public void shouldResponseExceptionSaveConfiguration() {
        ConfigurationPresenter configurationPresenter = testData.configurationPresenterFake(ConfigurationNameEnum.EMAIL_VALIDATION);
        when(configurationService.saveConfiguration(configurationPresenter)).thenThrow(new ValidationException("Fake Error"));
        Response response = configurationController.saveConfiguration(configurationPresenter);
        Assertions.assertThat(response.getMessage()).isNotEqualTo("SUCCESS");
    }

}
