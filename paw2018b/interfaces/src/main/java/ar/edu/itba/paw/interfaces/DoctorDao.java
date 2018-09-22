package ar.edu.itba.paw.interfaces;

import ar.edu.itba.paw.models.*;
import ar.edu.itba.paw.models.workingHours;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public interface DoctorDao {

    Optional<CompressedSearch> listDoctors();

    Optional<CompressedSearch> findDoctors(Search search);

    Optional<Doctor> findDoctorById(Integer id);

    Optional<Doctor> createDoctor(String firstName, String lastName, String sex, String address, String address,
                                  String avatar, Set<String> specialty, Map<String, Set<String>> insurance,
                                  List<WorkingHours> workingHours, Description description, String phonenumber, String licence);
}
