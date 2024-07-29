package com.example.demo.controller.User;

import com.example.demo.service.user.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/register")
public class RegisterController {

    private final UserService userService;

    public RegisterController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String showRegisterForm() {
        return "register";  // This returns the register.html template
    }

    @PostMapping
    public String registerUser(@RequestParam String email, @RequestParam String name, @RequestParam Long universityId) {
        userService.saveUser(email, name, universityId);
        return "registrationSuccess";  // Redirect to a success page or show a success message
    }
}
