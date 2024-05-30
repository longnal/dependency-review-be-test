package com.example.restapi.services;

import com.example.restapi.exceptions.AppException;
import com.example.restapi.models.User;
import com.example.restapi.models.requests.CreateUserRequest;
import com.example.restapi.models.requests.UpdateUserRequest;
import com.example.restapi.models.responses.UserResponse;
import com.example.restapi.repositories.UserRepository;
import com.example.restapi.services.interfaces.IUserService;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.example.restapi.exceptions.AppErrorMessages.*;
import static com.example.restapi.exceptions.AppErrors.*;


@Service
public class UserService implements IUserService {

    private static final String DUPLICATED_USER_MESSAGE = "Duplicated user";

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<UserResponse> getUsers() {
        Iterable<User> users = this.userRepository.findAll();
        List<UserResponse> response = new ArrayList<>();
        users.forEach(user -> response.add(UserResponse.builder()
                        .id(user.getId())
                        .email(user.getEmail())
                        .createdAt((user.getCreatedAt() != null) ? user.getCreatedAt().toString() : "")
                        .updatedAt((user.getUpdatedAt() != null) ? user.getUpdatedAt().toString() : "")
                .build()));
        return response;
    }

    public UserResponse getUserById(Long userId) throws AppException {
        try {
            User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User: " + userId));
            return UserResponse.builder()
                    .id(user.getId())
                    .email(user.getEmail())
                    .createdAt((user.getCreatedAt() != null) ? user.getCreatedAt().toString() : "")
                    .updatedAt((user.getUpdatedAt() != null) ? user.getUpdatedAt().toString() : "")
                    .build();
        } catch (ResourceNotFoundException ex) {
            throw new ResourceNotFoundException(ex.getMessage());
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            throw new AppException(UNKNOWN_USER_DETAIL_MESSAGE, TO_DO_APP_ERROR_003);
        }
    }
    @Transactional
    public UserResponse createUser(CreateUserRequest createUserRequest) throws AppException {
        try {
            User createdUser = userRepository.save(User.builder()
                    .email(createUserRequest.getEmail())
                    .password(passwordEncoder.encode(createUserRequest.getPassword()))
                    .createdAt(new Date())
                    .updatedAt(new Date())
                    .build());
            String createdAt = new SimpleDateFormat("yyyy-MM-dd H:m:s").format(createdUser.getCreatedAt());
            String updatedAt = new SimpleDateFormat("yyyy-MM-dd H:m:s").format(createdUser.getUpdatedAt());

            return UserResponse.builder()
                    .id(createdUser.getId())
                    .email(createdUser.getEmail())
                    .createdAt(createdAt)
                    .updatedAt(updatedAt)
                    .build();
        } catch (DataIntegrityViolationException ex) {
            throw new DataIntegrityViolationException(DUPLICATED_USER_MESSAGE);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            throw new AppException(UNKNOWN_USER_CREATION_MESSAGE, TO_DO_APP_ERROR_003);
        }
    }

    @Transactional
    public UserResponse updateUser(Long userId, UpdateUserRequest updateUserRequest) throws AppException {
        String updatedEmail = updateUserRequest.getEmail();
        boolean checkEmailExist = emailExist(updatedEmail);
        if (checkEmailExist) {
            throw new AppException(DUPLICATED_USER_EMAIL_MESSAGE, TO_DO_APP_ERROR_004);
        }
        try {
            User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User: " + userId));
            user.setEmail(updatedEmail);
            User updatedUser = userRepository.save(user);

            return UserResponse.builder()
                    .id(updatedUser.getId())
                    .email(updatedUser.getEmail())
                    .createdAt(updatedUser.getCreatedAt().toString())
                    .updatedAt(updatedUser.getUpdatedAt().toString())
                    .build();
        } catch (ResourceNotFoundException ex) {
            throw new ResourceNotFoundException(ex.getMessage());
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            throw new AppException(UNKNOWN_USER_CREATION_MESSAGE, TO_DO_APP_ERROR_003);
        }
    }

    private boolean emailExist(String email) {
        User user = userRepository.findByEmail(email);
        return !Objects.isNull(user);
    }

    public void deleteUser(Long userId) throws AppException {
        try {
            User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User: " + userId));
            user.setIsDeleted(true);
            userRepository.save(user);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            throw new AppException(UNKNOWN_USER_DELETE_MESSAGE, TO_DO_APP_ERROR_003);
        }
    }
}
