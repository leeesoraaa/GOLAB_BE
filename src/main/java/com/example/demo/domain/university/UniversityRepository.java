package com.example.demo.domain.university;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UniversityRepository extends JpaRepository<Universities, Long> {
    List<Universities> findByNameStartingWith(String name);
    Optional<Universities> findById(Long id);
}
