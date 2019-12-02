package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.services.AppointmentService;
import ar.edu.itba.paw.interfaces.services.DoctorService;
import ar.edu.itba.paw.interfaces.services.PatientService;
import ar.edu.itba.paw.models.Appointment;
import ar.edu.itba.paw.models.Doctor;
import ar.edu.itba.paw.models.Patient;
import ar.edu.itba.paw.models.exceptions.NotFoundPacientException;
import ar.edu.itba.paw.models.exceptions.NotValidEmailException;
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
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller

public class PatientPanelController {

    private static final Logger LOGGER = LoggerFactory.getLogger(PatientPanelController.class);

    @Autowired
    PatientService patientService;

    @Autowired
    AppointmentService appointmentService;

    @Autowired
    DoctorService doctorService;

    @RequestMapping("/patientPanel")
    public ModelAndView patientPanel(){

        LOGGER.debug("Calling: patientPanel GET");

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        Patient patient;
        patient = patientService.findPatientByEmail(authentication.getName());
        /*
        try {
            patient = patientService.findPatientByEmail(authentication.getName());
        } catch (NotValidEmailException e) {
            LOGGER.trace("Error 404");
            return new ModelAndView("404");
        } catch (NotFoundPacientException e) {
            LOGGER.trace("Error 404");
            return new ModelAndView("404");
        }
        */

        ModelAndView mav = new ModelAndView("patientPanel");
        mav.addObject("patient", patient);
        Map<LocalDate, List<Appointment>> patientAppointment = patient.appointmentsMap();
        mav.addObject("patientAppointments", patientAppointment);
        List<Appointment> patientHistoricalAppointments = patient.getHistoricalAppointments();
        mav.addObject("patientHistoricalAppointments",patientHistoricalAppointments);

        return mav;
    }

    @RequestMapping(value = "/patientPanel", method = {RequestMethod.POST})
    public ModelAndView patientPanel(@ModelAttribute("appointment")CancelAppointmentForm form) throws Exception{

        LOGGER.debug("Calling: patientPanel POST");

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        Patient patient;
        patient = patientService.findPatientByEmail(authentication.getName());
        /*
        try {
            patient = patientService.findPatientByEmail(authentication.getName());
        } catch (NotValidEmailException e) {
            LOGGER.trace("Error 404");
            return new ModelAndView("404");
        } catch (NotFoundPacientException e) {
            LOGGER.trace("Error 404");
            return new ModelAndView("404");
        }
         */


        if(form.getDay() != null){
            Optional<Doctor> doctor = doctorService.findDoctorById(String.valueOf(form.getDoctorid()));
            appointmentService.cancelAppointment(form.getDay(), form.getTime(), patient, doctor.get());
        }

        ModelAndView mav = new ModelAndView("redirect:/patientPanel");

        return mav;

    }

}
