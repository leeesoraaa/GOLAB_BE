package com.example.demo.controller.review;

import com.example.demo.dto.review.ReviewRequestDto;
import com.example.demo.service.review.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/review")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    @PostMapping
    public ResponseEntity<String> reviewUser(@RequestHeader("accessToken") String token
            ,@RequestBody ReviewRequestDto reviewRequestDto) {
        reviewService.reviewUser(reviewRequestDto.getUserId(), reviewRequestDto.isNoshow(), reviewRequestDto.isLate(), reviewRequestDto.getApplicationId());
        return ResponseEntity.ok("Review submitted successfully");
    }
}
