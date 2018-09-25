package ar.edu.itba.paw.webapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class LogInController {

    /*TODO: arreglar del view el tema de que esta decentrado*/
    /*TODO: chequear si hay alguien ya loggeado para hacer un cambio entre el boton de log in y el de log out*/

    @RequestMapping("/showLogIn")
    public ModelAndView logIn(){
        return new ModelAndView("login");
    }


}
