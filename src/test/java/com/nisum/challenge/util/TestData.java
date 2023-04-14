package com.nisum.challenge.util;

import com.nisum.challenge.config.enumerator.ConfigurationNameEnum;
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

    public Configuration configurationFake(ConfigurationNameEnum name) {
        return Configuration.builder()
                .id(ID)
                .name(name)
                .pattern(name.toString().equals("") ? "^(?=\\w*\\d)(?=\\w*[A-Z])(?=\\w*[a-z])\\S{8,16}$" : "")
                .message(name.toString())
                .build();
    }

    public ConfigurationPresenter configurationPresenterFake(ConfigurationNameEnum name) {
        return ConfigurationPresenter.builder()
                .id(ID)
                .name(name.toString())
                .pattern(name.toString().equals("") ? "^(?=\\w*\\d)(?=\\w*[A-Z])(?=\\w*[a-z])\\S{8,16}$" : "")
                .message(name.toString())
                .build();
    }
}
