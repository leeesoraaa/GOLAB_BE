package com.example.demo.controller.User;

import com.example.demo.dto.user.UserRequestDto;
import com.example.demo.security.JwtTokenProvider;
import com.example.demo.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final JwtTokenProvider jwtTokenProvider;

    @PostMapping()
    public ResponseEntity<String> saveUser(@RequestHeader String accessToken,
                                           @RequestBody UserRequestDto userRequest) {

        String email = jwtTokenProvider.getEmail(accessToken);
        userService.updateUser(email, userRequest);

        return ResponseEntity.ok("저장되었습니다.");
    }
}
