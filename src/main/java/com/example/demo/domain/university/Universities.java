package com.example.demo.domain.university;

import com.example.demo.domain.post.Posts;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

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

    @OneToMany(mappedBy = "universities", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = false)
    @JsonManagedReference
    private List<Posts> posts;

    // 기본 생성자
    public Universities() {
    }

    // Long 타입의 생성자 추가
    //public Universities(Long universityId) {
    //    this.id = universityId;
    //}


    public void setUniversityId(Long universityId) {
        this.id = universityId;
    }
    // Getters and Setters
}
