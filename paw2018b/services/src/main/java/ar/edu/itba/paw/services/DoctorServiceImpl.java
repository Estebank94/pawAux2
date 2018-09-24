package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.*;
import ar.edu.itba.paw.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

@Service("DoctorServiceImpl")
public class DoctorServiceImpl implements DoctorService {

    @Autowired
    private DoctorDao doctorDao;

//    @Autowired
//    private SpecialtyDao specialtyDao;
//
//    @Autowired
//    private MedicalCareDao medicalcareDao;
//
//    @Autowired
//    private DoctorSpecialtyDao doctorSpecialtyDao;
//
//    @Autowired
//    private InsurancePlanDao insurancePlanDao;
//
//    @Autowired
//    private DescriptionDao descriptionDao;
//
//    @Autowired
//    private WorkingHoursDao workingHoursDao;

    @Override
    public Optional<CompressedSearch> listDoctors() {
        return doctorDao.listDoctors();
    }

    @Override
    public Optional<CompressedSearch> findDoctors(Search search) {
        return doctorDao.findDoctors(search);
    }

    @Override
    public Optional<Doctor> findDoctorById(Integer id){
        return doctorDao.findDoctorById(id);
    }

    @Override
    public Doctor createDoctor(String firstName, String lastName, String phoneNumber, String sex, String licence,
                               String avatar, String address){

        Doctor doctor = doctorDao.createDoctor(firstName, lastName, phoneNumber, sex, licence, avatar, address);
        return doctor;

    }

//    @Override
//    public Doctor createDoctor(String firstName, String lastName, String sex, String address,
//                               String avatar, Set<String> specialty, Map<String, Set<String>> insurance,
//                               List<WorkingHours> workingHours, Description description, String phoneNumber, String licence){
//        //TODO: ACA VA TODO A AGREGAR A LA BASE DE DATOS
//
//        Doctor doctor = doctorDao.createDoctor(firstName, lastName, phoneNumber, sex, licence, avatar, address);
//
//
//        doctor.setSpecialty(specialty);
//
//
////        doctor.setWorkingHours(workingHours);
//        doctor.setInsurance(insurance);
//        doctor.setDescription(description);
//
//        Optional<List<Integer>> specialtysId = specialtyDao.findSpecialtysId(specialty);
//        if (specialtysId.isPresent()){
//            doctorSpecialtyDao.addDoctorSpecialtyList(doctor.getId(),specialtysId.get());
//        }
//
//        Optional<List<Integer>> insurancesPlanIds = insurancePlanDao.getInsurancesPlanIds(insurance);
//        if (insurancesPlanIds.isPresent()){
//            medicalcareDao.addMedicalCare(doctor.getId(), insurancesPlanIds.get());
//        }
//
//        descriptionDao.addDescription(doctor.getId(), description);
//        workingHoursDao.addWorkingHour(doctor.getId(), workingHours);

        /*
         * Del set de Specialtys Obtengo un List de Integers de specialtysId
         * Y agrego una tupla a doctorSpecialty con el id de Doctor y el id de Specialty
         *
         * Del list de working Hours agrego tuplas a la tabla working hours con el Id del medico
         *
         * Agrego tupla a la tabla Information con el Id de Doctor
         *
         * Del map <String, Set<String>> insurance, consigo un List<Integers> con los id de Insurances Plans
         * Por cada Integer agrego una tupla a medicalCare con el doctorId y el insurancePlanId,
         *
         *
         */

//        return doctor;
//    }




}

