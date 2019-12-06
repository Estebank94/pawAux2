package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.persistance.ReviewDao;
import ar.edu.itba.paw.models.Appointment;
import ar.edu.itba.paw.models.Doctor;
import ar.edu.itba.paw.models.Patient;
import ar.edu.itba.paw.models.Review;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.Collections;
import java.util.List;


@Repository
public class ReviewHibernateDaoImpl implements ReviewDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Review createReview(Integer stars, String description, Doctor doctor, Patient patient, Appointment appointment) {
        Review review = new Review(stars, description , doctor, patient, appointment);
        appointment.setReview(review);
        em.getTransaction().begin();
        em.persist(review);
        em.persist(appointment);
        em.getTransaction().commit();
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
}
