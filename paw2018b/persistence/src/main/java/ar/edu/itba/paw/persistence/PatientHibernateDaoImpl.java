package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.PatientDao;
import ar.edu.itba.paw.models.Appointment;
import ar.edu.itba.paw.models.Patient;
import ar.edu.itba.paw.models.exceptions.NotCreatePatientException;
import ar.edu.itba.paw.models.exceptions.NotFoundDoctorException;
import ar.edu.itba.paw.models.exceptions.RepeatedEmailException;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

/**
 * Created by estebankramer & Palito on 10/10/2018.
 */

@Repository
public class PatientHibernateDaoImpl implements PatientDao {


    @PersistenceContext
    private EntityManager em;

    @Override
    public Patient createPatient(String firstName, String lastName, String phoneNumber, String email, String password) throws RepeatedEmailException {
        final Patient patient = new Patient(firstName,lastName,phoneNumber, email, password);
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
    public Optional<Patient> findPatientByEmail(String email) {
        return Optional.ofNullable(em.find(Patient.class, email));
    }

    private List<Appointment> findPacientAppointmentsById(Integer id){
        final TypedQuery<Appointment> query = em.createQuery("FROM appointment", Appointment.class);
        final List<Appointment> list = query.getResultList();
        return list.isEmpty() ? null : list;
    }
}
