package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.persistance.ReviewDao;
import ar.edu.itba.paw.models.Appointment;
import ar.edu.itba.paw.models.Doctor;
import ar.edu.itba.paw.models.Patient;
import ar.edu.itba.paw.models.Review;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.Collections;
import java.util.List;


@Repository
public class ReviewHibernateDaoImpl implements ReviewDao {

    @PersistenceContext
    private EntityManager em;

    @Transactional
    @Override
    public Review createReview(Integer stars, String description, Doctor doctor, Patient patient, Appointment appointment) {
        Review review = new Review(stars, description , doctor, patient, appointment);
        appointment.setReview(review);
        em.persist(review);
        em.merge(appointment);
        return review;
    }

    @Transactional
    @Override
    public Review createReview(Integer stars, String description, Doctor doctor, Patient patient) {
        Review review = new Review(stars, description , doctor, patient);
        em.persist(review);
        return review;
    }

    @Override
    public List<Review> listReviews(Doctor doctor) {
        final TypedQuery<Review> query = em.createQuery("FROM Review" +
                "WHERE doctorid = :doctor", Review.class);
        query.setParameter("doctor", doctor.getId());
        List<Review> list = query.getResultList();
        return list.isEmpty() ? Collections.emptyList() : list;
    }

    @Override
    public List<Review> getSharedReviews (Doctor doctor, Patient patient){
        final TypedQuery<Review> query = em.createQuery("FROM Review as r " +
                "WHERE r.doctor = :doctor AND "+
                "r.patient = :patient", Review.class);
        query.setParameter("doctor", doctor);
        query.setParameter("patient", patient);
        List<Review> list = query.getResultList();
        return list.isEmpty() ? Collections.emptyList() : list;
    }
}
