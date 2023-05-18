package com.absbank.exception;

import com.absbank.model.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class APIExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(CustomerException.class)
    public ResponseEntity<ErrorResponse> handleCreatorException(CustomerException customerException){
        return new ResponseEntity<>(ErrorResponse.builder()
                .errorMessage(customerException.getMessage())
                .errorCode(customerException.getErrorCode())
                .build(),
                HttpStatus.valueOf(customerException.getStatus()));
    }
}
