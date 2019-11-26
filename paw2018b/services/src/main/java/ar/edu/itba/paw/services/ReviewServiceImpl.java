package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.persistance.ReviewDao;
import ar.edu.itba.paw.interfaces.services.ReviewService;
import ar.edu.itba.paw.models.Doctor;
import ar.edu.itba.paw.models.Review;
import ar.edu.itba.paw.models.exceptions.NotValidReviewException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewServiceImpl implements ReviewService {

    @Autowired
    private ReviewDao reviewDao;

    private static final Logger LOGGER = LoggerFactory.getLogger(DoctorServiceImpl.class);

    @Override
    public Review createReview(String stars, String description, Doctor doctor, String reviewerFirstName, String reviewerLastName) throws NotValidReviewException {

        if (stars == null){
            LOGGER.debug("stars can't be null");
            throw new NotValidReviewException("stars can't be null");
        }
        if (!stars.matches("[1-5]")){
            LOGGER.debug("stars must be an Integer between 1 and 5");
            throw new NotValidReviewException("stars must be an Integer between 1 and 5");
        }
        if (String.valueOf(Integer.MAX_VALUE).length() < stars.length()){
            LOGGER.debug("stars must be an Integer between 1 and 5");
            throw new NotValidReviewException("stars must be an Integer between 1 and 5");
        }

        int starsToInt = Integer.parseInt(stars);
        if (starsToInt < 1 || starsToInt > 5){
            LOGGER.debug("stars must be an Integer between 1 and 5");
            throw new NotValidReviewException("stars must be an Integer between 1 and 5");
        }

        if (doctor == null) {
            LOGGER.debug("doctor can't be null");
            throw new NotValidReviewException("doctor can't be null");
        }
        try {
            return reviewDao.createReview(starsToInt, description, doctor, reviewerFirstName, reviewerLastName);
        } catch (Exception e){
            throw new NotValidReviewException();
        }
    }


    @Override
    public List<Review> listReviews(Doctor doctor) {
        return reviewDao.listReviews(doctor);
    }
}
