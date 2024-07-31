package com.example.demo.domain.user;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByEmail(String email);

    User findByName(String username);

    Integer countByName(String username);
}
