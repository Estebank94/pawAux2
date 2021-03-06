package ar.edu.itba.paw.interfaces;

import ar.edu.itba.paw.models.Appointment;
import ar.edu.itba.paw.models.CompressedSearch;
import ar.edu.itba.paw.models.Doctor;
import ar.edu.itba.paw.models.Search;
import ar.edu.itba.paw.models.exceptions.NotCreateDoctorException;
import ar.edu.itba.paw.models.exceptions.RepeatedLicenceException;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface DoctorDao {

    Optional<CompressedSearch> listDoctors();

    Optional<CompressedSearch> findDoctors(Search search);

    Boolean setProfilePicture(Integer doctorId, byte[] pixel);

    Optional<Doctor> findDoctorById(Integer id);

    Doctor createDoctor(String firstName, String lastName, String phoneNumber, String sex, String licence,
                               String avatar, String address) throws RepeatedLicenceException, NotCreateDoctorException;

    Boolean isAnExistingLicence(Integer licence);
}
