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
    public ResponseEntity<Posts> createPost(@RequestHeader("Authorization") String token, @RequestBody PostRequestDto postRequestDto) {
        String nickname = jwtTokenProvider.getNickName(token.substring(7)); // Bearer 토큰 제거
        Posts createdPost = postService.createPost(nickname, postRequestDto);
        return ResponseEntity.ok(createdPost);
    }

    @PutMapping("/{postId}")
    public ResponseEntity<Posts> updatePost(@PathVariable Long postId, @RequestHeader("Authorization") String token, @RequestBody PostRequestDto postRequestDto) {
        String nickname = jwtTokenProvider.getNickName(token.substring(7)); // Bearer 토큰 제거
        Posts updatedPost = postService.updatePost(postId, nickname, postRequestDto);
        return ResponseEntity.ok(updatedPost);
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

    @DeleteMapping("/{postId}")
    public ResponseEntity<Void> deletePost(@PathVariable Long postId, @RequestHeader("Authorization") String token) {
        String nickname = jwtTokenProvider.getNickName(token.substring(7)); // Bearer 토큰 제거
        postService.deletePost(postId, nickname);
        return ResponseEntity.noContent().build();
    }
}
