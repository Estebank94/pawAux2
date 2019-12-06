package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.persistance.PatientDao;
import ar.edu.itba.paw.models.*;
import ar.edu.itba.paw.models.exceptions.NotCreatePatientException;
import ar.edu.itba.paw.models.exceptions.NotFoundDoctorException;
import ar.edu.itba.paw.models.exceptions.RepeatedEmailException;
import org.hibernate.annotations.common.util.impl.LoggerFactory;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Repository
public class PatientHibernateDaoImpl implements PatientDao {


    @PersistenceContext
    private EntityManager em;


    @Override
    public Patient createPatient(String firstName, String lastName, String phoneNumber, String email, String password) throws RepeatedEmailException {
        //try catch aca?
        final Patient patient;
        try{
            patient = new Patient(firstName,lastName,phoneNumber, email, password);
            em.persist(patient);
        }catch (DataIntegrityViolationException | ConstraintViolationException e){
            throw new RepeatedEmailException();
        }
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
        return Optional.ofNullable(em.find(Patient.class, id));
    }

    @Override
    public Patient findPatientByEmail(String email) {
        final TypedQuery<Patient> query = em.createQuery("FROM Patient as p where p.email = :email", Patient.class);
        query.setParameter("email", email);
        final List<Patient> list = query.getResultList();
        return list.isEmpty() ? null : list.get(0);
    }

    private List<Appointment> findPacientAppointmentsById(Integer id){
        final TypedQuery<Appointment> query = em.createQuery("FROM appointment", Appointment.class);
        final List<Appointment> list = query.getResultList();
        return list.isEmpty() ? null : list;
    }

    @Override
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
        final TypedQuery<Appointment> query = em.createQuery("FROM Appointment ap where ap.appointmentDay >= :day AND ap.patient = :patient AND ap.appointmentCancelled = :cancel", Appointment.class);
        query.setParameter("patient", patient);
        query.setParameter("day", today);
        query.setParameter("cancel", false);
        final List<Appointment> list = query.getResultList();
        return list.isEmpty() ? Collections.emptyList() : list;
    }

    @Override
    public List<Appointment> getHistoricalAppointments(Patient patient) {
        String today = LocalDate.now().toString();
        final TypedQuery<Appointment> query = em.createQuery("FROM Appointment ap where ap.appointmentDay < :day AND ap.patient = :patient AND ap.appointmentCancelled = :cancel", Appointment.class);
        query.setParameter("patient", patient);
        query.setParameter("day", today);
        query.setParameter("cancel", false);
        final List<Appointment> list = query.getResultList();
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
