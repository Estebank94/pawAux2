package ar.edu.itba.paw.interfaces;

import ar.edu.itba.paw.models.Review;

import java.util.Optional;

public interface ReviewDao {
    Optional<Review> createReview(String description,Integer stars,Integer doctorId, Integer userId);
}
