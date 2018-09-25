package ar.edu.itba.paw.interfaces;

import ar.edu.itba.paw.models.*;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public interface DoctorService {

   Optional<CompressedSearch> listDoctors();

   Optional<CompressedSearch> findDoctors(Search search);

   Optional<Doctor> findDoctorById(Integer id);

//   Doctor createDoctor(String firstName, String lastName, String sex, String address,
//                       String avatar, Set<String> specialty, Map<String, Set<String>> insurance,
//                       List<WorkingHours> workingHours, Description description, String phoneNumber, String licence);

   Doctor createDoctor(String firstName, String lastName, String phoneNumber, String sex, String licence,
                              String avatar, String address);

   Optional<Doctor> setDoctorInfo(Integer doctorId, Set<String> specialty, Map<String, Set<String>> insurance,
                      List<WorkingHours> workingHours, Description description);
}
