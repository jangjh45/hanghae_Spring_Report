package com.report.hanghae_spring_report.dto;

import lombok.Getter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Getter
public class SignupRequestDto {

    @NotNull(message = "username은 필수 입니다.")
//    @Size(min = 4, max = 10, message = "최소 4자 이상, 10자 이하의 숫자를 입력하세요")
    @Pattern(regexp = "^*[a-z0-9]{4,10}$", message = "4~10글자 알파벳 소문자(a~z), 숫자(0~9)만 입력 가능합니다.")
    private String username;

    @NotNull(message = "password은 필수 입니다.")
//    @Size(min = 8, max = 15, message = "최소 8자 이상, 15자 이하의 숫자를 입력하세요")
    @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[@#$!%*?&])[A-Za-z\\d@#$!%*?&]{8,15}$", message = "8~15글자, 글자 1개, 숫자 1개, 특수문자 1개 꼭 입력해야합니다.")
    private String password;

    private boolean admin = false;

    private String adminToken = "";
}
