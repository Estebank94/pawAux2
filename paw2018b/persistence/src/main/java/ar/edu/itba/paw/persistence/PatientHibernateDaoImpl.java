package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.PatientDao;
import ar.edu.itba.paw.models.Appointment;
import ar.edu.itba.paw.models.Patient;
import ar.edu.itba.paw.models.exceptions.NotCreatePatientException;
import ar.edu.itba.paw.models.exceptions.NotFoundDoctorException;
import ar.edu.itba.paw.models.exceptions.RepeatedEmailException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

@Repository
public class PatientHibernateDaoImpl implements PatientDao {


    @PersistenceContext
    private EntityManager em;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
//    @Transactional
    public Patient createPatient(String firstName, String lastName, String phoneNumber, String email, String password) throws RepeatedEmailException {
        String finalpassword = passwordEncoder.encode(password);
        final Patient patient = new Patient(firstName,lastName,phoneNumber, email, finalpassword);
        em.persist(patient);
        return patient;
    }

    @Override
    public Boolean setDoctorId(Integer patientId, Integer doctorId) throws NotFoundDoctorException, NotCreatePatientException {
        return null;
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
}