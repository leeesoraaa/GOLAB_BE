package com.example.demo.controller.post;

import com.example.demo.domain.post.Posts;
import com.example.demo.service.post.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/{userid}/post")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @PostMapping()
    public ResponseEntity<Posts> createPost(@PathVariable Long userid,@RequestBody Posts post) {
        Posts createdPost = postService.createPost(userid, post);
        return ResponseEntity.ok(createdPost);
    }
}
