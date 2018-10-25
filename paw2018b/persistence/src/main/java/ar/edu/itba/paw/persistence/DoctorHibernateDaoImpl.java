package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.DoctorDao;
import ar.edu.itba.paw.models.CompressedSearch;
import ar.edu.itba.paw.models.Doctor;
import ar.edu.itba.paw.models.Search;
import ar.edu.itba.paw.models.exceptions.NotCreateDoctorException;
import ar.edu.itba.paw.models.exceptions.RepeatedLicenceException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.print.Doc;
import java.util.List;
import java.util.Optional;

/**
 * Created by estebankramer on 10/10/2018.
 */
@Repository
public class DoctorHibernateDaoImpl implements DoctorDao {

    @PersistenceContext
    private EntityManager em;

//    @Override
//    public Optional<CompressedSearch> listDoctors() {
//        final TypedQuery<Doctor> query = em.createQuery("FROM doctor", Doctor.class);
//        final List<Doctor> list = query.getResultList();
//
//        return list.isEmpty() ? null : list
//    }

    @Override
    public List<Doctor> listDoctors() {
        final TypedQuery<Doctor> query = em.createQuery("FROM Doctor", Doctor.class);
        final List<Doctor> list = query.getResultList();
        return list.isEmpty() ? null : list;
    }

//    public List<Doctor> listDoctors(Search search) {
//        final TypedQuery<Doctor> query = em.createQuery("FROM Doctor" +
//                "", Doctor.class);
//
//        final List<Doctor> list = query.getResultList();
//        return list.isEmpty() ? null : list;
//    }

    @Override
    public Optional<CompressedSearch> findDoctors(Search search) {
        return null;
    }

    @Override
    public Optional<Doctor> findDoctorById(Integer id) {
        final TypedQuery<Doctor> query = em.createQuery("FROM Doctor where id = :id", Doctor.class);
        query.setParameter("id", id);
        Doctor doctor = query.getSingleResult();
        return Optional.ofNullable(doctor);
    }

    @Override
    public Doctor createDoctor(String firstName, String lastName, String phoneNumber, String sex, Integer licence, String avatar, String address) throws RepeatedLicenceException, NotCreateDoctorException {
        final Doctor doctor = new Doctor(firstName, lastName, phoneNumber, sex, licence, avatar, address);
        em.persist(doctor);
        return doctor;
    }

    @Override
    public Boolean isAnExistingLicence(Integer licence) {
        return null;
    }
}
