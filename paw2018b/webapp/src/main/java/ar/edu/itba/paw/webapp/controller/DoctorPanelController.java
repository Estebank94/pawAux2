package ar.edu.itba.paw.webapp.controller;


import ar.edu.itba.paw.interfaces.services.AppointmentService;
import ar.edu.itba.paw.interfaces.services.DoctorService;
import ar.edu.itba.paw.interfaces.services.PatientService;
import ar.edu.itba.paw.models.Appointment;
import ar.edu.itba.paw.models.Doctor;
import ar.edu.itba.paw.models.Patient;
import ar.edu.itba.paw.models.exceptions.NotFoundDoctorException;
import ar.edu.itba.paw.models.exceptions.NotFoundPacientException;
import ar.edu.itba.paw.models.exceptions.NotValidEmailException;
import ar.edu.itba.paw.models.exceptions.NotValidIDException;
import ar.edu.itba.paw.webapp.forms.CancelAppointmentForm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDate;
import java.util.*;

@Controller
public class DoctorPanelController {

    @Autowired
    private DoctorService doctorService;

    @Autowired
    private PatientService patientService;

    @Autowired
    AppointmentService appointmentService;

    private static final Logger LOGGER = LoggerFactory.getLogger(DoctorPanelController.class);

    @RequestMapping("/doctorPanel")
    public ModelAndView doctorPanel(){

        ModelAndView mav = new ModelAndView("doctorPanel");

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        Patient patient;
        patient = patientService.findPatientByEmail(authentication.getName());

        /*
        try {
            patient = patientService.findPatientByEmail(authentication.getName());
        } catch (NotValidEmailException | NotFoundPacientException e) {
            LOGGER.trace("Error 404");
            return new ModelAndView("404");
        }
         */

        Integer doctorId = patient.getDoctor().getId();

        Doctor doctor = null;
        try {
            doctor = doctorService.findDoctorById(String.valueOf(doctorId));
        } catch (NotFoundDoctorException | NotValidIDException e) {
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
            else if(doctor.getDescription() == null
                    || doctor.getDescription().getEducation() == null
                    || doctor.getDescription().getLanguages() == null
                    || doctor.getDescription().getCertificate() == null){
               mav.addObject("addInfo", true);
            }


            Map<LocalDate, List<Appointment>> appointments = doctor.appointmentsMap();
            LOGGER.debug("GET doctor's appointments: {}", appointments);
            mav.addObject("appointments", appointments);
            mav.addObject("doctor", doctor);
            Map<LocalDate, List<Appointment>> patientAppointment = patient.appointmentsMap();
            mav.addObject("patientAppointments", patientAppointment);
            List<Appointment> doctorHistoricalAppointments = doctor.getHistoricalAppointments();
            mav.addObject("doctorHistoricalAppointments", doctorHistoricalAppointments);
            List<Appointment> patientHistoricalAppointments = patient.getHistoricalAppointments();
            mav.addObject("patientHistoricalAppointments", patientHistoricalAppointments);
            List<Doctor> favoritesDoctors = patient.getFavoriteDoctors();
            mav.addObject("favoritesDoctors", favoritesDoctors);

        }

        return mav;

    }

    @RequestMapping(value = "/doctorPanel", method = {RequestMethod.POST})
    public ModelAndView doctorPanel(@ModelAttribute("appointment") CancelAppointmentForm form) throws Exception{
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        Patient patient;
        patient = patientService.findPatientByEmail(authentication.getName());
        /*
        try {
            patient = patientService.findPatientByEmail(authentication.getName());
        } catch (NotValidEmailException | NotFoundPacientException e) {
            LOGGER.trace("Error 404");
            return new ModelAndView("404");
        }
         */

        if(form.getDay() != null){
            Doctor doctor = doctorService.findDoctorById(String.valueOf(form.getDoctorid()));
            appointmentService.cancelAppointment(form.getDay(), form.getTime(), patient, doctor);
        }
        ModelAndView mav = new ModelAndView("redirect:/doctorPanel");
        return mav;
    }


}

