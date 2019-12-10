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
import java.util.Collection;
import java.util.Collections;
import java.util.List;
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
    public Appointment createAppointment(String appointmentDay, String appointmentTime, Patient patient, Doctor doctor) throws RepeatedAppointmentException, NotCreatedAppointmentException, NotValidDoctorIdException, NotValidAppointmentDayException, NotValidAppointmentTimeException, NotFoundDoctorException, NotValidPatientIdException, NotValidAppointment {
        LOGGER.debug("AppointmentServiceImpl: CreateAppointment");

        Appointment appointment = null;
        //Optional<Appointment> repeated = Optional.empty();
        //Optional<Appointment> app = Optional.empty();
        List<Appointment> appointmentList = Collections.EMPTY_LIST;
        try {
            appointmentList = appointmentDao.findAppointmentByTime(appointmentDay, appointmentTime, doctor);
            LOGGER.debug("AppointmentList count {}", appointmentList.size());
        } catch (Exception e) {
            LOGGER.debug("No appointment");
            throw new NotValidAppointment("An error occurs while finding appointments");
        }


        for (Appointment ap: appointmentList){
            if (!ap.getAppointmentCancelled()){
                if (ap.getPatient().getId() == patient.getId()){
                    throw new NotValidAppointment("Appointment already exits");
                    //ap.setAppointmentCancelled(Boolean.FALSE);
                    //return ap;
                } else {
                    LOGGER.debug("The doctor has an appointment with another patient at that time");
                    throw new NotValidAppointment("The doctor has an appointment with another patient at that time");
                }
            } else if (ap.getPatient().getId() == patient.getId()){
                LOGGER.debug("Appointment found. Updating cancelled");
                ap.setAppointmentCancelled(Boolean.FALSE);
                return ap;
            }
        }

        LOGGER.debug("There is no active appointment at that dayTime");
        /* If reach this point is because no one has an active appointment at that time*/
        try{
            LOGGER.debug("Creating Appointment");
            appointment =  appointmentDao.createAppointment(appointmentDay, appointmentTime, patient, doctor);
            return appointment;
        } catch (RepeatedAppointmentException e) {
            LOGGER.debug("Appointment already Exists");
            throw new RepeatedAppointmentException("Appointment already exits");
        } catch (Exception e) {
            throw new NotCreatedAppointmentException();
        }

        /*
        try {
            repeated = appointmentDao.findAppointmentByDoctor(appointmentDay, appointmentTime, doctor);
        } catch (Exception e){
            LOGGER.debug("No appointment");
        }
        if (repeated.isPresent()){
            if (repeated.get().getPatient().getId() == patient.getId() ){
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

         */
    }

    @Transactional
    @Override
    public Appointment cancelAppointment(String appointmentDay, String appointmentTime, Patient patient, Doctor doctor) throws NotFoundAppointmentException, NotValidCancelAppointment {
        LOGGER.debug("AppointmentServiceImpl: cancelAppointment");
        Boolean ans = true;

        Appointment appointment = null;
        Optional<Appointment> app = Optional.empty();
        LOGGER.debug("Looking for doctor appointment at {} - {}", appointmentDay, appointmentTime);
        List<Appointment> appointmentList = Collections.EMPTY_LIST;
        try {
             appointmentList = appointmentDao.findAppointmentByTime(appointmentDay, appointmentTime, doctor);
            LOGGER.debug("Found {} appointments", appointmentList.size());
        } catch (Exception e){
            LOGGER.debug("Appointment not found. An error occurs");
            throw new NotFoundAppointmentException();
        }

        LOGGER.debug("Appointment patient {}", appointmentList.get(0).getPatient().getId());
        LOGGER.debug("Appointment id {}", appointmentList.get(0).getId());
        LOGGER.debug("Appointmnet cancelled {}", appointmentList.get(0).getAppointmentCancelled());


        for (Appointment ap: appointmentList){
            if (ap.getPatient().getPatientId() == patient.getId() && !ap.getAppointmentCancelled()){
                LOGGER.debug("Cancelling appointment. ");
                ap.setAppointmentCancelled(Boolean.TRUE);
                return ap;
                //return appointmentDao.cancelAppointment(app.get());
            }
        }
        throw new NotFoundAppointmentException("Appointment not found");
        /*
        try {
            app = appointmentDao.findActiveAppointment(appointmentDay, appointmentTime, doctor, patient);
        } catch (Exception e){
            LOGGER.debug("Appointment not found. An error occurs");
            throw new NotFoundAppointmentException();
        }

        if (app.isPresent()){
            LOGGER.debug("Cancelling appointment. ");
            try {
                appointmentDao.cancelAppointment(app.get());
                return true;
            } catch (Exception e){
                LOGGER.debug("An error while cancelling appointment");
            }
        }
        throw new  NotValidCancelAppointment("Appointment not found");
         */
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
