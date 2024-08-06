package com.example.demo.domain.university;

import com.example.demo.dto.university.UniversitySearchResponseDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


public interface UniversityRepository extends JpaRepository<Universities, Long> {
    @Query("SELECT new com.example.demo.dto.university.UniversitySearchResponseDTO(u.id, u.name) FROM Universities u WHERE u.name LIKE :name%")
    List<UniversitySearchResponseDTO> findByNameStartingWith(String name);
    @Query("SELECT new com.example.demo.dto.university.UniversitySearchResponseDTO(u.id, u.name) FROM Universities u")
    List<UniversitySearchResponseDTO> findAllUniversity();
    Optional<Universities> findById(Long id);
}
