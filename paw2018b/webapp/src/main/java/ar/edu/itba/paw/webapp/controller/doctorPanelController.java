package ar.edu.itba.paw.webapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@RequestMapping("/doctorPanel")
@Controller
public class doctorPanelController {

    @RequestMapping("/")
    public ModelAndView doctorPanel(){
        ModelAndView mav = new ModelAndView("doctorPanel");
        return mav;
    }


}

