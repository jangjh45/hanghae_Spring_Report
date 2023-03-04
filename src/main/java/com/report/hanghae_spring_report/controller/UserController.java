package com.report.hanghae_spring_report.controller;

import com.report.hanghae_spring_report.dto.SignupRequestDto;
import com.report.hanghae_spring_report.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/post/user")
public class UserController {

    private UserService userService;

    @PostMapping("/signup")
    public String signup(@RequestBody SignupRequestDto signupRequestDto) {
        userService.signup(signupRequestDto);
        return "success";
    }
}
