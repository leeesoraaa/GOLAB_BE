package com.example.demo.domain.user;

import com.example.demo.domain.university.Universities;
import com.example.demo.dto.user.UserRequestDto;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Setter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String nickname;

    @Column(nullable = false, unique = true)
    private String email;
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "university_id")
    private Universities universities;

    public void updateEmail(String email) {
        this.email = email;
    }

    public void updateUser(UserRequestDto userRequestDto) {
        this.name = userRequestDto.getName();
        updateUniversity(userRequestDto.getUniversityId());
    }

    private void updateUniversity(Long universityId) {
        if (this.universities == null) {
            this.universities = new Universities();
        }
        this.universities.setUniversityId(universityId);
    }
}
