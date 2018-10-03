package ar.edu.itba.paw.webapp.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@Controller
public class ErrorController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ErrorController.class);

    @RequestMapping("/403")
    public ModelAndView forbidden(){
        return new ModelAndView("403");
    }

    @RequestMapping("/404")
    public ModelAndView error404(){
        return new ModelAndView("404");
    }

    @RequestMapping("/500")
    public ModelAndView error500(){return new ModelAndView("500"); }
}
