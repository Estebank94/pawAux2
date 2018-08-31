package ar.edu.itba.paw.interfaces;

import ar.edu.itba.paw.models.Doctor;

import java.util.List;
import java.util.Optional;

public interface DoctorService {

   Optional<List<Doctor>> listDoctors();

}
