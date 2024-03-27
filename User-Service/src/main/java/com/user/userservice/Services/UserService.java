package com.user.userservice.Services;

import com.user.userservice.Dtos.CreateUserRequest;
import com.user.userservice.Dtos.UpdateUserRequest;
import com.user.userservice.Dtos.UserResponse;
import com.user.userservice.Entities.User;

import java.util.List;

public interface UserService {
    public UserResponse getUserById(Long userId);
    public List<UserResponse> getAllUsers();
    public UserResponse createUser(CreateUserRequest newUser);
    public UserResponse updateUser(Long userId, UpdateUserRequest updatedUserRequest);
    public void deleteUser(Long userId);

}
