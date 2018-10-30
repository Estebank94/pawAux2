package ar.edu.itba.paw.interfaces;

import ar.edu.itba.paw.models.Doctor;
import ar.edu.itba.paw.models.Specialty;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface SpecialtyDao {


//    <List<Integer>> findSpecialtysId(Set<String> specialtySet);

    List<Specialty> findSpecialties(Set<Specialty> specialties);

    Specialty findSpecialty(Specialty specialty);

    Specialty findSpecialtyByName(String specialty);

    void addDoctorSpecialty(Doctor doctor, Specialty specialty);


}
