package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.*;
import ar.edu.itba.paw.models.*;
import ar.edu.itba.paw.models.exceptions.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service("DoctorServiceImpl")
public class DoctorServiceImpl implements DoctorService {

    @PersistenceContext
    private EntityManager em;

    @Autowired
    private DoctorDao doctorDao;
    
    @Autowired
    private SpecialtyDao specialtyDao;

    @Autowired
    private InsurancePlanDao insurancePlanDao;

    @Autowired
    private DescriptionDao descriptionDao;

    @Autowired
    private WorkingHoursDao workingHoursDao;


    private static final Logger LOGGER = LoggerFactory.getLogger(DoctorServiceImpl.class);

    @Override
    public List<Doctor> listDoctors(int page) {
        LOGGER.debug("DoctorServiceImpl: listDoctors");
        return doctorDao.listDoctors(page);
    }

    @Override
    public int getLastPage(){
        LOGGER.debug("DoctorServiceImpl: getLastPage");
        return doctorDao.getLastPage();
    }

    @Override
    @Transactional
    public List<Doctor> listDoctors(Search search, String pageAsString) throws NotValidSearchException, NotValidPageException {
        LOGGER.debug("DoctorServiceImpl: listDoctors");

        if (pageAsString == null){
            LOGGER.debug("Page can't be null");
            throw new NotValidPageException("Page can't be null");
        }

        if (!pageAsString.matches("[0-9]+")){
            LOGGER.debug("Page must be integer");
            throw new NotValidPageException("Page must be integer");
        }

        if (String.valueOf(Integer.MAX_VALUE).length() < pageAsString.length()){
            LOGGER.debug("Doctor ID can't have more than "+ String.valueOf(Integer.MAX_VALUE).length() + " numbers. " +
                    "The ID given is: {}", pageAsString);
        }

        int pageAsInt = Integer.parseInt(pageAsString);

        if (pageAsInt < 0){
            LOGGER.debug("Page can't be a negative number. The page given is: {}", pageAsInt);
            throw new NotValidPageException("Page can't be negative");
        }
        if (pageAsInt >= Integer.MAX_VALUE){
            LOGGER.debug("Page can't be greater than the biggest number");
            throw new NotValidPageException("Page can't be greater than the biggest number");
        }
        List<Doctor> list = doctorDao.listDoctors(search, pageAsInt);
//        if(!list.isEmpty()){
//            list.get(0).getReviews();
//        }
//        list.get(0).getInsurancePlans().size();
        return list;
    }



    @Override
    @Transactional
    public Optional<Doctor> findDoctorById(String idAsString) throws NotFoundDoctorException, NotValidIDException {
        LOGGER.debug("DoctorServiceImpl: findDoctorById");

        if (idAsString == null ){
            LOGGER.debug("Doctor ID can't be null");
            throw new NotValidIDException("Doctor Id can't be null");
        }

        if (!idAsString.matches("[0-9]+")){
            LOGGER.debug("Doctor ID must be Integer");
            throw new NotValidIDException("Doctor ID must be Integer");
        }

        if (String.valueOf(Integer.MAX_VALUE).length() < idAsString.length()){
            LOGGER.debug("Doctor ID can't have more than "+ String.valueOf(Integer.MAX_VALUE).length() + " numbers. " +
                    "The ID given is: {}", idAsString);
            throw new NotValidIDException("Id number");
        }

        int idAsInt = Integer.parseInt(idAsString);

        if (idAsInt < 0){
            LOGGER.debug("Doctor ID can't be a negative number. The ID given is: {}", idAsInt);
            throw new NotValidIDException("Doctor Id can't be negative");
        }
        if (idAsInt >= Integer.MAX_VALUE ){
            LOGGER.debug("Doctor ID can't be bigger than bigger number. The ID given is: {}", idAsInt);
        }

        LOGGER.debug("Find doctor by ID");
        Optional<Doctor> thisdoctor =  doctorDao.findDoctorById(idAsInt);
        thisdoctor.get().getWorkingHours().size();
        thisdoctor.get().getAppointments().size();
        thisdoctor.get().getReviews().size();

        if(thisdoctor.get().getDescription() != null){
            if(thisdoctor.get().getDescription().getLanguages() != null){
                thisdoctor.get().getDescription().getLanguages();
            }
            if(thisdoctor.get().getDescription().getCertificate() != null){
                thisdoctor.get().getDescription().getCertificate();
            }
            if(thisdoctor.get().getDescription().getEducation() != null){
                thisdoctor.get().getDescription().getCertificate();
            }
        }

        Doctor doc = thisdoctor.get();
        em.merge(doc);
        if (!thisdoctor.isPresent()){
            LOGGER.debug("The Doctor doesn't exist with ID number: {}", idAsInt);
            throw new NotFoundDoctorException("Doctor doesn't exist");
        }
        LOGGER.debug("Doctor with ID: {} found", idAsInt);
        LOGGER.debug("Doctor is: {}", thisdoctor);
        return thisdoctor;
    }


