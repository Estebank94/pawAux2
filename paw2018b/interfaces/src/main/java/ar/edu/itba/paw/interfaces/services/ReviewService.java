package ar.edu.itba.paw.interfaces.services;

import ar.edu.itba.paw.models.Doctor;
import ar.edu.itba.paw.models.Review;
import ar.edu.itba.paw.models.exceptions.NotValidReviewException;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface ReviewService {

        @Transactional
        Review createReview(String stars, String description, Doctor doctor, String reviewerFirstName, String reviewerLastName) throws NotValidReviewException;

        List<Review> listReviews(Doctor doctor);
}
