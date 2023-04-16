package com.nisum.challenge.service.impl;


import com.nisum.challenge.entity.Validation;
import com.nisum.challenge.enumerator.ValidationEnum;
import com.nisum.challenge.exception.ValidationException;
import com.nisum.challenge.presenter.ValidationPresenter;
import com.nisum.challenge.repository.ValidationRepository;
import com.nisum.challenge.service.ValidationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class ValidationServiceImpl implements ValidationService {
    @Autowired
    private ValidationRepository validationRepository;

    @Override
    public Validation getValidationByName(ValidationEnum name) {
        return validationRepository.findByName(name).orElseThrow(() -> new ValidationException(HttpStatus.CONFLICT, "Configuration of validation " + name + " Not Found"));
    }

    @Override
    public ValidationPresenter saveValidation(ValidationPresenter validationPresenter) {
        try {
            ValidationEnum configurationName = ValidationEnum.valueOf(validationPresenter.getName());
            Validation validation = validationRepository.findByName(configurationName).orElse(new Validation());
            validation.setName(configurationName);
            validation.setMessage(validationPresenter.getMessage());
            validation.setPattern(validationPresenter.getPattern());
            validationRepository.save(validation);
            validationPresenter.setId(validation.getId());
            return validationPresenter;
        } catch (IllegalArgumentException e) {
            throw new ValidationException(HttpStatus.CONFLICT, "Configuration of validation " + validationPresenter.getName() + " not found");
        }
    }

    @Override
    public List<ValidationPresenter> getValidations() {
        List<ValidationPresenter> validationPresenters = new ArrayList<>();
        validationRepository.findAll().forEach(validation -> validationPresenters.add(toPresenter(validation)));
        return validationPresenters;
    }

    private ValidationPresenter toPresenter (Validation validation) {
        return ValidationPresenter.builder()
                .id(validation.getId())
                .name(validation.getName().toString())
                .message(validation.getMessage())
                .pattern(validation.getPattern())
                .build();
    }
}
