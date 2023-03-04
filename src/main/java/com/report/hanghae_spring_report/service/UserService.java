package com.report.hanghae_spring_report.service;

import com.report.hanghae_spring_report.dto.SignupRequestDto;
import com.report.hanghae_spring_report.dto.UserResponseDto;
import com.report.hanghae_spring_report.entity.User;
import com.report.hanghae_spring_report.entity.UserEnum;
import com.report.hanghae_spring_report.jwt.JwtUtil;
import com.report.hanghae_spring_report.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private UserRepository userRepository;
    private JwtUtil jwtUtil;
    private static final String ADMIN_TOKEN = "wkdwlsgurwkdwlsgur";

    public void signup(SignupRequestDto signupRequestDto) {
        String username = signupRequestDto.getUsername();
        String password = signupRequestDto.getUsername();

        if (!(username.length() >= 4 || username.length() <= 10)) throw new IllegalArgumentException("아이디는 최소 4자 이상, 10자 이하입니다.");
        if (!(password.length() >= 8 || password.length() <= 15)) throw new IllegalArgumentException("비밀번호는 최소 8자 이상, 15자 이하입니다.");
        for (char c : username.toCharArray()) {
            if (c < 'a' && c > 'z' && c < '0' && c > '9') {
                throw new IllegalArgumentException("알파벳 소문자(a~z), 숫자(0~9)");
            }
        }
        for (char c : password.toCharArray()) {
            if (c < 'a' && c > 'z' && c < 'A' && c > 'Z' && c < '0' && c > '9') {
                throw new IllegalArgumentException("알파벳 대소문자(a~z, A~Z), 숫자(0~9)");
            }
        }

        // 회원 중복 확인
        Optional<User> found = userRepository.findByUsername(username);
        if (found.isPresent()) { // Optional 객체가 값을 가지고 있다면 true, 값이 없다면 false 리턴
            throw new IllegalArgumentException("중복된 사용자가 존재합니다.");
        }

        UserEnum role = UserEnum.USER;
        if (signupRequestDto.isAdmin()) {
            if (!signupRequestDto.getAdminToken().equals(ADMIN_TOKEN)) {
                throw new IllegalArgumentException("관리자 암호가 틀려 등록이 불가능 합니다.");
            }
            role = UserEnum.ADMIN;
        }
        User user = new User(username, password, role);
        userRepository.save(user);
        return new UserResponseDto(HttpStatus.OK);
    }
}
