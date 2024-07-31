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

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class PostService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final UniversityRepository universityRepository;

    public Posts createPost(String nickname, PostRequestDto postRequestDto) {
        System.out.println("Nickname: " + nickname); // 닉네임 디버깅 출력
        User user = userRepository.findByName(nickname);
        if (user == null) {
            throw new RuntimeException("User not found");
        }

        Universities university = universityRepository.findById(postRequestDto.getUniversityId())
                .orElseThrow(() -> new RuntimeException("University not found"));

        Posts post = Posts.builder()
                .universities(university)
                .user(user)
                .title(postRequestDto.getTitle())
                .location_latitude(postRequestDto.getLocationLatitude())
                .location_longitude(postRequestDto.getLocationLongitude())
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

    public Posts updatePost(Long postId, String nickname, PostRequestDto postRequestDto) {
        Posts post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found"));

        if (!post.getUser().getNickname().equals(nickname)) {
            throw new RuntimeException("Unauthorized");
        }

        Universities university = universityRepository.findById(postRequestDto.getUniversityId())
                .orElseThrow(() -> new RuntimeException("University not found"));

        post.setUniversities(university);
        post.setTitle(postRequestDto.getTitle());
        post.setLocation_latitude(postRequestDto.getLocationLatitude());
        post.setLocation_longitude(postRequestDto.getLocationLongitude());
        post.setUntact(postRequestDto.isUntact());
        post.setDuration(postRequestDto.getDuration());
        post.setTrade(postRequestDto.isTrade());
        post.setReward(postRequestDto.getReward());
        post.setRequirements(postRequestDto.getRequirements());
        post.setContactlink(postRequestDto.getContactLink());
        post.setEtc(postRequestDto.getEtc());
        post.setStatus(postRequestDto.getStatus());
        post.setSurveylink(postRequestDto.getSurveyLink());
        post.setStartdate(postRequestDto.getStartdate());
        post.setEnddate(postRequestDto.getEnddate());

        return postRepository.save(post);
    }

    public Posts getPost(Long postId) {
        return postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found"));
    }

    public List<Posts> getAllPosts() {
        return postRepository.findAll();
    }

    public void deletePost(Long postId, String nickname) {
        Posts post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found"));

        if (!post.getUser().getNickname().equals(nickname)) {
            throw new RuntimeException("Unauthorized");
        }

        postRepository.delete(post);
    }
}
