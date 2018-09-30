package ar.edu.itba.paw.webapp.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller

public class PatientPanelController {

    private static final Logger LOGGER = LoggerFactory.getLogger(PatientPanelController.class);

    @RequestMapping("/patientPanel")
    public ModelAndView patientPanel(){
        ModelAndView mav = new ModelAndView("patientPanel");
        return mav;
    }

}
