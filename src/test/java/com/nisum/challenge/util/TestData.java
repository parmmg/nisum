package com.nisum.challenge.util;

import com.nisum.challenge.enumerator.ValidationEnum;
import com.nisum.challenge.entity.*;
import com.nisum.challenge.presenter.*;

import java.util.*;

public class TestData {

    public final UUID ID = UUID.randomUUID();

    public User userFake() {
        return User.builder()
                .id(ID)
                .name("name")
                .email("a@a.aa")
                .active(true)
                .token(UUID.randomUUID().toString())
                .created(new Date())
                .modified(new Date())
                .lastLogin(new Date())
                .password("P4ssW0rd")
                .phones(Collections.emptySet())
                .build();
    }

    public UserPresenter userPresenterFake() {
        return UserPresenter.builder()
                .id(ID)
                .password("P4ssW0rd")
                .name("name")
                .email("a@a.aa")
                .active(true)
                .token(ID.toString())
                .created(new Date())
                .modified(new Date())
                .lastLogin(new Date())
                .phones(Collections.emptySet())
                .build();
    }

    public LoginPresenter loginPresenterFake() {
        return LoginPresenter.builder()
                .user("a@a.aa")
                .password("P4ssW0rd")
                .build();
    }

    public Validation validationFake(ValidationEnum name) {
        return Validation.builder()
                .id(ID)
                .name(name)
                .pattern(name.toString().equals("PASSWORD_VALIDATION")?"^(?=\\w*\\d)(?=\\w*[A-Z])(?=\\w*[a-z])\\S{8,16}$":"^[^@]+@[^@]+\\.[a-zA-Z]{2,}$")
                .message(name.toString())
                .build();
    }

    public ValidationPresenter validationPresenterFake(ValidationEnum name) {
        return ValidationPresenter.builder()
                .id(ID)
                .name(name.toString())
                .pattern(name.toString().equals("PASSWORD_VALIDATION")?"^(?=\\w*\\d)(?=\\w*[A-Z])(?=\\w*[a-z])\\S{8,16}$":"^[^@]+@[^@]+\\.[a-zA-Z]{2,}$")
                .message(name.toString())
                .build();
    }
}
