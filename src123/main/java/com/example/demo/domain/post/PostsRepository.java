package com.example.demo.domain.post;

import com.example.demo.domain.university.Universities;
import com.example.demo.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PostsRepository extends JpaRepository<Posts, Long> {
    List<Posts> findAllByUser(User user);
    List<Posts> findAllByUniversities(Universities universities);
    Optional<Posts> findTopByUniversitiesIdOrderByEnddateAsc(Long universityId);
}
