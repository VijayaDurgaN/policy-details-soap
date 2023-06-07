package com.allstateonboarding.policydetailsrest.controller;


import com.allstateonboarding.policydetailsrest.dto.ErrorInfo;
import com.allstateonboarding.policydetailsrest.exception.BadRequestException;
import com.allstateonboarding.policydetailsrest.exception.PolicyNotFoundException;
import com.allstateonboarding.policydetailsrest.exception.ServiceUnavailableException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class PolicyDetailsControllerAdvice {

    @ExceptionHandler(ServiceUnavailableException.class)
    public ResponseEntity<ErrorInfo> handle(ServiceUnavailableException e) {
        return new ResponseEntity<>(
                ErrorInfo.builder()
                        .message(e.getMessage())
                        .code("SERVICE_UNAVAILABLE")
                        .timestamp(LocalDateTime.now())
                        .build(),
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }

    @ExceptionHandler(PolicyNotFoundException.class)
    public ResponseEntity<ErrorInfo> handle(PolicyNotFoundException e) {
        return new ResponseEntity<>(
                ErrorInfo.builder()
                        .message(e.getMessage())
                        .code("POLICY_NOT_FOUND")
                        .timestamp(LocalDateTime.now())
                        .build(),
                HttpStatus.NOT_FOUND
        );
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ErrorInfo> handle(BadRequestException e) {
        return new ResponseEntity<>(
                ErrorInfo.builder()
                        .message(e.getMessage())
                        .code("BAD_REQUEST")
                        .timestamp(LocalDateTime.now())
                        .build(),
                HttpStatus.BAD_REQUEST
        );
    }

}
