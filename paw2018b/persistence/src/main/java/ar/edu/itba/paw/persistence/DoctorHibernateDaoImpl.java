package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.*;
import ar.edu.itba.paw.models.*;
import ar.edu.itba.paw.models.exceptions.NotCreateDoctorException;
import ar.edu.itba.paw.models.exceptions.RepeatedLicenceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.*;

/**
 * Created by estebankramer on 10/10/2018.
 */
@Repository
public class DoctorHibernateDaoImpl implements DoctorDao {

    private static int PAGESIZE = 10;

    @PersistenceContext
    private EntityManager em;

    @Autowired
    private SpecialtyDao specialtyDao;

    @Autowired
    private InsuranceDao insuranceDao;

    @Autowired
    private InsurancePlanDao insurancePlanDao;

    @Autowired
    private ReviewDao reviewDao;


    @Override
    public List<Doctor> listDoctors(int page) {
        final TypedQuery<Doctor> query = em.createQuery("FROM Doctor", Doctor.class);
        query.setFirstResult(PAGESIZE*(page));
        query.setMaxResults(PAGESIZE);
        final List<Doctor> list = query.getResultList();
        return list.isEmpty() ? Collections.emptyList() : list;
    }

    @Override
    public int getLastPage(){
        int pageSize = PAGESIZE;
        Number amount = em.createQuery("SELECT COUNT(*) FROM Doctor",Number.class).getSingleResult();
        int lastPageNumber = (int) (Math.ceil(amount.intValue() / pageSize));
        return lastPageNumber;
    }

    public List<Doctor> listDoctors(Search search, int page) {

        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Doctor> query = cb.createQuery(Doctor.class);
        Root<Doctor> root = query.from(Doctor.class);

        Optional<String> name = search.getName().equals("")? Optional.ofNullable(null):Optional.ofNullable(search.getName());
        Optional<String> specialty = search.getSpecialty().equals("noSpecialty")?Optional.ofNullable(null):Optional.ofNullable(search.getSpecialty());
        Optional<String> insurance = search.getInsurance().matches("no")?Optional.ofNullable(null):Optional.ofNullable(search.getInsurance());
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
            query.where(cb.or(cb.like(cb.lower(root.get("firstName")), "%"+name.get().toLowerCase()+"%"),
                    (cb.like(cb.lower(root.get("lastName")), "%"+name.get().toLowerCase()+"%"))));
        }

        if (specialty.isPresent())
        {
            Specialty specialtyObj = specialtyDao.findSpecialtyByName(specialty.get());
            query.where(cb.isMember(specialtyObj, root.get("specialties")));
        }

        if (insurance.isPresent())
        {
            Insurance insuranceObj = insuranceDao.findInsuranceByName(insurance.get());
            List<InsurancePlan> insurancePlans = insurancePlanDao.findAllInsurancePlansByInsurance(insuranceObj);
            boolean first = true;
            for(InsurancePlan plan : insurancePlans){
                if(first){
                    query.where(cb.isMember(plan, root.get("insurancePlans")));
                } else {
                    query.where(cb.or(cb.isMember(plan, root.get("insurancePlans"))));
                }
                first = false;
            }
        }

        if (sex.isPresent()){
            query.where(cb.equal(root.get("sex"), sex.get()));
        }
        if(insurancePlan.isPresent()){
            for(String plan : insurancePlan.get()){
                InsurancePlan insurancePlanObj = insurancePlanDao.findInsurancePlanByPlanName(plan);
                query.where(cb.isMember(insurancePlanObj, root.get("insurancePlans")));
            }
        }
//        TODO averiguar porque si descomento esto me tira could not extract resultset
//        query.where(cb.isNotNull(root.get("specialties")));
//        query.where(cb.isNotNull(root.get("insurancePlans")));

        TypedQuery<Doctor> typedQuery = em.createQuery(query);
        typedQuery.setFirstResult(PAGESIZE*(page));
        typedQuery.setMaxResults(PAGESIZE);
        List<Doctor> list = typedQuery.getResultList();

        return list.isEmpty() ? Collections.emptyList() : list;

    }

    @Override
    public Optional<Doctor> findDoctorById(Integer id) {
        Doctor doctor = em.find(Doctor.class, id);
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

    @Override
    public Boolean setDoctorSpecialty(Doctor doctor, Set<Specialty> specialty){

        for(Specialty s : specialty){
           s.setId(specialtyDao.findSpecialtyByName(s.getSpeciality()).getId());
        }

        doctor.setSpecialties(specialty);
        em.merge(doctor);
        return true;
    }

    public Boolean setWorkingHours(Doctor doctor, List<WorkingHours> workingHours){
        doctor.addWorkingHours(workingHours);
        workingHours.stream().forEach(workingHour -> workingHour.setDoctor(doctor));
        em.merge(doctor);
        return true;
    }











}
