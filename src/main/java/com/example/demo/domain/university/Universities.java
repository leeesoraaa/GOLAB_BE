package com.example.demo.domain.university;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Column;

@Entity
public class Universities {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long university_id;

    @Column(nullable = false)
    private String name;

    public Long getUniversity_id() {
        return university_id;
    }

    public void setUniversity_id(Long university_id) {
        this.university_id = university_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    // Getters and Setters
}