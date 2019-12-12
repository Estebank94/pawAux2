package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.persistance.AppointmentDao;
import ar.edu.itba.paw.models.Appointment;
import ar.edu.itba.paw.models.Doctor;
import ar.edu.itba.paw.models.Patient;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collections;
import java.util.List;
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
    public Appointment cancelAppointment(Appointment appointment) {
        appointment.setAppointmentCancelled(Boolean.TRUE);
        return em.merge(appointment);
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
    public Optional<Appointment> findAppointmentByDoctor (String appointmentDay, String appointmentTime, Doctor doctor) throws Exception{
        final TypedQuery<Appointment> query = em.createQuery("from Appointment as a WHERE a.appointmentDay = :appointmentDay AND a.appointmentTime = :appointmentTime AND a.doctor = :doctor AND a.appointmentCancelled = :cancelled",Appointment.class);
        query.setParameter("appointmentDay", appointmentDay);
        query.setParameter("appointmentTime", appointmentTime);
        query.setParameter("doctor", doctor);
        query.setParameter("cancelled", Boolean.TRUE);
        return Optional.ofNullable(query.getSingleResult());
    }

    @Override
    public Optional<Appointment> findActiveAppointment(String appointmentDay, String appointmentTime, Doctor doctor, Patient patient) throws Exception {
        final TypedQuery<Appointment> query = em.createQuery("from Appointment as a WHERE " +
                "a.appointmentDay = :appointmentDay AND " +
                "a.appointmentTime = :appointmentTime AND " +
                "a.patient = :patient AND " +
                "a.doctor = :doctor AND " +
                "a.appointmentCancelled = :cancelled",Appointment.class);
        query.setParameter("appointmentDay", appointmentDay);
        query.setParameter("appointmentTime", appointmentTime);
        query.setParameter("patient", patient);
        query.setParameter("doctor", doctor);
        query.setParameter("cancelled", Boolean.FALSE);
        return Optional.ofNullable(query.getSingleResult());
    }

    public List<Appointment> findAppointmentByTime(String appointmentDay, String appointmentTime, Doctor doctor){
        final TypedQuery<Appointment> query = em.createQuery("from Appointment as a WHERE " +
                "a.appointmentDay = :appointmentDay AND " +
                "a.appointmentTime = :appointmentTime AND " +
                "a.doctor = :doctor" ,Appointment.class);
        query.setParameter("appointmentDay", appointmentDay);
        query.setParameter("appointmentTime", appointmentTime);
        query.setParameter("doctor", doctor);
        List<Appointment> appointmentList = query.getResultList();
        return appointmentList == null || appointmentList.isEmpty()? Collections.emptyList(): appointmentList;
    }

    public List<Appointment> findAppointmentByPatient(String appointmentDay, String appointmentTime, Patient patient) {
        final TypedQuery<Appointment> query = em.createQuery("from Appointment as a WHERE " +
                "a.appointmentDay = :appointmentDay AND " +
                "a.appointmentTime = :appointmentTime AND " +
                "a.patient = :patient" ,Appointment.class);
        query.setParameter("appointmentDay", appointmentDay);
        query.setParameter("appointmentTime", appointmentTime);
        query.setParameter("patient", patient);
        List<Appointment> appointmentList = query.getResultList();
        return appointmentList == null || appointmentList.isEmpty()? Collections.emptyList(): appointmentList;
    }

    public List<Appointment> findSharedAppointment(Patient patient, Doctor doctor){
        final TypedQuery<Appointment> query = em.createQuery("from Appointment as a WHERE " +
                "a.doctorid = :doctorid AND " +
                "a.clientid = :clientid AND " +
                "a.apponitmentcancelled=false",Appointment.class);
        query.setParameter("doctorid", doctor.getId());
        query.setParameter("clientid", patient.getId());
        List<Appointment> appointmentList = query.getResultList();
        return appointmentList == null || appointmentList.isEmpty()? Collections.emptyList(): appointmentList;
    }

    @Override
    public List<Appointment> getHistoricalAppointmentsWithDoctor(Patient patient, Doctor doctor) {
        String today = LocalDate.now().toString();
        String now = LocalTime.now().toString();
        final TypedQuery<Appointment> query;
        query = em.createQuery("FROM Appointment ap " +
                "where ((ap.appointmentDay < :day)  OR (ap.appointmentDay = :day AND ap.appointmentTime < :time)) " +
                "AND ap.patient = :patient AND ap.appointmentCancelled = :cancel AND ap.doctor = :doctor"/* AND ap.review = :review*/,
                Appointment.class);
        query.setParameter("doctor", doctor);
        query.setParameter("patient", patient);
        query.setParameter("day", today);
        query.setParameter("time", now);
        query.setParameter("cancel", false);
//        query.setParameter("review", null);
        final List<Appointment> list = query.getResultList();
        return list.isEmpty() ? Collections.emptyList() : list;
    }

    @Override
    public Appointment undoCancelAppointment(Appointment appointment) {
        appointment.setAppointmentCancelled(Boolean.FALSE);
        Appointment  updatedAppointmnet = em.merge(appointment);
        return updatedAppointmnet;
    }

    @Override
    public Optional<Appointment> findAppointmentById(Integer id) {
        final TypedQuery<Appointment> query = em.createQuery("FROM Appointment as a WHERE a.id = :id", Appointment.class);
        query.setParameter("id", id);
        return Optional.ofNullable(query.getSingleResult());
    }
}
