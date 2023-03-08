package com.report.hanghae_spring_report.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ApiExceptionController {
    @ExceptionHandler(value = {ApiException.class})
    protected ResponseEntity<ApiExceptionResponse> handleCustomException(ApiException e) {
        log.error("handleCustomException throw CustomException : {}", e.getErrorCode());
        return ApiExceptionResponse.toResponseEntity(e.getErrorCode());
    }
}
