package com.example.demo.security;

import com.example.demo.domain.user.User;
import com.example.demo.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class OAuth2LoginSuccessHandler implements AuthenticationSuccessHandler {

    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;

    @Value("${frontend.url}")
    private String frontendUrl;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        OAuth2AuthenticationToken token = (OAuth2AuthenticationToken) authentication;
        OAuth2User user = token.getPrincipal();
        Map<String, Object> attributes = user.getAttributes();

        String email = null;
        String nickname = null;

        if (token.getAuthorizedClientRegistrationId().equals("kakao")) {
            Map<String, Object> kakaoAccount = (Map<String, Object>) attributes.get("kakao_account");
            Map<String, Object> profile = (Map<String, Object>) kakaoAccount.get("profile");
            email = (String) kakaoAccount.get("email");
            nickname = (String) profile.get("nickname");
        }

        if (email != null && nickname != null) {
            Optional<User> existingUser = userRepository.findByEmail(email);
            User userEntity;
            if (existingUser.isEmpty()) {
                userEntity = new User();
                userEntity.setEmail(email);
                userEntity.setNickname(nickname);
                userRepository.save(userEntity);
            } else {
                userEntity = existingUser.get();
            }

            boolean isRegisterComplete = userEntity.getName() != null && !userEntity.getName().isEmpty() &&
                    userEntity.getUniversityId() != null;

            String jwtToken = jwtTokenProvider.createToken(userEntity.getEmail());
            String redirectUrl = frontendUrl + "/authkakao?accessToken=" + jwtToken +
                    "&isRegisterComplete=" + isRegisterComplete;
            response.sendRedirect(redirectUrl);
        } else {
            response.sendRedirect("/login?error");
        }
    }
}
