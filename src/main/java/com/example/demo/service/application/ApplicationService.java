package com.example.demo.service.application;

import com.example.demo.domain.application.Application;
import com.example.demo.domain.application.Application.Status;
import com.example.demo.domain.application.ApplicationRepository;
import com.example.demo.domain.post.Posts;
import com.example.demo.domain.post.PostsRepository;
import com.example.demo.domain.user.User;
import com.example.demo.dto.application.ApplicationGetResponseDto;
import com.example.demo.dto.application.ApplicationStatusResponseDto;
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

    public List<ApplicationGetResponseDto> findByPostId(Long postId) {
        return applicationRepository.findByPostId(postId)
                .stream()
                .filter(application -> application.getStatus() != Application.Status.Rejected)
                .map(ApplicationGetResponseDto::new)
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

    public ApplicationStatusResponseDto acceptApplication(Long applicationId, String userEmail) {
        Optional<Application> optionalApplication = applicationRepository.findById(applicationId);

        if (optionalApplication.isEmpty()) {
            return new ApplicationStatusResponseDto("Error", "Application not found", null);
        }

        Application application = optionalApplication.get();
        Posts post = application.getPost();
        User author = post.getUser();

        if (!author.getEmail().equals(userEmail)) {
            return new ApplicationStatusResponseDto("Error", "Unauthorized", null);
        }

        if (application.getStatus() != Status.Requested || !application.isTrade()) {
            return new ApplicationStatusResponseDto("Error", "Invalid status transition", null);
        }

        application.setStatus(Status.Soorack);
        Application updatedApplication = applicationRepository.save(application);

        // 교환할 게시물의 상태도 Soorack으로 변경
        Posts exchangePost = application.getExchangePost();
        if (exchangePost != null) {
            Application exchangeApplication = new Application();
            exchangeApplication.setPost(exchangePost);
            exchangeApplication.setUser(author);  // 교환 게시물의 작성자를 지원자로 설정
            exchangeApplication.setStatus(Status.Soorack);
            exchangeApplication.setTrade(true);
            exchangeApplication.setExchangePost(post);  // 도와주기한 postid 설정

            applicationRepository.save(exchangeApplication);
        }

        String contactLink = null;
        if (exchangePost != null) {
            contactLink = exchangePost.getContactlink();
        }

        return new ApplicationStatusResponseDto("Soorack","지원이 수락되었습니다.", contactLink);
    }

    public String changeApplicationStatus(Long id, String userEmail, Status newStatus, Status... requiredStatuses) {
        Optional<Application> optionalApplication = applicationRepository.findById(id);
        if (optionalApplication.isPresent()) {
            Application application = optionalApplication.get();
            Posts post = application.getPost();
            User author = post.getUser();

            if (!author.getEmail().equals(userEmail)) {
                return "작성자만 지원 상태를 변경할 수 있습니다.";
            }

            boolean isValidStatus = false;
            for (Status requiredStatus : requiredStatuses) {
                if (application.getStatus() == requiredStatus) {
                    isValidStatus = true;
                    break;
                }
            }

            if (isValidStatus) {
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