package com.nisum.challenge.controller;

import com.nisum.challenge.presenter.ValidationPresenter;
import com.nisum.challenge.presenter.LoginPresenter;
import com.nisum.challenge.infraestructure.Response;
import com.nisum.challenge.service.ValidationService;
import com.nisum.challenge.infraestructure.ResponseFactory;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@Generated
@RestController
@Tag(name = "Validation", description = "Validation Configurations Update and Create")
public class ValidationController {

    @Autowired
    private ValidationService validationService;

    @GetMapping("/listValidations")
    public Response listValidations() {
        try {
            return ResponseFactory.getStatusOk("SUCCESS", validationService.getValidations());
        } catch (ResponseStatusException e) {
            return ResponseFactory.getStatusException(e.getStatusCode(),e.getMessage(), LoginPresenter.class);
        } catch (Exception ex) {
            return ResponseFactory.getStatusException(HttpStatus.INTERNAL_SERVER_ERROR,ex.getMessage(), LoginPresenter.class);
        }
    }

    @PutMapping("/saveValidation")
    public Response saveValidation(@RequestBody ValidationPresenter validationPresenter) {
        try {
            return ResponseFactory.getStatusOk("SUCCESS", validationService.saveValidation(validationPresenter));
        } catch (ResponseStatusException e) {
            return ResponseFactory.getStatusException(e.getStatusCode(),e.getMessage(), LoginPresenter.class);
        } catch (Exception ex) {
            return ResponseFactory.getStatusException(HttpStatus.INTERNAL_SERVER_ERROR,ex.getMessage(), LoginPresenter.class);
        }
    }

}
