package com.example.demo.controller.User;

import com.example.demo.domain.user.User;
import com.example.demo.dto.user.UserRequestDto;
import com.example.demo.security.JwtTokenProvider;
import com.example.demo.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

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

    @GetMapping("/mypage")
    public ResponseEntity<Map<String, Object>> getUser(@RequestHeader String accessToken) {
        String email = jwtTokenProvider.getEmail(accessToken);
        User user = userService.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        UserRequestDto userRequestDto = UserRequestDto.builder()
                .name(user.getName())
                .universityId(user.getUniversityId())
                .build();

        Map<String, Object> response = new HashMap<>();
        response.put("user", userRequestDto);
        response.put("nickname", user.getNickname());
        response.put("email", user.getEmail());
        response.put("noshow", user.getNoshow());
        response.put("late", user.getLate());
        response.put("trust", user.getTrust());
        response.put("isbanned", user.isIsbanned());
        response.put("participated", user.getParticipated());

        return ResponseEntity.ok(response);
    }

}
