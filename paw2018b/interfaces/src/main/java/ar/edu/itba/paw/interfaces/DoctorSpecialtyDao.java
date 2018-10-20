package ar.edu.itba.paw.interfaces;

import ar.edu.itba.paw.models.Doctor;
import ar.edu.itba.paw.models.Specialty;

import java.util.List;

public interface DoctorSpecialtyDao {

    void addDoctorSpecialty(Doctor doctor, Specialty specialty);

    void addDoctorSpecialtyList(Doctor doctor, List<Specialty> specialty);

}
