package ar.edu.itba.paw.interfaces.persistance;

import ar.edu.itba.paw.models.Appointment;
import ar.edu.itba.paw.models.Doctor;
import ar.edu.itba.paw.models.Patient;
import ar.edu.itba.paw.models.Review;

import java.util.List;
import java.util.Optional;

public interface ReviewDao {

    Review createReview(Integer stars, String description, Doctor doctor, Patient patient, Appointment appointment);

    List<Review> listReviews(Doctor doctor);

    List<Review> getSharedReviews (Doctor doctor, Patient patient);

    Review createReview(Integer stars, String description, Doctor doctor, Patient patient);
}
