package com.example.demo.security;

import com.example.demo.domain.user.User;
import com.example.demo.domain.user.UserRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;
import java.util.Optional;

@Component
public class OAuth2LoginSuccessHandler implements AuthenticationSuccessHandler {

    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;

    public OAuth2LoginSuccessHandler(UserRepository userRepository, JwtTokenProvider jwtTokenProvider) {
        this.userRepository = userRepository;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        OAuth2AuthenticationToken token = (OAuth2AuthenticationToken) authentication;
        OAuth2User user = token.getPrincipal();
        Map<String, Object> attributes = user.getAttributes();

        String email = null;
        String nickname = null;

        if (token.getAuthorizedClientRegistrationId().equals("google")) {
            email = (String) attributes.get("email");
            nickname = (String) attributes.get("name"); // 구글의 경우
        } else if (token.getAuthorizedClientRegistrationId().equals("kakao")) {
            Map<String, Object> kakaoAccount = (Map<String, Object>) attributes.get("kakao_account");
            Map<String, Object> profile = (Map<String, Object>) kakaoAccount.get("profile");
            email = (String) kakaoAccount.get("email");
            nickname = (String) profile.get("nickname"); // 카카오의 경우
        }

        if (email != null && nickname != null) {
            Optional<User> existingUser = userRepository.findByEmail(email);
            if (existingUser.isEmpty()) {
                User newUser = new User();
                newUser.setEmail(email);
                newUser.setNickname(nickname);
                userRepository.save(newUser);
            }
        }

        String jwtToken = jwtTokenProvider.createToken(authentication);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write("{\"token\": \"" + jwtToken + "\"}");
    }
}
