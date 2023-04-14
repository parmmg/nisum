package com.nisum.challenge.exception;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import jakarta.validation.ConstraintViolation;
import java.util.Set;

@ExtendWith(MockitoExtension.class)
public class ValidationExceptionTest {

    @Mock
    private Set<ConstraintViolation<?>> constraintViolations;


    @Test
    public void shouldReturnExceptionString() {
        ValidationException validationException = new ValidationException("Exception", null);
        Assertions.assertThat(validationException.getMessage()).isNotEmpty();
    }


    @Test
    public void shouldReturnNonEmptyString() {
        ValidationException validationException = new ValidationException("Exception", constraintViolations);
        Assertions.assertThat(validationException.getMessage()).isNotEmpty();
    }

    @Test
    public void shouldReturnException() {
        constraintViolations.add(null);
        ValidationException validationException = new ValidationException("Exception", constraintViolations);
        Assertions.assertThat(validationException.getMessage()).isNotEmpty();
    }

}
