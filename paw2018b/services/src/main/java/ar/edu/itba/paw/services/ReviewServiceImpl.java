package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.DoctorDao;
import ar.edu.itba.paw.interfaces.PatientDao;
import ar.edu.itba.paw.interfaces.ReviewDao;
import ar.edu.itba.paw.interfaces.ReviewService;
import ar.edu.itba.paw.models.Review;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.acls.model.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ReviewServiceImpl implements ReviewService {

    @Autowired
    private ReviewDao reviewDao;

    @Autowired
    private PatientDao patientDao;

    @Autowired
    private DoctorDao doctorDao;

    @Override
    public Optional<Review> createReview(String description, Integer stars, Integer doctorId, Integer userId) {
        if (description == null){
            throw new IllegalArgumentException("description can't be null");
        }

        if (description.length() > 100){
            throw new IllegalArgumentException("Description max lenght is 100 characters");
        }
        if (stars == null) {
            throw new IllegalArgumentException("Stars can't be null");
        }
        if (stars > 5 || stars < 0){
            throw new IllegalArgumentException("Starts should be an integer number between 0 and 5 ");
        }
        if (userId == null){
            throw new IllegalArgumentException("Pacient can't be null");
        }
        if(!patientDao.findPatientById(userId).isPresent()){
            throw new NotFoundException("Pacient was not found");
        }
        if (doctorId == null){
            throw new IllegalArgumentException("Doctor can't be null");
        }
        if (doctorId <= 0){
            throw new IllegalArgumentException("PatientId can't be negative or zero");
        }
        if(!doctorDao.findDoctorById(doctorId).isPresent()){
            throw new NotFoundException("Doctor was not found");
        }
        Optional<Review> review = reviewDao.createReview(description, stars, doctorId, userId);
        if (!review.isPresent()){
            throw new NotFoundException("Review creation failed");
        }
        return review;
    }
}
