package ar.edu.itba.paw.interfaces;

import ar.edu.itba.paw.models.Appointment;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

public interface AppointmentDao {
    Optional<Appointment> createAppointment(Integer doctorId, Integer clientId, LocalDate appointmentDay, LocalTime appointmentTime);

}
