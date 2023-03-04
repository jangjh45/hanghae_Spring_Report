package com.report.hanghae_spring_report.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class UserResponseDto {

    private HttpStatus status;

    public UserResponseDto(HttpStatus ok) {
        this.status = ok;

    }
}
