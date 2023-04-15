package com.nisum.challenge.service;


import com.nisum.challenge.entity.Validation;
import com.nisum.challenge.enumerator.ValidationEnum;
import com.nisum.challenge.presenter.ValidationPresenter;

import java.util.List;


public interface ValidationService {

    Validation getValidationByName(ValidationEnum name);

    ValidationPresenter saveValidation(ValidationPresenter validationPresenter);

    List<ValidationPresenter> getValidations();

}
