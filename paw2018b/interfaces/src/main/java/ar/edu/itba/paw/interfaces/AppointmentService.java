package ar.edu.itba.paw.interfaces;

import ar.edu.itba.paw.models.Appointment;
import ar.edu.itba.paw.models.Doctor;
import ar.edu.itba.paw.models.Patient;
import ar.edu.itba.paw.models.exceptions.*;

import javax.print.Doc;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

public interface AppointmentService {
    Appointment createAppointment(String appointmentDay, String appointmentTime, Patient patient, Doctor doctor) throws RepeatedAppointmentException, NotCreatedAppointmentException, NotValidDoctorIdException, NotValidAppointmentDayException, NotValidAppointmentTimeException, NotFoundDoctorException, NotValidPatientIdException;

    Boolean cancelAppointment(String appointmentDay, String appointmentTime, Patient patient, Doctor doctor);
}
