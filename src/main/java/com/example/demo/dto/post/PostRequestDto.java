package com.example.demo.dto.post;

import com.example.demo.domain.post.Posts;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

import java.sql.Date;
import java.sql.Timestamp;


@Data
@Builder
@Getter
public class PostRequestDto {
    private String title;
    private Long universityId;
    private float locationLatitude;
    private float locationLongitude;
    private Timestamp created_At;
    private Timestamp updated_At;
    private boolean isUntact;
    private Posts.Duration duration;
    private boolean isTrade;
    private String reward;
    private String requirements;
    private String contactLink;
    private String etc;
    private Posts.Status status;
    private String surveyLink;
    private Date startdate;
    private Date enddate;
}
