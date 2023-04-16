package com.nisum.challenge.service.impl;

import com.nisum.challenge.entity.Validation;
import com.nisum.challenge.entity.Phone;
import com.nisum.challenge.entity.User;
import com.nisum.challenge.enumerator.ValidationEnum;
import com.nisum.challenge.exception.ValidationException;
import com.nisum.challenge.presenter.LoginPresenter;
import com.nisum.challenge.presenter.PhonePresenter;
import com.nisum.challenge.presenter.UserPresenter;
import com.nisum.challenge.repository.PhoneRepository;
import com.nisum.challenge.repository.UserRepository;
import com.nisum.challenge.service.ValidationService;
import com.nisum.challenge.service.UserService;
import com.nisum.challenge.infraestructure.Security;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ValidationService validationService;
    @Autowired
    private PhoneRepository phoneRepository;

    @Override
    public UserPresenter getUserById(UUID userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ValidationException(HttpStatus.NOT_FOUND, "User account not found"));
        return toPresenter(user);
    }

    @Override
    @Transactional
    public UserPresenter saveUser(UserPresenter userPresenter) {
        validateUserPresenter(userPresenter);
        userPresenter.setPassword(Security.encode(userPresenter.getPassword()));
        Optional<User> optionalUser = userRepository.findByEmail(userPresenter.getEmail());
        if (Objects.isNull(userPresenter.getId()) || userPresenter.getId().toString().isEmpty()) {
            if (optionalUser.isPresent() && optionalUser.get().getName().equals(userPresenter.getName())) {
                userPresenter.setId(optionalUser.get().getId());
            } else {
                userPresenter.setId(UUID.randomUUID());
            }
        }
        if (optionalUser.isPresent() && !optionalUser.get().getId().equals(userPresenter.getId())) {
            throw new ValidationException("Email already exist. User: " + optionalUser.get().getName());
        }
        User user = User.builder().build();
        optionalUser = userRepository.findById(userPresenter.getId());
        if (optionalUser.isEmpty()) {
            user.setId(userPresenter.getId());
            user.setCreated(new Date());
            user.setLastLogin(user.getCreated());
        } else {
            user = optionalUser.get();
            user.getPhones().clear();
        }
        user.setModified(new Date());
        user.setName(userPresenter.getName());
        user.setEmail(userPresenter.getEmail());
        user.setPassword(userPresenter.getPassword());
        if (Objects.nonNull(userPresenter.getActive())) {
            user.setActive(userPresenter.getActive());
        } else {
            userPresenter.setActive(user.getActive());
        }
        userPresenter.setToken(Security.createToken(user));
        user.setToken(userPresenter.getToken());
        final User userFinal = userRepository.save(user);
        user.setPhones(userPresenter.getPhones().stream().map(phonePresenter -> phoneRepository.save(Phone.builder()
                .number(phonePresenter.getNumber())
                .cityCode(phonePresenter.getCityCode())
                .countryCode(phonePresenter.getCountryCode())
                .user(userFinal)
                .build())).collect(Collectors.toSet()));
        userRepository.save(user);
        userPresenter.setCreated(user.getCreated());
        userPresenter.setModified(user.getModified());
        userPresenter.setLastLogin(user.getLastLogin());
        return userPresenter;
    }

    @Override
    public List<UserPresenter> getUsers() {
        List<UserPresenter> userPresenters = new ArrayList<>();
        userRepository.findAll().forEach(user -> userPresenters.add(toPresenter(user)));
        return userPresenters;
    }

    @Override
    public UserPresenter login(LoginPresenter loginPresenter) {
        User user = userRepository.findByEmail(loginPresenter.getUser()).orElseThrow(() -> new ValidationException(HttpStatus.FORBIDDEN, "Denied access"));
        String password = Security.encode(loginPresenter.getPassword());
        if (!user.getPassword().equals(password)) {
            throw new ValidationException(HttpStatus.FORBIDDEN, "Denied access");
        }
        user.setLastLogin(new Date());
        user.setModified(user.getLastLogin());
        user.setToken(Security.createToken(user));
        return toPresenter(userRepository.save(user));
    }

    private UserPresenter toPresenter(User user) {
        return UserPresenter.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .password(user.getPassword())
                .created(user.getCreated())
                .modified(user.getModified())
                .lastLogin(user.getLastLogin())
                .token(user.getToken())
                .active(user.getActive())
                .phones(user.getPhones().stream().map(phone -> PhonePresenter.builder()
                        .number(phone.getNumber())
                        .cityCode(phone.getCityCode())
                        .countryCode(phone.getCountryCode())
                        .build()).collect(Collectors.toSet()))
                .build();
    }

    private void validateUserPresenter(UserPresenter userPresenter) {
        if (userPresenter.getEmail() == null || userPresenter.getEmail().isEmpty() || userPresenter.getEmail().isBlank()) {
            throw new ValidationException("Email required. ");
        }
        if (userPresenter.getPassword() == null || userPresenter.getPassword().isEmpty() || userPresenter.getPassword().isBlank()) {
            throw new ValidationException("Password required. ");
        }
        Validation validationEmail = validationService.getValidationByName(ValidationEnum.EMAIL_VALIDATION);
        Validation validationPassword = validationService.getValidationByName(ValidationEnum.PASSWORD_VALIDATION);
        if (!validationEmail.getPattern().isBlank() && !Pattern.compile(validationEmail.getPattern()).matcher(userPresenter.getEmail()).matches()) {
            throw new ValidationException(validationEmail.getMessage() + " ");
        }
        if (!validationPassword.getPattern().isBlank() && !Pattern.compile(validationPassword.getPattern()).matcher(userPresenter.getPassword()).matches()) {
            throw new ValidationException(validationPassword.getMessage() + " ");
        }
    }

}
