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

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id")
    private User user;

    @Column(nullable = false)
    private String title;
    @Column(nullable = false)
    private float location_latitude;
    @Column(nullable = false)
    private float location_longitude;
    @Column(nullable = false)
    private Timestamp created_at;
    @Column(nullable = false)
    private Timestamp updated_at;
    private boolean is_untact;

    private enum duration {Min15, Min30, Hour1, Hour2, Over};
    @Column(nullable = false)
    private boolean is_trade;
    @Column(nullable = false)
    private String reward;
    @Column(nullable = false)
    private String requirements;
    @Column(nullable = false)
    private String contact_link;
    private String etc;
    private enum status {Requested, Rejected, Accepted, Confirmed};

    @Column(nullable=true)
    private String survey_link;



}
