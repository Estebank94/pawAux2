package ar.edu.itba.paw.interfaces.services;

import ar.edu.itba.paw.models.Appointment;
import ar.edu.itba.paw.models.Doctor;
import ar.edu.itba.paw.models.Patient;
import ar.edu.itba.paw.models.Review;
import ar.edu.itba.paw.models.exceptions.NotValidReviewException;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface ReviewService {

        Review createReview(String stars, String description, Doctor doctor, Patient patient, Appointment appointment) throws NotValidReviewException;

        List<Review> listReviews(Doctor doctor);

        public List<Review> getSharedReviews (Doctor doctor, Patient patient);

        Review createReview(String stars, String description, Doctor doctor, Patient patient) throws NotValidReviewException;
}
