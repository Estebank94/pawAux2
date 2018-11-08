package ar.edu.itba.paw.interfaces;

import ar.edu.itba.paw.models.*;
import ar.edu.itba.paw.models.exceptions.*;

import javax.print.Doc;
import javax.swing.text.html.Option;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public interface DoctorService {

   int getLastPage();

   Optional<Doctor> setDoctorAvatar(Doctor doctor, byte[] avatar);

   List<Doctor> listDoctors(int page);

   List<Doctor> listDoctors(Search search, String pageAsString) throws NotValidSearchException, NotValidPageException;

   Optional<Doctor> findDoctorById(Integer id) throws NotFoundDoctorException, NotValidIDException;

//   Doctor createDoctor(String firstName, String lastName, String sex, String address,
//                       String avatar, Set<String> specialty, Map<String, Set<String>> insurance,
//                       List<WorkingHours> workingHours, Description description, String phoneNumber, String licence);

   Doctor createDoctor(String firstName, String lastName, String phoneNumber, String sex, Integer licence,
                              byte[] avatar, String address) throws NotValidFirstNameException, NotValidLastNameException, NotValidPhoneNumberException, NotCreateDoctorException, RepeatedLicenceException, NotValidSexException, NotValidLicenceException, NotValidAddressException;
//
   Optional<Doctor> setDoctorInfo(Integer doctorId, Set<Specialty> specialty, Set<Insurance> insurances, List<WorkingHours> workingHours, Description description) throws NotValidDoctorIdException, NotFoundDoctorException, NotValidSpecialtyException, NotValidWorkingHourException, NotValidInsuranceException, NotValidInsurancePlanException, NotValidDescriptionException, NotValidLanguagesException, NotValidCertificateException, NotValidEducationException;

   Optional<Doctor> setDoctorSpecialty(Doctor doctor, Set<Specialty> specialty);

   Optional<Doctor> setDoctorInsurancePlans(Doctor doctor,  List<InsurancePlan> insurancePlans);

   Optional<Doctor> setWorkingHours(Doctor doctor, List<WorkingHours> workingHours);

}
