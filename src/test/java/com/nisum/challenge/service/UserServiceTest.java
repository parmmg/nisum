package com.nisum.challenge.service;

import com.nisum.challenge.enumerator.ValidationEnum;
import com.nisum.challenge.entity.User;
import com.nisum.challenge.exception.ValidationException;
import com.nisum.challenge.configuration.Security;
import com.nisum.challenge.presenter.LoginPresenter;
import com.nisum.challenge.presenter.UserPresenter;
import com.nisum.challenge.repository.UserRepository;
import com.nisum.challenge.service.impl.UserServiceImpl;
import com.nisum.challenge.util.TestData;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.*;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @InjectMocks
    @Spy
    private final UserService userService = new UserServiceImpl();
    @Mock
    private UserRepository userRepository;
    @Mock
    private ModelMapper modelMapper;
    @Mock
    private ValidationService validationService;
    private final TestData testData = new TestData();


    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        lenient().when(modelMapper.map(testData.userFake(), UserPresenter.class)).thenReturn(testData.userPresenterFake());
    }
    @Test
    public void shouldGetUsers(){
        List<User> users = new ArrayList<>();
        users.add(testData.userFake());
        when(userRepository.findAll()).thenReturn(users);
        List<UserPresenter> userPresenters = userService.getUsers();
        Assertions.assertThat(userPresenters).isNotEmpty();
    }

    @Test
    public void shouldGetUserById(){
        User user = testData.userFake();
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        UserPresenter userPresenter = userService.getUserById(user.getId());
        Assertions.assertThat(userPresenter.getId()).isEqualTo(user.getId());
    }
    @Test
    public void shouldThrowExceptionWhenIdNotExist(){
        User user = testData.userFake();
        when(userRepository.findById(user.getId())).thenReturn(Optional.empty());
        Assertions.assertThatThrownBy(() -> userService.getUserById(user.getId())).isInstanceOf(ValidationException.class)
                .hasMessageContaining("User account not found");
    }

    @Test
    public void shouldDeleteUserById(){
        User user = testData.userFake();
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        doNothing().when(userRepository).delete(user);
        UserPresenter userPresenter = userService.deleteUser(user.getId());
        Assertions.assertThat(userPresenter.getId()).isEqualTo(user.getId());
    }
    @Test
    public void shouldThrowExceptionWhenDeleteIdNotExist(){
        User user = testData.userFake();
        when(userRepository.findById(user.getId())).thenReturn(Optional.empty());
        Assertions.assertThatThrownBy(() -> userService.deleteUser(user.getId())).isInstanceOf(ValidationException.class)
                .hasMessageContaining("User Not Found");
    }
    @Test
    public void shouldGetLogin(){
        try (MockedStatic<Security> securityMockedStatic = Mockito.mockStatic(Security.class)) {
            LoginPresenter loginPresenter = testData.loginPresenterFake();
            User user = testData.userFake();
            when(userRepository.findByEmail(loginPresenter.getUser())).thenReturn(Optional.of(user));
            securityMockedStatic.when(() -> Security.encode(loginPresenter.getPassword())).thenReturn(user.getPassword());
            when(userRepository.save(user)).thenReturn(user);
            UserPresenter userPresenter = userService.login(loginPresenter);
            Assertions.assertThat(userPresenter.getEmail()).isEqualTo(loginPresenter.getUser());
        }
    }

    @Test
    public void shouldThrowExceptionWhenLoginWithIncorrectPassword() {
        LoginPresenter loginPresenter = testData.loginPresenterFake();
        when(userRepository.findByEmail(loginPresenter.getUser())).thenReturn(Optional.of(testData.userFake()));
        Assertions.assertThatThrownBy(() -> userService.login(loginPresenter)).isInstanceOf(ValidationException.class)
                .hasMessageContaining("Denied access");
    }

    @Test
    public void shouldThrowExceptionWhenLoginWithIncorrectEmail() {
        LoginPresenter loginPresenter = testData.loginPresenterFake();
        when(userRepository.findByEmail(loginPresenter.getUser())).thenReturn(Optional.empty());
        Assertions.assertThatThrownBy(() -> userService.login(loginPresenter)).isInstanceOf(ValidationException.class)
                .hasMessageContaining("Denied access");
    }

    @Test
    public void shouldSaveUser() {
        try (MockedStatic<Security> securityMockedStatic = Mockito.mockStatic(Security.class)) {
            User user = testData.userFake();
            UserPresenter userPresenter = testData.userPresenterFake();
            when(validationService.getValidationByName(ValidationEnum.EMAIL_VALIDATION)).thenReturn(testData.validationFake(ValidationEnum.EMAIL_VALIDATION));
            when(validationService.getValidationByName(ValidationEnum.PASSWORD_VALIDATION)).thenReturn(testData.validationFake(ValidationEnum.PASSWORD_VALIDATION));
            securityMockedStatic.when(() -> Security.encode(userPresenter.getPassword())).thenReturn(userPresenter.getPassword());
            lenient().when(userRepository.save(user)).thenReturn(user);
            UserPresenter userSaved = userService.saveUser(null,userPresenter);
            Assertions.assertThat(userPresenter).isEqualTo(userSaved);
        }
    }

    @Test
    public void shouldGetValidationExceptionWhenEmailExist() {
        UserPresenter user = testData.userPresenterFake();
        user.setId(UUID.randomUUID());
        when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.of(testData.userFake()));
        when(validationService.getValidationByName(ValidationEnum.EMAIL_VALIDATION)).thenReturn(testData.validationFake(ValidationEnum.EMAIL_VALIDATION));
        when(validationService.getValidationByName(ValidationEnum.PASSWORD_VALIDATION)).thenReturn(testData.validationFake(ValidationEnum.PASSWORD_VALIDATION));
        Assertions.assertThatThrownBy(() -> userService.saveUser(null, user)).isInstanceOf(ValidationException.class)
                .hasMessageContaining("Email already exist");
    }

    @Test
    public void shouldGetValidationExceptionWhenEmailIsMissing() {
        UserPresenter user = testData.userPresenterFake();
        user.setEmail("");
        lenient().when(validationService.getValidationByName(ValidationEnum.EMAIL_VALIDATION)).thenReturn(testData.validationFake(ValidationEnum.EMAIL_VALIDATION));
        lenient().when(validationService.getValidationByName(ValidationEnum.PASSWORD_VALIDATION)).thenReturn(testData.validationFake(ValidationEnum.PASSWORD_VALIDATION));
        Assertions.assertThatThrownBy(() -> userService.saveUser(null, user)).isInstanceOf(ValidationException.class)
                .hasMessageContaining("required");
    }

    @Test
    public void shouldGetValidationExceptionWhenEmailIsInvalid() {
        UserPresenter user = testData.userPresenterFake();
        user.setEmail("a");
        lenient().when(validationService.getValidationByName(ValidationEnum.EMAIL_VALIDATION)).thenReturn(testData.validationFake(ValidationEnum.EMAIL_VALIDATION));
        lenient().when(validationService.getValidationByName(ValidationEnum.PASSWORD_VALIDATION)).thenReturn(testData.validationFake(ValidationEnum.PASSWORD_VALIDATION));
        Assertions.assertThatThrownBy(() -> userService.saveUser(null, user)).isInstanceOf(ValidationException.class)
                .hasMessageContaining("EMAIL_VALIDATION");
    }

    @Test
    public void shouldGetValidationExceptionWhenPasswordIsMissing() {
        UserPresenter user = testData.userPresenterFake();
        user.setPassword("");
        lenient().when(validationService.getValidationByName(ValidationEnum.EMAIL_VALIDATION)).thenReturn(testData.validationFake(ValidationEnum.EMAIL_VALIDATION));
        lenient().when(validationService.getValidationByName(ValidationEnum.PASSWORD_VALIDATION)).thenReturn(testData.validationFake(ValidationEnum.PASSWORD_VALIDATION));
        Assertions.assertThatThrownBy(() -> userService.saveUser(null, user)).isInstanceOf(ValidationException.class)
                .hasMessageContaining("required");
    }
    @Test
    public void shouldGetValidationExceptionWhenPasswordIsInvalid() {
        UserPresenter user = testData.userPresenterFake();
        user.setPassword("a");
        when(validationService.getValidationByName(ValidationEnum.EMAIL_VALIDATION)).thenReturn(testData.validationFake(ValidationEnum.EMAIL_VALIDATION));
        when(validationService.getValidationByName(ValidationEnum.PASSWORD_VALIDATION)).thenReturn(testData.validationFake(ValidationEnum.PASSWORD_VALIDATION));
        Assertions.assertThatThrownBy(() -> userService.saveUser(null, user)).isInstanceOf(ValidationException.class)
                .hasMessageContaining("PASSWORD_VALIDATION");
    }

}
