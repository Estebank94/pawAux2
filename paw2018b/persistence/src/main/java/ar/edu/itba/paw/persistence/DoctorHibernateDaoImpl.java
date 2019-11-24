package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.persistance.*;
import ar.edu.itba.paw.models.*;
import ar.edu.itba.paw.models.exceptions.NotCreateDoctorException;
import ar.edu.itba.paw.models.exceptions.RepeatedLicenceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.time.LocalDate;
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
    private WorkingHoursDao workingHoursDao;

    @Override
    public List<Doctor> listDoctors() {
        final TypedQuery<Doctor> query = em.createQuery("FROM Doctor", Doctor.class);
        final List<Doctor> list = query.getResultList();
        return list.isEmpty() ? Collections.emptyList() : list;
    }

    @Override
    public List<Doctor> listDoctors(int page) {
        final TypedQuery<Doctor> query = em.createQuery("FROM Doctor", Doctor.class);
        query.setFirstResult(PAGESIZE*(page));
        query.setMaxResults(PAGESIZE);
        final List<Doctor> list = query.getResultList();
        return list.isEmpty() ? Collections.emptyList() : list;
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
        Optional<String> days = search.getDays().equals("no")|| search.getDays().isEmpty() || search.getDays().equals("")?Optional.ofNullable(null):Optional.ofNullable(search.getDays());

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
            List<Predicate> predicates = new ArrayList<>();
            for(InsurancePlan plan : insurancePlans){
                predicates.add(cb.isMember(plan, root.get("insurancePlans")));
            }
            query.where(cb.or(predicates.toArray(new Predicate[predicates.size()])));
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

        if (days.isPresent()) {
            Integer day = Integer.valueOf(LocalDate.parse(search.getDays()).getDayOfWeek().getValue());
            List<WorkingHours> workingHours = workingHoursDao.findWorkingHoursByDayWeek(day);
            List<Predicate> predicates = new ArrayList<>();
            for(WorkingHours w : workingHours){
                predicates.add(cb.isMember(w, root.get("workingHours")));
            }
            query.where(cb.or(predicates.toArray(new Predicate[predicates.size()])));

         }

        TypedQuery<Doctor> typedQuery = em.createQuery(query);
        typedQuery.setFirstResult(PAGESIZE*(page));
        typedQuery.setMaxResults(PAGESIZE);
        List<Doctor> list = typedQuery.getResultList();

        return list.isEmpty() ? Collections.emptyList() : list;

    }

    @Override
    public Long getLastPage() {
        final TypedQuery<Doctor> query = em.createQuery("FROM Doctor", Doctor.class);
        final List<Doctor> list = query.getResultList();
        int pageCount = (int) (Math.ceil(list.size() / PAGESIZE));
        return Long.valueOf(pageCount);
    }

    @Override
    public Long getLastPage(Search search) {

        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Long> query = cb.createQuery(Long.class);
        Root<Doctor> root = query.from(Doctor.class);

        Optional<String> name = search.getName().equals("")? Optional.ofNullable(null):Optional.ofNullable(search.getName());
        Optional<String> specialty = search.getSpecialty().equals("noSpecialty")?Optional.ofNullable(null):Optional.ofNullable(search.getSpecialty());
        Optional<String> insurance = search.getInsurance().matches("no")?Optional.ofNullable(null):Optional.ofNullable(search.getInsurance());
        Optional<String> sex = search.getSex().equals("ALL") || search.getSex().isEmpty() || search.getSex().equals("")?Optional.ofNullable(null): Optional.ofNullable(search.getSex());
        Optional<String> days = search.getDays().equals("no")|| search.getDays().isEmpty() || search.getDays().equals("")?Optional.ofNullable(null):Optional.ofNullable(search.getDays());
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

        query.select(cb.count(root));

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
            List<Predicate> predicates = new ArrayList<>();
            for(InsurancePlan plan : insurancePlans){
                predicates.add(cb.isMember(plan, root.get("insurancePlans")));
            }
            query.where(cb.or(predicates.toArray(new Predicate[predicates.size()])));
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

        if (days.isPresent()) {
            Integer day = Integer.valueOf(LocalDate.parse(search.getDays()).getDayOfWeek().getValue());
            List<WorkingHours> workingHours = workingHoursDao.findWorkingHoursByDayWeek(day);
            List<Predicate> predicates = new ArrayList<>();
            for(WorkingHours w : workingHours){
                predicates.add(cb.isMember(w, root.get("workingHours")));
            }
            query.where(cb.or(predicates.toArray(new Predicate[predicates.size()])));

        }

        TypedQuery<Long> typedQuery = em.createQuery(query);
        return typedQuery.getSingleResult() / PAGESIZE;

    }


    @Override
    public Optional<Doctor> findDoctorById(Integer id) {
        Doctor doctor = em.find(Doctor.class, id);
        return Optional.ofNullable(doctor);
    }

    @Override
    public Doctor createDoctor(String firstName, String lastName, String phoneNumber, String sex, Integer licence, byte[] avatar, String address) throws RepeatedLicenceException, NotCreateDoctorException {
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

    @Override
    public Boolean setDoctorAvatar(Doctor doctor, byte[] avatar) {
        doctor.setProfilePicture(avatar);
        em.merge(doctor);
        return true;
    }

    public Boolean setDoctorInsurances(Doctor doctor, List<InsurancePlan> insurancePlans){
        doctor.addInsurancePlans(insurancePlans);
        for(InsurancePlan i : insurancePlans){
            i.setId(insurancePlanDao.findInsurancePlanByPlanName(i.getPlan()).getId());
        }
        em.merge(doctor);
        return true;
    }

    public Boolean setDoctorDescription(Doctor doctor, Description description){
        doctor.setDescription(description);
        description.setDoctor(doctor);
        em.merge(doctor);
        return true;
    }

    public Optional<Description> findDescriptionByDoctor(Doctor doctor){
        final TypedQuery<Description> query = em.createQuery("from Description as d WHERE d.doctor = :doctor", Description.class);
        query.setParameter("doctor", doctor);
        return Optional.ofNullable(query.getSingleResult());
    }

    public boolean mergeDoctorDescription(Description original, Description toMerge){
        if (original == null){
            return false;
        }
        if (toMerge == null){
            return false;
        }
        Doctor doctor = original.getDoctor();

        if (original.getLanguages() == null && toMerge.getLanguages() != null){
            original.setLanguages(toMerge.getLanguages());
        }
        if (original.getEducation() == null && toMerge.getEducation() != null){
            original.setEducation(toMerge.getEducation());
        }
        if (original.getCertificate() == null && toMerge.getCertificate() != null){
            original.setCertificate(toMerge.getCertificate());
        }
        doctor.setDescription(original);
        em.merge(doctor);
        return true;
    }

    public void mergeDoctor(Doctor doctor){
        em.merge(doctor);
    }

}
