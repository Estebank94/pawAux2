package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.AppointmentDao;
import ar.edu.itba.paw.models.Appointment;
import ar.edu.itba.paw.models.Doctor;
import ar.edu.itba.paw.models.Patient;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

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

    @Override
    public void cancelAppointment(Appointment appointment){
        appointment.setAppointmentCancelled(true);
        em.persist(appointment);
    }

    @Override
    public Optional<Appointment> findAppointment (String appointmentDay, String appointmentTime, Patient patient, Doctor doctor) throws Exception{
        final TypedQuery<Appointment> query = em.createQuery("from Appointment as a WHERE a.appointmentDay = :appointmentDay AND a.appointmentTime = :appointmentTime AND a.patient = :patient AND a.doctor = :doctor",Appointment.class);
        query.setParameter("appointmentDay", appointmentDay);
        query.setParameter("appointmentTime", appointmentTime);
        query.setParameter("patient", patient);
        query.setParameter("doctor", doctor);
        return Optional.ofNullable(query.getSingleResult());
    }

    @Override
    public void undoCancelAppointment(Appointment appointment) {
        appointment.setAppointmentCancelled(false);
        em.persist(appointment);
    }
}