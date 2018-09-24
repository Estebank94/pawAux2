package ar.edu.itba.paw.webapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class LogInController {

    @RequestMapping("/showLogIn")
    public ModelAndView logIn(){
        return new ModelAndView("login");
    }


}