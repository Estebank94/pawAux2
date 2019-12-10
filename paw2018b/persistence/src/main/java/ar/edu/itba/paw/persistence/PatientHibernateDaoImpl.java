package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.persistance.PatientDao;
import ar.edu.itba.paw.models.*;
import ar.edu.itba.paw.models.exceptions.NotCreatePatientException;
import ar.edu.itba.paw.models.exceptions.NotFoundDoctorException;
import ar.edu.itba.paw.models.exceptions.RepeatedEmailException;
import org.hibernate.Hibernate;
import org.hibernate.annotations.common.util.impl.LoggerFactory;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.TypedQuery;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Repository
public class PatientHibernateDaoImpl implements PatientDao {

    @PersistenceContext (type = PersistenceContextType.EXTENDED)
    private EntityManager em;

    @Override
    public Patient createPatient(String firstName, String lastName, String phoneNumber, String email, String password) throws RepeatedEmailException {
        //try catch aca?
        Patient patient;
        patient = new Patient(firstName,lastName,phoneNumber, email, password);
        em.persist(patient);
        //}catch (DataIntegrityViolationException | ConstraintViolationException e){
        //    throw new RepeatedEmailException();
        //}
        return patient;
    }

    @Override
    public Verification createToken(final Patient patient) {

        final String token = UUID.randomUUID().toString();
        final Verification v = new Verification(token, patient);
        em.persist(v);
        return v;
    }

    @Override
    public Optional<Verification> findToken(final String token) {
        final TypedQuery<Verification> query = em.createQuery("FROM Verification as vt where vt.token = :token", Verification.class);
        query.setParameter("token", token);
        return query.getResultList().stream().findFirst();
    }

    @Override
    public Boolean setDoctorId(Patient patient, Doctor doctor) throws NotFoundDoctorException, NotCreatePatientException {
        patient.setDoctor(doctor);
        em.merge(patient);
        return true;
    }

    @Override
    public Optional<Patient> findPatientById(Integer id) {
        Patient patient = em.find(Patient.class, id);
        if (patient != null){
            Hibernate.initialize(patient);
            if (patient.getDoctor() != null) {
                Hibernate.initialize(patient.getDoctor());
                if (patient.getDoctor().getReviews() != null){
                    Hibernate.initialize(patient.getDoctor().getReviews());
                }
            }
        }
        //return Optional.ofNullable(em.find(Patient.class, id));
        return Optional.ofNullable(patient);
    }

    @Override
    public Patient findPatientByEmail(String email) {
        final TypedQuery<Patient> query = em.createQuery("FROM Patient as p where p.email = :email", Patient.class);
        query.setParameter("email", email);
        final List<Patient> list = query.getResultList();
        Patient patient = list.isEmpty() ? null : list.get(0);
        if(patient!=null){
            Hibernate.initialize(patient);
            if (patient.getDoctor() != null){
                Hibernate.initialize(patient.getDoctor());
                Hibernate.initialize(patient.getDoctor().getWorkingHours());
            }
        }
        return patient;
    }

    private List<Appointment> findPacientAppointmentsById(Integer id){
        final TypedQuery<Appointment> query = em.createQuery("FROM appointment", Appointment.class);
        final List<Appointment> list = query.getResultList();
        return list.isEmpty() ? null : list;
    }

    @Override
    @Transactional
    public void deleteToken(final Verification token) {
        final Verification vt = em.merge(token);
        em.remove(vt);
    }

    @Override
    public Patient enableUser(final Patient patient) {
        final Patient u = em.merge(patient);
        u.setEnabled(true);
        return u;
    }

    @Override
    public void deleteUser(final Patient patient) {
        final Patient u = em.merge(patient);
        em.remove(u);
    }

    @Override
    public List<Appointment> getFutureAppointments(Patient patient) {
        String today = LocalDate.now().toString();
        String now = LocalTime.now().toString();
        final TypedQuery<Appointment> query = em.createQuery("FROM Appointment ap " +
                "where ((ap.appointmentDay = :day AND ap.appointmentTime >= :time) OR (ap.appointmentDay > :day))  " +
                "AND ap.patient = :patient AND ap.appointmentCancelled = :cancel", Appointment.class);
        query.setParameter("patient", patient);
        query.setParameter("day", today);
        query.setParameter("time", now);
        query.setParameter("cancel", false);
        final List<Appointment> list = query.getResultList();
        return list.isEmpty() ? Collections.emptyList() : list;
    }

    @Override
    public List<Appointment> getHistoricalAppointments(Patient patient) {
        String today = LocalDate.now().toString();
        String now = LocalTime.now().toString();
        Hibernate.initialize(patient.getDoctor());
        final TypedQuery<Appointment> query;
        if (patient.getDoctor() != null){
            query = em.createQuery("FROM Appointment ap " +
                    "where ((ap.appointmentDay < :day)  OR (ap.appointmentDay = :day AND ap.appointmentTime < :time)) " +
                    "AND ap.patient = :patient AND ap.appointmentCancelled = :cancel AND ap.doctor !=:doctor", Appointment.class);
            query.setParameter("doctor", patient.getDoctor());
        } else {
            query = em.createQuery("FROM Appointment ap where (ap.appointmentDay < :day  OR (ap.appointmentDay = :day AND ap.appointmentTime < :time)) AND ap.patient = :patient AND ap.appointmentCancelled = :cancel", Appointment.class);
        }
        query.setParameter("patient", patient);
        query.setParameter("day", today);
        query.setParameter("time", now);
        query.setParameter("cancel", false);
        final List<Appointment> list = query.getResultList();
        for (Appointment ap: list){
            if (ap.getReview() != null){
                Hibernate.initialize(ap.getReview());
            }
        }
        return list.isEmpty() ? Collections.emptyList() : list;
    }

    @Override
    public List<Favorite> getFavoriteDoctors(Patient patient) {
        final TypedQuery<Favorite> query = em.createQuery("FROM Favorite fav where fav.patient = :patient AND  fav.favoriteCancelled = :cancel", Favorite.class);
        query.setParameter("patient", patient);
        query.setParameter("cancel", false);
        final List<Favorite> list = query.getResultList();
        return list.isEmpty() ? Collections.emptyList() : list;
    }
}
