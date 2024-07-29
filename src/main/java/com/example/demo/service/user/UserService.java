package com.example.demo.service.user;

import com.example.demo.domain.user.User;
import com.example.demo.domain.user.UserRepository;
import com.example.demo.domain.university.Universities;
import com.example.demo.service.university.UniversityService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final UniversityService universityService;

    // Constructor injection
    public UserService(UserRepository userRepository, UniversityService universityService) {
        this.userRepository = userRepository;
        this.universityService = universityService;
    }



    public void saveUser(String email, String name, Long universityId) {
        // Validate the universityId
        List<Universities> universities = universityService.searchUniversitiesById(universityId);
        if (universities.isEmpty()) {
            throw new IllegalArgumentException("University not found");
        }

        User user = new User();
        user.setEmail(email);
        user.setName(name);
        user.setUniversityId(universityId);

        userRepository.save(user);
    }
}
