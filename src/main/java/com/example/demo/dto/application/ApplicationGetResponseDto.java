package com.example.demo.dto.application;

import com.example.demo.domain.application.Application;
import com.example.demo.domain.post.Posts;
import com.example.demo.domain.user.User;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ApplicationGetResponseDto {
    private Long applicationId;
    private String applicationStatus;
    private boolean isTrade;
    private Long exchangePostId;
    private LocalDateTime applicationCreatedAt;
    private LocalDateTime applicationUpdatedAt;

    private Long userId;
    private String userNickname;
    private String userEmail;
    private String userName;
    private Long userUniversityId;
    private int userNoshow;
    private int userLate;
    private float userTrust;
    private boolean userIsBanned;
    private int userParticipated;

    public ApplicationGetResponseDto(Application application) {
        this.applicationId = application.getId();
        this.applicationStatus = application.getStatus().name();
        this.isTrade = application.isTrade();
        this.exchangePostId = application.getExchangePost() != null ? application.getExchangePost().getId() : null;
        this.applicationCreatedAt = application.getCreatedAt();
        this.applicationUpdatedAt = application.getUpdatedAt();

        User user = application.getUser();
        this.userId = user.getId();
        this.userNickname = user.getNickname();
        this.userEmail = user.getEmail();
        this.userName = user.getName();
        this.userUniversityId = user.getUniversityId();
        this.userNoshow = user.getNoshow();
        this.userLate = user.getLate();
        this.userTrust = user.getTrust();
        this.userIsBanned = user.isIsbanned();
        this.userParticipated = user.getParticipated();
    }
}
