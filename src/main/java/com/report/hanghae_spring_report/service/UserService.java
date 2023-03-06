package com.report.hanghae_spring_report.service;

import com.report.hanghae_spring_report.dto.LoginRequestDto;
import com.report.hanghae_spring_report.dto.MessageResponse;
import com.report.hanghae_spring_report.dto.SignupRequestDto;
import com.report.hanghae_spring_report.dto.StatusEnum;
import com.report.hanghae_spring_report.entity.User;
import com.report.hanghae_spring_report.entity.UserEnum;
import com.report.hanghae_spring_report.jwt.JwtUtil;
import com.report.hanghae_spring_report.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private static final String ADMIN_TOKEN = "wkdwlsgurwkdwlsgur";

    @Transactional
    public MessageResponse signup(SignupRequestDto signupRequestDto) {
        String username = signupRequestDto.getUsername();
        String password = signupRequestDto.getPassword();

        // 회원 중복 확인
        Optional<User> found = userRepository.findByUsername(username);
        if (found.isPresent()) { // Optional 객체가 값을 가지고 있다면 true, 값이 없다면 false 리턴
            throw new IllegalArgumentException("중복된 사용자가 존재합니다.");
        }

        UserEnum role = UserEnum.USER; // 사용자권한이 기본값으로 설정함
        if (signupRequestDto.isAdmin()) { // signupRequestDto에 있는 Admin이 참이면 관리자 토큰 확인
            if (!signupRequestDto.getAdminToken().equals(ADMIN_TOKEN)) { // 설정된 관리자 토큰하고 일치하는가?
                throw new IllegalArgumentException("관리자 암호가 틀려 등록이 불가능 합니다.");
            }
            role = UserEnum.ADMIN; // 조건문이 다 통과되면 관리자계정이라고 판단
        }
        User user = new User(username, password, role);
        userRepository.save(user);
        return new MessageResponse(StatusEnum.OK);
    }

    @Transactional(readOnly = true)
    public MessageResponse login(LoginRequestDto loginRequestDto, HttpServletResponse response) {
        String username = loginRequestDto.getUsername();
        String password = loginRequestDto.getPassword();

        // 사용자 확인
        User user = userRepository.findByUsername(username).orElseThrow(
                () -> new IllegalArgumentException("등록된 사용자가 없습니다.")
        );
        // 비밀번호 확인
        if (!user.getPassword().equals(password)) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }
        response.addHeader(JwtUtil.AUTHORIZATION_HEADER, jwtUtil.createToken(user.getUsername(), user.getRole()));
        return new MessageResponse(StatusEnum.OK);
    }
}
