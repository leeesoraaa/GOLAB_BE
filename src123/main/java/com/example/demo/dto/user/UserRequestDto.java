package com.example.demo.dto.user;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserRequestDto {

    private String name;
    private Long universityId;
}
