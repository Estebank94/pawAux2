package ar.edu.itba.paw.interfaces;

import ar.edu.itba.paw.models.Appointment;
import ar.edu.itba.paw.models.exceptions.RepeatedAppointmentException;

import javax.swing.text.html.Option;
import java.sql.SQLIntegrityConstraintViolationException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

public interface AppointmentDao {
    Appointment createAppointment(LocalDate appointmentDay, LocalTime appointmentTime) throws Exception;

}
