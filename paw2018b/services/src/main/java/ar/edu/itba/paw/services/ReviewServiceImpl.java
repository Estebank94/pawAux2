package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.ReviewDao;
import ar.edu.itba.paw.interfaces.ReviewService;
import ar.edu.itba.paw.models.Doctor;
import ar.edu.itba.paw.models.Review;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewServiceImpl implements ReviewService {

    @Autowired
    private ReviewDao reviewDao;

    @Override
    public Review createReview(Review review) {
        return reviewDao.createReview(review);
    }

    @Override
    public List<Review> listReviews(Doctor doctor) {
        return reviewDao.listReviews(doctor);
    }
}
