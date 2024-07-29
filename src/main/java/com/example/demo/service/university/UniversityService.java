package com.example.demo.service.university;

import com.example.demo.domain.university.Universities;
import com.example.demo.domain.university.UniversityRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Collections;
@Service
public class UniversityService {
    private final UniversityRepository universityRepository;

    // Constructor injection
    public UniversityService(UniversityRepository universityRepository) {
        this.universityRepository = universityRepository;
    }

    public List<Universities> searchUniversities(String name) {
        return universityRepository.findByNameStartingWith(name);
    }

    public List<Universities> searchUniversitiesById(Long universityId) {
        Optional<Universities> universityOptional = universityRepository.findById(universityId);
        if (universityOptional.isPresent()) {
            return Collections.singletonList(universityOptional.get());
        } else {
            return Collections.emptyList();
        }
    }
}
