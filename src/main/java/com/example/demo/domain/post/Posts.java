package com.example.demo.domain.post;

import com.example.demo.domain.university.Universities;
import com.example.demo.domain.user.User;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.sql.Date;
import java.sql.Timestamp;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Setter
public class Posts {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "university_id")
    @Setter
    @JsonBackReference
    private Universities universities;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @JsonBackReference
    private User user;

    @Column(nullable = false)
    private String title;
    @Column(nullable = false)
    private float location_latitude;
    @Column(nullable = false)
    private float location_longitude;
    @Column(nullable=false)
    private String location_name;
    @Column(nullable = false)
    private int people;
    @Column(nullable = false)
    @Setter
    private Timestamp created_at;
    @Setter
    private Timestamp updated_at;
    @Column (nullable = false)
    private boolean isuntact;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Duration duration;
    @Column(nullable = false)
    private String reward;
    @Column(nullable = false)
    private String requirements;
    @Column(nullable = false)
    private String contactlink;

    @Column(nullable=true)
    private String surveylink;
    @Column(nullable=false)
    private Date startdate;
    @Column(nullable=false)
    private Date enddate;

    public void setUntact(boolean untact) {
    }

    public void setTrade(boolean trade) {
    }

    //Enum
    public enum Duration {
        Min15, Min30, Hour1, Hour2, Over
    }

    @PrePersist
    protected void onCreate() {
        if (this.created_at == null) {
            this.created_at = new Timestamp(System.currentTimeMillis());
        }
    }

    @PreUpdate
    protected void onUpdate() {
        this.updated_at = new Timestamp(System.currentTimeMillis());
    }
}
