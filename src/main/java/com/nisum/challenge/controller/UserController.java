package com.nisum.challenge.controller;

import com.nisum.challenge.presenter.LoginPresenter;
import com.nisum.challenge.presenter.UserPresenter;
import com.nisum.challenge.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Generated
@RestController
@RequestMapping("/users")
@Tag(name = "Users", description = "Users Update and Create")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/{id}")
    @ResponseBody
    public UserPresenter getUserById(@PathVariable("id") UUID userId) {
        return userService.getUserById(userId);
    }

    @GetMapping("/")
    @ResponseBody
    public List<UserPresenter> getUsers() {
        return userService.getUsers();
    }

    @PutMapping("/{id}")
    @ResponseBody
    public UserPresenter updateUser(@PathVariable("id") UUID userId, @RequestBody UserPresenter userPresenter) {
        return userService.saveUser(userId, userPresenter);
    }

    @PostMapping("/")
    @ResponseBody
    public UserPresenter createUser(@RequestBody UserPresenter userPresenter) {
        return userService.saveUser(null, userPresenter);
    }

    @DeleteMapping("/{id}")
    @ResponseBody
    public UserPresenter deleteUser(@PathVariable("id") UUID userId) {
        return userService.deleteUser(userId);
    }

    @PostMapping("/login")
    @ResponseBody
    public UserPresenter login(@RequestBody LoginPresenter loginPresenter) {
        return userService.login(loginPresenter);
    }

}
