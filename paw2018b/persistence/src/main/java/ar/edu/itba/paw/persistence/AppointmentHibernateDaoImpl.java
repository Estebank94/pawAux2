package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.AppointmentDao;
import ar.edu.itba.paw.models.Appointment;
import ar.edu.itba.paw.models.Doctor;
import ar.edu.itba.paw.models.Patient;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Created by estebankramer on 13/10/2018.
 */

@Repository
public class AppointmentHibernateDaoImpl implements AppointmentDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Appointment createAppointment(String appointmentDay, String appointmentTime, Patient patient, Doctor doctor) throws Exception {
        final Appointment appointment = new Appointment(appointmentDay, appointmentTime, patient, doctor);
        em.persist(appointment);
        return appointment;
    }
}
