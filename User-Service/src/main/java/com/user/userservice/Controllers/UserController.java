package com.user.userservice.Controllers;

import com.user.userservice.Dtos.CreateUserRequest;
import com.user.userservice.Dtos.UpdateUserRequest;
import com.user.userservice.Dtos.UserResponse;
import com.user.userservice.Services.UserServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@AllArgsConstructor

public class UserController {

    private final UserServiceImpl userService;

    // Retrieve a single user by ID
    @GetMapping("/{userId}")
    public ResponseEntity<?> getUserById(@PathVariable Long userId) {
        UserResponse userResponse = userService.getUserById(userId);
        return ResponseEntity.ok(userResponse);
    }

    // Retrieve all users
    @GetMapping
    public ResponseEntity<?> getAllUsers() {
        List<UserResponse> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    // Create a new user
    @PostMapping
    public ResponseEntity<?> createUser(@RequestBody CreateUserRequest request) {
        UserResponse newUser = userService.createUser(request);
        return new ResponseEntity<>(newUser, HttpStatus.CREATED);
    }

    // Update an existing user
    @PutMapping("/{userId}")
    public ResponseEntity<UserResponse> updateUser(@PathVariable Long userId, @RequestBody UpdateUserRequest request) {
        UserResponse updatedUser = userService.updateUser(userId, request);
        return ResponseEntity.ok(updatedUser);
    }

    // Delete a user
    @DeleteMapping("/{userId}")
    public ResponseEntity<?> deleteUser(@PathVariable Long userId) {
        userService.deleteUser(userId);
        return ResponseEntity.noContent().build();
    }
}

