package com.example.demo.service.post;

import com.example.demo.domain.post.PostRepository;
import com.example.demo.domain.post.Posts;
import com.example.demo.domain.user.User;
import com.example.demo.domain.user.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class PostService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public Posts createPost(Long userid, Posts post) {
        User user = userRepository.findById(userid).orElseThrow(() -> new RuntimeException("User not found"));
        post.setUser(user);
        return postRepository.save(post);
    }
}
