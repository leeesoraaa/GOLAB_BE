package com.example.demo.dto.post;

import com.example.demo.domain.post.Posts;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

@Data
@Builder
@Getter
@Setter
public class PostRequestDto {

    private String title;
    private float location_latitude;
    private float location_longitude;
    private String location_name;
    private boolean isUntact;
    private int people;
    private String reward;
    private String requirements;
    private String contactlink;
    private String surveylink;
    private Date startdate;
    private Date enddate;
    private Posts.Duration duration;
    private Long universityId;
}
