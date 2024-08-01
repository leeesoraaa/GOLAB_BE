package com.example.demo.controller.post;

import com.example.demo.domain.post.Posts;
import com.example.demo.dto.post.PostRequestDto;
import com.example.demo.security.JwtTokenProvider;
import com.example.demo.service.post.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/post")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;
    private final JwtTokenProvider jwtTokenProvider;

    @PostMapping
    public ResponseEntity<Posts> createPost(@RequestHeader("Authorization") String token,
                                            @RequestBody PostRequestDto postRequestDto) {
        String email = jwtTokenProvider.getEmail(token.replace("Bearer ", ""));
        Posts createdPost = postService.createPost(email, postRequestDto);
        return ResponseEntity.ok(createdPost);
    }

    @GetMapping("/{postId}")
    public ResponseEntity<Posts> getPost(@PathVariable Long postId) {
        Posts post = postService.getPost(postId);
        return ResponseEntity.ok(post);
    }

    @GetMapping
    public ResponseEntity<List<Posts>> getAllPosts() {
        List<Posts> posts = postService.getAllPosts();
        return ResponseEntity.ok(posts);
    }

    @PutMapping("/{postId}")
    public ResponseEntity<Posts> updatePost(@RequestHeader("Authorization") String token,
                                            @PathVariable Long postId,
                                            @RequestBody PostRequestDto postRequestDto) {
        String email = jwtTokenProvider.getEmail(token.replace("Bearer ", ""));
        Posts updatedPost = postService.updatePost(postId, postRequestDto, email);
        return ResponseEntity.ok(updatedPost);
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<Void> deletePost(@RequestHeader("Authorization") String token,
                                           @PathVariable Long postId) {
        String email = jwtTokenProvider.getEmail(token.replace("Bearer ", ""));
        postService.deletePost(postId, email);
        return ResponseEntity.noContent().build();
    }
}
