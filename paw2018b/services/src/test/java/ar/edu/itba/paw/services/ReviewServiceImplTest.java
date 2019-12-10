package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.persistance.ReviewDao;
import ar.edu.itba.paw.models.Appointment;
import ar.edu.itba.paw.models.Doctor;
import ar.edu.itba.paw.models.Patient;
import ar.edu.itba.paw.models.Review;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
public class ReviewServiceImplTest {

    @Autowired
    private ReviewServiceImpl reviewServiceImpl;

    private static final String STARS = "3";
    private static final Integer STARS_INTEGERS = 3;
    private static final long REVIEWS_SIZE = 2;
    private static final String DESCRIPTION = "Estuvo maso. Ni bien ni mal";

    private Doctor doctor;
    private Patient patient;
    private Appointment appointment;
    private Review review, review2;

    @Autowired
    private ReviewDao reviewDao;

    @Test
    public void testCreateReview() throws Exception{
        doctor = Mockito.mock(Doctor.class);
        patient = Mockito.mock(Patient.class);
        appointment = Mockito.mock(Appointment.class);
        review = Mockito.mock(Review.class);
        when(reviewDao.createReview(STARS_INTEGERS, DESCRIPTION, doctor, patient, appointment)).thenReturn(review);

        Review reviewReturned = reviewServiceImpl.createReview(STARS, DESCRIPTION, doctor, patient, appointment);

        assertEquals(review, reviewReturned);
    }

    @Test
    public void testListReviews() {
        doctor = Mockito.mock(Doctor.class);
        review = Mockito.mock(Review.class);
        review2 = Mockito.mock(Review.class);
        List<Review> reviews = new LinkedList<>();
        reviews.add(0, review);
        reviews.add(1, review2);
        when(reviewDao.listReviews(doctor)).thenReturn(reviews);

        List<Review> reviewsReturned = reviewServiceImpl.listReviews(doctor);

        assertEquals(REVIEWS_SIZE, reviewsReturned.size());
        assertEquals(review, reviewsReturned.get(0));
        assertEquals(review2, reviewsReturned.get(1));
    }
}
