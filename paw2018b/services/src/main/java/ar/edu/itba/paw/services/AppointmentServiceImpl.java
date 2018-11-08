package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.*;
import ar.edu.itba.paw.models.Appointment;
import ar.edu.itba.paw.models.Doctor;
import ar.edu.itba.paw.models.Patient;
import ar.edu.itba.paw.models.WorkingHours;
import ar.edu.itba.paw.models.exceptions.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.NoResultException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Service
public class AppointmentServiceImpl implements AppointmentService {
    @Autowired
    private AppointmentDao appointmentDao;

    @Autowired
    private DoctorDao doctorDao;

    private static final Logger LOGGER = LoggerFactory.getLogger(DoctorServiceImpl.class);

    @Transactional(rollbackFor= SQLException.class)
    @Override
    public Appointment createAppointment(String appointmentDay, String appointmentTime, Patient patient, Doctor doctor) throws RepeatedAppointmentException, NotCreatedAppointmentException, NotValidDoctorIdException, NotValidAppointmentDayException, NotValidAppointmentTimeException, NotFoundDoctorException, NotValidPatientIdException {
        LOGGER.debug("AppointmentServiceImpl: CreateAppointment");

        Appointment appointment = null;
        Optional<Appointment> app = Optional.empty();
        try{
            app = appointmentDao.findAppointment(appointmentDay, appointmentTime, patient, doctor);
        }catch (NoResultException e){

        }catch (Exception e){

        }
        if (app.isPresent()){
            appointmentDao.undoCancelAppointment(app.get());
            return app.get();
        }

        try{
           appointment =  appointmentDao.createAppointment(appointmentDay, appointmentTime, patient, doctor);
        } catch (RepeatedAppointmentException e) {
            throw new RepeatedAppointmentException();
        } catch (Exception e) {
            throw new NotCreatedAppointmentException();
        }
        return appointment;
    }

    @Transactional(rollbackFor = SQLException.class)
    @Override
    public Boolean cancelAppointment(String appointmentDay, String appointmentTime, Patient patient, Doctor doctor){
        LOGGER.debug("AppointmentServiceImpl: cancelAppointment");
        Boolean ans = true;

        Appointment appointment = null;
        Optional<Appointment> app = Optional.empty();
        try {
            app = appointmentDao.findAppointment(appointmentDay, appointmentTime, patient, doctor);
        } catch (NoResultException e){
            ans = false;
        } catch (Exception e){
            ans = false;
        }
        try {
            if (ans){
                appointmentDao.cancelAppointment(app.get());
            }
        }catch (Exception e) {
            ans = false;
        }
        return ans;
    }
}
