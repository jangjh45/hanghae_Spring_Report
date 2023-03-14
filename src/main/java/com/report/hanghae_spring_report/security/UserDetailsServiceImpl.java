package com.report.hanghae_spring_report.security;

import com.report.hanghae_spring_report.entity.User;
import com.report.hanghae_spring_report.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service // 빈으로 등록
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    // 사용자가 입력을 한 username을 가져와서 파라미터로 넣어준 다음에
    // DB에 접근을 해서 user를 조회해서 인증한다.
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 데이터 베이스에서 사용자 조회
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다."));

        // UserDetailsImpl 로 조회된 값을 반환 한다.
        return new UserDetailsImpl(user, user.getUsername());
    }

}
