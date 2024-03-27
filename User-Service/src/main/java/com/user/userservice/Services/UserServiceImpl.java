package com.user.userservice.Services;

import com.user.userservice.Dao.UserRepository;
import com.user.userservice.Dtos.CreateUserRequest;
import com.user.userservice.Dtos.UpdateUserRequest;
import com.user.userservice.Dtos.UserResponse;
import com.user.userservice.Entities.User;
import com.user.userservice.Enums.ErrorCode;
import com.user.userservice.Exceptions.CustomException;
import com.user.userservice.Utils.UserMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor

public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;



    @Override
    public UserResponse getUserById(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        return userMapper.userToUserResponse(user);
    }

    @Override
    public List<UserResponse> getAllUsers() {

        List<User> users = userRepository.findAll();
        return users.stream()
                .map(userMapper::userToUserResponse)
                .collect(Collectors.toList());
    }

    @Override
    public UserResponse createUser(CreateUserRequest newUser) {
       User user = userMapper.createUserRequestToUser(newUser);
       User savedUser = userRepository.save(user);
       return userMapper.userToUserResponse(savedUser);
    }

    @Override
    public UserResponse updateUser(Long userId, UpdateUserRequest updatedUserRequest) {

       User user = userRepository.findById(userId).orElseThrow(() ->
               new CustomException(ErrorCode.USER_NOT_FOUND));

       userMapper.updateUserFromUpdateUserRequest(updatedUserRequest, user);
       User updatedUser = userRepository.save(user);

       return userMapper.userToUserResponse(updatedUser);



    }

    @Override
    public void deleteUser(Long userId) {
        userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        userRepository.deleteById(userId);


    }
}
