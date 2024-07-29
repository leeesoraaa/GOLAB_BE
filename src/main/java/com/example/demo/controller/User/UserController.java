package com.example.demo.controller.User;

import com.example.demo.domain.user.User;
import com.example.demo.domain.user.UserUpdateRequest;
import com.example.demo.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

@Controller
public class UserController {

    private final UserService userService;

    // Constructor injection
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users/")
    public String userInfoForm(Model model) {
        // 사용자 정보를 입력받는 폼을 반환
        return "userinfo";
    }

//    @PutMapping("/users/")
//    public ResponseEntity<String> saveUser(@RequestBody UserUpdateRequest request) {
//        try {
//            userService.saveUser(request.getEmail(), request.getName(), request.getUniversityId());
//            return ResponseEntity.ok("User updated successfully");
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to update user");
//        }
//    }

    @PostMapping("/register")
    public void registerUser(@RequestParam String email, @RequestParam String name, @RequestParam Long universityId) {
        userService.saveUser(email, name, universityId);
    }
}
