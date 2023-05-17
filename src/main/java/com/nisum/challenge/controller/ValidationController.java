package com.nisum.challenge.controller;

import com.nisum.challenge.presenter.ValidationPresenter;
import com.nisum.challenge.service.ValidationService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Generated
@RestController
@RequestMapping("/validations")
@Tag(name = "Validation", description = "Validation Configurations Update and Create")
public class ValidationController {

    @Autowired
    private ValidationService validationService;

    @GetMapping("/")
    @ResponseBody
    public List<ValidationPresenter> listValidations() {
        return validationService.getValidations();
    }

    @PostMapping("/")
    @ResponseBody
    public ValidationPresenter saveValidation(@RequestBody ValidationPresenter validationPresenter) {
        return validationService.saveValidation(validationPresenter);
    }
}
