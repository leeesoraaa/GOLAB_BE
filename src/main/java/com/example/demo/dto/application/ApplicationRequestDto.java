package com.example.demo.dto.application;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApplicationRequestDto {
    private boolean trade;
    private Long exchangePostId;
}

