package com.example.demo.controller.university;


import com.example.demo.domain.university.Universities;
import com.example.demo.service.university.UniversityService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UniversityController {
    private final UniversityService universityService;

    // Constructor injection
    public UniversityController(UniversityService universityService) {
        this.universityService = universityService;
    }

    @GetMapping("/search")
    public List<Universities> searchUniversities(@RequestParam(defaultValue = "") String name) {
        return universityService.searchUniversities(name);
    }
}
