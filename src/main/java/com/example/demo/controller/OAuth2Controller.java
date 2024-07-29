package com.example.demo.controller;

import com.example.demo.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OAuth2Controller {

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @GetMapping("/oauth2/callback/kakao")
    public String handleKakaoCallback(@RequestParam("code") String code) {
        // 여기서 액세스 토큰을 가져오는 코드를 구현해야 합니다.
        // 예: String accessToken = getAccessTokenFromCode(code);

        // JWT 생성
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String jwtToken = jwtTokenProvider.createToken(authentication);

        return "Authorization code: " + code + ", JWT: " + jwtToken;
    }

    @GetMapping("/oauth2/callback/google")
    public String handleGoogleCallback(@RequestParam("code") String code) {
        // 여기서 액세스 토큰을 가져오는 코드를 구현해야 합니다.
        // 예: String accessToken = getAccessTokenFromCode(code);

        // JWT 생성
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String jwtToken = jwtTokenProvider.createToken(authentication);

        return "Authorization code: " + code + ", JWT: " + jwtToken;
    }
}
