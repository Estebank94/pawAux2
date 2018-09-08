package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.DoctorDao;
import ar.edu.itba.paw.interfaces.DoctorService;
import ar.edu.itba.paw.models.CompressedSearch;
import ar.edu.itba.paw.models.Doctor;
import ar.edu.itba.paw.models.Search;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

}

