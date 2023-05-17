package com.nisum.challenge.controller;

import com.nisum.challenge.enumerator.ValidationEnum;
import com.nisum.challenge.exception.ValidationException;
import com.nisum.challenge.presenter.ValidationPresenter;
import com.nisum.challenge.service.ValidationService;
import com.nisum.challenge.util.TestData;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ValidationControllerTest {

    @Mock
    private ValidationService validationService;

    @InjectMocks
    @Spy
    private final ValidationController validationController = new ValidationController();
    private final TestData testData = new TestData();

    @Test
    public void shouldResponseOKGetValidations() {
        when(validationService.getValidations()).thenReturn(Collections.singletonList(testData.validationPresenterFake(ValidationEnum.EMAIL_VALIDATION)));
        List<ValidationPresenter> validationPresenter = validationController.listValidations();
        Assertions.assertThat(validationPresenter).isNotEmpty();
    }
    @Test
    public void shouldResponseOKSaveValidation() {
        ValidationPresenter validationPresenter = testData.validationPresenterFake(ValidationEnum.EMAIL_VALIDATION);
        when(validationService.saveValidation(validationPresenter)).thenReturn(validationPresenter);
        ValidationPresenter validation = validationController.saveValidation(validationPresenter);
        Assertions.assertThat(validationPresenter).isEqualTo(validation);
    }

    @Test
    public void shouldResponseExceptionSaveValidation() {
        ValidationPresenter validationPresenter = testData.validationPresenterFake(ValidationEnum.EMAIL_VALIDATION);
        when(validationService.saveValidation(validationPresenter)).thenThrow(new ValidationException("Fake Error"));
        Throwable exception = Assertions.catchThrowable(()->validationController.saveValidation(validationPresenter));
        Assertions.assertThat(exception).isInstanceOf(ValidationException.class);
        Assertions.assertThat(exception.getMessage()).contains("Fake Error");
    }

}
