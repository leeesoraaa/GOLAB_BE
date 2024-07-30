package com.example.demo.service.user;

import com.example.demo.domain.user.User;
import com.example.demo.domain.user.UserRepository;
import com.example.demo.domain.university.Universities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User saveOrUpdateUser(String email, String nickname, String name, Long universityId) {

        User user = userRepository.findByEmail(email);

        if (user == null) {
            user = new User();
            user.setEmail(email);
            user.setNickname(nickname);
        }

        user.setName(name);
        user.setUniversityId(universityId);

        return userRepository.save(user);
    }
}
