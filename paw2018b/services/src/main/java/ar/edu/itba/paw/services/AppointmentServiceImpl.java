package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.AppointmentDao;
import ar.edu.itba.paw.interfaces.AppointmentService;
import ar.edu.itba.paw.models.Appointment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

@Service
public class AppointmentServiceImpl implements AppointmentService {
    @Autowired
    private AppointmentDao appointmentDao;

    @Override
    public Optional<Appointment> createAppointment(Integer doctorId, Integer clientId, LocalDate appointmentDay, LocalTime appointmentTime) {
        return appointmentDao.createAppointment(doctorId, clientId, appointmentDay, appointmentTime);
    }
}
