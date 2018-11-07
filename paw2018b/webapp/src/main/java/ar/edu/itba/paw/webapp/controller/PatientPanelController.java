package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.PatientService;
import ar.edu.itba.paw.models.Appointment;
import ar.edu.itba.paw.models.Patient;
import ar.edu.itba.paw.models.exceptions.NotFoundPacientException;
import ar.edu.itba.paw.models.exceptions.NotValidEmailException;
import ar.edu.itba.paw.webapp.forms.ProfessionalForm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Controller

public class PatientPanelController {

    private static final Logger LOGGER = LoggerFactory.getLogger(PatientPanelController.class);

    @Autowired
    PatientService patientService;

    @RequestMapping("/patientPanel")
    /*cambiar el model and attribute*/
    public ModelAndView patientPanel(@ModelAttribute("professional")ProfessionalForm professionalForm){

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

        ModelAndView mav = new ModelAndView("patientPanel");
        mav.addObject("patient", patient);
        Map<LocalDate, List<Appointment>> patientAppointment = patient.appointmentsMap();
        mav.addObject("patientAppointments", patientAppointment);

        return mav;
    }

}
