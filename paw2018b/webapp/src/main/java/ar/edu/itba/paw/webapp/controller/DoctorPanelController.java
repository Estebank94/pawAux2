package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.App;
import ar.edu.itba.paw.interfaces.DoctorService;
import ar.edu.itba.paw.interfaces.PatientService;
import ar.edu.itba.paw.models.Appointment;
import ar.edu.itba.paw.models.Doctor;
import ar.edu.itba.paw.models.Patient;
import ar.edu.itba.paw.models.exceptions.NotFoundDoctorException;
import ar.edu.itba.paw.models.exceptions.NotFoundPacientException;
import ar.edu.itba.paw.models.exceptions.NotValidEmailException;
import ar.edu.itba.paw.models.exceptions.NotValidIDException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

@Controller
public class DoctorPanelController {

    @Autowired
    private DoctorService doctorService;

    @Autowired
    private PatientService patientService;

    private static final Logger LOGGER = LoggerFactory.getLogger(DoctorPanelController.class);

    @RequestMapping("/doctorPanel")
    public ModelAndView doctorPanel(){

        ModelAndView mav = new ModelAndView("doctorPanel");

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        Patient patient;
        try {
            patient = patientService.findPatientByEmail(authentication.getName());
        } catch (NotValidEmailException e) {
            LOGGER.trace("Error 404");
            return new ModelAndView("404");
        } catch (NotFoundPacientException e) {
            LOGGER.trace("Error 404");
            return new ModelAndView("404");
        }

        Integer doctorId = patient.getDoctor().getId();

        Doctor doctor = null;
        try {
            doctor = doctorService.findDoctorById(doctorId).get();
        } catch (NotFoundDoctorException e) {
            LOGGER.trace("Error 404");
            return new ModelAndView("404");
        } catch (NotValidIDException e) {
            LOGGER.trace("Error 404");
            return new ModelAndView("404");
        }

        LOGGER.debug("Doctor Panel: Doctor with ID: {}", doctorId);

        if(doctorId != 0 && doctorId != null){

            /*completar información*/
            if(doctor.getSpecialties() == null
                    || doctor.getInsurancePlans().isEmpty()
                    || doctor.getWorkingHours().isEmpty()){
                mav.addObject("professionalIncomplete", true);
            }
            /*agregar información*/
            else if(doctor.getDescription().getEducation() == null
                    || doctor.getDescription().getLanguages().contains(null)
                    || doctor.getDescription().getCertificate() == null){
               mav.addObject("addInfo", true);
            }

            Map<LocalDate, List<Appointment>> appointments = doctor.appointmentsMap();
            LOGGER.debug("GET doctor's appointments: {}", appointments);
            mav.addObject("appointments", appointments);
            mav.addObject("doctor", doctor);
            Map<LocalDate, List<Appointment>> patientAppointment = patient.appointmentsMap();
            mav.addObject("patientAppointments", patientAppointment);


        }else{
            /*TODO CHECK IF THE ID IS A REAL DOCTOR*/
        }


        return mav;

    }


}

