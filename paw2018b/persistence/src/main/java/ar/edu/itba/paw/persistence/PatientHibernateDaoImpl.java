package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.persistance.PatientDao;
import ar.edu.itba.paw.models.Appointment;
import ar.edu.itba.paw.models.Doctor;
import ar.edu.itba.paw.models.Patient;
import ar.edu.itba.paw.models.VerificationToken;
import ar.edu.itba.paw.models.exceptions.NotCreatePatientException;
import ar.edu.itba.paw.models.exceptions.NotFoundDoctorException;
import ar.edu.itba.paw.models.exceptions.RepeatedEmailException;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class PatientHibernateDaoImpl implements PatientDao {


    @PersistenceContext
    private EntityManager em;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public Patient createPatient(String firstName, String lastName, String phoneNumber, String email, String password) throws RepeatedEmailException {
        //try catch aca?
        final Patient patient;
        try{
            patient = new Patient(firstName,lastName,phoneNumber, email, password);
            em.persist(patient);
        }catch (DataIntegrityViolationException exc2){
            throw new RepeatedEmailException();
        }catch (ConstraintViolationException exc3){
            throw new RepeatedEmailException();
        }
        return patient;
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
    public VerificationToken createToken(Patient patient) {
        final String token = UUID.randomUUID().toString();
        final VerificationToken vt = new VerificationToken(token, patient);
        // em.persist(vt);
        return vt;
    }

    @Override
    public Optional<VerificationToken> findToken(String token) {
        /*
        final TypedQuery<VerificationToken> query = em.createQuery("from VerificationToken as vt where vt.token =:token", VerificationToken.class);
        query.setParameter("token", token);
        return query.getResultList().stream().findFirst();
        */
        return Optional.empty();
    }

    @Override
    public void deleteToken(final VerificationToken token) {
        /*
        final VerificationToken vt = em.merge(token);
        em.remove(vt);

         */
    }
}
