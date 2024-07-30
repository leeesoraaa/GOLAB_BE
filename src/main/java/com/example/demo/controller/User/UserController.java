package com.example.demo.controller.User;

import com.example.demo.dto.user.UserRequestDto;
import com.example.demo.security.JwtTokenProvider;
import com.example.demo.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final JwtTokenProvider jwtTokenProvider;

    @PostMapping("/saveUser")
    public ResponseEntity<String> saveUser(@RequestHeader String accessToken,
                                      @RequestBody UserRequestDto userRequest) {

        String nickname = jwtTokenProvider.getNickName(accessToken);
        String email = jwtTokenProvider.getEmail(accessToken);
        userService.updateUser(nickname, email, userRequest);

        return ResponseEntity.ok("저장되었습니다.");

//        User savedUser = userService.saveOrUpdateUser(
//
//                userRequest.getName(),
//                userRequest.getUniversityId()
//        );
//        return ResponseEntity.ok(savedUser);
    }
}
