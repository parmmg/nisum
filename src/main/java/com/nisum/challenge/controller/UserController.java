package com.nisum.challenge.controller;

import com.nisum.challenge.presenter.LoginPresenter;
import com.nisum.challenge.infraestructure.Response;
import com.nisum.challenge.presenter.UserPresenter;
import com.nisum.challenge.service.UserService;
import com.nisum.challenge.infraestructure.ResponseFactory;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

@Generated
@RestController
@Tag(name = "Users", description = "Users Update and Create")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/findUserById")
    public Response getUserById(@RequestParam("userId") UUID userId) {
        try {
            return ResponseFactory.getStatusOk("SUCCESS", userService.getUserById(userId));
        } catch (ResponseStatusException e) {
            return ResponseFactory.getStatusException(e.getStatusCode(),e.getMessage(), UserPresenter.class);
        } catch (Exception ex) {
            return ResponseFactory.getStatusException(HttpStatus.INTERNAL_SERVER_ERROR,ex.getMessage(), UserPresenter.class);
        }
    }

    @GetMapping("/listUsers")
    public Response getUsers() {
        try {
            return ResponseFactory.getStatusOk("SUCCESS", userService.getUsers());
        } catch (ResponseStatusException e) {
            return ResponseFactory.getStatusException(e.getStatusCode(),e.getMessage(), UserPresenter.class);
        } catch (Exception ex) {
            return ResponseFactory.getStatusException(HttpStatus.INTERNAL_SERVER_ERROR,ex.getMessage(), UserPresenter.class);
        }
    }

    @PutMapping("/saveUser")
    public Response saveUser(@RequestBody UserPresenter userPresenter) {
        try {
            return ResponseFactory.getStatusOk("SUCCESS", userService.saveUser(userPresenter));
        } catch (ResponseStatusException e) {
            return ResponseFactory.getStatusException(e.getStatusCode(),e.getMessage(), UserPresenter.class);
        } catch (Exception ex) {
            return ResponseFactory.getStatusException(HttpStatus.INTERNAL_SERVER_ERROR,ex.getMessage(), UserPresenter.class);
        }
    }

    @PostMapping("/login")
    public Response login(@RequestBody LoginPresenter loginPresenter) {
        try {
            return ResponseFactory.getStatusOk("SUCCESS", userService.login(loginPresenter));
        } catch (ResponseStatusException e) {
            return ResponseFactory.getStatusException(e.getStatusCode(),e.getMessage(), LoginPresenter.class);
        } catch (Exception ex) {
            return ResponseFactory.getStatusException(HttpStatus.INTERNAL_SERVER_ERROR,ex.getMessage(), LoginPresenter.class);
        }
    }
}
