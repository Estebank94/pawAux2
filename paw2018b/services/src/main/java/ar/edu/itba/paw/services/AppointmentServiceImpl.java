package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.*;
import ar.edu.itba.paw.models.Appointment;
import ar.edu.itba.paw.models.Doctor;
import ar.edu.itba.paw.models.Patient;
import ar.edu.itba.paw.models.WorkingHours;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.acls.model.NotFoundException;
import org.springframework.stereotype.Service;

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

    @Override
    public Optional<Appointment> createAppointment(Integer doctorId, Integer clientId, LocalDate appointmentDay, LocalTime appointmentTime) {
        if (doctorId == null){
            throw new IllegalArgumentException("DoctorId can't be null");
        }
        if (doctorId <= 0 ){
            throw new IllegalArgumentException("DoctorId can't be negative or 0");
        }
        Optional<Doctor> doctorOp = doctorDao.findDoctorById(doctorId);

        if (!doctorOp.isPresent()){
            throw new NotFoundException("doctor not found");
        }

        Doctor doctor = doctorOp.get();

        if (clientId == null){
            throw new IllegalArgumentException("PatienId can't be null");
        }

        if (clientId <= 0){
            throw new IllegalArgumentException("PatienId can'b be negative or 0");
        }

        Optional<Patient> patient = patientDao.findPatientById(clientId);

        if (!patient.isPresent()){
            throw new NotFoundException("Patient not Found");
        }

        if (appointmentDay == null){
            throw new  IllegalArgumentException("Appointment Day can't be null");
        }
        if (appointmentTime == null){
            throw new  IllegalArgumentException("Appointment Time can't be null");
        }

        if (!doctor.getWorkingHours().containsKey(appointmentDay.getDayOfWeek())){
            throw new IllegalArgumentException("The Doctor does not work on that Day");
        }
        List<WorkingHours> wh =  doctor.getWorkingHours().get(appointmentDay.getDayOfWeek());
        boolean isInCorrectAppointment = true;
        for (int i = 0; i< wh.size() && isInCorrectAppointment; i++ ){
            WorkingHours thiswh = wh.get(i);
            if ((appointmentTime.isAfter(thiswh.getStartTime()) || appointmentTime.compareTo(thiswh.getStartTime()) == 0 ) && appointmentTime.isBefore(thiswh.getFinishTime())){
                isInCorrectAppointment = false;
            }
        }
        if (isInCorrectAppointment){
            throw new IllegalArgumentException("The doctor does not work on that time");
        }
        
        return appointmentDao.createAppointment(doctorId, clientId, appointmentDay, appointmentTime);
    }
}
