package com.nisum.challenge.controller;

import com.nisum.challenge.exception.ValidationException;
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
import java.util.List;

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
    public void shouldGetUsers() {
        when(userService.getUsers()).thenReturn(Collections.singletonList(testData.userPresenterFake()));
        List<UserPresenter> userPresenters = userController.getUsers();
        Assertions.assertThat(userPresenters).size().isEqualTo(1);
    }

    @Test
    public void shouldResponseExceptionGetUsersEmpty() {
        when(userService.getUsers()).thenThrow(new ValidationException("Fake Error"));
        Throwable exception = Assertions.catchThrowable(userController::getUsers);
        Assertions.assertThat(exception).isInstanceOf(ValidationException.class);
        Assertions.assertThat(exception.getMessage()).contains("Fake Error");
    }
    @Test
    public void shouldGetUserById() {
        when(userService.getUserById(testData.ID)).thenReturn(testData.userPresenterFake());
        UserPresenter userPresenter = userController.getUserById(testData.ID);
        Assertions.assertThat(userPresenter.getId()).isEqualTo(testData.ID);
    }

    @Test
    public void shouldResponseExceptionGetUserById() {
        when(userService.getUserById(testData.ID)).thenThrow(new ValidationException("Fake Error"));
        Throwable exception = Assertions.catchThrowable(() -> userController.getUserById(testData.ID));
        Assertions.assertThat(exception).isInstanceOf(ValidationException.class);
        Assertions.assertThat(exception.getMessage()).contains("Fake Error");
    }
    @Test
    public void shouldResponseOKLogin() {
        LoginPresenter loginPresenter = testData.loginPresenterFake();
        when(userService.login(loginPresenter)).thenReturn(testData.userPresenterFake());
        UserPresenter response = userController.login(loginPresenter);
        Assertions.assertThat(response.getEmail()).isEqualTo(loginPresenter.getUser());
    }

    @Test
    public void shouldResponseExceptionLogin() {
        LoginPresenter loginPresenter = testData.loginPresenterFake();
        when(userService.login(loginPresenter)).thenThrow(new ValidationException("Fake Error"));
        Throwable exception = Assertions.catchThrowable(() -> userController.login(loginPresenter));
        Assertions.assertThat(exception).isInstanceOf(ValidationException.class);
        Assertions.assertThat(exception.getMessage()).contains("Fake Error");
    }

    @Test
    public void shouldResponseOKCreateUser() {
        UserPresenter userPresenter = testData.userPresenterFake();
        when(userService.saveUser(null, userPresenter)).thenReturn(userPresenter);
        UserPresenter userCreated = userController.createUser(userPresenter);
        Assertions.assertThat(userCreated.getId()).isEqualTo(userPresenter.getId());
    }

    @Test
    public void shouldResponseExceptionCreateUser() {
        UserPresenter userPresenter = testData.userPresenterFake();
        when(userService.saveUser(null, userPresenter)).thenThrow(new ValidationException("Fake Error"));
        Throwable exception = Assertions.catchThrowable(() -> userController.createUser(userPresenter));
        Assertions.assertThat(exception).isInstanceOf(ValidationException.class);
        Assertions.assertThat(exception.getMessage()).contains("Fake Error");
    }

    @Test
    public void shouldResponseOKUpdateUser() {
        UserPresenter userPresenter = testData.userPresenterFake();
        when(userService.saveUser(testData.ID, userPresenter)).thenReturn(userPresenter);
        UserPresenter userCreated = userController.updateUser(testData.ID, userPresenter);
        Assertions.assertThat(userCreated.getId()).isEqualTo(userPresenter.getId());
    }

    @Test
    public void shouldResponseExceptionUpdateUser() {
        UserPresenter userPresenter = testData.userPresenterFake();
        when(userService.saveUser(testData.ID, userPresenter)).thenThrow(new ValidationException("Fake Error"));
        Throwable exception = Assertions.catchThrowable(() -> userController.updateUser(testData.ID, userPresenter));
        Assertions.assertThat(exception).isInstanceOf(ValidationException.class);
        Assertions.assertThat(exception.getMessage()).contains("Fake Error");
    }
}
