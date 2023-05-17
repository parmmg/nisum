package com.nisum.challenge.service.impl;

import com.nisum.challenge.entity.Validation;
import com.nisum.challenge.entity.Phone;
import com.nisum.challenge.entity.User;
import com.nisum.challenge.enumerator.ValidationEnum;
import com.nisum.challenge.exception.ValidationException;
import com.nisum.challenge.presenter.LoginPresenter;
import com.nisum.challenge.presenter.UserPresenter;
import com.nisum.challenge.repository.UserRepository;
import com.nisum.challenge.service.ValidationService;
import com.nisum.challenge.service.UserService;
import com.nisum.challenge.configuration.Security;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

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
    private ModelMapper modelMapper;

    @Override
    public UserPresenter getUserById(UUID userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ValidationException(HttpStatus.NOT_FOUND, "User account not found"));
        return modelMapper.map(user, UserPresenter.class);
    }

    @Override
    public UserPresenter saveUser(UUID userId, UserPresenter userPresenter) {
        User user = (userId==null ? new User() : userRepository.findById(userId).orElseThrow(()-> new ValidationException(HttpStatus.NOT_FOUND, "User Id Not Found")));
        validateUserPresenter(userPresenter);
        userPresenter.setPassword(Security.encode(userPresenter.getPassword()));
        userPresenter.setId(userId==null?UUID.randomUUID():userId);
        Optional<User> optionalUser = userRepository.findByEmail(userPresenter.getEmail());
        if (optionalUser.isPresent() && !optionalUser.get().getId().equals(userPresenter.getId())) {
            throw new ValidationException("Email already exist. User: " + optionalUser.get().getName());
        }
        if (user.getId()==null) {
            user.setId(userPresenter.getId());
            user.setCreated(new Date());
            user.setLastLogin(user.getCreated());
        } else {
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
        user.setPhones(userPresenter.getPhones().stream().map(phonePresenter -> {
            Phone phone = modelMapper.map(phonePresenter, Phone.class);
            phone.setUser(user);
            return phone;
        }).collect(Collectors.toSet()));
        userPresenter.setToken(Security.createToken(user));
        user.setToken(userPresenter.getToken());
        userPresenter.setCreated(user.getCreated());
        userPresenter.setModified(user.getModified());
        userPresenter.setLastLogin(user.getLastLogin());
        userRepository.save(user);
        return userPresenter;
    }

    @Override
    public List<UserPresenter> getUsers() {
        List<UserPresenter> userPresenters = new ArrayList<>();
        userRepository.findAll().forEach(user -> userPresenters.add(modelMapper.map(user,UserPresenter.class)));
        if (userPresenters.isEmpty()) {
            throw new ValidationException(HttpStatus.NOT_FOUND, "Empty Users");
        }
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
        return modelMapper.map(userRepository.save(user), UserPresenter.class);
    }

    @Override
    public UserPresenter deleteUser(UUID userId) {
        User user = userRepository.findById(userId).orElseThrow(()-> new ValidationException(HttpStatus.NOT_FOUND, "User Not Found"));
        userRepository.delete(user);
        return modelMapper.map(user, UserPresenter.class);
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
