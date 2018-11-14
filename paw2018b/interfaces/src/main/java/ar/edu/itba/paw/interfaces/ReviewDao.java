package ar.edu.itba.paw.interfaces;

import ar.edu.itba.paw.models.Doctor;
import ar.edu.itba.paw.models.Review;

import java.util.List;
import java.util.Optional;

public interface ReviewDao {

    Review createReview(Integer stars, String description, Doctor doctor, String reviewerFirstName, String reviewerLastName);

    List<Review> listReviews(Doctor doctor);
}
