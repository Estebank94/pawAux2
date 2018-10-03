package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.*;
import ar.edu.itba.paw.models.Appointment;
import ar.edu.itba.paw.models.Doctor;
import ar.edu.itba.paw.models.WorkingHours;
import ar.edu.itba.paw.models.exceptions.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    @Override
    public Optional<Appointment> createAppointment(Integer doctorId, Integer clientId, LocalDate appointmentDay, LocalTime appointmentTime) throws RepeatedAppointmentException, NotCreatedAppointmentException, NotValidDoctorIdException, NotValidAppointmentDayException, NotValidAppointmentTimeException, NotFoundDoctorException, NotValidPatientIdException {
        LOGGER.debug("AppointmentServiceImpl: CreateAppointment");
        if (doctorId == null) {
            LOGGER.debug("Doctor ID is null");
            throw new NotValidDoctorIdException("DoctorId can't be null");
        }
        if (doctorId <= 0) {
            LOGGER.debug("Doctor ID is negative. Doctor ID given: {}", doctorId);
            throw new NotValidDoctorIdException("DoctorId can't be negative or 0");
        }

        LOGGER.debug("Calling method doctorDao.findDoctorById(doctorId),doctorID = {}", doctorId);
        Optional<Doctor> doctorOp;

        doctorOp = doctorDao.findDoctorById(doctorId);

        if (!doctorOp.isPresent()){
            LOGGER.debug("Doctor with ID: {}, not found", doctorId);
            throw new NotFoundDoctorException("doctor not found");
        }

        Doctor doctor = doctorOp.get();

        if (clientId == null){
            LOGGER.debug("Patient with ID: {} was not found", clientId);
            throw new NotValidPatientIdException("PatienId can't be null");
        }

        if (clientId <= 0){
            LOGGER.debug("Patient ID is null. The ID given is: {}", clientId);
            throw new NotValidPatientIdException("PatienId can'b be negative or 0");
        }

        if (appointmentDay == null){
            LOGGER.debug("The appointment day is null");
            throw new NotValidAppointmentDayException("Appointment Day can't be null");
        }
        if (appointmentTime == null){
            LOGGER.debug("The apointment time is null");
            throw new NotValidAppointmentTimeException("Appointment Time can't be null");
        }

        LOGGER.debug("Asking if Doctor has a working hour on a specific day of the week. DayOfWeek: {}", doctor.getWorkingHours().containsKey(appointmentDay.getDayOfWeek()));
        if (!doctor.getWorkingHours().containsKey(appointmentDay.getDayOfWeek())){
            LOGGER.debug("The Doctor with ID: {} does not work on that day", doctorId);
            throw new NotValidAppointmentDayException("The Doctor does not work on that Day");
        }
        LOGGER.debug("Getting working hours of Doctor with id: {}", doctorId);
        List<WorkingHours> wh =  doctor.getWorkingHours().get(appointmentDay.getDayOfWeek());
        LOGGER.debug("Working Hours of the doctor: {}", wh);
        boolean isInCorrectAppointment = true;
        for (int i = 0; i< wh.size() && isInCorrectAppointment; i++ ){
            WorkingHours thiswh = wh.get(i);
            if ((appointmentTime.isAfter(thiswh.getStartTime()) || appointmentTime.compareTo(thiswh.getStartTime()) == 0 ) && appointmentTime.isBefore(thiswh.getFinishTime())){
                isInCorrectAppointment = false;
            }
        }
        if (isInCorrectAppointment){
            LOGGER.debug("The doctor does not work on the specific time");
            throw new NotValidAppointmentTimeException("The doctor does not work on that time");
        }

        LOGGER.debug("Create an Appointment with appointmentDao.createAppointment(doctorId, clientId, appointmentDay, appointmentTime)");
        LOGGER.debug("doctorId: {}", doctorId);
        LOGGER.debug("Client ID: {}", clientId);
        LOGGER.debug("Appointment Day: {}", appointmentDay);
        LOGGER.debug("Appointment Time: {}", appointmentTime);

        Optional<Appointment> appointment = Optional.empty();
        try{
           appointment =  appointmentDao.createAppointment(doctorId, clientId, appointmentDay, appointmentTime);
        } catch (RepeatedAppointmentException e) {
            throw new RepeatedAppointmentException();
        } catch (Exception e) {
            throw new NotCreatedAppointmentException();
        }
        if (!appointment.isPresent()){
            throw new NotCreatedAppointmentException();
        }
        return appointment;
    }
}
