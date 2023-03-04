package com.report.hanghae_spring_report.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserResponseDto {

    private String messege;
    private int errorNum;

    public UserResponseDto(String success, int i) {
        this.messege = success;
        this.errorNum = i;
    }
}
