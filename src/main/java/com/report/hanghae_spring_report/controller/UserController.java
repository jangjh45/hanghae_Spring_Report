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

    // UserController 클래스의 생성자에서 주입된 UserService 클래스의 인스턴스를 저장하는 필드
    // UserService 인스턴스를 통해 비즈니스 로직을 수행
    // 이를 의존성 주입(Dependency Injection)이라고 한다.
    private final UserService userService;

    //    @ApiOperation(value = "signupTest", notes = "회원가입 테스트중")
    @PostMapping("/signup")
    public ResponseEntity signup(
            @RequestBody @Valid SignupRequestDto signupRequestDto) {
        return ResponseEntity.ok().body(userService.signup(signupRequestDto));
    }

    //    @ApiOperation(value = "loginTest", notes = "로그인 테스트중")
    @PostMapping("/login")
    public ResponseEntity login(
            @RequestBody LoginRequestDto loginRequestDto,
            HttpServletResponse response) {
        return ResponseEntity.ok().body(userService.login(loginRequestDto, response));
    }
}
