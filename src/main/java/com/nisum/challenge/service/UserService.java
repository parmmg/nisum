package com.nisum.challenge.service;


import com.nisum.challenge.presenter.LoginPresenter;
import com.nisum.challenge.presenter.UserPresenter;

import java.util.List;
import java.util.UUID;

public interface UserService {

    UserPresenter getUserById(UUID userId);
    UserPresenter saveUser(UUID userId, UserPresenter userPresenter);
    List<UserPresenter> getUsers();
    UserPresenter login(LoginPresenter loginPresenter);
    UserPresenter deleteUser(UUID userId);

}
