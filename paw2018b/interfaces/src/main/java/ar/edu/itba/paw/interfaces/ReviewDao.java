package ar.edu.itba.paw.interfaces;

import ar.edu.itba.paw.models.Doctor;
import ar.edu.itba.paw.models.Review;

import java.util.List;
import java.util.Optional;

public interface ReviewDao {

    Review createReview(Review review);

    List<Review> listReviews(Doctor doctor);
}
