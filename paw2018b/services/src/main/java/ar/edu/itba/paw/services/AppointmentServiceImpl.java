package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.*;
import ar.edu.itba.paw.models.Appointment;
import ar.edu.itba.paw.models.Doctor;
import ar.edu.itba.paw.models.Patient;
import ar.edu.itba.paw.models.WorkingHours;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.acls.model.NotFoundException;
import org.springframework.stereotype.Service;
import sun.util.locale.provider.LocaleServiceProviderPool;

import javax.print.Doc;
import java.time.DayOfWeek;
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

    @Autowired
    private PatientDao patientDao;

    private static final Logger LOGGER = LoggerFactory.getLogger(DoctorServiceImpl.class);

    @Override
    public Optional<Appointment> createAppointment(Integer doctorId, Integer clientId, LocalDate appointmentDay, LocalTime appointmentTime) {
        LOGGER.debug("AppointmentServiceImpl: CreateAppointment");
        if (doctorId == null){
            LOGGER.debug("Doctor ID is null");
            throw new IllegalArgumentException("DoctorId can't be null");
        }
        if (doctorId <= 0 ){
            LOGGER.debug("Doctor ID is negative. Doctor ID given: {}", doctorId);
            throw new IllegalArgumentException("DoctorId can't be negative or 0");
        }
        LOGGER.debug("Calling method doctorDao.findDoctorById(doctorId),doctorID = {}", doctorId);
        Optional<Doctor> doctorOp = doctorDao.findDoctorById(doctorId);

        if (!doctorOp.isPresent()){
            LOGGER.debug("Doctor with ID: {}, not found", doctorId);
            throw new NotFoundException("doctor not found");
        }

        Doctor doctor = doctorOp.get();

        if (clientId == null){
            LOGGER.debug("Patient with ID: {} was not found", clientId);
            throw new IllegalArgumentException("PatienId can't be null");
        }

        if (clientId <= 0){
            LOGGER.debug("Patient ID is null. The ID given is: {}", clientId);
            throw new IllegalArgumentException("PatienId can'b be negative or 0");
        }

        LOGGER.debug("Calling function patientDao.findPatientByID(clientID). clientID = {}", clientId);
        Optional<Patient> patient = patientDao.findPatientById(clientId);

        if (!patient.isPresent()){
            LOGGER.debug("No patient found");
            throw new NotFoundException("Patient not Found");
        }

        if (appointmentDay == null){
            LOGGER.debug("The appointment day is null");
            throw new  IllegalArgumentException("Appointment Day can't be null");
        }
        if (appointmentTime == null){
            LOGGER.debug("The apointment time is null");
            throw new  IllegalArgumentException("Appointment Time can't be null");
        }

        LOGGER.debug("Asking if Doctor has a working hour on a specific day of the week. DayOfWeek: {}", doctor.getWorkingHours().containsKey(appointmentDay.getDayOfWeek()));
        if (!doctor.getWorkingHours().containsKey(appointmentDay.getDayOfWeek())){
            LOGGER.debug("The Doctor with ID: {} does not work on that day", doctorId);
            throw new IllegalArgumentException("The Doctor does not work on that Day");
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
            throw new IllegalArgumentException("The doctor does not work on that time");
        }

        LOGGER.debug("Create an Appointment with appointmentDao.createAppointment(doctorId, clientId, appointmentDay, appointmentTime)");
        LOGGER.debug("doctorId: {}", doctorId);
        LOGGER.debug("Client ID: {}", clientId);
        LOGGER.debug("Appointment Day: {}", appointmentDay);
        LOGGER.debug("Appointment Time: {}", appointmentTime);
        return appointmentDao.createAppointment(doctorId, clientId, appointmentDay, appointmentTime);
    }
}
