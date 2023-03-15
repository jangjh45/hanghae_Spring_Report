package com.report.hanghae_spring_report.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice // 예외 처리를 담당하는 클래스에 적용
public class ApiExceptionController {

    @ExceptionHandler(value = {ApiException.class})
    protected ResponseEntity<ApiExceptionResponse> handleCustomException(ApiException e) {
        log.error("handleCustomException : {}", e.getErrorCode());
        return ApiExceptionResponse.toResponseEntity(e.getErrorCode());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler
    public ErrorResult singupExHandler(MethodArgumentNotValidException e) {
        log.error("ERROR : {}", e.getBindingResult().getFieldErrors().get(0).getDefaultMessage());
        return new ErrorResult(HttpStatus.BAD_REQUEST, e.getBindingResult().getFieldErrors().get(0).getDefaultMessage());
    }
}
