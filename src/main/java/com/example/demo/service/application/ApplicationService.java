package com.example.demo.service.application;

import com.example.demo.domain.application.Application;
import com.example.demo.domain.post.Posts;
import com.example.demo.domain.application.ApplicationRepository;
import com.example.demo.domain.post.PostsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ApplicationService {

    private final ApplicationRepository applicationRepository;
    private final PostsRepository postRepository;

    public List<Application> findAll() {
        return applicationRepository.findAll();
    }

    public Optional<Application> findById(Long id) {
        return applicationRepository.findById(id);
    }

    public Application save(Application application) {
        return applicationRepository.save(application);
    }

    public void deleteById(Long id) {
        applicationRepository.deleteById(id);
    }

    public List<Application> findByPostId(Long postId) {
        return applicationRepository.findByPostId(postId);
    }

    public Optional<Posts> findPostById(Long id) {
        return postRepository.findById(id);
    }
}
