package ar.edu.itba.paw.interfaces;Number

import ar.edu.itba.paw.models.*;
import ar.edu.itba.paw.models.workingHours;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public interface DoctorService {

   Optional<CompressedSearch> listDoctors();

   Optional<CompressedSearch> findDoctors(Search search);

   Optional<Doctor> findDoctorById(Integer id);

   /*
   * Create a new Doctor
   *
   * @param firsName The name of the doctor
   * @param lastName The Last name of the doctor
   * @param phone The phone of the doctor
   * @param sex The sex of the doctor
   * @param location The location of the doctor
   * @param avatar The url of the doctor avatar
   * @param specialty The list of specialties
   *
   * @return Optional<Doctor>
   *
    */
   Optional<Doctor> createDoctor(String firstName, String lastName, String sex, String address, String address,
    String avatar, Set<String> specialty, Map<String, Set<String>> insurance, List<WorkingHours> workingHours, Description description, String phonenumber);

}
