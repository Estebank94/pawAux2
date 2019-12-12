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
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.time.LocalDate;
import java.time.LocalTime;
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
    public Boolean reviewAvailables(Doctor doctor, Patient patient) {
        String today = LocalDate.now().toString();
        String now = LocalTime.now().toString();
        Query query = em.createQuery("select count(*) from Appointment as ap where ((ap.appointmentDay < :day)" +
                "  OR (ap.appointmentDay = :day AND ap.appointmentTime < :time)) AND ap.doctor = :doctor AND" +
                " ap.appointmentCancelled = :cancel AND ap.patient = :patient");
        /* and review is not null, then compare if is greater than zero if appointment include review */
        query.setParameter("doctor", doctor);
        query.setParameter("patient", patient);
        query.setParameter("day", today);
        query.setParameter("time", now);
        query.setParameter("cancel", false);
        Long countAppointments = (Long)query.getSingleResult();
        Query query2 = em.createQuery("select count(r) from Review as r "+
                "WHERE r.doctor = :doctor AND "+
                "r.patient = :patient");
        query2.setParameter("doctor", doctor);
        query2.setParameter("patient", patient);
        Long countreviews = (Long)query2.getSingleResult();
        return (countAppointments - countreviews) > 0;
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
