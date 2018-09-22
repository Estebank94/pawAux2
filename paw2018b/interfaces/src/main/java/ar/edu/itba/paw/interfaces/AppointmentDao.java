package ar.edu.itba.paw.interfaces;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

public interface AppointmentDao {
    Optional<Appointment> createAppointment(Integer doctorId, LocalDate appointmentDay, LocalTime appointmentTime);
}
