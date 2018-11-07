package ar.edu.itba.paw.interfaces;

import ar.edu.itba.paw.models.Appointment;
import ar.edu.itba.paw.models.Doctor;
import ar.edu.itba.paw.models.Patient;
import ar.edu.itba.paw.models.exceptions.RepeatedAppointmentException;

import javax.swing.text.html.Option;
import java.sql.SQLIntegrityConstraintViolationException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

public interface AppointmentDao {
    Appointment createAppointment(String appointmentDay, String appointmentTime, Patient patient, Doctor doctor) throws Exception;

    void cancelAppointment(Appointment appointment);

    Optional<Appointment> findAppointment(String appointmentDay, String appointmentTime, Patient patient, Doctor doctor) throws Exception;

    void undoCancelAppointment(Appointment appointment);
}
