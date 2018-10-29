package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.DoctorDao;
import ar.edu.itba.paw.models.CompressedSearch;
import ar.edu.itba.paw.models.Doctor;
import ar.edu.itba.paw.models.Search;
import ar.edu.itba.paw.models.Specialty;
import ar.edu.itba.paw.models.exceptions.NotCreateDoctorException;
import ar.edu.itba.paw.models.exceptions.RepeatedLicenceException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import javax.persistence.metamodel.Metamodel;
import javax.print.Doc;
import java.util.*;

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

    public List<Doctor> listDoctors(Search search) {

        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Doctor> query = cb.createQuery(Doctor.class);
        Root<Doctor> root = query.from(Doctor.class);

        Optional<String> name = search.getName().equals("")? Optional.ofNullable(null):Optional.ofNullable(search.getName());
        Optional<String> specialty = search.getSpecialty().equals("noSpecialty")?Optional.ofNullable(null):Optional.ofNullable(search.getSpecialty());
        Optional<String> insurance = search.getInsurance().matches("no")?Optional.ofNullable(null):Optional.ofNullable(search.getName());
        Optional<String> sex = search.getSex().equals("ALL") || search.getSex().isEmpty() || search.getSex().equals("")?Optional.ofNullable(null): Optional.ofNullable(search.getSex());
        Optional<List<String>> insurancePlan;

        if (search.getInsurancePlan() != null)
        {
            boolean hasAll = false;
            for (String insurancePlanIterator : search.getInsurancePlan()){
                if (insurancePlanIterator.equals("ALL")) {
                    hasAll = true;
                }
            }
            if (hasAll || search.getInsurancePlan().size() == 0)
            {
                insurancePlan = Optional.ofNullable(null);
            } else {
                insurancePlan =Optional.ofNullable(search.getInsurancePlan());
            }
        } else {
            insurancePlan = Optional.ofNullable(null);
        }

        query.select(root);

        if (name.isPresent())
        {

            Metamodel m = em.getMetamodel();

//            si pone % encontraria todos
            if(!name.get().equals("%")){
                query.where(cb.like(root.get("firstName"), name.get()));
//                TODO find by lastname or second name
//                query.where(cb.or(cb.like(root.get("lastName"), name.get())));
            }
        }

        if (specialty.isPresent())
        {
//            Subquery<Specialty> squery = query.subquery(Specialty.class);
//            Root<Specialty> specialtyRoot = squery.from(Specialty.class);
//            Join<Specialty, Doctor> join = root.join("specialties");

            Set<Specialty>  specialtySet = new HashSet<>();
            specialtySet.add(new Specialty(specialty.get()));
            query.where(root.get("specialties").in(specialtySet));
        }

        if (insurance.isPresent())
        {
            query.where(cb.equal(root.get("insurancePlans"), insurance.get()));
        }

        if (sex.isPresent()){
            query.where(cb.equal(root.get("sex"), sex.get()));
        }

//        query.where(cb.notEqual(root.get("specialties"), null));
//        query.where(cb.notEqual(root.get("insurancePlans"), null));

        List<Doctor> list = em.createQuery(query).getResultList();
        list.size();
        return list.isEmpty() ? Collections.emptyList() : list;

    }


    @Override
    public Optional<Doctor> findDoctorById(Integer id) {
        Doctor doctor = em.find(Doctor.class, id);
//        final TypedQuery<Doctor> query = em.createQuery("FROM Doctor where id = :id", Doctor.class);
//        query.setParameter("id", id);
//        Doctor doctor = query.getSingleResult();
        return Optional.ofNullable(doctor);
    }

    @Override
    public Doctor createDoctor(String firstName, String lastName, String phoneNumber, String sex, String licence, String avatar, String address) throws RepeatedLicenceException, NotCreateDoctorException {
        return null;
    }

    @Override
    public Boolean isAnExistingLicence(Integer licence) {
        return null;
    }
}
