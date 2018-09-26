package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.DoctorService;
import ar.edu.itba.paw.interfaces.SearchService;
import ar.edu.itba.paw.models.Description;
import ar.edu.itba.paw.models.Doctor;
import ar.edu.itba.paw.models.WorkingHours;
import ar.edu.itba.paw.webapp.forms.PersonalForm;
import ar.edu.itba.paw.webapp.forms.ProfessionalForm;
import com.sun.xml.internal.bind.v2.TODO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.Set;


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
            mav.addObject("specialtyList", searchService.listSpecialties().get());

            /*TODO: habria que agregarle un campo a este create doctor que se settee en profileNotCompleted*/
            /*TODO: manejar errores 500*/
            /*TODO: agregar boton de cancelar y volver al incio*/

            Doctor doctor = doctorService.createDoctor(personalForm.getFirstName(), personalForm.getLastName(), personalForm.getPhoneNumber(),
                     personalForm.getSex(), personalForm.getLicence(), "null2", personalForm.getAddress());
            mav.addObject("doctor", doctor);
            return mav;
        }

    }

    @RequestMapping(value="/showDoctorRegistration", method = { RequestMethod.GET })
    public ModelAndView showDoctorRegistration (@ModelAttribute("personal") PersonalForm personalForm){

        final ModelAndView mav = new ModelAndView("registerSpecialist");
        /*TODO: agregar boton de cancelar y volver al incio*/
        return mav;
    }

    @RequestMapping(value = "/showDoctorProfile/{doctorId}", method = {RequestMethod.GET})
    public ModelAndView showDoctorProfile(@PathVariable Integer doctorId, @ModelAttribute("professional")ProfessionalForm professionalForm){

        /*TODO: agregar specialty al view*/
        /*TODO: agregar info en los lists de insurances y planes*/
        /*TODO: agregar boton de cancelar y volver al incio y mostrar mensaje en pantalla que esta registrado como profesional pero que todavia
        * no va a figurar en la lista de doctores porque no completo su perfil*/

        final ModelAndView mav = new ModelAndView("registerSpecialist2");

        mav.addObject("insuranceList", searchService.listInsurances().get());
        mav.addObject("insurancePlan", searchService.listInsurancePlan().get());

        mav.addObject("specialtyList", searchService.listSpecialties().get());
        return mav;
    }

    @RequestMapping(value = "/doctorProfile/{doctorId}", method = {RequestMethod.POST})
    public ModelAndView doctorProfile (@PathVariable Integer doctorId, @Valid @ModelAttribute("professional") ProfessionalForm professionalForm, final BindingResult errors){

        if(errors.hasErrors()){
            System.out.println("error");
            return showDoctorProfile(doctorId, professionalForm);
        }

        Doctor doctor = doctorService.findDoctorById(doctorId).get();
        System.out.println(doctor.getFirstName());
        System.out.println(doctor.getId());

//        doctorService.setDoctorInfo(doctorId,"" ,professionalForm.getInsurance(),"" ,professionalForm.getDescription());
//
//        Integer doctorId, Set<String> specialty, Map<String, Set<String>> insurance,
//                List<WorkingHours > workingHours, Description description

        Map<String, Set<String>> map = professionalForm.createMap(professionalForm.getInsurance(), professionalForm.getInsurancePlan());

        System.out.println(professionalForm.getEducation());
        System.out.println(professionalForm.getCertificate());
        System.out.println("languages");
        for(String string : professionalForm.getLanguages()){
            System.out.println(string);
        }
        System.out.println("specialty");
        for(String string : professionalForm.getSpecialty()){
            System.out.println(string);
        }


        /*TODO: agregar setters a la informacion total del doctor*/
        /*TODO: daos y binding de data a las tablas sobre la informacion puesta aca*/
        /*TODO: agregar boton de cancelar y volver al incio y mostrar mensaje en pantalla que esta registrado como profesional pero que todavia
         * no va a figurar en la lista de doctores porque no completo su perfil*/

        final ModelAndView mav = new ModelAndView("finalStep");
        return mav;
    }

}
