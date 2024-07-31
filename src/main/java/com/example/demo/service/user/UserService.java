package com.example.demo.service.user;

import com.example.demo.domain.university.UniversityRepository;
import com.example.demo.domain.user.User;
import com.example.demo.domain.user.UserRepository;
import com.example.demo.domain.university.Universities;
import com.example.demo.dto.user.UserRequestDto;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {

    private final UserRepository userRepository;
    private final UniversityRepository universityRepository;

    public void updateUser(String nickname, String email, UserRequestDto userRequestDto) {
        User user = userRepository.findByName(nickname);

        if (user != null) {
            // UPDATE
            user.updateUser(userRequestDto);
        } else {
            // CREATE
            throw new RuntimeException("User with nickname " + nickname + " not found. Cannot create a new user.");
        }
    }
}
