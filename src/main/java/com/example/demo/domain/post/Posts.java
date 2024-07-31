package com.example.demo.domain.post;

import com.example.demo.domain.university.Universities;
import com.example.demo.domain.user.User;
import com.example.demo.dto.user.UserRequestDto;
import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class Posts {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private Long id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "university_id")
    private Universities universities;

    @Setter
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(nullable = false)
    private String title;
    @Column(nullable = false)
    private float location_latitude;
    @Column(nullable = false)
    private float location_longitude;
    @Column(nullable = false)
    private Timestamp created_at;
    private Timestamp updated_at;
    private boolean is_untact;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Duration duration;
    @Column(nullable = false)
    private boolean is_trade;
    @Column(nullable = false)
    private String reward;
    @Column(nullable = false)
    private String requirements;
    @Column(nullable = false)
    private String contact_link;
    private String etc;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status;

    @Column(nullable=true)
    private String survey_link;

    //Enum
    public enum Duration {
        Min15, Min30, Hour1, Hour2, Over
    }
     public enum Status {
         Requested, Rejected, Accepted, Confirmed
     }
}
