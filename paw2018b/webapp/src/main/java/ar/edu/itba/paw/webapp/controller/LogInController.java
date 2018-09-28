package ar.edu.itba.paw.webapp.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@Controller
public class LogInController {

    private static final Logger LOGGER = LoggerFactory.getLogger(LogInController.class);

    @RequestMapping("/showLogIn")
    public ModelAndView logIn(HttpServletRequest request){
        String referrer = request.getHeader("Referer");
        request.getSession().setAttribute("/specialist/{doctorId}", referrer);
        return new ModelAndView("login");
    }


}
