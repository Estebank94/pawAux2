package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.persistance.*;
import ar.edu.itba.paw.models.*;
import ar.edu.itba.paw.models.exceptions.NotCreateDoctorException;
import ar.edu.itba.paw.models.exceptions.RepeatedLicenceException;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.time.LocalDate;
import java.time.LocalTime;
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

    @Override
    public List<Doctor> listDoctors(Search search, int page, int pageSize) {

        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Doctor> query = cb.createQuery(Doctor.class);

        Root<Doctor> root = query.from(Doctor.class);

        Expression expression = null;

        Optional<String> name = search.getName().equals("") ? Optional.ofNullable(null) : Optional.ofNullable(search.getName());
        Optional<String> specialty = search.getSpecialty().equals("noSpecialty") ? Optional.ofNullable(null) : Optional.ofNullable(search.getSpecialty());
        Optional<String> insurance = search.getInsurance().matches("no") ? Optional.ofNullable(null) : Optional.ofNullable(search.getInsurance());
        Optional<String> sex = search.getSex().equals("ALL") || search.getSex().isEmpty() || search.getSex().equals("") ? Optional.ofNullable(null) : Optional.ofNullable(search.getSex());
        Optional<List<String>> insurancePlan;
        Optional<List<String>> days;

        if (search.getDays() != null) {
            boolean hasAllDays = false;
            for (String daysIterator : search.getDays()) {
                if (daysIterator.equals("ALL")) {
                    hasAllDays = true;
                }
            }
            if (hasAllDays || search.getDays().size() == 0) {
                days = Optional.ofNullable(null);
            } else {
                days = Optional.ofNullable(search.getDays());
            }
        } else {
            days = Optional.empty();
        }

        if (search.getInsurancePlan() != null) {
            boolean hasAll = false;
            for (String insurancePlanIterator : search.getInsurancePlan()) {
                if (insurancePlanIterator.equals("ALL")) {
                    hasAll = true;
                }
            }
            if (hasAll || search.getInsurancePlan().size() == 0) {
                insurancePlan = Optional.ofNullable(null);
            } else {
                insurancePlan = Optional.ofNullable(search.getInsurancePlan());
            }
        } else {
            insurancePlan = Optional.ofNullable(null);
        }

        query.select(root);

        if (name.isPresent()) {
            expression = cb.or(cb.like(cb.lower(root.get("firstName")), "%" + escapeSpecialCharacters(name.get()).toLowerCase() + "%"),
                    (cb.like(cb.lower(root.get("lastName")), "%" + escapeSpecialCharacters(name.get()).toLowerCase() + "%")));
        }

        if (specialty.isPresent()) {
            Specialty specialtyObj = specialtyDao.findSpecialtyByName(specialty.get());
            Expression specialtyExpresion = cb.isMember(specialtyObj, root.get("specialties"));
            if (expression == null) {
                expression = specialtyExpresion;
            } else {
                expression = cb.and(expression, specialtyExpresion);
            }
        }

        if (sex.isPresent()) {
            Expression sexExpression = cb.like(root.get("sex"), "%" + sex.get().toUpperCase() + "%");
            if (expression == null) {
                expression = sexExpression;
            } else {
                expression = cb.and(expression, sexExpression);
            }
        }

        if (insurance.isPresent()) {
            Insurance insuranceObj = insuranceDao.findInsuranceByName(insurance.get());
            List<InsurancePlan> insurancePlans = insuranceObj.getPlans();
            List<Predicate> predicates = new ArrayList<>();
            for (InsurancePlan plan : insurancePlans) {
                predicates.add(cb.isMember(plan, root.get("insurancePlans")));
            }
            Expression insuranceExpression = cb.or(predicates.toArray(new Predicate[predicates.size()]));
            if (expression == null) {
                expression = insuranceExpression;
            } else {
                expression = cb.and(expression, insuranceExpression);
            }
        }

        if (insurancePlan.isPresent()) {
            List<InsurancePlan> insurancePlans = insurancePlanDao.getInsurancePlansByList(insurancePlan.get());
            List<Predicate> insurancePlansPredicates = new ArrayList<>();
            for (InsurancePlan plan : insurancePlans) {
                insurancePlansPredicates.add(cb.isMember(plan, root.get("insurancePlans")));
            }
            Expression insurancePlansExpression = cb.or(insurancePlansPredicates.toArray(new Predicate[insurancePlansPredicates.size()]));
            if (expression == null) {
                expression = insurancePlansExpression;
            } else {
                expression = cb.and(expression, insurancePlansExpression);
            }

            /* AND SEARCHING
            for(String plan : insurancePlan.get()){
                InsurancePlan insurancePlanObj = insurancePlanDao.findInsurancePlanByPlanName(plan);
                // query.where(cb.isMember(insurancePlanObj, root.get("insurancePlans")));
                Expression insuranceExpression = cb.isMember(insurancePlanObj, root.get("insurancePlans"));
                if (expression == null){
                    expression = insuranceExpression;
                } else {
                    expression = cb.and(expression, insuranceExpression);
                }
            }

             */
        }

        if (days.isPresent()) {
            List<WorkingHours> workingHours = workingHoursDao.findWorkingHoursByDayWeek(days.get());
            List<Predicate> predicates = new ArrayList<>();
            for (WorkingHours w : workingHours) {
                predicates.add(cb.isMember(w, root.get("workingHours")));
            }
            Expression daysExpression = cb.or(predicates.toArray(new Predicate[predicates.size()]));
            if (expression == null) {
                expression = daysExpression;
            } else {
                expression = cb.and(expression, daysExpression);
            }
        }

        Expression hasWh = cb.isNotEmpty(root.get("workingHours"));
        if (expression == null) {
            expression = hasWh;
        } else {
            expression = cb.and(expression, hasWh);
        }

        Expression hasSpecialty = cb.isNotEmpty(root.get("specialties"));
        expression = cb.and(expression, hasSpecialty);


        Expression hasInsurancePlans = cb.isNotEmpty(root.get("insurancePlans"));
        expression = cb.and(expression, hasInsurancePlans);


        Expression hasAddress = cb.isNotNull(root.get("address"));
        expression = cb.and(expression, hasAddress);

        Expression hasPhoneNumber = cb.isNotNull(root.get("phoneNumber"));
        expression = cb.and(expression, hasPhoneNumber);

        if (expression != null){
            query.where(expression);
        }

        TypedQuery<Doctor> typedQuery = em.createQuery(query);
        typedQuery.setFirstResult(pageSize * (page));
        typedQuery.setMaxResults(pageSize);
        List<Doctor> list = typedQuery.getResultList();
        for(Doctor doctor : list){
            Hibernate.initialize(doctor.getWorkingHours());
            Hibernate.initialize(doctor.getReviews());
        }

        return list.isEmpty() ? Collections.emptyList() : list;
    }

    @Override
    public Long getLastPage() {
        final TypedQuery<Doctor> query = em.createQuery("FROM Doctor", Doctor.class);
        final List<Doctor> list = query.getResultList();
        int pageCount = (int) (Math.ceil(list.size() * 1.0 / PAGESIZE));
        return (long) pageCount;
    }

    @Override
    public Long getLastPage(Search search, int pageSize) {

        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Doctor> query = cb.createQuery(Doctor.class);
        Root<Doctor> root = query.from(Doctor.class);

        Expression expression = null;
        Optional<String> name = search.getName().equals("")? Optional.ofNullable(null):Optional.ofNullable(search.getName());
        Optional<String> specialty = search.getSpecialty().equals("noSpecialty")?Optional.ofNullable(null):Optional.ofNullable(search.getSpecialty());
        Optional<String> insurance = search.getInsurance().matches("no")?Optional.ofNullable(null):Optional.ofNullable(search.getInsurance());
        Optional<String> sex = search.getSex().equals("ALL") || search.getSex().isEmpty() || search.getSex().equals("")?Optional.ofNullable(null): Optional.ofNullable(search.getSex());
        Optional<List<String>> insurancePlan;
        Optional<List<String>> days;
        if (search.getDays() != null){
            boolean hasAllDays = false;
            for (String daysIterator: search.getDays()) {
                if (daysIterator.equals("ALL")){
                    hasAllDays = true;
                }
            }
            if (hasAllDays || search.getDays().size() == 0){
                days = Optional.ofNullable(null);
            } else {
                days = Optional.ofNullable(search.getDays());
            }
        } else {
            days = Optional.empty();
        }

        if (search.getInsurancePlan() != null) {
            boolean hasAll = false;
            for (String insurancePlanIterator : search.getInsurancePlan()){
                if (insurancePlanIterator.equals("ALL")) {
                    hasAll = true;
                }
            }
            if (hasAll || search.getInsurancePlan().size() == 0) {
                insurancePlan = Optional.ofNullable(null);
            } else {
                insurancePlan = Optional.ofNullable(search.getInsurancePlan());
            }
        } else {
            insurancePlan = Optional.ofNullable(null);
        }

        query.select(root);

        if (name.isPresent()) {
            expression = cb.or(cb.like(cb.lower(root.get("firstName")), "%"+escapeSpecialCharacters(name.get()).toLowerCase()+"%"),
                    (cb.like(cb.lower(root.get("lastName")), "%"+escapeSpecialCharacters(name.get()).toLowerCase()+"%")));
        }

        if (specialty.isPresent()) {
            Specialty specialtyObj = specialtyDao.findSpecialtyByName(specialty.get());
            Expression specialtyExpresion = cb.isMember(specialtyObj, root.get("specialties"));
            if (expression == null){
                expression = specialtyExpresion;
            } else {
                expression = cb.and(expression, specialtyExpresion);
            }
        }

        if (sex.isPresent()) {
            Expression sexExpression = cb.like(root.get("sex"), "%" + sex.get().toUpperCase() + "%");
            if (expression == null){
                expression = sexExpression;
            } else {
                expression = cb.and(expression, sexExpression);
            }
        }

        if (insurance.isPresent()) {
            Insurance insuranceObj = insuranceDao.findInsuranceByName(insurance.get());
            List<InsurancePlan> insurancePlans = insuranceObj.getPlans();
            List<Predicate> predicates = new ArrayList<>();
            for(InsurancePlan plan : insurancePlans){
                predicates.add(cb.isMember(plan, root.get("insurancePlans")));
            }
            Expression insuranceExpression = cb.or(predicates.toArray(new Predicate[predicates.size()]));
            if (expression == null){
                expression = insuranceExpression;
            } else {
                expression = cb.and(expression, insuranceExpression);
            }
        }

        if(insurancePlan.isPresent()){
            List<InsurancePlan> insurancePlans = insurancePlanDao.getInsurancePlansByList(insurancePlan.get());
            List<Predicate> insurancePlansPredicates = new ArrayList<>();
            for (InsurancePlan plan : insurancePlans){
                insurancePlansPredicates.add(cb.isMember(plan, root.get("insurancePlans")));
            }
            Expression insurancePlansExpression = cb.or(insurancePlansPredicates.toArray(new Predicate[insurancePlansPredicates.size()]));
            if (expression == null) {
                expression = insurancePlansExpression;
            } else {
                expression = cb.and(expression, insurancePlansExpression);
            }
            /* AND SEARCHING
            for(String plan : insurancePlan.get()){
                InsurancePlan insurancePlanObj = insurancePlanDao.findInsurancePlanByPlanName(plan);
                // query.where(cb.isMember(insurancePlanObj, root.get("insurancePlans")));
                Expression insuranceExpression = cb.isMember(insurancePlanObj, root.get("insurancePlans"));
                if (expression == null){
                    expression = insuranceExpression;
                } else {
                    expression = cb.and(expression, insuranceExpression);
                }
            }

             */
        }

        if (days.isPresent()) {
            List <WorkingHours> workingHours = workingHoursDao.findWorkingHoursByDayWeek(days.get());
            List<Predicate> predicates = new ArrayList<>();
            for(WorkingHours w : workingHours){
                predicates.add(cb.isMember(w, root.get("workingHours")));
            }
            Expression daysExpression = cb.or(predicates.toArray(new Predicate[predicates.size()]));
            if (expression == null){
                expression = daysExpression;
            } else {
                expression = cb.and(expression, daysExpression);
            }
        }

        Expression hasWh = cb.isNotEmpty(root.get("workingHours"));
        if (expression == null){
            expression = hasWh;
        } else {
            expression = cb.and(expression, hasWh);
        }

        Expression hasSpecialty = cb.isNotEmpty(root.get("specialties"));
        expression = cb.and(expression, hasSpecialty);


        Expression hasInsurancePlans = cb.isNotEmpty(root.get("insurancePlans"));
        expression = cb.and(expression, hasInsurancePlans);


        Expression hasAddress = cb.isNotNull(root.get("address"));
        expression = cb.and(expression, hasAddress);

        Expression hasPhoneNumber = cb.isNotNull(root.get("phoneNumber"));
        expression = cb.and(expression, hasPhoneNumber);

        if (expression != null){
            query.where(expression);
        }

        TypedQuery<Doctor> typedQuery = em.createQuery(query);

        final List<Doctor> list = typedQuery.getResultList();
        int pageCount = (int) (Math.ceil(list.size() * 1.0 / pageSize));
        return (long) pageCount;

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
        em.merge(doctor);
        doctor.setSpecialties(specialty);

        return true;
    }


    public Boolean setWorkingHours(Doctor doctor, List<WorkingHours> workingHours){
        Hibernate.initialize(doctor.getWorkingHours());

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
        em.merge(doctor);
        doctor.addInsurancePlans(insurancePlans);
        for(InsurancePlan i : insurancePlans){
            i.setId(insurancePlanDao.findInsurancePlanByPlanName(i.getPlan()).getId());
        }
        return true;
    }

    public Boolean setDoctorDescription(Doctor doctor, Description description){
        em.merge(doctor);
        doctor.setDescription(description);
        description.setDoctor(doctor);
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

    @Override
    public List<Appointment> getFutureAppointments(Doctor doctor) {
        String now = LocalTime.now().toString();
        String today = LocalDate.now().toString();
        final TypedQuery<Appointment> query = em.createQuery("FROM Appointment ap where ((ap.appointmentDay = :day AND ap.appointmentTime >= :time) OR (ap.appointmentDay > :day)) AND ap.doctor = :doctor AND ap.appointmentCancelled = :cancel", Appointment.class);
        query.setParameter("doctor", doctor);
        query.setParameter("day", today);
        query.setParameter("time", now);
        query.setParameter("cancel", false);
        final List<Appointment> list = query.getResultList();
        return list.isEmpty() ? Collections.emptyList() : list;
    }

    @Override
    public List<Appointment> getHistoricalAppointments(Doctor doctor) {
        String today = LocalDate.now().toString();
        String now = LocalTime.now().toString();
        final TypedQuery<Appointment> query = em.createQuery("FROM Appointment ap where ((ap.appointmentDay < :day)  OR (ap.appointmentDay = :day AND ap.appointmentTime < :time)) AND ap.doctor = :doctor AND ap.appointmentCancelled = :cancel", Appointment.class);
        query.setParameter("doctor", doctor);
        query.setParameter("day", today);
        query.setParameter("time", now);
        query.setParameter("cancel", false);
        final List<Appointment> list = query.getResultList();
        for (Appointment ap: list){
            Hibernate.initialize(ap.getReview());
        }
        return list.isEmpty() ? Collections.emptyList() : list;
    }

    @Override
    public List<Review> getReviews(Doctor doctor) {
        final TypedQuery<Review> query = em.createQuery("Select distinct r FROM Doctor d join d.reviews r where r.doctor.id = :id", Review.class);
        query.setParameter("id", doctor.getId());
        final List<Review> list = query.getResultList();
        return list.isEmpty() ? Collections.emptyList() : list;
    }

    @Override
    public void createReview(Doctor doctor, Patient patient, Review review) {
        doctor.addReview(review);
        em.persist(doctor);
    }

    private String escapeSpecialCharacters(String input) {
        StringBuilder resultStr = new StringBuilder();
        for (char ch : input.toCharArray()) {
            if (!isUnsafe(ch)) {
                resultStr.append(ch);
            } else{
                resultStr.append('\\');
            }
        }
        return resultStr.toString();
    }

    private static boolean isUnsafe(char ch) {
        return (ch == '%' || ch == '_' || ch == '\\' || ch == '"' || ch == '\'' || ch == '\b' || ch == '\n'
                || ch == '\r' || ch == '\t' || ch == '\0');
    }

}