    @Override
    @Transactional
    public Doctor createDoctor(String firstName, String lastName, String phoneNumber, String sex, String licence,
                               byte[] avatar, String address) throws NotValidFirstNameException, NotValidLastNameException, NotValidPhoneNumberException, NotCreateDoctorException, RepeatedLicenceException, NotValidSexException, NotValidLicenceException, NotValidAddressException {
        LOGGER.debug("DoctorServiceImpl: createDoctor");

        if (firstName == null){
            LOGGER.debug("The First Name of a Doctor can't be null");
            throw new NotValidFirstNameException("firstName can't be null");
        }

        if (firstName.length() == 0){
            LOGGER.debug("The First Name must at least have a character");
            throw new NotValidFirstNameException("Doctor firstname can't be empty");
        }

        if (firstName.length() > 45){
            LOGGER.debug("The First Name of a doctor can't have more than 45 characters. The name givven is: {}", firstName);
            throw new NotValidFirstNameException("Doctor firstname maxlength is 50");
        }
        if (!InputValidation.validUTF8(firstName.getBytes())){
            LOGGER.debug("The first name is not a valid UTF8");
            throw new NotValidFirstNameException();
        }

        if (lastName == null) {
            LOGGER.debug("The Last Name of a doctor can't be null");
            throw new NotValidLastNameException("last name can't be null");
        }
        if (lastName.length() == 0){
            LOGGER.debug("The Last Name of a Doctor must have at least 1 character");
            throw new NotValidLastNameException("Doctor lastName can't be empty");
        }

        if (lastName.length() > 45){
            LOGGER.debug("The Last Name of a doctor can't have more than 45 characters. The name given is: {}", lastName);
            throw new NotValidLastNameException("Doctor lastName maxlength is 50");
        }

        if (!InputValidation.validUTF8(lastName.getBytes())){
            LOGGER.debug("The last name is not a valid UTF-8");
            throw new NotValidLastNameException();
        }

        if (phoneNumber == null) {
            LOGGER.debug("The Phone Number of a doctor can't be null.");
            throw new NotValidPhoneNumberException("phonenumber can't be null");
        }
        if (phoneNumber.length() == 0){
            LOGGER.debug("The Phone Number of a doctor must have at least 8 numbers");
            throw new NotValidPhoneNumberException("phonenumber firstname can't be empty");
        }
        if (!InputValidation.validUTF8(phoneNumber.getBytes())){
            LOGGER.debug("The phonenumber is not a valid UTF8");
            throw new NotValidPhoneNumberException();
        }
        /*TODO: validar regExp de phone*/
        if (phoneNumber.length() > 20){
            LOGGER.debug("The Phone Number of a doctor can't have more than 20 numbers. The Phone Number given is: {}", phoneNumber);
            throw new NotValidPhoneNumberException("phonenumber can't have more than 20 characters");
        }

        if (sex == null) {
            LOGGER.debug("The Sex of a Doctor can't be null");
            throw new NotValidSexException("sex can't be null");
        }
        if (sex.length() == 0){
            LOGGER.debug("The Sex can't have 0 characters");
            throw new NotValidSexException("sex can't be empty");
        }

        if (sex.length() > 1){
            LOGGER.debug("The Sex can't have more than one character. Sex given: {}", sex);
            throw new NotValidSexException("sex can't have more than 1 characters");
        }

        if (licence == null) {
            LOGGER.debug("The Licence of a Doctor can't be null");
            throw new NotValidLicenceException("licence can't be null");
        }

        if (!licence.matches("[0-9]+")){
            LOGGER.debug("The licence of a Doctor only can have numbers");
            throw new NotValidLicenceException("The licence of a Doctor only can have numbers");
        }

        if (String.valueOf(Integer.MAX_VALUE).length() < licence.length()){
            LOGGER.debug("Licence can't have more than "+ String.valueOf(Integer.MAX_VALUE).length()+ " digits. " +
            "The licence given is: {}",licence);
            throw new NotValidLicenceException("Licence is bigger than biggert licence");
        }
        int licenceAsInt = Integer.parseInt(licence);

        if (licenceAsInt <= 0){
            LOGGER.debug("The Licence of a Doctor can't have 0 characters");
            throw new NotValidLicenceException("licence can't be empty");
        }

        if (licenceAsInt >= Integer.MAX_VALUE){
            LOGGER.debug("The Licence of a Doctor can't have more than 9 characters. The Licence given is: {}", licence);
            throw new NotValidLicenceException("licence can't have more than 10 characters");
        }

        if (address == null) {
            LOGGER.debug("The address of a Doctor can't be null");
            throw new NotValidAddressException("address can't be null");
        }
        if (address.length() == 0){
            LOGGER.debug("The address of a Doctor can't have 0 characters");
            throw new NotValidLicenceException("address can't be empty");
        }

        if (address.length() > 100){
            LOGGER.debug("The address of a Doctor can't have more tan 100 characters. The address given is: {}", address);
            throw new NotValidAddressException("address can't have more than 100 characters");
        }
        if (!InputValidation.validUTF8(address.getBytes())){
            LOGGER.debug("The first name is not a valid UTF8");
            throw new NotValidAddressException();
        }

        LOGGER.debug("Doctor's First Name: {}", firstName);
        LOGGER.debug("Doctor's Last Name: {}", lastName);
        LOGGER.debug("Doctor's Phone Number: {}", phoneNumber);
        LOGGER.debug("Doctor's Sex: {}", sex);
        LOGGER.debug("Doctor's Licence: {}", licenceAsInt);
        LOGGER.debug("Doctor's Avatar: {}", avatar);
        LOGGER.debug("Doctor's Address: {}", address);
        Doctor doctor;

        try {
            doctor = doctorDao.createDoctor(firstName, lastName, phoneNumber, sex, licenceAsInt, avatar, address);
        } catch (NotCreateDoctorException e) {
            throw new NotCreateDoctorException();
        } catch (RepeatedLicenceException e) {
            throw new RepeatedLicenceException();
        }
        return doctor;
    }

