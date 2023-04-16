package com.nisum.challenge.service;


import com.nisum.challenge.entity.Validation;
import com.nisum.challenge.enumerator.ValidationEnum;
import com.nisum.challenge.exception.ValidationException;
import com.nisum.challenge.presenter.ValidationPresenter;
import com.nisum.challenge.repository.ValidationRepository;
import com.nisum.challenge.service.impl.ValidationServiceImpl;
import com.nisum.challenge.util.TestData;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class ValidationServiceTest {
    @Mock
    private ValidationRepository validationRepository;

    @InjectMocks
    @Spy
    private final ValidationService validationService = new ValidationServiceImpl();
    private final TestData testData = new TestData();

    @Test
    public void shouldGetValidations(){
        List<Validation> validations = new ArrayList<>();
        validations.add(testData.validationFake(ValidationEnum.TEST_VALIDATION));
        when(validationRepository.findAll()).thenReturn(validations);
        List<ValidationPresenter> validationPresenters = validationService.getValidations();
        Assertions.assertThat(validationPresenters).isNotEmpty();
    }

    @Test
    public void shouldGetValidationByName() {
        Validation validation = testData.validationFake(ValidationEnum.TEST_VALIDATION);
        when(validationRepository.findByName(ValidationEnum.TEST_VALIDATION)).thenReturn(Optional.of(validation));
        Validation validationSearched = validationService.getValidationByName(ValidationEnum.EMAIL_VALIDATION);
        Assertions.assertThat(validation.getName()).isEqualTo(validationSearched.getName());
    }

    @Test
    public void shouldThrowExceptionWhenValidationByNameNotExist() {
        when(validationRepository.findByName(ValidationEnum.TEST_VALIDATION)).thenReturn(Optional.empty());
        Assertions.assertThatThrownBy(() -> validationService.getValidationByName(ValidationEnum.TEST_VALIDATION)).isInstanceOf(ValidationException.class)
                .hasMessageContaining("Configuration");
    }

    @Test
    public void shouldSaveValidation() {
        ValidationPresenter validationPresenter = testData.validationPresenterFake(ValidationEnum.TEST_VALIDATION);
        Validation validation = testData.validationFake(ValidationEnum.TEST_VALIDATION);
        when(validationRepository.findByName(ValidationEnum.TEST_VALIDATION)).thenReturn(Optional.of(validation));
        ValidationPresenter validationPresenterSaved = validationService.saveValidation(validationPresenter);
        Assertions.assertThat(validationPresenterSaved.getId()).isEqualTo(validationPresenter.getId());
    }

    @Test
    public void shouldThrowExceptionWhenSaveValidationWithNameNotExist() {
        ValidationPresenter validationPresenter = testData.validationPresenterFake(ValidationEnum.TEST_VALIDATION);
        validationPresenter.setName("nameNotExist");
        Assertions.assertThatThrownBy(() -> validationService.saveValidation(validationPresenter)).isInstanceOf(ValidationException.class)
                .hasMessageContaining("Configuration");
    }

}
