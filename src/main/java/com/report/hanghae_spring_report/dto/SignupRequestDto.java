package com.report.hanghae_spring_report.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
@Setter
public class SignupRequestDto {

    @NotNull(message = "username은 필수 입니다.")
    @Size(min = 4, max = 10, message = "최소 4자 이상, 10자 이하의 숫자를 입력하세요")
    @Pattern(regexp = "^[a-z0-9]*$", message = "알파벳 소문자(a~z), 숫자(0~9)만 입력 가능합니다.")
    private String username;

    @NotNull(message = "password은 필수 입니다.")
    @Size(min = 8, max = 15, message = "최소 8자 이상, 15자 이하의 숫자를 입력하세요")
    @Pattern(regexp = "^[a-zA-Z0-9]*$", message = "알파벳 대소문자(a~z, A~Z), 숫자(0~9)만 입력 가능합니다.")
    private String password;

    private boolean admin = false;

    private String adminToken = "";
}
