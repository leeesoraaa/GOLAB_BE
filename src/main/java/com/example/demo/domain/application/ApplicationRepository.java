package com.example.demo.domain.application;

import com.example.demo.domain.application.Application;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ApplicationRepository extends JpaRepository<Application, Long> {
    List<Application> findByPostId(Long postId);
    boolean existsByPostIdAndUserId(Long postId, Long userId);
}