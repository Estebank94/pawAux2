package ar.edu.itba.paw.interfaces.persistance;

import ar.edu.itba.paw.models.*;
import ar.edu.itba.paw.models.exceptions.NotCreateDoctorException;
import ar.edu.itba.paw.models.exceptions.RepeatedLicenceException;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface DoctorDao {

    Long getLastPage(Search search, int pageSize);

    Long getLastPage();

    Boolean setDoctorAvatar(Doctor doctor, byte[] avatar);

    List<Doctor> listDoctors(int page);

    List<Doctor> listDoctors();

    List<Doctor> listDoctors(Search search, int page, int pageSize);

    Optional<Doctor> findDoctorById(Integer id);

    Doctor createDoctor(String firstName, String lastName, String phoneNumber, String sex, Integer licence,
                               byte[] avatar, String address) throws RepeatedLicenceException, NotCreateDoctorException;

    Boolean isAnExistingLicence(Integer licence);

    Boolean setDoctorSpecialty(Doctor doctor, Set<Specialty> specialty);

    Boolean setWorkingHours(Doctor doctor, List<WorkingHours> workingHours);

    Boolean setDoctorInsurances(Doctor doctor, List<InsurancePlan> insurancePlans);

    Boolean setDoctorDescription(Doctor doctor, Description description);

    Optional<Description> findDescriptionByDoctor(Doctor doctor);

    boolean mergeDoctorDescription(Description original, Description toMerge);

    void mergeDoctor(Doctor doctor);

    List<Appointment> getFutureAppointments(Doctor doctor);

    List<Appointment> getHistoricalAppointments(Doctor doctor);

    List<Review> getReviews(Doctor doctor);

    void createReview(Doctor doctor, Patient patient, Review review);
}
