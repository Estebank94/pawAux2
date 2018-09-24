package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.DoctorService;
import ar.edu.itba.paw.interfaces.SearchService;
import ar.edu.itba.paw.models.Doctor;
import ar.edu.itba.paw.webapp.forms.PersonalForm;
import ar.edu.itba.paw.webapp.forms.ProfessionalForm;
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
                showDoctorRegistration(personalForm).addObject("noMatchingPassword", true);
            }
            return showDoctorRegistration(personalForm);
        }else{

            final ModelAndView mav = new ModelAndView("registerSpecialist2");
            mav.addObject("professional", new ProfessionalForm());
            mav.addObject("insuranceList", searchService.listInsurances().get());
            mav.addObject("insurancePlan", searchService.listInsurancePlan().get());

            doctorService.createDoctor(personalForm.getFirstName(), personalForm.getLastName(), personalForm.getPhoneNumber(),
                     personalForm.getSex(), personalForm.getLala(), "jajaja", personalForm.getAddress());

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
        final ModelAndView mav = new ModelAndView("finalStep");
        return mav;
    }

}
