package com.example.demo.controller.User;

import com.example.demo.domain.user.User;
import com.example.demo.dto.user.UserRequestDto;
import com.example.demo.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private UserService userService;

//    @PostMapping("/saveUser")
//    public ResponseEntity<?> saveUser(@RequestHeader String accessToken,
//                                      @RequestBody UserRequestDto userRequest) {
//        User savedUser = userService.saveOrUpdateUser(
//                userRequest.getEmail(),
//                userRequest.getNickname(),
//                userRequest.getName(),
//                userRequest.getUniversityId()
//        );
//        return ResponseEntity.ok(savedUser);
//    }
}
