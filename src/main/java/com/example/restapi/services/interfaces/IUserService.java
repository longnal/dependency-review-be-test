package com.example.restapi.services.interfaces;

import com.example.restapi.exceptions.AppException;
import com.example.restapi.models.requests.CreateUserRequest;
import com.example.restapi.models.requests.UpdateUserRequest;
import com.example.restapi.models.responses.UserResponse;

import java.util.List;

public interface IUserService {
    public List<UserResponse> getUsers();

    public UserResponse getUserById(Long userId) throws AppException;

    public UserResponse createUser(CreateUserRequest createUserRequest) throws AppException;

    public UserResponse updateUser(Long userId, UpdateUserRequest updateUserRequest) throws AppException;

    public void deleteUser(Long userId) throws AppException;
}
