package ar.edu.itba.paw.webapp.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@RequestMapping("/doctorPanel")
@Controller
public class DoctorPanelController {

    private static final Logger LOGGER = LoggerFactory.getLogger(DoctorPanelController.class);

    @RequestMapping("/")
    public ModelAndView doctorPanel(){
        ModelAndView mav = new ModelAndView("doctorPanel");
        return mav;
    }


}

