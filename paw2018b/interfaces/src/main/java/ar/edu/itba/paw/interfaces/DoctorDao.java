package ar.edu.itba.paw.interfaces;

import ar.edu.itba.paw.models.*;
import ar.edu.itba.paw.models.exceptions.NotCreateDoctorException;
import ar.edu.itba.paw.models.exceptions.RepeatedLicenceException;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface DoctorDao {


    List<Doctor> listDoctors();

    List<Doctor> listDoctors(Search search);

    Optional<Doctor> findDoctorById(Integer id);

    Doctor createDoctor(String firstName, String lastName, String phoneNumber, String sex, Integer licence,
                               String avatar, String address) throws RepeatedLicenceException, NotCreateDoctorException;

    Boolean isAnExistingLicence(Integer licence);

    Boolean setDoctorSpecialty(Doctor doctor, Set<Specialty> specialty);

    Boolean setWorkingHours(Doctor doctor, List<WorkingHours> workingHours);
}
