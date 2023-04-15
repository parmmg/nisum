package com.nisum.challenge.controller;

import com.nisum.challenge.exception.ValidationException;
import com.nisum.challenge.infraestructure.Response;
import com.nisum.challenge.presenter.LoginPresenter;
import com.nisum.challenge.presenter.UserPresenter;
import com.nisum.challenge.service.UserService;
import com.nisum.challenge.util.TestData;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    @Spy
    private final UserController userController = new UserController();
    private final TestData testData = new TestData();

    @Test
    public void shouldResponseOKGetUsers() {
        when(userService.getUsers()).thenReturn(Collections.singletonList(testData.userPresenterFake()));
        Response response = userController.getUsers();
        Assertions.assertThat(response.getMessage()).isEqualTo("SUCCESS");
    }

    @Test
    public void shouldResponseOKGetUserById() {
        when(userService.getUserById(testData.ID)).thenReturn(testData.userPresenterFake());
        Response response = userController.getUserById(testData.ID);
        Assertions.assertThat(response.getMessage()).isEqualTo("SUCCESS");
    }

    @Test
    public void shouldResponseExceptionGetUserById() {
        when(userService.getUserById(testData.ID)).thenThrow(new ValidationException("Fake Error"));
        Response response = userController.getUserById(testData.ID);
        Assertions.assertThat(response.getMessage()).isNotEqualTo("SUCCESS");
    }
    @Test
    public void shouldResponseOKLogin() {
        LoginPresenter loginPresenter = testData.loginPresenterFake();
        when(userService.login(loginPresenter)).thenReturn(testData.userPresenterFake());
        Response response = userController.login(loginPresenter);
        Assertions.assertThat(response.getMessage()).isEqualTo("SUCCESS");
    }

    @Test
    public void shouldResponseExceptionLogin() {
        LoginPresenter loginPresenter = testData.loginPresenterFake();
        when(userService.login(loginPresenter)).thenThrow(new ValidationException("Fake Error"));
        Response response = userController.login(loginPresenter);
        Assertions.assertThat(response.getMessage()).isNotEqualTo("SUCCESS");
    }

    @Test
    public void shouldResponseOKSaveUser() {
        UserPresenter userPresenter = testData.userPresenterFake();
        when(userService.saveUser(userPresenter)).thenReturn(userPresenter);
        Response response = userController.saveUser(userPresenter);
        Assertions.assertThat(response.getMessage()).isEqualTo("SUCCESS");
    }

    @Test
    public void shouldResponseExceptionSaveUser() {
        UserPresenter userPresenter = testData.userPresenterFake();
        when(userService.saveUser(userPresenter)).thenThrow(new ValidationException("Fake Error"));
        Response response = userController.saveUser(userPresenter);
        Assertions.assertThat(response.getMessage()).isNotEqualTo("SUCCESS");
    }

}
