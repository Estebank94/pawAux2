package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.DoctorDao;
import ar.edu.itba.paw.interfaces.DoctorService;
import ar.edu.itba.paw.models.*;
import ar.edu.itba.paw.models.workingHours;
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
    public Doctor createDoctor(String firstName, String lastName, String sex, String address, String address,
                                String avatar, Set<String> specialty, Map<String, Set<String>> insurance,
                               List<WorkingHours> workingHours, Description description, String phonenumber){
        //TODO: ACA VA TODO A AGREGAR A LA BASE DE DATOS
        Doctor doctor = doctorDao.createDoctor(firstName, lastName, phonenumber, sex, licence, avatar, workingHours, address);


        return doctor;
    }

}

