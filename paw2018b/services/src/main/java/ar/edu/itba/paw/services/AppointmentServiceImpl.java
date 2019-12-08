package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.persistance.AppointmentDao;
import ar.edu.itba.paw.interfaces.persistance.DoctorDao;
import ar.edu.itba.paw.interfaces.services.AppointmentService;
import ar.edu.itba.paw.models.Appointment;
import ar.edu.itba.paw.models.Doctor;
import ar.edu.itba.paw.models.Patient;
import ar.edu.itba.paw.models.exceptions.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.NoResultException;
import java.sql.SQLException;
import java.util.Optional;

@Service
public class AppointmentServiceImpl implements AppointmentService {
    @Autowired
    private AppointmentDao appointmentDao;

    @Autowired
    private DoctorDao doctorDao;

    private static final Logger LOGGER = LoggerFactory.getLogger(DoctorServiceImpl.class);

    @Transactional/*(rollbackFor= SQLException.class)*/
    @Override
    public Appointment createAppointment(String appointmentDay, String appointmentTime, Patient patient, Doctor doctor) throws RepeatedAppointmentException, NotCreatedAppointmentException, NotValidDoctorIdException, NotValidAppointmentDayException, NotValidAppointmentTimeException, NotFoundDoctorException, NotValidPatientIdException {
        LOGGER.debug("AppointmentServiceImpl: CreateAppointment");

        Appointment appointment = null;
        Optional<Appointment> repeated = Optional.empty();
        Optional<Appointment> app = Optional.empty();
        try {
            repeated = appointmentDao.findAppointmentByDoctor(appointmentDay, appointmentTime, doctor);
        } catch (Exception e){
            LOGGER.debug("No appointment");
        }
        if (repeated.isPresent()){
            if (repeated.get().getPatient().getId() == patient.getId()){
                LOGGER.debug("Appointment already Exists by the patient");
                throw new RepeatedAppointmentException("Appointment already exits by the patient");
            }
            LOGGER.debug("Appointment already Exists by the another patient");
            throw new RepeatedAppointmentException("Appointment already Exists by the another patient");
        }

        try{
            app = appointmentDao.findAppointment(appointmentDay, appointmentTime, patient, doctor);
        }catch (Exception e){
            LOGGER.debug("No appointment");
        }
        if (app.isPresent()){
            if (app.get().getAppointmentCancelled()){
                LOGGER.debug("Appointment exits but was cancelled. Undoing cancel");
                appointmentDao.undoCancelAppointment(app.get());
                return app.get();
            }
        }

        try{
            LOGGER.debug("Creating Appointment");
           appointment =  appointmentDao.createAppointment(appointmentDay, appointmentTime, patient, doctor);
        } catch (RepeatedAppointmentException e) {
            LOGGER.debug("Appointment already Exists");
            throw new RepeatedAppointmentException("Appointment already exits");
        } catch (Exception e) {
            throw new NotCreatedAppointmentException();
        }
        LOGGER.debug("Appointment create with id {}", appointment.getId());
        return appointment;
    }

    @Transactional(rollbackFor = SQLException.class)
    @Override
    public Boolean cancelAppointment(String appointmentDay, String appointmentTime, Patient patient, Doctor doctor) throws NotFoundAppointmentException, NotValidCancelAppointment {
        LOGGER.debug("AppointmentServiceImpl: cancelAppointment");
        Boolean ans = true;

        Appointment appointment = null;
        Optional<Appointment> app = Optional.empty();
        try {
            app = appointmentDao.findAppointment(appointmentDay, appointmentTime, patient, doctor);
        } catch (Exception e){
            LOGGER.debug("Appointment not found. An error occurs");
            throw new NotFoundAppointmentException();
        }

        if (app.isPresent()){
            if (!app.get().getAppointmentCancelled()){
                LOGGER.debug("Cancelling appointment. ");
                try {
                    appointmentDao.cancelAppointment(app.get());
                    return true;
                } catch (Exception e){
                    LOGGER.debug("An error while cancelling appointment");
                }

            } else {
                LOGGER.debug("Appointment already cancelled");
                throw new NotValidCancelAppointment("Appointment already cancelled");
            }
        }
        throw new  NotValidCancelAppointment("Appointment not found");
    }

    @Override
    public Appointment findAppointmentById(Integer id) throws NotFoundAppointmentException {
        LOGGER.debug("Find appointment by id with id {}", id);
        Optional<Appointment> appointmentOptional = appointmentDao.findAppointmentById(id);
        if (!appointmentOptional.isPresent()){
            LOGGER.debug("Appointment with id {} not found", id);
            throw new NotFoundAppointmentException("Appointment not found");
        }
        return appointmentOptional.get();
    }
}
