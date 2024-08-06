package com.example.demo.service.post;

import com.example.demo.domain.post.PostsRepository;
import com.example.demo.domain.post.Posts;
import com.example.demo.domain.university.Universities;
import com.example.demo.domain.university.UniversityRepository;
import com.example.demo.domain.user.User;
import com.example.demo.domain.user.UserRepository;
import com.example.demo.dto.post.PostRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostsRepository postRepository;
    private final UserRepository userRepository;
    private final UniversityRepository universityRepository;

    @Transactional
    public Posts createPost(String email, PostRequestDto postRequestDto) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Universities university = universityRepository.findById(postRequestDto.getUniversityId())
                .orElseThrow(() -> new RuntimeException("University not found"));
        if (user.isIsbanned()) {
            throw new RuntimeException("User is banned from creating posts");
        }
        Posts post = Posts.builder()
                .user(user)
                .title(postRequestDto.getTitle())
                .location_latitude(postRequestDto.getLocation_latitude())
                .location_longitude(postRequestDto.getLocation_longitude())
                .people(postRequestDto.getPeople())
                .isuntact(postRequestDto.isIsuntact())
                .duration(postRequestDto.getDuration())
                .reward(postRequestDto.getReward())
                .requirements(postRequestDto.getRequirements())
                .contactlink(postRequestDto.getContactlink())
                .surveylink(postRequestDto.getSurveylink())
                .startdate(postRequestDto.getStartdate())
                .enddate(postRequestDto.getEnddate())
                .universities(university)
                .location_name(postRequestDto.getLocation_name())
                .build();

        return postRepository.save(post);
    }

    @Transactional
    public Posts updatePost(Long postId, PostRequestDto postRequestDto, String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
        Posts post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found"));

        if (!post.getUser().equals(user)) {
            throw new RuntimeException("Unauthorized");
        }

        Universities university = universityRepository.findById(postRequestDto.getUniversityId())
                .orElseThrow(() -> new RuntimeException("University not found"));

        post.setTitle(postRequestDto.getTitle());
        post.setLocation_latitude(postRequestDto.getLocation_latitude());
        post.setLocation_longitude(postRequestDto.getLocation_longitude());
        post.setIsuntact(postRequestDto.isIsuntact());
        post.setPeople(postRequestDto.getPeople());
        post.setDuration(postRequestDto.getDuration());
        post.setReward(postRequestDto.getReward());
        post.setRequirements(postRequestDto.getRequirements());
        post.setContactlink(postRequestDto.getContactlink());
        post.setSurveylink(postRequestDto.getSurveylink());
        post.setStartdate(postRequestDto.getStartdate());
        post.setEnddate(postRequestDto.getEnddate());
        post.setUniversities(university);
        post.setLocation_name(postRequestDto.getLocation_name());

        return postRepository.save(post);
    }

    public Posts getPost(Long postId) {
        return postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found"));
    }

    public List<Posts> getAllPosts() {
        return postRepository.findAll();
    }

    @Transactional
    public void deletePost(Long postId, String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
        Posts post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found"));

        if (!post.getUser().equals(user)) {
            throw new RuntimeException("Unauthorized");
        }

        postRepository.deleteById(postId);
    }

    public List<Posts> getPostsByUser(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return postRepository.findAllByUser(user);
    }
    public List<Posts> getPostsByUniversity(Long universityId) {
        Universities university = universityRepository.findById(universityId)
                .orElseThrow(() -> new RuntimeException("University not found"));
        return postRepository.findAllByUniversities(university);
    }
    public Optional<Posts> getClosestDeadlinePostByUniversity(Long universityId) {
        return postRepository.findTopByUniversitiesIdOrderByEnddateAsc(universityId);
    }
}
