package com.user.userservice.Exceptions.Handler;


import com.user.userservice.Enums.ErrorCode;
import com.user.userservice.Exceptions.ApiErrorResponse;
import com.user.userservice.Exceptions.CustomException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;


@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ApiErrorResponse> handleCustomException(CustomException ex){
        ErrorCode errorCode = ex.getErrorCode();
        ApiErrorResponse response =
                new ApiErrorResponse
                        (errorCode.getMessage(), List.of(ex.getLocalizedMessage()));

        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR; // Default to 500

        switch (errorCode){
            case USER_NOT_FOUND -> status = HttpStatus.NOT_FOUND;
            case DATA_INTEGRITY_VIOLATION ->  status = HttpStatus.BAD_REQUEST;
            case VALIDATION_ERROR -> status = HttpStatus.UNPROCESSABLE_ENTITY;
        }


    return new ResponseEntity<>(response, status);


    }
}
