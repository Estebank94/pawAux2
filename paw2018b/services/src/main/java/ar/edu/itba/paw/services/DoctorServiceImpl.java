package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.*;
import ar.edu.itba.paw.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.acls.model.NotFoundException;
import org.springframework.stereotype.Service;

import java.security.acl.LastOwnerException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

@Service("DoctorServiceImpl")
public class DoctorServiceImpl implements DoctorService {

    @Autowired
    private DoctorDao doctorDao;
    
    @Autowired
    private SpecialtyDao specialtyDao;

    @Autowired
    private MedicalCareDao medicalcareDao;

    @Autowired
    private DoctorSpecialtyDao doctorSpecialtyDao;

    @Autowired
    private InsurancePlanDao insurancePlanDao;

    @Autowired
    private DescriptionDao descriptionDao;

    @Autowired
    private WorkingHoursDao workingHoursDao;

    @Override
    public Optional<CompressedSearch> listDoctors() {
        return doctorDao.listDoctors();
    }

    @Override
    public Optional<CompressedSearch> findDoctors(Search search) {
        if (search == null){
            throw new IllegalArgumentException("Search can't be null");
        }
        return doctorDao.findDoctors(search);
    }

    @Override
    public Optional<Doctor> findDoctorById(Integer id){
        if (id == null ){
            throw new IllegalArgumentException("Doctor Id can't be null");
        }

        if (id < 0){
            throw new IllegalArgumentException("Doctor Id can't be negative");
        }

        Optional<Doctor> thisdoctor =  doctorDao.findDoctorById(id);

        if (!thisdoctor.isPresent()){
            throw new NotFoundException("Doctor doesn't exist");
        }
        return thisdoctor;
    }


    @Override
    public Doctor createDoctor(String firstName, String lastName, String phoneNumber, String sex, String licence,
                               String avatar, String address) {

        if (firstName == null){
            throw new IllegalArgumentException("firstName can't be null");
        }

        if (firstName.length() == 0){
            throw new IllegalArgumentException("Doctor firstname can't be empty");
        }

        if (firstName.length() > 45){
            throw new IllegalArgumentException("Doctor firstname maxlength is 50");
        }

        if (lastName == null) {
            throw new IllegalArgumentException("last name can't be null");
        }
        if (lastName.length() == 0){
            throw new IllegalArgumentException("Doctor firstname can't be empty");
        }

        if (lastName.length() > 45){
            throw new IllegalArgumentException("Doctor firstname maxlength is 50");
        }

        if (phoneNumber == null) {
            throw new IllegalArgumentException("phonenumber can't be null");
        }
        if (phoneNumber.length() == 0){
            throw new IllegalArgumentException("phonenumber firstname can't be empty");
        }

        if (phoneNumber.length() > 20){
            throw new IllegalArgumentException("phonenumber can't have more than 20 characters");
        }

        if (sex == null) {
            throw new IllegalArgumentException("sex can't be null");
        }
        if (sex.length() == 0){
            throw new IllegalArgumentException("sex can't be empty");
        }

        if (sex.length() > 1){
            throw new IllegalArgumentException("sex can't have more than 1 characters");
        }

        if (licence == null) {
            throw new IllegalArgumentException("licence can't be null");
        }
        if (licence.length() == 0){
            throw new IllegalArgumentException("licence can't be empty");
        }

        if (licence.length() > 10){
            throw new IllegalArgumentException("licence can't have more than 10 characters");
        }

        if (address == null) {
            throw new IllegalArgumentException("address can't be null");
        }
        if (address.length() == 0){
            throw new IllegalArgumentException("address can't be empty");
        }

        if (address.length() > 100){
            throw new IllegalArgumentException("address can't have more than 100 characters");
        }

        Doctor doctor = doctorDao.createDoctor(firstName, lastName, phoneNumber, sex, licence, avatar, address);
        return doctor;
    }

