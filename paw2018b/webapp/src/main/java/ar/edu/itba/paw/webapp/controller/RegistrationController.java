package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.DoctorService;
import ar.edu.itba.paw.interfaces.PatientService;
import ar.edu.itba.paw.interfaces.SearchService;
import ar.edu.itba.paw.models.*;
import ar.edu.itba.paw.webapp.forms.PatientForm;
import ar.edu.itba.paw.webapp.forms.PersonalForm;
import ar.edu.itba.paw.webapp.forms.ProfessionalForm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.time.DayOfWeek;
import java.util.HashMap;
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

    @Autowired
    protected AuthenticationManager authenticationManager;

    @RequestMapping(value="/doctorRegistration", method = { RequestMethod.POST })
    public ModelAndView doctorRegistration (@Valid @ModelAttribute("personal") PersonalForm personalForm, final BindingResult errors,
                                            HttpServletRequest request){


        if(errors.hasErrors() || !personalForm.matchingPasswords(personalForm.getPassword(), personalForm.getPasswordConfirmation())
              /* || patientService.findPatientByEmail(personalForm.getEmail()) != null*/){
            if(!personalForm.matchingPasswords(personalForm.getPassword(), personalForm.getPasswordConfirmation())){
                /*TODO: this doesn't show the error message*/
                showDoctorRegistration(personalForm).addObject("noMatchingPassword", true);
            }/*else if(patientService.findPatientByEmail(personalForm.getEmail()) != null){
                showDoctorRegistration(personalForm).addObject("userExists", true);
            }*/
            return showDoctorRegistration(personalForm);
        }else{

            final ModelAndView mav = new ModelAndView("registerSpecialist2");
            mav.addObject("professional", new ProfessionalForm());
            mav.addObject("insuranceList", searchService.listInsurances().get());
            mav.addObject("insurancePlan", searchService.listInsurancePlan().get());
            mav.addObject("specialtyList", searchService.listSpecialties().get());
            mav.addObject("noLanguage", true);
            mav.addObject("noEducation", true);
            mav.addObject("noCertificate", true);
            mav.addObject("noSpecialty", true);
            mav.addObject("noInsurance", true);
            mav.addObject("EmptyMonday", true);
            mav.addObject("EmptyTuesday", true);
            mav.addObject("EmptyWednesday", true);
            mav.addObject("EmptyThursday", true);
            mav.addObject("EmptyFriday", true);
            mav.addObject("EmptySaturday", true);
            mav.addObject("cancelButton", true);

            try {
                Doctor doctor = doctorService.createDoctor(personalForm.getFirstName(), personalForm.getLastName(), personalForm.getPhoneNumber(),
                        personalForm.getSex(), personalForm.getLicence(), "https://image.freepik.com/free-icon/male-user-shadow_318-34042.jpg", personalForm.getAddress());
                Patient patient = patientService.createPatient(personalForm.getFirstName(), personalForm.getLastName(), personalForm.getPhoneNumber(), personalForm.getEmail(),
                        personalForm.getPassword());
                patientService.setDoctorId(patient.getPatientId(), doctor.getId());

                authenticateUserAndSetSession(patient, ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest());

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

        return mav;
    }

    @RequestMapping(value = "/doctorProfile", method = {RequestMethod.GET})
    public ModelAndView showDoctorProfile(@ModelAttribute("professional")ProfessionalForm professionalForm){

        final ModelAndView mav = new ModelAndView("registerSpecialist2");

        mav.addObject("insuranceList", searchService.listInsurances().get());
        mav.addObject("insurancePlan", searchService.listInsurancePlan().get());
        mav.addObject("specialtyList", searchService.listSpecialties().get());

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Patient patient = patientService.findPatientByEmail(authentication.getName());
        Doctor doctor = doctorService.findDoctorById(patient.getDoctorId()).get();
        doctor.getDescription().getLanguages().remove(null);
        doctor.getSpecialty().remove(null);
        doctor.getInsurance().keySet().remove(null);



        Set<DayOfWeek> emptyWorkingHours = doctor.emptyWorkingHours();
        if(emptyWorkingHours.contains(DayOfWeek.MONDAY)){mav.addObject("EmptyMonday", true);}
        else{mav.addObject("EmptyMonday", false);}
        if(emptyWorkingHours.contains(DayOfWeek.TUESDAY)){mav.addObject("EmptyTuesday", true);}
        else{ mav.addObject("EmptyTuesday", false);}
        if(emptyWorkingHours.contains(DayOfWeek.WEDNESDAY)){mav.addObject("EmptyWednesday", true);}
        else{mav.addObject("EmptyWednesday", false);}
        if(emptyWorkingHours.contains(DayOfWeek.THURSDAY)){mav.addObject("EmptyThursday", true);}
        else{mav.addObject("EmptyThursday", false);}
        if(emptyWorkingHours.contains(DayOfWeek.FRIDAY)){mav.addObject("EmptyFriday", true);}
        else{mav.addObject("EmptyFriday", false);}
        if(emptyWorkingHours.contains(DayOfWeek.SATURDAY)){mav.addObject("EmptySaturday", true);}
        else{mav.addObject("EmptySaturday", false);}

        if(doctor.getDescription().getLanguages().size() == 0 && doctor.getDescription().getEducation() == null &&
                doctor.getDescription().getCertificate() == null && doctor.getSpecialty().isEmpty() && doctor.getInsurance().isEmpty()){
            mav.addObject("noLanguage", true);
            mav.addObject("noEducation", true);
            mav.addObject("noCertificate", true);

        }else{
            mav.addObject("noLanguage", false);
            mav.addObject("noEducation", false);
            mav.addObject("noCertificate", false);
            mav.addObject("cancelButton", false);
        }
        mav.addObject("noSpecialty", true);
        mav.addObject("noInsurance", true);

        return mav;
    }

    @RequestMapping(value = "/doctorProfile", method = {RequestMethod.POST})
    public ModelAndView doctorProfile ( @Valid @ModelAttribute("professional") ProfessionalForm professionalForm, final BindingResult errors){


        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Patient patient = patientService.findPatientByEmail(authentication.getName());
        Doctor doctor = doctorService.findDoctorById(patient.getDoctorId()).get();

        boolean withInfo = false;
        if(doctor.getSpecialty() != null) {
            withInfo = true;
        }


        boolean specialtyExists = false;
        if(professionalForm.getSpecialty() != null){
           specialtyExists = doctor.containsSpecialty(professionalForm.getSpecialty());
        }

        boolean medicalCareExists = false;
        Map<String, Set<String>> insurance = new HashMap<>();
        if(professionalForm.getInsurance() != null || professionalForm.getInsurancePlan() != null){
            insurance = professionalForm.createMap(professionalForm.getInsurance(), professionalForm.getInsurancePlan());
            medicalCareExists = doctor.containsPlan(insurance);
        }


        boolean doctorTime = false;
        if(doctor.getWorkingHours().keySet().isEmpty() && professionalForm.workingHoursList().isEmpty()){doctorTime = true; }

        if(errors.hasErrors() || doctorTime || specialtyExists || medicalCareExists){
            if(doctorTime) System.out.println("doctorTime");
            if(specialtyExists) System.out.println("specialtyExists");
            if(medicalCareExists) System.out.println("medicalCareExists");
            return showDoctorProfile(professionalForm);
        }



        Description description = new Description(professionalForm.getCertificate(), professionalForm.getLanguages(), professionalForm.getEducation());

        List<WorkingHours> workingHours = professionalForm.workingHoursList();

        //when no desceription is avaliable, just set one field
        if(withInfo){
            doctorService.setDoctorSpecialty(doctor.getId(), professionalForm.getSpecialty());
            if(insurance != null){
                doctorService.setDoctorInsurance(doctor.getId(), insurance);
            }
            if(workingHours != null){
                doctorService.setWorkingHours(doctor.getId(), workingHours);
            }
        }else{
            //can't have description values in null;
            Doctor doctorProfessional = doctorService.setDoctorInfo(patient.getDoctorId(), professionalForm.getSpecialty(), insurance,workingHours ,description).get();
        }

        final ModelAndView mav = new ModelAndView("finalStep");
        return mav;
    }


    private void authenticateUserAndSetSession(Patient patient, HttpServletRequest request) {
        String username = patient.getEmail();
        String password = patient.getPassword();

        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username, password);
        // generate session if one doesn't exist
        request.getSession();
        token.setDetails(new WebAuthenticationDetails(request));
        Authentication authenticatedUser = authenticationManager.authenticate(token);
        SecurityContextHolder.getContext().setAuthentication(authenticatedUser);
        request.getSession().setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY, SecurityContextHolder.getContext());
    }

    @RequestMapping(value="/patientRegistration", method = { RequestMethod.GET })
    public ModelAndView showPatientRegistration (@ModelAttribute("personal") PatientForm patientForm,
                                                 @RequestParam(required=false) final String wrongPassword){

        final ModelAndView mav = new ModelAndView("registerPatient");
        mav.addObject("wrongPassword", wrongPassword);

        return mav;
    }

    @RequestMapping(value="/patientRegistration", method = { RequestMethod.POST })
    public ModelAndView patientRegistration (@Valid @ModelAttribute("personal") PatientForm patientForm, final BindingResult errors,
                                             HttpServletRequest request) {

        final ModelAndView mav = new ModelAndView("index");

        if (errors.hasErrors() || !patientForm.matchingPasswords(patientForm.getPassword(), patientForm.getPasswordConfirmation())
            /* || patientService.findPatientByEmail(personalForm.getEmail()) != null*/) {
            if (!patientForm.matchingPasswords(patientForm.getPassword(), patientForm.getPasswordConfirmation())) {
                /*TODO: this doesn't show the error message*/
                showPatientRegistration(patientForm, "wrongPassword");
            }/*else if(patientService.findPatientByEmail(personalForm.getEmail()) != null){
                showDoctorRegistration(personalForm).addObject("userExists", true);
            }*/
            return showPatientRegistration(patientForm, "");
        } else {
            try {
                Patient patient = patientService.createPatient(patientForm.getFirstName(), patientForm.getLastName(), patientForm.getPhoneNumber(), patientForm.getEmail(),
                        patientForm.getPassword());

                authenticateUserAndSetSession(patient, ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest());

                mav.addObject("search", new Search());
                mav.addObject("insuranceList", searchService.listInsurancesWithDoctors().get());
                mav.addObject("specialtyList", searchService.listSpecialtiesWithDoctors().get());

                return mav;

            } catch (IllegalArgumentException ex) {
                return new ModelAndView("500");
            }
        }
    }

}
