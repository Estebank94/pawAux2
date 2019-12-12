package ar.edu.itba.paw.interfaces.persistance;

import ar.edu.itba.paw.models.Appointment;
import ar.edu.itba.paw.models.Doctor;
import ar.edu.itba.paw.models.Patient;
import ar.edu.itba.paw.models.exceptions.RepeatedAppointmentException;

import javax.swing.text.html.Option;
import java.sql.SQLIntegrityConstraintViolationException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

public interface AppointmentDao {
    Appointment createAppointment(String appointmentDay, String appointmentTime, Patient patient, Doctor doctor) throws Exception;

    Appointment cancelAppointment(Appointment appointment);

    Optional<Appointment> findAppointment(String appointmentDay, String appointmentTime, Patient patient, Doctor doctor) throws Exception;

    Optional<Appointment> findAppointmentByDoctor (String appointmentDay, String appointmentTime, Doctor doctor) throws Exception;

    Optional<Appointment> findActiveAppointment(String appointmentDay, String appointmentTime, Doctor doctor, Patient patient) throws Exception;

    List<Appointment> findAppointmentByTime(String appointmentDay, String appointmentTime, Doctor doctor)throws Exception;

    List<Appointment> findAppointmentByPatient(String appointmentDay, String appointmentTime, Patient patient);

    Appointment undoCancelAppointment(Appointment appointment);

    Optional<Appointment> findAppointmentById(Integer id);

    List<Appointment> getHistoricalAppointmentsWithDoctor(Patient patient, Doctor doctor);
}
