package com.example.demo.dto.university;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UniversitySearchResponseDTO {
    private Long id;
    private String name;

    public UniversitySearchResponseDTO(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}
