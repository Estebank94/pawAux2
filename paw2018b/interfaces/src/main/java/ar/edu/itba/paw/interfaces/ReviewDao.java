package ar.edu.itba.paw.interfaces;

import ar.edu.itba.paw.models.Review;

import java.util.Optional;

public interface ReviewDao {
    Review createReview(Integer stars, String description, Integer doctorId, Integer userId);
}
