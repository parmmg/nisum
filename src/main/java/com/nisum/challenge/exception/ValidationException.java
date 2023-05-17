package com.nisum.challenge.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.server.ResponseStatusException;

import jakarta.validation.ConstraintViolation;
import java.util.Set;
import java.util.stream.Collectors;

public class ValidationException extends ResponseStatusException {

    public ValidationException(String exString) {
        super(HttpStatus.PRECONDITION_FAILED, exString);
    }

    public ValidationException(HttpStatusCode status, String exString) {
        super(status, exString);
    }

    public ValidationException(String exString, Set<ConstraintViolation<?>> constraintViolations) {
        super(HttpStatus.PRECONDITION_FAILED, createMessage(exString, constraintViolations));
    }

    private static String createMessage(String exString, Set<ConstraintViolation<?>> constraintViolations) {
        if (constraintViolations == null) {
            return exString;
        }
        if (exString==null) {
            exString = "";
        }
        return String.format(exString + " Datos incorrectos: %s",
                constraintViolations.stream()
                        .map(ValidationException::buildFieldFailed)
                        .collect(Collectors.joining(", "))
        );
    }

    private static String buildFieldFailed(ConstraintViolation<?> constraintViolation) {
        return String.format("Campo= %s, Mensaje= %s", constraintViolation.getPropertyPath(), constraintViolation.getMessage());
    }

}