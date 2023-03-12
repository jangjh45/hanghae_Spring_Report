package com.report.hanghae_spring_report.dto;

import javax.validation.constraints.NotNull;

public class LoginRequestDto {
    @NotNull(message = "아이디를 입력해 주세요")
    private String username;
    @NotNull(message = "비밀번호를 입력해 주세요")
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
