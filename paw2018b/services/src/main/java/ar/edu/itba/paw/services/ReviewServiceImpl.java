package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.persistance.ReviewDao;
import ar.edu.itba.paw.interfaces.services.ReviewService;
import ar.edu.itba.paw.models.Appointment;
import ar.edu.itba.paw.models.Doctor;
import ar.edu.itba.paw.models.Patient;
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
    public Review createReview(String stars, String description, Doctor doctor, Patient patient, Appointment appointment) throws NotValidReviewException {

        if (stars == null && description == null){
            LOGGER.debug("No stars or description");
            throw new NotValidReviewException("No stars or description ");
        }

        if (stars != null && !stars.matches("[1-5]")){
            LOGGER.debug("stars must be an Integer between 1 and 5");
            throw new NotValidReviewException("stars must be an Integer between 1 and 5");
        }
        if (String.valueOf(Integer.MAX_VALUE).length() < stars.length()){
            LOGGER.debug("stars must be an Integer between 1 and 5");
            throw new NotValidReviewException("stars must be an Integer between 1 and 5");
        }
        Integer starsToInt = null;
        if (stars != null){
            starsToInt = Integer.parseInt(stars);
            if (starsToInt < 1 || starsToInt > 5){
                LOGGER.debug("stars must be an Integer between 1 and 5");
                throw new NotValidReviewException("stars must be an Integer between 1 and 5");
            }
        }

        if (doctor == null) {
            LOGGER.debug("doctor can't be null");
            throw new NotValidReviewException("doctor can't be null");
        }

        if (patient == null){
            LOGGER.debug("patient can't be null");
            throw new NotValidReviewException("doctor can't be null");
        }

        if (patient.getDoctor() == doctor){
            LOGGER.debug("patient can't review his own doctor");
            throw new NotValidReviewException("Patient can't review his own doctor");
        }

        if (appointment == null){
            LOGGER.debug("appointment can't be null");
            throw new NotValidReviewException("appointment already has a review");
        }

        if (appointment.getReview() != null){
            LOGGER.debug("appointment already has a review");
            throw new NotValidReviewException("appointment already has a review");
        }

        LOGGER.debug("Time to create review");
        //try {
            return reviewDao.createReview(starsToInt, description, doctor, patient, appointment);
        //} catch (Exception e){
        //    throw new NotValidReviewException();
        //}
    }

    @Override
    public Review createReview(String stars, String description, Doctor doctor, Patient patient) throws NotValidReviewException {

        if (stars == null && description == null){
            LOGGER.debug("No stars or description");
            throw new NotValidReviewException("No stars or description ");
        }

        if (stars != null && !stars.matches("[1-5]")){
            LOGGER.debug("stars must be an Integer between 1 and 5");
            throw new NotValidReviewException("stars must be an Integer between 1 and 5");
        }
        if (String.valueOf(Integer.MAX_VALUE).length() < stars.length()){
            LOGGER.debug("stars must be an Integer between 1 and 5");
            throw new NotValidReviewException("stars must be an Integer between 1 and 5");
        }
        Integer starsToInt = null;
        if (stars != null){
            starsToInt = Integer.parseInt(stars);
            if (starsToInt < 1 || starsToInt > 5){
                LOGGER.debug("stars must be an Integer between 1 and 5");
                throw new NotValidReviewException("stars must be an Integer between 1 and 5");
            }
        }

        if (doctor == null) {
            LOGGER.debug("doctor can't be null");
            throw new NotValidReviewException("doctor can't be null");
        }

        if (patient == null){
            LOGGER.debug("patient can't be null");
            throw new NotValidReviewException("doctor can't be null");
        }

        if (patient.getDoctor() == doctor){
            LOGGER.debug("patient can't review his own doctor");
            throw new NotValidReviewException("Patient can't review his own doctor");
        }


        LOGGER.debug("Time to create review");
        //try {
        return reviewDao.createReview(starsToInt, description, doctor, patient);
        //} catch (Exception e){
        //    throw new NotValidReviewException();
        //}
    }

    @Override
    public List<Review> listReviews(Doctor doctor) {
        return reviewDao.listReviews(doctor);
    }

    @Override
    public List<Review> getSharedReviews (Doctor doctor, Patient patient){
        return reviewDao.getSharedReviews(doctor, patient);
    }
}
