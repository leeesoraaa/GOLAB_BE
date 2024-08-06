package com.example.demo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import com.example.demo.domain.user.User;

import lombok.Getter;

@Getter
public class CustomUserDetails implements UserDetails, OAuth2User {
    private User user;
    private Map<String, Object> attribute;



    /* OAuth2 로그인 사용자 */
    public CustomUserDetails(User user, Map<String, Object> attributes) {
        this.user = user;
        this.attribute = attributes;
    }

    /* 유저의 권한 목록, 권한 반환 */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return new ArrayList<>(); // 역할이 없는 경우 비어 있는 권한 목록 반환
    }

    @Override
    public String getPassword() {
        return null; // 비밀번호가 없는 경우 null 반환
    }

    @Override
    public String getUsername() {
        return user.getEmail(); // username 대신 email 사용
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return attribute;
    }

    @Override
    public String getName() {
        return user.getName(); // name 반환
    }
}
