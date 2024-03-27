package com.user.userservice.Dtos;

import jakarta.persistence.Column;
import lombok.Data;

@Data

public class UpdateUserRequest {
    @Column(nullable = false, length = 50)
    private String name;
    @Column(nullable = false, length = 50, unique = true)
    private String email;
    @Column(nullable = false)
    private String password;
}
