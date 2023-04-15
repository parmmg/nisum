package com.nisum.challenge.controller;

import com.nisum.challenge.presenter.ConfigurationPresenter;
import com.nisum.challenge.presenter.LoginPresenter;
import com.nisum.challenge.infraestructure.Response;
import com.nisum.challenge.service.ConfigurationService;
import com.nisum.challenge.infraestructure.ResponseFactory;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@Generated
@RestController
@Tag(name = "Configuration", description = "Configuration Update and Create")
public class ConfigurationController {

    @Autowired
    private ConfigurationService configurationService;

    @GetMapping("/listConfigurations")
    public Response listConfigurations() {
        try {
            return ResponseFactory.getStatusOk("SUCCESS", configurationService.getConfigurations());
        } catch (ResponseStatusException e) {
            return ResponseFactory.getStatusException(e.getStatusCode(),e.getMessage(), LoginPresenter.class);
        } catch (Exception ex) {
            return ResponseFactory.getStatusException(HttpStatus.INTERNAL_SERVER_ERROR,ex.getMessage(), LoginPresenter.class);
        }
    }

    @PutMapping("/saveConfiguration")
    public Response saveConfiguration(@RequestBody ConfigurationPresenter configurationPresenter) {
        try {
            return ResponseFactory.getStatusOk("SUCCESS", configurationService.saveConfiguration(configurationPresenter));
        } catch (ResponseStatusException e) {
            return ResponseFactory.getStatusException(e.getStatusCode(),e.getMessage(), LoginPresenter.class);
        } catch (Exception ex) {
            return ResponseFactory.getStatusException(HttpStatus.INTERNAL_SERVER_ERROR,ex.getMessage(), LoginPresenter.class);
        }
    }

}
