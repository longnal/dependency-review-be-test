package com.example.restapi.controllers;

import ch.qos.logback.classic.Logger;
import com.example.restapi.exceptions.AppException;
import com.example.restapi.models.requests.CreateUserRequest;
import com.example.restapi.models.requests.UpdateUserRequest;
import com.example.restapi.models.responses.SuccessResponse;
import com.example.restapi.models.responses.UserResponse;
import com.example.restapi.services.UserService;
import com.example.restapi.services.interfaces.IUserService;
import jakarta.validation.Valid;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.example.restapi.constants.AdminConstants.ADMIN_USERS_ENDPOINT;
import static com.example.restapi.constants.ServiceConstants.API;
import static com.example.restapi.constants.ServiceConstants.VERSION_1;
import static com.example.restapi.exceptions.AppErrorMessages.DUPLICATED_USER_MESSAGE;
import static com.example.restapi.exceptions.AppErrorMessages.USER_NOT_FOUND_MESSAGE;
import static com.example.restapi.exceptions.AppErrors.*;

@RestController
public class UserController {

    private final IUserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    public void Index(final UserService userService) {

    }
    private static final Logger log = (Logger) LoggerFactory.getLogger(UserController.class);

    @GetMapping(API + "/" + VERSION_1 + "/" + ADMIN_USERS_ENDPOINT)
    public List<UserResponse> getUsers() throws AppException {
        try {
            log.info("Incoming request for getting all users.");
            return userService.getUsers();
        } finally {
            log.info("Processing request for getting all users finished.");
        }
    }

    @GetMapping(API + "/" + VERSION_1 + "/" + ADMIN_USERS_ENDPOINT + "/{userId}")
    public UserResponse getUser(@PathVariable Long userId) throws AppException {
        try {
            log.info("Incoming request for getting an user.");
            return userService.getUserById(userId);
        } catch (ResourceNotFoundException ex) {
            throw new AppException(USER_NOT_FOUND_MESSAGE, TO_DO_APP_ERROR_002);
        } finally {
            log.info("Processing request for getting an user finished.");
        }
    }

    @PostMapping(API + "/" + VERSION_1 + "/" + ADMIN_USERS_ENDPOINT)
    public UserResponse createUser(@RequestBody(required = true) CreateUserRequest createUserRequest) throws AppException {
        try {
            log.info("Incoming request for user creation.");
            return userService.createUser(createUserRequest);
        } catch (DataIntegrityViolationException ex) {
            throw new AppException(DUPLICATED_USER_MESSAGE, TO_DO_APP_ERROR_004);
        } finally {
            log.info("Processing for user creation request finished.");
        }
    }

    @PutMapping(API + "/" + VERSION_1 + "/" + ADMIN_USERS_ENDPOINT + "/{userId}")
    public UserResponse updateUser(@PathVariable Long userId, @RequestBody(required = true) @Valid UpdateUserRequest updateUserRequest, BindingResult bindingResult) throws AppException {
        try {
            if (bindingResult.hasErrors()) {
                throw new AppException(bindingResult.getAllErrors().getFirst().getDefaultMessage(), TO_DO_APP_ERROR_001);
            }
            log.info("Incoming request for user edit");
            return userService.updateUser(userId, updateUserRequest);
        } finally {
            log.info("Processing for user edit process finished.");
        }
    }

    @DeleteMapping(API + "/" + VERSION_1 + "/" + ADMIN_USERS_ENDPOINT + "/{userId}")
    public SuccessResponse deleteUser(@PathVariable Long userId) throws AppException {
        try {
            log.info("Incoming request for user edit");
            userService.deleteUser(userId);
            ResponseEntity<String> response = new ResponseEntity<>(HttpStatus.OK);
            return SuccessResponse.builder()
                    .errorCode(response.getStatusCode().value())
                    .message("Deleted successfully")
                    .build();
        } finally {
            log.info("Processing for user edit process finished.");
        }
    }

}
