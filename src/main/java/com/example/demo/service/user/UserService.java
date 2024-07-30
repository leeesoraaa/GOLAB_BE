package com.example.demo.service.user;

import com.example.demo.domain.university.UniversityRepository;
import com.example.demo.domain.user.User;
import com.example.demo.domain.user.UserRepository;
import com.example.demo.domain.university.Universities;
import com.example.demo.dto.user.UserRequestDto;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {

    private final UserRepository userRepository;
    private final UniversityRepository universityRepository;

    public void updateUser(String nickname, String email, UserRequestDto userRequestDto) {

        if (userRepository.countByName(nickname) > 0) {
            //UPDATE

            User user = userRepository.findByName(nickname);
            user.updateUser(userRequestDto);

        } else {
            //CREATE

            Universities universities = universityRepository.findById(userRequestDto.getUniversityId()).orElseThrow(() -> new RuntimeException(String.valueOf(Error.class)));

            User user = User.builder()
                    .name(userRequestDto.getName())
                    .email(email)
                    .nickname(nickname)
                    .universities(universities).build();

            userRepository.save(user);
        }


    }

//    public User saveOrUpdateUser(String email, String nickname, String name, Long universityId) {
//
//        User user = userRepository.findByEmail(email);
//
//        if (user == null) {
//            user = new User();
//            user.setEmail(email);
//            user.setNickname(nickname);
//        }
//
//        user.setName(name);
//
//        String name = user.getUniversities().getName();
//
//        user.setUniversityId(universityId);
//
//        return userRepository.save(user);
//    }
}
