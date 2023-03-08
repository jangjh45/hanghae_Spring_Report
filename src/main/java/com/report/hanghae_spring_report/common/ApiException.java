package com.report.hanghae_spring_report.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ApiException extends RuntimeException {
    private final ExceptionEnum errorCode;
}
