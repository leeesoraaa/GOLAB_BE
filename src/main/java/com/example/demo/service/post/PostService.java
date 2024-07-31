package com.example.demo.service.post;

import com.example.demo.domain.post.PostRepository;
import com.example.demo.domain.post.Posts;
import com.example.demo.domain.university.Universities;
import com.example.demo.domain.university.UniversityRepository;
import com.example.demo.domain.user.User;
import com.example.demo.domain.user.UserRepository;
import com.example.demo.dto.post.PostRequestDto;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class PostService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final UniversityRepository universityRepository;

    public Posts createPost(Long user_id, PostRequestDto postRequestDto) {
        User user = userRepository.findById(user_id).orElseThrow(() -> new RuntimeException("User not found"));

        Universities university = universityRepository.findById(postRequestDto.getUniversityId())
                .orElseThrow(() -> new RuntimeException("University not found"));
        Posts post = Posts.builder()
                .universities(university) // Assuming you have a constructor or method to create Universities
                .user(user)
                .title(postRequestDto.getTitle())
                .location_latitude(postRequestDto.getLocationLatitude())
                .location_longitude(postRequestDto.getLocationLongitude())
//                .created_at(postRequestDto.getCreated_At())
//                .updated_at(postRequestDto.getUpdated_At())
                .isuntact(postRequestDto.isUntact())
                .duration(postRequestDto.getDuration())
                .istrade(postRequestDto.isTrade())
                .reward(postRequestDto.getReward())
                .requirements(postRequestDto.getRequirements())
                .contactlink(postRequestDto.getContactLink())
                .etc(postRequestDto.getEtc())
                .status(postRequestDto.getStatus())
                .surveylink(postRequestDto.getSurveyLink())
                .startdate(postRequestDto.getStartdate())
                .enddate(postRequestDto.getEnddate())
                .build();

        return postRepository.save(post);
    }
}
