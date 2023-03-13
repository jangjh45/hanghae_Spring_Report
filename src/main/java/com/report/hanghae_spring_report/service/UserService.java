package com.report.hanghae_spring_report.service;

import com.report.hanghae_spring_report.common.ApiException;
import com.report.hanghae_spring_report.common.ExceptionEnum;
import com.report.hanghae_spring_report.dto.LoginRequestDto;
import com.report.hanghae_spring_report.dto.MessageResponse;
import com.report.hanghae_spring_report.dto.SignupRequestDto;
import com.report.hanghae_spring_report.dto.StatusEnum;
import com.report.hanghae_spring_report.entity.User;
import com.report.hanghae_spring_report.entity.UserRoleEnum;
import com.report.hanghae_spring_report.jwt.JwtUtil;
import com.report.hanghae_spring_report.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;

    // 관리자 코드
    private static final String ADMIN_TOKEN = "wkdwlsgurwkdwlsgur";

    // 회원가입
    @Transactional
    public MessageResponse signup(SignupRequestDto signupRequestDto) {

        // 아이디
        String username = signupRequestDto.getUsername();
        // 비밀번호 인코더
        String password = passwordEncoder.encode(signupRequestDto.getPassword());
//        String password = signupRequestDto.getPassword();

        // 회원 중복 확인
        Optional<User> found = userRepository.findByUsername(username);
        if (found.isPresent()) { // Optional 객체가 값을 가지고 있다면 true, 값이 없다면 false 리턴
            throw new ApiException(ExceptionEnum.NOT_FOUND_USER);
        }

        UserRoleEnum role = UserRoleEnum.USER; // 사용자권한이 기본값으로 설정함
        if (signupRequestDto.isAdmin()) { // signupRequestDto에 있는 Admin이 참이면 관리자 토큰 확인
            if (!signupRequestDto.getAdminToken().equals(ADMIN_TOKEN)) { // 설정된 관리자 토큰하고 일치하는가?
                throw new IllegalArgumentException("관리자 암호가 틀려 등록이 불가능 합니다.");
            }
            role = UserRoleEnum.ADMIN; // 조건문이 다 통과되면 관리자계정이라고 판단
        }
        User user = new User(username, password, role);
        userRepository.save(user);
        return new MessageResponse(StatusEnum.OK);
    }

    // 로그인
    @Transactional(readOnly = true)
    public MessageResponse login(LoginRequestDto loginRequestDto, HttpServletResponse response) {
        String username = loginRequestDto.getUsername();
        String password = loginRequestDto.getPassword();

        // 사용자 확인
        User user = userRepository.findByUsername(username).orElseThrow(
                () -> new ApiException(ExceptionEnum.NOT_FOUND_USER)
        );
        // 비밀번호 확인
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new ApiException(ExceptionEnum.NOT_FOUND_USER);
        }
        response.addHeader(JwtUtil.AUTHORIZATION_HEADER, jwtUtil.createToken(user.getUsername(), user.getRole()));
        return new MessageResponse(StatusEnum.OK);
    }
}
