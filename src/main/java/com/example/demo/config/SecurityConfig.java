package com.example.demo.config;

import com.example.demo.security.JwtAuthenticationFilter;
import com.example.demo.security.JwtTokenProvider;
import com.example.demo.security.OAuth2LoginSuccessHandler;
import com.example.demo.service.CustomUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final JwtTokenProvider jwtTokenProvider;
    private final OAuth2LoginSuccessHandler oAuth2LoginSuccessHandler;
    private final CustomUserDetailsService customUserDetailsService;

    public SecurityConfig(JwtTokenProvider jwtTokenProvider, OAuth2LoginSuccessHandler oAuth2LoginSuccessHandler, CustomUserDetailsService customUserDetailsService) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.oAuth2LoginSuccessHandler = oAuth2LoginSuccessHandler;
        this.customUserDetailsService = customUserDetailsService;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(authorizeRequests ->
                        authorizeRequests
                                .requestMatchers("/", "/login", "/oauth2/**", "/user/**").permitAll()
                                .anyRequest().authenticated()
                )
                .formLogin(formLogin ->
                        formLogin
                                .loginPage("/login")
                                .permitAll()
                )
                .oauth2Login(oauth2Login ->
                        oauth2Login
                                .loginPage("/login")
                                .successHandler(oAuth2LoginSuccessHandler)
                )
                .logout(logout ->
                        logout
                                .logoutUrl("/logout")
                                .logoutSuccessUrl("/")
                                .invalidateHttpSession(true)
                                .deleteCookies("JSESSIONID")
                                .permitAll()
                )
                .addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider, customUserDetailsService), UsernamePasswordAuthenticationFilter.class)
                .sessionManagement(sessionManagement ->
                        sessionManagement
                                .maximumSessions(1)
                                .expiredUrl("/login?expired")
                );

        return http.build();
    }
}
