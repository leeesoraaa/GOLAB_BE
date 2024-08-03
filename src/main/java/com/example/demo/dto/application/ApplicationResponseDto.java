package com.example.demo.dto.application;

import com.example.demo.domain.application.Application.Status;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ApplicationResponseDto {
    private String status;
    private String message;
    private String contactLink;
}
