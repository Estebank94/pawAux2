package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.App;
import ar.edu.itba.paw.interfaces.DoctorService;
import ar.edu.itba.paw.models.Appointment;
import ar.edu.itba.paw.models.Doctor;
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

    private static final Logger LOGGER = LoggerFactory.getLogger(DoctorPanelController.class);

    @RequestMapping("/doctorPanel")
    public ModelAndView doctorPanel(){
        ModelAndView mav = new ModelAndView("doctorPanel");

//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
////        if (!(authentication instanceof AnonymousAuthenticationToken)) {
////            String currentUserName = authentication.getName();
////            Doctor doctor = (Doctor) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
////            Map<LocalDate, List<Appointment>> appointments = doctor.getAvailableAppointments();
////        }

        Doctor doctor = doctorService.findDoctorById(2).get();

        Map<LocalDate, List<LocalTime>> appointments = doctor.appointmentsToMap();

        mav.addObject("appointments", appointments);
        mav.addObject("doctor", doctor);

        return mav;

    }


}

