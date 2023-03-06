package com.report.hanghae_spring_report.controller;

import com.report.hanghae_spring_report.dto.LoginRequestDto;
import com.report.hanghae_spring_report.dto.SignupRequestDto;
import com.report.hanghae_spring_report.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/post/user")
public class UserController {

    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity signup(@RequestBody @Valid SignupRequestDto signupRequestDto) {
        return ResponseEntity.ok().body(userService.signup(signupRequestDto));
    }

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody LoginRequestDto loginRequestDto, HttpServletResponse response) {
        return ResponseEntity.ok().body(userService.login(loginRequestDto, response));
    }
}
