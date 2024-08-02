package com.example.demo.domain.user;

import com.example.demo.domain.post.Posts;
import com.example.demo.domain.university.Universities;
import com.example.demo.dto.user.UserRequestDto;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Setter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;
    @Column
    private String nickname;

    @Column( unique = true)
    private String email;
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "university_id")
    private Universities universities;

    @OneToMany(mappedBy = "user", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = true)
    @JsonManagedReference
    private List<Posts> posts;

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
    public Long getUniversityId() {
        return universities != null ? universities.getId() : null;
    }
}
