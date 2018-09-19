package ar.edu.itba.paw.interfaces;

import ar.edu.itba.paw.models.CompressedSearch;
import ar.edu.itba.paw.models.Doctor;
import ar.edu.itba.paw.models.Search;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

public interface DoctorService {

   Optional<CompressedSearch> listDoctors();

   Optional<CompressedSearch> findDoctors(Search search);

   Optional<Doctor> findDoctorById(Integer id);

}
