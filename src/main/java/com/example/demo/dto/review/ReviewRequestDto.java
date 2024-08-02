package com.example.demo.dto.review;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReviewRequestDto {
    private Long userId;
    private boolean noshow;
    private boolean late;
}