    @Transactional(rollbackFor= SQLException.class)
    @Override
    public Optional<Doctor> setDoctorInfo(Integer doctorId, Set<Specialty> specialty, Set<Insurance> insurances, List<WorkingHours> workingHours, Description description) throws NotValidDoctorIdException, NotFoundDoctorException, NotValidSpecialtyException, NotValidWorkingHourException, NotValidInsuranceException, NotValidInsurancePlanException, NotValidDescriptionException, NotValidLanguagesException, NotValidCertificateException, NotValidEducationException {
        LOGGER.debug("DoctorServiceImpl: setDoctorInfo");
        if (doctorId == null ){
            LOGGER.debug("The Doctor ID can't be null");
            throw new NotValidDoctorIdException("Doctor Id can't be null");
        }

        if (doctorId < 0){
            LOGGER.debug("The Doctor ID can't be a negative number. The number given is: {}", doctorId);
            throw new NotValidDoctorIdException("Doctor Id can't be negative");
        }

        Optional<Doctor> doctorOptional = doctorDao.findDoctorById(doctorId);

        if (!doctorOptional.isPresent()){
            LOGGER.debug("The Doctor with ID: {}, doesn't exist", doctorId);
            throw new NotFoundDoctorException("Doctor doesn't exist");
        }

        Doctor doctor = doctorOptional.get();

        if (specialty == null){
            LOGGER.debug("The Specialty of a Doctor can't be null. Doctor ID: {}", doctorId);
            throw new NotValidSpecialtyException("SpecialtySet can't be null");
        }
//        for (Specialty specialtyIterator: specialty){
//            if(specialtyIterator == null){
//                LOGGER.debug("There is a null specialty. A specialty can't be null");
//                throw new NotValidSpecialtyException("One Specialty is null. Specialty can't be null");
//            }
//            if (specialtyIterator.length() == 0){
//                LOGGER.debug("There is a specialty with 0 characters. A specialty can't be empty");
//                throw new NotValidSpecialtyException("Specialty can't be empty");
//            }
//
//            if (specialtyIterator.length() > 50){
//                LOGGER.debug("The specialty can't have more than 50 characters. Specialty Name: {}", specialty);
//                throw new NotValidSpecialtyException("Specialty can't have more than 50 characters");
//            }
//        }
        LOGGER.debug("Set specilty for Doctor with ID: {}", doctorId);
        doctor.setSpecialties(specialty);

        List<Specialty> specialties = specialtyDao.findSpecialties(specialty);

//        if (!specialty.isPresent()){
//            LOGGER.debug("Specialty: {} not recognized. Is not in the list", specialty);
//            throw new NotValidSpecialtyException("Not know specialty.");
//        } else {
//            LOGGER.debug("Add doctor Specialty List to Doctor with ID: {}", doctorId);
//            doctorSpecialtyDao.addDoctorSpecialtyList(doctor.getId(),specialty.get());
//        }
//
        if (workingHours == null){
            LOGGER.debug("The Working Hours of a Doctor can't be null. Doctor ID: {}", doctorId);
            throw new NotValidWorkingHourException("The Working Hours list can't be null");
        }
        for (WorkingHours wh: workingHours){
            if (wh == null){
                LOGGER.debug("The Working Hour list contains a null working hour. Day: {}", wh.getDayOfWeek());
                throw new NotValidWorkingHourException("The Working Hours list contains a null working hour");
            }
            if (wh.getFinishTime() == null){
                LOGGER.debug("The Finish Time on {} is null. There can't be a null finish time", wh.getDayOfWeek());
                throw new NotValidWorkingHourException("Finish Time from a Working Hour is null");
            }
            if (wh.getStartTime() == null) {
                LOGGER.debug("The Start Time on {} is null. There can't be a null starting time", wh.getDayOfWeek());
                throw new NotValidWorkingHourException("StartTime from a Working Hour is null");
            }
            if (wh.getDayOfWeek() == null){
                LOGGER.debug("There is a null DayOfWeek. The working hour is {}", wh);
                throw new NotValidWorkingHourException("DayOfWeek from a working hour is null");
            }
        }
        LOGGER.debug("Set working hours to Doctor with ID: {}", doctorId);
        //doctor.setWorkingHours(workingHours);
        LOGGER.debug("Add working hours to DAO");
        LOGGER.debug("Doctor with ID: {}", doctor.getId());
        LOGGER.debug("Doctor's working hours {}", workingHours);
        //workingHoursDao.addWorkingHour( workingHours);

//        if (insurance == null){
//            LOGGER.debug("The insurance map of the doctor with ID: {} is null", doctorId);
//            throw new NotValidInsuranceException("Insurance map can't me null");
//        }

//        for (Insurance i : insurances){
//            if (key == null){
//                LOGGER.debug("The is a null insurance value for the Doctor with ID: {}", doctorId);
//                throw new NotValidInsuranceException("There is a null Insurance");
//            }
//            if (key.length() == 0){
//                LOGGER.debug("There is an empty insurance. Doctor with ID: {}", doctorId);
//                throw new NotValidInsuranceException("Insurance can't be empty");
//            }
//            if (key.length() > 100){
//                LOGGER.debug("Doctor with ID: {}", doctorId);
//                LOGGER.debug("An insurance can't have more than 100 characters. Insurance given: {}", insurance);
//                throw new NotValidInsuranceException("Insurance name can't have more than 100 characters");
//            }
//            if (insurance.get(key) == null){
//                LOGGER.debug("The doctor with ID {} has a null insurance set", doctorId);
//                throw new NotValidInsuranceException("There is a null Insurace Set");
//            }
//            for (String insuranceplan :  insurance.get(key)){
//                if (insuranceplan == null){
//                    LOGGER.debug("There is a null insurance plan in {}", key);
//                    throw new NotValidInsurancePlanException("There is an null Insurance Plan in " + key);
//                }
//                if (insuranceplan.length() == 0){
//                    LOGGER.debug("There can't be an Insurance Plan with 0 characters. Doctor ID: {}", doctorId);
//                    throw new NotValidInsurancePlanException("InsurancePlanName can't be empty");
//                }
//                if (insuranceplan.length() > 50){
//                    LOGGER.debug("The Doctor ID is: {}", doctorId);
//                    LOGGER.debug("There can't be an Insurance Plan with more than 50 characters. The insurance given is: {}", insuranceplan);
//                    throw new NotValidInsurancePlanException("InsurancePlanName can't have more than 50 characters");
//                }
//            }
//        }

        LOGGER.debug("Get insurance plan ids");
        List<InsurancePlan> insurancePlans = insurancePlanDao.getInsurancePlansFromAllInsurances(insurances);


//        if (!insurancesPlan.isPresent()) {
//            LOGGER.debug("There is an unknown insurance plan: {}", insurancesPlanIds);
//            throw new NotValidInsurancePlanException("There is a not known InsurancesPlan");
//        }else{
//            LOGGER.debug("Add medical care to Dao");
//            LOGGER.debug("Insurance Plan IDs: {}", insurancesPlanIds.get());
//            medicalcareDao.addMedicalCare(doctor.getId(), insurancesPlanIds.get());
//        }
//        LOGGER.debug("Set insurance to doctor. Insurance: {}", insurance);
        doctor.setInsurancePlans(insurancePlans);


        if (description == null) {
            LOGGER.debug("The Doctor description can't be null");
            throw new NotValidDescriptionException("Doctor description can't be null");
        }
        if (description.getCertificate() == null){
            LOGGER.debug("The Doctor certificate can't be null");
            throw new NotValidCertificateException("Certificate can't be null");
        }
        if (description.getCertificate().length() > 250){
            LOGGER.debug("The Certificate can't have more than 250 characters. Certificate: {}", description.getCertificate());
            throw new NotValidCertificateException("Certificate can't have more than 250 characters");
        }
        if (!InputValidation.validUTF8(description.getCertificate().getBytes())){
            LOGGER.debug("The first name is not a valid UTF8");
            throw new NotValidCertificateException();
        }

        if (description.getLanguages() == null){
            LOGGER.debug("The Languages of a Doctor can't be null");
            throw new NotValidLanguagesException("Languages can't be null");
        }

        int size = 0;
//        for (String language : description.getLanguages()){
//           if (language == null) {
//               LOGGER.debug("A null language was found in the Description");
//               throw new NotValidLanguagesException("There is a null language");
//           }
//
//           if (language.length() == 0){
//               LOGGER.debug("A language with 0 characters was found in the Description");
//               throw new NotValidLanguagesException("A language can't be empty");
//           }
//            size = size + language.length();
//           LOGGER.debug("Language: {}", language);
//        }
        if (size > 100) {
            LOGGER.debug("A language with more than 100 can't exists");
            throw new NotValidLanguagesException("Languages size count can't have more than 100 characters");
        }

        if (description.getEducation() == null){
            LOGGER.debug("The Education of a Doctor can't be null");
            throw new NotValidEducationException("Education can't be null");
        }
        if (description.getEducation().length() > 250){
            LOGGER.debug("The Education of a Doctor can't have more than 250 characters. Education: {}", description.getEducation());
            throw new NotValidEducationException("Education can't have more than 250 characters");
        }
        if (!InputValidation.validUTF8(description.getEducation().getBytes())){
            LOGGER.debug("The first name is not a valid UTF8");
            throw new NotValidEducationException();
        }
        LOGGER.debug("Set descrption for doctor with ID: {}", doctorId);
        doctor.setDescription(description);
        LOGGER.debug("Set the description in the DB for doctor with ID: {}", doctorId);
        descriptionDao.createDescription(description.getCertificate(), description.getLanguages(), description.getEducation());
        return Optional.ofNullable(doctor);
    }

