package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.DoctorService;
import ar.edu.itba.paw.interfaces.SearchService;
import ar.edu.itba.paw.models.Doctor;
import ar.edu.itba.paw.webapp.forms.PersonalForm;
import ar.edu.itba.paw.webapp.forms.ProfessionalForm;
import com.sun.xml.internal.bind.v2.TODO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;


@Controller
public class RegistrationController {

    @Autowired
    private SearchService searchService;

    @Autowired
    private DoctorService doctorService;

    @RequestMapping(value="/doctorRegistration", method = { RequestMethod.POST })
    public ModelAndView doctorRegistration (@Valid @ModelAttribute("personal") PersonalForm personalForm, final BindingResult errors){


        if(errors.hasErrors() || !personalForm.matchingPasswords(personalForm.getPassword(), personalForm.getPasswordConfirmation())){
            if(!personalForm.matchingPasswords(personalForm.getPassword(), personalForm.getPasswordConfirmation())){
                /*TODO: this doesn't show the error message*/
                showDoctorRegistration(personalForm).addObject("noMatchingPassword", true);
            }
            return showDoctorRegistration(personalForm);
        }else{

            final ModelAndView mav = new ModelAndView("registerSpecialist2");
            mav.addObject("professional", new ProfessionalForm());
            mav.addObject("insuranceList", searchService.listInsurances().get());
            mav.addObject("insurancePlan", searchService.listInsurancePlan().get());

            /*TODO: habria que agregarle un campo a este create doctor que se settee en profileNotCompleted*/
            /*TODO: pasar el id de doctor por parametro URL asi despues en profile agarro el doctor de ese ID y hago sets de toda la info del profile*/

            doctorService.createDoctor(personalForm.getFirstName(), personalForm.getLastName(), personalForm.getPhoneNumber(),
                     personalForm.getSex(), personalForm.getLala(), "null", personalForm.getAddress());

            return mav;
        }

    }

    @RequestMapping(value="/showDoctorRegistration", method = { RequestMethod.GET })
    public ModelAndView showDoctorRegistration (@ModelAttribute("personal") PersonalForm personalForm){

        final ModelAndView mav = new ModelAndView("registerSpecialist");
        return mav;
    }

    @RequestMapping(value = "/showDoctorProfile", method = {RequestMethod.GET})
    public ModelAndView showDoctorProfile(@ModelAttribute("professional")ProfessionalForm professionalForm){

        final ModelAndView mav = new ModelAndView("registerSpecialist2");
        mav.addObject("insuranceList", searchService.listInsurances().get());
        mav.addObject("insurancePlan", searchService.listInsurancePlan().get());
        return mav;
    }

    @RequestMapping(value = "/doctorProfile", method = {RequestMethod.POST})
    public ModelAndView doctorProfile (@Valid @ModelAttribute("professional") ProfessionalForm professionalForm, final BindingResult errors){

        if(errors.hasErrors()){
            return showDoctorProfile(professionalForm);
        }
        System.out.println(professionalForm.getLanguages());

        /*TODO: aca tenemos que llamar a una funcion de professionalForm que lo que haga es pasar ese String de Languages a una lista, easy peasy, para despues poder pasarselo bien al doctor.*/
        /*TODO: recibir el id del doctor creado recientemente, o que este loggeado para poder hacer los sets de toda la info nueva*/
        /*TODO: poner el valor de profileCompleted en true, asi ya se puede mostrar en la pantalla*/

        final ModelAndView mav = new ModelAndView("finalStep");
        return mav;
    }

}
