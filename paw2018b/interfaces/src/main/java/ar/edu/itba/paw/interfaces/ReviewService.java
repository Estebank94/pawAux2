package ar.edu.itba.paw.interfaces;

import ar.edu.itba.paw.models.Doctor;
import ar.edu.itba.paw.models.Review;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface ReviewService {

        @Transactional
        Review createReview(Review review);

        List<Review> listReviews(Doctor doctor);
}
