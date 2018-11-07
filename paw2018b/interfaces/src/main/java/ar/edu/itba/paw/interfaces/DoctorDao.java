package ar.edu.itba.paw.interfaces;

import ar.edu.itba.paw.models.*;
import ar.edu.itba.paw.models.exceptions.NotCreateDoctorException;
import ar.edu.itba.paw.models.exceptions.RepeatedLicenceException;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface DoctorDao {

    int getLastPage();

    Boolean setDoctorAvatar(Doctor doctor, byte[] avatar);

    List<Doctor> listDoctors(int page);

    List<Doctor> listDoctors(Search search, int page);

    Optional<Doctor> findDoctorById(Integer id);

    Doctor createDoctor(String firstName, String lastName, String phoneNumber, String sex, Integer licence,
                               byte[] avatar, String address) throws RepeatedLicenceException, NotCreateDoctorException;

    Boolean isAnExistingLicence(Integer licence);

    Boolean setDoctorSpecialty(Doctor doctor, Set<Specialty> specialty);

    Boolean setWorkingHours(Doctor doctor, List<WorkingHours> workingHours);

    Boolean setDoctorInsurances(Doctor doctor, List<InsurancePlan> insurancePlans);
}
