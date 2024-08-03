package com.example.demo.service.review;

import com.example.demo.domain.application.Application;
import com.example.demo.domain.application.ApplicationRepository;
import com.example.demo.domain.review.Review;
import com.example.demo.domain.review.ReviewRepository;
import com.example.demo.domain.user.User;
import com.example.demo.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;
    private final ApplicationRepository applicationRepository;

    @Transactional
    public void reviewUser(Long userId, boolean noshow, boolean late, Long applicationId) {
        if (noshow && late) {
            throw new IllegalArgumentException("late와 noshow는 동시에 true일 수 없습니다.");
        }

        Application application = applicationRepository.findById(applicationId)
                .orElseThrow(() -> new RuntimeException("Application not found"));

        if (reviewRepository.existsByApplicationId(applicationId)) {
            throw new RuntimeException("Review for this application already exists");
        }
        User user = application.getUser();

        Review review = new Review();
        review.setUser(user);
        review.setNoshow(noshow);
        review.setLate(late);
        review.setApplication(application); // application 설정
        reviewRepository.save(review);

        if (noshow) {
            user.setNoshow(user.getNoshow() + 1);
        }
        if (late) {
            user.setLate(user.getLate() + 1);
        }

        float trust = user.getNoshow() + (user.getLate() * 0.5f);
        user.setTrust(trust);

        if (trust >= 3) {
            user.setIsbanned(true);
        }
        if (noshow) {
            user.setNoshow(user.getNoshow() + 1);
        } else {
            user.incrementParticipated(); // noshow가 false일 때만 participated 값을 증가시킴
        }
        userRepository.save(user);
    }
}
