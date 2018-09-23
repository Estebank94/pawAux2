package ar.edu.itba.paw.webapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/patientPanel")
public class patientPanelController {

    @RequestMapping("/")
    public ModelAndView patientPanel(){
        ModelAndView mav = new ModelAndView("patientPanel");
        return mav;
    }

}
