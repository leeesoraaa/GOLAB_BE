package com.example.demo.dto.application;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ApplicationStatusResponseDto {
    private String status;
    private String message;
    private String contactLink;
}
