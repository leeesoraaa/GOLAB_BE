package com.example.demo.controller.application;

import com.example.demo.domain.application.Application;
import com.example.demo.domain.application.Application.Status;
import com.example.demo.domain.post.Posts;
import com.example.demo.domain.user.User;
import com.example.demo.dto.application.ApplicationRequestDto;
import com.example.demo.service.application.ApplicationService;
import com.example.demo.service.post.PostService;
import com.example.demo.service.user.UserService;
import com.example.demo.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/")
@RequiredArgsConstructor
public class ApplicationController {

    private final ApplicationService applicationService;
    private final PostService postService;
    private final UserService userService;
    private final JwtTokenProvider jwtTokenProvider;

    @GetMapping("posts/{postId}/applications")
    public ResponseEntity<List<Application>> getApplicationsByPostId(@PathVariable Long postId) {
        List<Application> applications = applicationService.findByPostId(postId);
        return ResponseEntity.ok(applications);
    }

    @PostMapping("posts/{postId}/applications")
    public ResponseEntity<String> createApplication(@PathVariable Long postId, @RequestHeader("accessToken") String token, @RequestBody ApplicationRequestDto applicationRequestDto) {
        String email = jwtTokenProvider.getEmail(token.replace("Bearer ", ""));
        Optional<User> user = userService.findByEmail(email);

        if (user.isEmpty()) {
            return ResponseEntity.status(401).body("Unauthorized");
        }

        Posts post = postService.getPost(postId);
        if (post != null) {
            Application application = new Application();
            application.setPost(post);
            application.setUser(user.get());
            application.setStatus(Status.Requested);
            application.setTrade(applicationRequestDto.isTrade());

            if (applicationRequestDto.isTrade()) {
                Posts exchangePost = postService.getPost(applicationRequestDto.getExchangePostId());
                if (exchangePost != null) {
                    application.setExchangePost(exchangePost);
                } else {
                    return ResponseEntity.badRequest().body("Invalid exchange post ID");
                }
            }

            applicationService.save(application);
            return ResponseEntity.ok("지원되었습니다.");
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/applications/{id}")
    public ResponseEntity<Application> getApplicationById(@PathVariable Long id) {
        Optional<Application> application = applicationService.findById(id);
        return application.map(ResponseEntity ::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/applications/{id}")
    public ResponseEntity<Application> updateApplication(@PathVariable Long id, @RequestBody Application applicationDetails) {
        Optional<Application> optionalApplication = applicationService.findById(id);
        if (optionalApplication.isPresent()) {
            Application application = optionalApplication.get();
            application.setStatus(applicationDetails.getStatus());
            application.setTrade(applicationDetails.isTrade());
            application.setExchangePost(applicationDetails.getExchangePost());
            Application updatedApplication = applicationService.save(application);
            return ResponseEntity.ok(updatedApplication);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/applications/{id}")
    public ResponseEntity<Void> deleteApplication(@PathVariable Long id) {
        applicationService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