    @Override
    @Transactional
    public Optional<Doctor> setDoctorSpecialty(Doctor doctor, Set<Specialty> specialty){
        LOGGER.debug("setDoctorSpecialty");
        doctorDao.setDoctorSpecialty(doctor, specialty);
        return Optional.ofNullable(doctor);
    }

    @Override
    @Transactional
    public Optional<Doctor> setDoctorAvatar(Doctor doctor, byte[] avatar) {
        LOGGER.debug("setDoctorAvatar");
        doctorDao.setDoctorAvatar(doctor, avatar);
        return Optional.ofNullable(doctor);
    }

    @Override
    @Transactional
    public Optional<Doctor> setDoctorInsurancePlans(Doctor doctor,  List<InsurancePlan> insurancePlans) {
        doctorDao.setDoctorInsurances(doctor, insurancePlans);
        return Optional.ofNullable(doctor);
    }

//    @Override
//    public Optional<Doctor> setDoctorInsurance(Integer doctorId,  Set<Insurance> insurances){
//        LOGGER.debug("setDoctorInsurance");
//        Optional<Doctor> doctorOptional = doctorDao.findDoctorById(doctorId);
//        Doctor doctor = doctorOptional.get();
//        List<InsurancePlan> insurancesPlans = insurancePlanDao.getInsurancePlansFromAllInsurances(insurances);
//        medicalcareDao.addMedicalCare(doctor.getId(), insurancesPlan);
//        doctor.setInsurance(insurance);
//        return Optional.ofNullable(doctor);
//    }

    @Override
    @Transactional
    public Optional<Doctor> setWorkingHours(Doctor doctor, List<WorkingHours> workingHours){
        LOGGER.debug("setWorkingHours");
        doctorDao.setWorkingHours(doctor, workingHours);
        return Optional.ofNullable(doctor);
    }

}

