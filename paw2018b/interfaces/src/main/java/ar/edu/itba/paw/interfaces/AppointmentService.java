package ar.edu.itba.paw.interfaces;

import ar.edu.itba.paw.models.Appointment;
import ar.edu.itba.paw.models.exceptions.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

public interface AppointmentService {
    Appointment createAppointment(LocalDate appointmentDay, LocalTime appointmentTime) throws RepeatedAppointmentException, NotCreatedAppointmentException, NotValidDoctorIdException, NotValidAppointmentDayException, NotValidAppointmentTimeException, NotFoundDoctorException, NotValidPatientIdException;

}
