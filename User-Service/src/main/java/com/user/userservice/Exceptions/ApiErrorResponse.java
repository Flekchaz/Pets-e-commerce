package com.user.userservice.Exceptions;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter

public class ApiErrorResponse {
    private String message;
    private List<String> details;

    public ApiErrorResponse(String message, List<String> details){
        this.message = message;
        this.details = details;
    }
}
