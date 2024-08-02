package com.example.demo.domain.application;

import com.example.demo.domain.post.Posts;
import com.example.demo.domain.university.Universities;
import com.example.demo.domain.user.User;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Setter;

import java.sql.Timestamp;
import java.time.LocalDateTime;

public class Application {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "application_id")
    private Long id;

    @OneToOne (fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    @Setter
    @JsonBackReference
    private Posts posts;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @JsonBackReference
    private User user;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Status status;

    @Column(nullable = false)
    private boolean is_trade;

    @Column(nullable = true)
    private Posts exchange_postId;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    public void prePersist() {
        createdAt = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        updatedAt = LocalDateTime.now();
    }
    @Column(nullable = false)
    @Setter
    private Timestamp created_at;
    public enum Status {
        Requested, Rejected, Soorack, Mannam
    }
}
