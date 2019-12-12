package ar.edu.itba.paw.interfaces.services;

import ar.edu.itba.paw.models.Appointment;
import ar.edu.itba.paw.models.Doctor;
import ar.edu.itba.paw.models.Patient;
import ar.edu.itba.paw.models.exceptions.*;

import javax.print.Doc;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

public interface AppointmentService {
    Appointment createAppointment(String appointmentDay, String appointmentTime, Patient patient, Doctor doctor) throws RepeatedAppointmentException, NotCreatedAppointmentException, NotValidDoctorIdException, NotValidAppointmentDayException, NotValidAppointmentTimeException, NotFoundDoctorException, NotValidPatientIdException, NotValidAppointment;

    Appointment cancelAppointment(String appointmentDay, String appointmentTime, Patient patient, Doctor doctor) throws NotFoundAppointmentException, NotValidCancelAppointment;

    Appointment findAppointmentById(Integer id) throws NotFoundAppointmentException;

    List<Appointment> findPastSharedAppointments(Doctor doctor, Patient patient);
}
