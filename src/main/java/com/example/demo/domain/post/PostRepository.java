package com.example.demo.domain.post;

import com.example.demo.domain.post.Posts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
public interface PostRepository extends JpaRepository<Posts, Long>{

}
