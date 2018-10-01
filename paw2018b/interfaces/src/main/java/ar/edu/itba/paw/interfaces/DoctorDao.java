package ar.edu.itba.paw.interfaces;

import ar.edu.itba.paw.models.Appointment;
import ar.edu.itba.paw.models.CompressedSearch;
import ar.edu.itba.paw.models.Doctor;
import ar.edu.itba.paw.models.Search;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface DoctorDao {

    Optional<CompressedSearch> listDoctors();

    Optional<CompressedSearch> findDoctors(Search search);

    Optional<Doctor> findDoctorById(Integer id);

    Doctor createDoctor(String firstName, String lastName, String phoneNumber, String sex, String licence,
                               String avatar, String address);

    Boolean isAnExistingLicence(Integer licence);
}
