package com.example.demo.service.review;

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

    @Transactional
    public void reviewUser(Long userId, boolean noshow, boolean late) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));

        Review review = new Review();
        review.setUser(user);
        review.setNoshow(noshow);
        review.setLate(late);
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

        userRepository.save(user);
    }
}
