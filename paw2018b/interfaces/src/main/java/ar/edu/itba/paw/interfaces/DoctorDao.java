package ar.edu.itba.paw.interfaces;

import ar.edu.itba.paw.models.CompressedSearch;
import ar.edu.itba.paw.models.Doctor;
import ar.edu.itba.paw.models.Search;

import java.util.List;
import java.util.Optional;

public interface DoctorDao {

    Optional<CompressedSearch> listDoctors();

    Optional<CompressedSearch> findDoctors(Search search);

    Doctor findDoctorById(Integer id);
}
