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
@RequestMapping()
@RequiredArgsConstructor
public class ApplicationController {

    private final ApplicationService applicationService;
    private final PostService postService;
    private final UserService userService;
    private final JwtTokenProvider jwtTokenProvider;

    @GetMapping("/posts/{postId}/applications")
    public ResponseEntity<List<Application>> getApplicationsByPostId(@PathVariable Long postId) {
        List<Application> applications = applicationService.findByPostId(postId);
        return ResponseEntity.ok(applications);
    }

    @PostMapping("/posts/{postId}/applications")
    public ResponseEntity<String> createApplication(@PathVariable Long postId, @RequestHeader("accessToken") String token, @RequestBody ApplicationRequestDto applicationRequestDto) {
        String email = jwtTokenProvider.getEmail(token.replace("Bearer ", ""));
        Optional<User> user = userService.findByEmail(email);

        if (user.isEmpty()) {
            return ResponseEntity.status(401).body("Unauthorized");
        }

        if (user.get().isIsbanned()) {
            throw new RuntimeException("User is banned from creating posts");
        }

        Posts post = postService.getPost(postId);
        if (post != null) {
            User author = post.getUser();

            if (author.getEmail().equals(email)) {
                return ResponseEntity.badRequest().body("작성자는 지원할 수 없습니다.");
            }
            if (applicationService.existsByPostIdAndUserId(postId, user.get().getId())) {
                return ResponseEntity.badRequest().body("이미 지원되었습니다.");
            }

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
        return application.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // 도와주기일때 수락하기
    @PutMapping("/applications/{id}/soorack")
    public ResponseEntity<String> soorackApplication(@PathVariable Long id, @RequestHeader("accessToken") String token) {
        String email = jwtTokenProvider.getEmail(token.replace("Bearer ", ""));
        String result = applicationService.changeApplicationStatus(id, email, Status.Soorack, Status.Requested);
        if (result.equals("지원 상태가 변경되었습니다.")) {
            return ResponseEntity.ok(result);
        } else {
            return ResponseEntity.badRequest().body(result);
        }
    }

    // 일반지원자는 Requested, 도와주기는 Requested 또는 Soorack에서 거절하기
    @PutMapping("/applications/{id}/reject")
    public ResponseEntity<String> rejectApplication(@PathVariable Long id, @RequestHeader("accessToken") String token) {
        String email = jwtTokenProvider.getEmail(token.replace("Bearer ", ""));
        String result = applicationService.changeApplicationStatus(id, email, Status.Rejected, Status.Requested);
        if (result.equals("지원 상태가 변경되었습니다.")) {
            return ResponseEntity.ok(result);
        } else {
            return ResponseEntity.badRequest().body(result);
        }
    }

    // 일반지원자는 Requested, 도와주기는 Soorack에서 확정하기(Mannam)
    @PutMapping("/applications/{id}/mannam")
    public ResponseEntity<String> mannamApplication(@PathVariable Long id, @RequestHeader("accessToken") String token) {
        String email = jwtTokenProvider.getEmail(token.replace("Bearer ", ""));
        String result = applicationService.changeApplicationStatus(id, email, Status.Mannam, Status.Soorack);
        if (result.equals("지원 상태가 변경되었습니다.")) {
            return ResponseEntity.ok(result);
        } else {
            return ResponseEntity.badRequest().body(result);
        }
    }

    @DeleteMapping("/applications/{id}")
    public ResponseEntity<Void> deleteApplication(@PathVariable Long id) {
        applicationService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
