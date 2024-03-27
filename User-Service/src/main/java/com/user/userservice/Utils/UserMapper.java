package com.user.userservice.Utils;

import com.user.userservice.Dtos.CreateUserRequest;
import com.user.userservice.Dtos.UpdateUserRequest;
import com.user.userservice.Dtos.UserResponse;
import com.user.userservice.Entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    UserResponse userToUserResponse(User user);
    User createUserRequestToUser(CreateUserRequest userRequest);

    void updateUserFromUpdateUserRequest(UpdateUserRequest updateUserRequest, @MappingTarget User user);


}
