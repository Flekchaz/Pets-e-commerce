package com.user.userservice.Enums;

public enum ErrorCode {
    USER_NOT_FOUND("User not found"),
    DATA_INTEGRITY_VIOLATION("Data integrity violation"),
    VALIDATION_ERROR("Validation error"),
    INTERNAL_SERVER_ERROR("Internal server error");

    private String message;

    ErrorCode(String message){
        this.message=message;
    }

    public String getMessage(){
        return message;
    }
}
