package com.example.demo.service.application;

import com.example.demo.domain.application.Application;
import com.example.demo.domain.application.Application.Status;
import com.example.demo.domain.application.ApplicationRepository;
import com.example.demo.domain.post.Posts;
import com.example.demo.domain.post.PostsRepository;
import com.example.demo.domain.user.User;
import com.example.demo.dto.application.ApplicationResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
        return applicationRepository.findByPostId(postId)
                .stream()
                .filter(application -> application.getStatus() != Application.Status.Rejected)
                .collect(Collectors.toList());
    }

    public Optional<Posts> findPostById(Long id) {
        return postRepository.findById(id);
    }

    public boolean existsByPostIdAndUserId(Long postId, Long userId) {
        return applicationRepository.existsByPostIdAndUserId(postId, userId);
    }

    public boolean isUserPostAuthor(Long postId, String userEmail) {
        Optional<Posts> postOptional = postRepository.findById(postId);
        return postOptional.isPresent() && postOptional.get().getUser().getEmail().equals(userEmail);
    }

    public ApplicationResponseDto acceptApplication(Long applicationId, String userEmail) {
        Optional<Application> optionalApplication = applicationRepository.findById(applicationId);

        if (optionalApplication.isEmpty()) {
            return new ApplicationResponseDto("Error", "Application not found", null);
        }

        Application application = optionalApplication.get();
        Posts post = application.getPost();
        User author = post.getUser();

        if (!author.getEmail().equals(userEmail)) {
            return new ApplicationResponseDto("Error", "Unauthorized", null);
        }

        if (application.getStatus() != Status.Requested || !application.isTrade()) {
            return new ApplicationResponseDto("Error", "Invalid status transition", null);
        }

        application.setStatus(Status.Soorack);
        Application updatedApplication = applicationRepository.save(application);

        String contactLink = null;
        if (application.getExchangePost() != null) {
            contactLink = application.getExchangePost().getContactlink();
        }

        return new ApplicationResponseDto("Soorack","지원이 수락되었습니다.", contactLink);
    }

    public String changeApplicationStatus (Long id, String userEmail, Status newStatus, Status requiredStatus){
        Optional<Application> optionalApplication = applicationRepository.findById(id);
        if (optionalApplication.isPresent()) {
            Application application = optionalApplication.get();
            Posts post = application.getPost();
            User author = post.getUser();

            if (!author.getEmail().equals(userEmail)) {
                return "작성자만 지원 상태를 변경할 수 있습니다.";
            }

            if (application.getStatus() == requiredStatus) {
                application.setStatus(newStatus);
                applicationRepository.save(application);
                return "지원 상태가 변경되었습니다.";
            } else {
                return "지원 상태를 변경할 수 없습니다.";
            }
        } else {
            return "지원 정보를 찾을 수 없습니다.";
        }
    }
}
