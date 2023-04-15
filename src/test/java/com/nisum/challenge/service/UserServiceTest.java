package com.nisum.challenge.service;

import com.nisum.challenge.enumerator.ConfigurationNameEnum;
import com.nisum.challenge.entity.User;
import com.nisum.challenge.exception.ValidationException;
import com.nisum.challenge.presenter.LoginPresenter;
import com.nisum.challenge.presenter.UserPresenter;
import com.nisum.challenge.repository.UserRepository;
import com.nisum.challenge.service.impl.UserServiceImpl;
import com.nisum.challenge.util.TestData;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.*;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @InjectMocks
    @Spy
    private UserService userService = new UserServiceImpl();
    @Mock
    PasswordEncoder passwordEncoder;
    @Mock
    private UserRepository userRepository;
    @Mock
    private ConfigurationService configurationService;
    private final TestData testData = new TestData();


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
    public void shouldGetLogin(){
        LoginPresenter loginPresenter = testData.loginPresenterFake();
        when(userRepository.findByEmail(loginPresenter.getUser())).thenReturn(Optional.of(testData.userFake()));
        lenient().when(passwordEncoder.encode(loginPresenter.getPassword())).thenReturn(loginPresenter.getPassword());
        UserPresenter userPresenter = userService.login(loginPresenter);
        Assertions.assertThat(userPresenter.getEmail()).isEqualTo(loginPresenter.getUser());
    }

    @Test
    public void shouldThrowExceptionWhenLoginWithIncorrectPassword() {
        LoginPresenter loginPresenter = testData.loginPresenterFake();
        loginPresenter.setPassword("");
        when(userRepository.findByEmail(loginPresenter.getUser())).thenReturn(Optional.of(testData.userFake()));
        lenient().when(passwordEncoder.encode(loginPresenter.getPassword())).thenReturn(anyString());
        Assertions.assertThatThrownBy(() -> userService.login(loginPresenter)).isInstanceOf(ValidationException.class)
                .hasMessageContaining("Denied access");
    }

    @Test
    public void shouldThrowExceptionWhenLoginWithIncorrectEmail() {
        LoginPresenter loginPresenter = testData.loginPresenterFake();
        when(userRepository.findByEmail(loginPresenter.getUser())).thenReturn(Optional.empty());
        lenient().when(passwordEncoder.encode(loginPresenter.getPassword())).thenReturn(anyString());
        Assertions.assertThatThrownBy(() -> userService.login(loginPresenter)).isInstanceOf(ValidationException.class)
                .hasMessageContaining("Denied access");
    }

    @Test
    public void shouldSaveUser() {
        User user = testData.userFake();
        UserPresenter userPresenter = testData.userPresenterFake();
        lenient().when(passwordEncoder.encode(user.getPassword())).thenReturn(user.getPassword());
        when(userRepository.save(user)).thenReturn(user);
        when(configurationService.getConfigurationByName(ConfigurationNameEnum.EMAIL_VALIDATION)).thenReturn(testData.configurationFake(ConfigurationNameEnum.EMAIL_VALIDATION));
        UserPresenter userSaved = userService.saveUser(userPresenter);
        Assertions.assertThat(userPresenter).isEqualTo(userSaved);
    }

    @Test
    public void shouldGetValidationExceptionWhenEmailExist() {
        UserPresenter user = testData.userPresenterFake();
        user.setId(UUID.randomUUID());
        lenient().when(passwordEncoder.encode(user.getPassword())).thenReturn(user.getPassword());
        when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.of(testData.userFake()));
        when(configurationService.getConfigurationByName(ConfigurationNameEnum.EMAIL_VALIDATION)).thenReturn(testData.configurationFake(ConfigurationNameEnum.EMAIL_VALIDATION));
        when(configurationService.getConfigurationByName(ConfigurationNameEnum.PASSWORD_VALIDATION)).thenReturn(testData.configurationFake(ConfigurationNameEnum.PASSWORD_VALIDATION));
        Assertions.assertThatThrownBy(() -> userService.saveUser(user)).isInstanceOf(ValidationException.class)
                .hasMessageContaining("Email already exist");
    }

    @Test
    public void shouldGetValidationExceptionWhenEmailIsInvalid() {
        UserPresenter user = testData.userPresenterFake();
        lenient().when(passwordEncoder.encode(user.getPassword())).thenReturn(user.getPassword());
        when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.empty());
        when(configurationService.getConfigurationByName(ConfigurationNameEnum.EMAIL_VALIDATION)).thenReturn(testData.configurationFake(ConfigurationNameEnum.EMAIL_VALIDATION));
        when(configurationService.getConfigurationByName(ConfigurationNameEnum.PASSWORD_VALIDATION)).thenReturn(testData.configurationFake(ConfigurationNameEnum.PASSWORD_VALIDATION));
        user.setEmail("");
        Assertions.assertThatThrownBy(() -> userService.saveUser(user)).isInstanceOf(ValidationException.class)
                .hasMessageContaining("EMAIL_VALIDATION");
    }

    @Test
    public void shouldGetValidationExceptionWhenPasswordIsInvalid() {
        UserPresenter user = testData.userPresenterFake();
        lenient().when(passwordEncoder.encode(user.getPassword())).thenReturn(user.getPassword());
        when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.empty());
        when(configurationService.getConfigurationByName(ConfigurationNameEnum.EMAIL_VALIDATION)).thenReturn(testData.configurationFake(ConfigurationNameEnum.EMAIL_VALIDATION));
        when(configurationService.getConfigurationByName(ConfigurationNameEnum.PASSWORD_VALIDATION)).thenReturn(testData.configurationFake(ConfigurationNameEnum.PASSWORD_VALIDATION));
        user.setPassword("");
        Assertions.assertThatThrownBy(() -> userService.saveUser(user)).isInstanceOf(ValidationException.class)
                .hasMessageContaining("PASSWORD_VALIDATION");
    }

}
