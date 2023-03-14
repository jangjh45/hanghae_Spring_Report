package com.report.hanghae_spring_report.security;

import com.report.hanghae_spring_report.entity.User;
import com.report.hanghae_spring_report.entity.UserRoleEnum;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

public class UserDetailsImpl implements UserDetails {

    // 인증이 완료된 사용자 추가
    private final User user; // 인증 완료된 user객체
    private final String username;

    // UserDetailsServiceImpl 에서 user의 정보를 가져옴
    // user와 이름, 비밀번호를 생성자로 초기화
    public UserDetailsImpl(User user, String username) {
        this.user = user;
        this.username = username;
    }

    // 인증완료된 User 를 가져오는 Getter
    public User getUser() {
        return user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        UserRoleEnum role = user.getRole(); // 사용자가 가지고있는 권한을 가지고 와서
        String authority = role.getAuthority();

        // 사용자의 권한 GrantedAuthority 로 추상화 및 반환
        SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority(authority);
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(simpleGrantedAuthority);

        return authorities;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }
}
