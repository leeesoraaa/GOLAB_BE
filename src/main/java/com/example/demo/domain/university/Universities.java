package com.example.demo.domain.university;

import com.example.demo.domain.user.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.apache.logging.log4j.util.Lazy;

@Setter
@Getter
@Entity
public class Universities {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "university_id")
    private Long id;

    @Column(nullable = false)
    private String name;

    public void setUniversityId(Long universityId) {
        this.id = universityId;
    }
    // Getters and Setters
}