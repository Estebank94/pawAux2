package ar.edu.itba.paw.interfaces;

import ar.edu.itba.paw.models.Doctor;
import ar.edu.itba.paw.models.Search;

import java.util.List;
import java.util.Optional;

public interface DoctorService {

   Optional<List<Doctor>> listDoctors();

   Optional<List<Doctor>> findDoctors(Search search);

}
