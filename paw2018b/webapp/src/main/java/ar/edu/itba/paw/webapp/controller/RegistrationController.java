package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.DoctorService;
import ar.edu.itba.paw.interfaces.PatientService;
import ar.edu.itba.paw.interfaces.SearchService;
import ar.edu.itba.paw.models.Description;
import ar.edu.itba.paw.models.Doctor;
import ar.edu.itba.paw.models.Patient;
import ar.edu.itba.paw.models.WorkingHours;
import ar.edu.itba.paw.webapp.forms.PersonalForm;
import ar.edu.itba.paw.webapp.forms.ProfessionalForm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;


@Controller
public class RegistrationController {

    private static final Logger LOGGER = LoggerFactory.getLogger(RegistrationController.class);

    @Autowired
    private SearchService searchService;

    @Autowired
    private DoctorService doctorService;

    @Autowired
    private PatientService patientService;

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

            try {
                Doctor doctor = doctorService.createDoctor(personalForm.getFirstName(), personalForm.getLastName(), personalForm.getPhoneNumber(),
                        personalForm.getSex(), personalForm.getLicence(), "null2", personalForm.getAddress());
                Patient patient = patientService.createPatient(personalForm.getFirstName(), personalForm.getLastName(), personalForm.getPhoneNumber(), personalForm.getEmail(),
                        personalForm.getPassword());
                patientService.setDoctorId(patient.getPatientId(), doctor.getId());

            /*TODO AUTOLOGIN*/
//            Authentication authentication =

                mav.addObject("doctor", doctor);
                return mav;
            } catch (IllegalArgumentException ex) {
                return new ModelAndView("500");
            }
        }

    }

    @RequestMapping(value="/doctorRegistration", method = { RequestMethod.GET })
    public ModelAndView showDoctorRegistration (@ModelAttribute("personal") PersonalForm personalForm){

        final ModelAndView mav = new ModelAndView("registerSpecialist");
        /*TODO: agregar boton de cancelar y volver al incio*/
        return mav;
    }

    @RequestMapping(value = "/doctorProfile/{doctorId}", method = {RequestMethod.GET})
    public ModelAndView showDoctorProfile(@PathVariable Integer doctorId, @ModelAttribute("professional")ProfessionalForm professionalForm){


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

        Doctor doctorById = doctorService.findDoctorById(doctorId).get();

        Map<String, Set<String>> insurance = professionalForm.createMap(professionalForm.getInsurance(), professionalForm.getInsurancePlan());

        Description description = new Description(professionalForm.getCertificate(), professionalForm.getLanguages(), professionalForm.getEducation());

        List<WorkingHours> workingHours = professionalForm.workingHoursList();

        Doctor doctorProfessional = doctorService.setDoctorInfo(doctorId, professionalForm.getSpecialty(), insurance,workingHours ,description).get();
        
        /*TODO: agregar boton de cancelar y volver al incio y mostrar mensaje en pantalla que esta registrado como profesional pero que todavia
         * no va a figurar en la lista de doctores porque no completo su perfil*/

        final ModelAndView mav = new ModelAndView("finalStep");
        return mav;
    }

    @RequestMapping(value="/doctorProfile")
    public ModelAndView doctorProfilePrueba(){
        return new ModelAndView("finalStep");
    }

//    public void authWithHttpServletRequest(HttpServletRequest request, String username, String password) {
//        try {
//
//        } catch (ServletException e) {
//            LOGGER.error("Error while login ", e);
//        }
//    }

}