    @Override
    public Optional<Doctor> setDoctorInfo(Integer doctorId, Set<String> specialty, Map<String, Set<String>> insurance, List<WorkingHours> workingHours, Description description) {

        if (doctorId == null ){
            throw new IllegalArgumentException("Doctor Id can't be null");
        }

        if (doctorId < 0){
            throw new IllegalArgumentException("Doctor Id can't be negative");
        }

        Optional<Doctor> doctorOptional = doctorDao.findDoctorById(doctorId);

        if (!doctorOptional.isPresent()){
            throw new IllegalArgumentException("Doctor doesn't exist");
        }

        Doctor doctor = doctorOptional.get();

        if (specialty == null){
            throw new IllegalArgumentException("SpecialtySet can't be null");
        }
        for (String specialtyIterator: specialty){
            if(specialtyIterator == null){
                throw new IllegalArgumentException("One Specialty is null. Specialty can't be null");
            }
            if (specialtyIterator.length() == 0){
                throw new IllegalArgumentException("Specialty can't be empty");
            }

            if (specialtyIterator.length() > 50){
                throw new IllegalArgumentException("Specialty can't have more than 50 characters");
            }
        }
        doctor.setSpecialty(specialty);

        Optional<List<Integer>> specialtysId = specialtyDao.findSpecialtysId(specialty);
        if (!specialtysId.isPresent()){
            throw new IllegalArgumentException("Not know specialty.");
        } else {
            doctorSpecialtyDao.addDoctorSpecialtyList(doctor.getId(),specialtysId.get());
        }

        if (workingHours == null){
            throw new IllegalArgumentException("The Working Hours list can't be null");
        }
        for (WorkingHours wh: workingHours){
            if (wh == null){
                throw new IllegalArgumentException("The Working Hours list contains a null working hour");
            }
            if (wh.getFinishTime() == null){
                throw new IllegalArgumentException("Finish Time from a Working Hour is null");
            }
            if (wh.getStartTime() == null) {
                throw new IllegalArgumentException("StartTime from a Working Hour is null");
            }
            if (wh.getDayOfWeek() == null){
                throw new IllegalArgumentException("DayOfWeek from a working hour is null");
            }
        }
        doctor.setWorkingHours(workingHours);
        workingHoursDao.addWorkingHour(doctor.getId(), workingHours);

        if (insurance == null){
            throw new IllegalArgumentException("Insurance map can't me null");
        }

        for (String key: insurance.keySet()){
            if (key == null){
                throw new IllegalArgumentException("There is a null Insurance");
            }
            if (key.length() == 0){
                throw new IllegalArgumentException("Insurance can't be empty");
            }
            if (key.length() > 100){
                throw new IllegalArgumentException("Insurance name can't have more than 100 characters");
            }
            if (insurance.get(key) == null){
                throw new IllegalArgumentException("There is a null Insurace Set");
            }
            for (String insuranceplan :  insurance.get(key)){
                if (insuranceplan == null){
                    throw new IllegalArgumentException("There is an null Insurance Plan in " + key);
                }
                if (insuranceplan.length() == 0){
                    throw new IllegalArgumentException("InsurancePlanName can't be empty");
                }
                if (insuranceplan.length() > 50){
                    throw new IllegalArgumentException("InsurancePlanName can't have more than 50 characters");
                }
            }
        }

        Optional<List<Integer>> insurancesPlanIds = insurancePlanDao.getInsurancesPlanIds(insurance);


        if (!insurancesPlanIds.isPresent()) {
            throw new IllegalArgumentException("There is a not known InsurancesPlan");
        }else{
            medicalcareDao.addMedicalCare(doctor.getId(), insurancesPlanIds.get());
        }
        doctor.setInsurance(insurance);


        if (description == null) {
            throw new IllegalArgumentException("Doctor description can't be null");
        }
        if (description.getCertificate() == null){
            throw new IllegalArgumentException("Certificate can't be null");
        }
        if (description.getCertificate().length() > 250){
            throw new IllegalArgumentException("Certificate can't have more than 250 characters");
        }

        if (description.getLanguages() == null){
            throw new IllegalArgumentException("Languages can't be null");
        }

        int size = 0;
        for (String language : description.getLanguages()){
           if (language == null) {
               throw new IllegalArgumentException("There is a null language");
           }

           if (language.length() == 0){
               throw new IllegalArgumentException("A language can't be empty");
           }
            size = size + language.length();
        }
        if (size > 100) {
            throw new IllegalArgumentException("Languages size count can't have more than 100 characters");
        }

        if (description.getEducation() == null){
            throw new IllegalArgumentException("Education can't be null");
        }
        if (description.getEducation().length() > 250){
            throw new IllegalArgumentException("Education can't have more than 250 characters");
        }
        doctor.setDescription(description);
        descriptionDao.addDescription(doctor.getId(), description);
        return Optional.ofNullable(doctor);
    }
}

