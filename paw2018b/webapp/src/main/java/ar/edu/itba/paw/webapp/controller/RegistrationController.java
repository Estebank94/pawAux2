package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.DoctorService;
import ar.edu.itba.paw.interfaces.EmailService;
import ar.edu.itba.paw.interfaces.PatientService;
import ar.edu.itba.paw.interfaces.SearchService;
import ar.edu.itba.paw.models.*;
import ar.edu.itba.paw.models.exceptions.*;
import ar.edu.itba.paw.webapp.forms.PatientForm;
import ar.edu.itba.paw.webapp.forms.PersonalForm;
import ar.edu.itba.paw.webapp.forms.ProfessionalForm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.sound.midi.SysexMessage;
import javax.validation.Valid;
import java.io.IOException;
import java.time.DayOfWeek;
import java.util.*;


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

    @Autowired
    private EmailService emailService;

    @Autowired
    private ApplicationContext applicationContext;

    @RequestMapping(value="/doctorRegistration", method = { RequestMethod.POST })
    public ModelAndView doctorRegistration (@Valid @ModelAttribute("personal") PersonalForm personalForm, final BindingResult errors,HttpServletRequest request)
    {
        LOGGER.debug("RegistrationController: doctorRegistration");

        if(errors.hasErrors() || !personalForm.matchingPasswords(personalForm.getPassword(), personalForm.getPasswordConfirmation()
        )){
            if(!personalForm.matchingPasswords(personalForm.getPassword(), personalForm.getPasswordConfirmation())){
                /*TODO: this doesn't show the error message*/
                showDoctorRegistration(personalForm).addObject("noMatchingPassword", true)
                        .addObject("repeatedEmail",false)
                        .addObject("wrongLastName",false)
                        .addObject("wrongFirstName",false)
                        .addObject("wrongPhoneNumber",false)
                        .addObject("wrongPassword",false)
                        .addObject("wrongEmail",false)
                        .addObject("repeatedLicence",false)
                        .addObject("wrongAddress",false)
                        .addObject("wrongSex",false)
                        .addObject("wrongLicence",false);
            }
            return showDoctorRegistration(personalForm).addObject("noMatchingPassword", true);
        }else{

            final ModelAndView mav = new ModelAndView("registerSpecialist2");
            mav.addObject("professional", new ProfessionalForm());
            mav.addObject("insurances", searchService.listInsurances());
            mav.addObject("specialties", searchService.listSpecialties());
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
//                String image = personalForm.getSex().equals("M") ? "https://i.imgur.com/au1zFvG.jpg" : "https://i.imgur.com/G66Hh4D.jpg";
                Doctor doctor = doctorService.createDoctor(personalForm.getFirstName(), personalForm.getLastName(), personalForm.getPhoneNumber(),
                        personalForm.getSex(), personalForm.getLicence(), null, personalForm.getAddress());
                Patient patient = patientService.createPatient(personalForm.getFirstName(), personalForm.getLastName(), personalForm.getPhoneNumber(), personalForm.getEmail(),
                        personalForm.getPassword());

                patientService.setDoctorId(patient, doctor);

                /*Send welcome email to new user*/
                emailService.sendMessageWithAttachment(patient.getFirstName(), patient.getEmail(), "Bienvenido a Waldoc");

                LOGGER.debug("Auto log in for: {}", patient.getPatientId());
                authenticateUserAndSetSession(patient, ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest());

                mav.addObject("doctor", doctor);

                return mav;

            } catch (NotValidLastNameException e) {
                return showDoctorRegistration(personalForm).addObject("wrongLastName",true);
            } catch (RepeatedEmailException e) {
                return showDoctorRegistration(personalForm).addObject("repeatedEmail",true);
            } catch (NotValidFirstNameException e) {
                return showDoctorRegistration(personalForm).addObject("wrongFirstName",true);
            } catch (NotCreatePatientException e) {
                return new ModelAndView("500");
            } catch (NotValidPhoneNumberException e) {
                return showDoctorRegistration(personalForm).addObject("wrongPhoneNumber",true);
            } catch (NotValidPasswordException e) {
                return showDoctorRegistration(personalForm).addObject("wrongPassword",true);
            } catch (NotValidEmailException e) {
                return showDoctorRegistration(personalForm).addObject("wrongEmail",true);
            } catch (NotCreateDoctorException e) {
                return new ModelAndView("500");
            } catch (RepeatedLicenceException e) {
                return showDoctorRegistration(personalForm).addObject("repeatedLicence",true);
            } catch (NotValidAddressException e) {
                return showDoctorRegistration(personalForm).addObject("wrongAddress",true);
            } catch (NotValidSexException e) {
                return showDoctorRegistration(personalForm).addObject("wrongSex",true);
            } catch (NotValidLicenceException e) {
                return showDoctorRegistration(personalForm).addObject("wrongLicence",true);
            } catch (NotFoundDoctorException e) {
                LOGGER.trace("404");
                return new ModelAndView("404");
            } catch (NotValidPatientIdException e) {
                LOGGER.trace("500");
                return new ModelAndView("500");
            } catch (NotValidDoctorIdException e) {
                LOGGER.trace("500");
                return new ModelAndView("500");
            }
        }

    }

    @RequestMapping(value="/doctorRegistration", method = { RequestMethod.GET })
    public ModelAndView showDoctorRegistration (@ModelAttribute("personal") PersonalForm personalForm){

        LOGGER.debug("RegistrationController: showDoctorRegistration");
        final ModelAndView mav = new ModelAndView("registerSpecialist");

        return mav;
    }

    @RequestMapping(value = "/doctorProfile", method = {RequestMethod.GET})
    public ModelAndView showDoctorProfile(@ModelAttribute("professional")ProfessionalForm professionalForm){

        System.out.print("Entre al Get de DOCTOR_PROFILE");
        LOGGER.debug("RegistrationController: showDoctorProfile");
        final ModelAndView mav = new ModelAndView("registerSpecialist2");

        mav.addObject("insurances", searchService.listInsurances());
        mav.addObject("specialties", searchService.listSpecialties());
        mav.addObject("wrongInsurancePlan",false)
                .addObject("wrongCertificate",false)
                .addObject("wrongWorkingHour",false)
                .addObject("wrongLanguage", false)
                .addObject("wrongSpecialty",false)
                .addObject("wrongDesciption",false)
                .addObject("wrongEducation",false)
                .addObject("wrongCertificate",false);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Patient patient = null;
        try {
            patient = patientService.findPatientByEmail(authentication.getName());
        } catch (NotValidEmailException e) {
            e.printStackTrace();
        } catch (NotFoundPacientException e) {
            e.printStackTrace();
        }
        Doctor doctor = null;
        try {
            doctor = doctorService.findDoctorById(String.valueOf(patient.getDoctor().getId())).get();
        } catch (NotFoundDoctorException e) {
            LOGGER.trace("Error 404");
            return new ModelAndView("404");
        } catch (NotValidIDException e) {
            LOGGER.trace("Error 404");
            return new ModelAndView("404");
        }


        doctor.getSpecialties().remove(null);
        doctor.getInsurancePlans().remove(null);

        LOGGER.debug("Logged: {}", patient.getPatientId());


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


        if(doctor.getDescription() == null  && doctor.getSpecialties().isEmpty() && doctor.getInsurancePlans().isEmpty()){
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

    @ResponseBody
    @RequestMapping(value = "/profile-image/{doctorId}", method = RequestMethod.GET, produces = MediaType.IMAGE_JPEG_VALUE)
    public byte[] avatar(@PathVariable(value = "doctorId") final Integer doctorId ) throws Exception {

        byte[] bytes = doctorService.findDoctorById(String.valueOf(doctorId)).get().getProfilePicture();
        if(bytes != null){
            return bytes;
        }else{

            Resource resource;

            if(doctorService.findDoctorById(String.valueOf(doctorId)).get().getSex().equals("M")){
                resource = applicationContext.getResource("/resources/images/defaultmen.jpg");
            }else{
                resource = applicationContext.getResource("/resources/images/defaultwomen.jpg");
            }

            long resourceLength = resource.contentLength();
            byte[] defaultImage = new byte[(int) resourceLength];
            resource.getInputStream().read(defaultImage);

            return defaultImage;
        }
    }


    @RequestMapping(value = "/doctorProfile", method = {RequestMethod.POST})
    public ModelAndView doctorProfile ( @Valid @ModelAttribute("professional") ProfessionalForm professionalForm, final BindingResult errors){

        LOGGER.debug("RegistrationController: doctorProfile");

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Patient patient = null;
        try {
            patient = patientService.findPatientByEmail(authentication.getName());
        } catch (NotValidEmailException e) {
            e.printStackTrace();
        } catch (NotFoundPacientException e) {
            e.printStackTrace();
        }
        Doctor doctor = null;
        try {
            doctor = doctorService.findDoctorById(String.valueOf(patient.getDoctor().getId())).get();
        } catch (NotFoundDoctorException e) {
            LOGGER.trace("Error 404");
            return new ModelAndView("404");
        } catch (NotValidIDException e) {
            LOGGER.trace("Error 404");
            return new ModelAndView("404");
        }

        boolean withInfo = false;
        doctor.getSpecialties().remove(null);
        if(doctor.getSpecialties() != null) {
            LOGGER.debug("Doctor has description");
            withInfo = true;
        }


        boolean specialtyExists = false;
        if(professionalForm.getSpecialty() != null){
           specialtyExists = doctor.containsSpecialty(professionalForm.getSpecialty());
        }

        boolean medicalCareExists = false;
        Set<Insurance> insurance = new HashSet<>();
        if(professionalForm.getInsurance() != null || professionalForm.getInsurancePlan() != null){
            medicalCareExists = doctor.containsPlan(professionalForm.getInsurancePlan());
        }


        boolean doctorTime = false;
        if(doctor.getWorkingHours().isEmpty() && professionalForm.workingHoursList().isEmpty()){ doctorTime = true; }

        if(errors.hasErrors()  || doctorTime /*specialtyExists || medicalCareExists*/){
            return showDoctorProfile(professionalForm);
        }

        MultipartFile file = professionalForm.getAvatar();

        if( file != null && file.getSize() != 0 ){

            String mimetype = file.getContentType();
            String type = mimetype.split("/")[0];

            if (!type.equals("image")) {
                LOGGER.warn("File is not an image");
            }else {
                try {
                    doctorService.setDoctorAvatar(doctor, file.getBytes());
                } catch (IOException e) {
                    LOGGER.warn("Could not upload image");
                }
            }

        }

        Description description = new Description(professionalForm.getCertificate(), professionalForm.getLanguages(), professionalForm.getEducation());

        List<WorkingHours> workingHours = professionalForm.workingHoursList();

        /* when no description is available, just set one field */
        if(withInfo){
            doctorService.setDoctorSpecialty(doctor, professionalForm.getSpecialties());
            if(insurance != null){
                LOGGER.debug("SET Doctor's insurance to DB");
                doctorService.setDoctorInsurancePlans(doctor, professionalForm.getInsurancePlans());
            }
            if(workingHours != null){
                LOGGER.debug("SET Doctor's workingHours to DB");
                doctorService.setWorkingHours(doctor, workingHours);
            }
            if(description != null){
                doctorService.setDescription(doctor, description);
            }
        }else{

            /* can't have description values in null */

            LOGGER.debug("SET full Doctor's information to DB");
            try {
                Doctor doctorProfessional = doctorService.setDoctorInfo(patient.getDoctor().getId(), professionalForm.getSpecialties(), insurance,workingHours ,description).get();
            } catch (NotValidDoctorIdException e) {
                LOGGER.trace("Error 404");
                return new ModelAndView("404");
            } catch (NotFoundDoctorException e) {
                LOGGER.trace("Error 404");
                return new ModelAndView("404");
            } catch (NotValidInsurancePlanException e) {
                LOGGER.debug("Wrong InsurancePlan Input");
                return showDoctorProfile(professionalForm).addObject("wrongInsurancePlan",true);
            } catch (NotValidCertificateException e) {
                LOGGER.debug("Wrong Certificate Input");
                return showDoctorProfile(professionalForm).addObject("wrongCertificate",true);
            } catch (NotValidWorkingHourException e) {
                LOGGER.debug("Wrong WorkingHour Input");
                return showDoctorProfile(professionalForm).addObject("wrongWorkingHour",true);
            } catch (NotValidLanguagesException e) {
                LOGGER.debug("Wrong Language Input");
                return showDoctorProfile(professionalForm).addObject("wrongLanguage",true);
            } catch (NotValidSpecialtyException e) {
                LOGGER.debug("Wrong specialty Input");
                return showDoctorProfile(professionalForm).addObject("wrongSpecialty",true);
            } catch (NotValidDescriptionException e) {
                LOGGER.debug("Wrong Description Input");
                return showDoctorProfile(professionalForm).addObject("wrongDesciption",true);
            } catch (NotValidEducationException e) {
                LOGGER.debug("Wrong education Input");
                return showDoctorProfile(professionalForm).addObject("wrongEducation",true);
            } catch (NotValidInsuranceException e) {
                LOGGER.debug("Wrong Certificate Input");
                return showDoctorProfile(professionalForm).addObject("wrongCertificat",true);
            }
        }
        LOGGER.debug("AutoLogIn of patient with ID: {}", patient.getPatientId());
        authenticateUserAndSetSession(patient, ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest());

        final ModelAndView mav = new ModelAndView("index");
        mav.addObject("search", new Search());
//        TODO LIST ONLY INSURANCES AND SPECIALTIES WITH DOCTORS
        mav.addObject("insuranceList", searchService.listInsurances());
        mav.addObject("specialtyList", searchService.listSpecialties());

        return mav;
    }


    private void authenticateUserAndSetSession(Patient patient, HttpServletRequest request) {

        String username = patient.getEmail();
        String password = patient.getPassword();

        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username, password);

        /* generate session if one doesn't exist */
        request.getSession();
        token.setDetails(new WebAuthenticationDetails(request));
        Authentication authenticatedUser = authenticationManager.authenticate(token);
        SecurityContextHolder.getContext().setAuthentication(authenticatedUser);
        request.getSession().setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY, SecurityContextHolder.getContext());
    }

    @RequestMapping(value="/patientRegistration", method = { RequestMethod.GET })
    public ModelAndView showPatientRegistration (@ModelAttribute("personal") PatientForm patientForm,
                                                 @RequestParam(required=false) final String wrongPassword){

        LOGGER.debug("RegistrationController: showPatientRegistration");
        final ModelAndView mav = new ModelAndView("registerPatient");
        mav.addObject("wrongPassword", wrongPassword)
                .addObject("repeatedEmail",false)
                .addObject("wrongLastName",false)
                .addObject("wrongFirstName",false)
                .addObject("wrongPhoneNumber",false)
                .addObject("wrongPassword",false)
                .addObject("wrongEmail",false);
        return mav;
    }

    @RequestMapping(value="/patientRegistration", method = { RequestMethod.POST })
    public ModelAndView patientRegistration (@Valid @ModelAttribute("personal") PatientForm patientForm, final BindingResult errors,
                                             HttpServletRequest request) {

        LOGGER.debug("RegistrationController: patientRegistration");
        final ModelAndView mav = new ModelAndView("index");

        if (errors.hasErrors() || !patientForm.matchingPasswords(patientForm.getPassword(), patientForm.getPasswordConfirmation())
            /* || patientService.findPatientByEmail(personalForm.getEmail()) != null*/) {
            if (!patientForm.matchingPasswords(patientForm.getPassword(), patientForm.getPasswordConfirmation())) {

                showPatientRegistration(patientForm, "wrongPassword");
            }/*else if(patientService.findPatientByEmail(personalForm.getEmail()) != null){
                showDoctorRegistration(personalForm).addObject("userExists", true);
            }*/
            return showPatientRegistration(patientForm, "").addObject("noMatchingPassword", true);
        } else {
            try {
                LOGGER.debug("patientRegistration: create a patient");
                Patient patient = patientService.createPatient(patientForm.getFirstName(), patientForm.getLastName(), patientForm.getPhoneNumber(), patientForm.getEmail(),
                        patientForm.getPassword());

                LOGGER.debug("AutoLogIn of patient with ID: {}", patient.getPatientId());
                authenticateUserAndSetSession(patient, ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest());

                /* Send welcome email to new user */
                emailService.sendMessageWithAttachment(patient.getFirstName(), patient.getEmail(), "Bienvenido a Waldoc");

                mav.addObject("search", new Search());
                mav.addObject("insuranceList", searchService.listInsurances());
                mav.addObject("specialtyList", searchService.listSpecialties());

                return mav;
            } catch (NotValidLastNameException e) {
                return showPatientRegistration(patientForm,"lastName").addObject("wrongLastName",true);
            } catch (RepeatedEmailException e) {
                return showPatientRegistration(patientForm, "RepeatedKeyError").addObject("repeatedEmail",true);
            } catch (NotValidFirstNameException e) {
                return showPatientRegistration(patientForm,"firstName").addObject("wrongFirstName",true);
            } catch (NotCreatePatientException e) {
                return new ModelAndView("500");
            } catch (NotValidPhoneNumberException e) {
                return showPatientRegistration(patientForm,"lastName").addObject("wrongPhoneNumber",true);
            } catch (NotValidPasswordException e) {
                return showPatientRegistration(patientForm,"lastName").addObject("wrongPassword",true);
            } catch (NotValidEmailException e) {
                return showPatientRegistration(patientForm,"lastName").addObject("wrongEmail",true);
            }
        }
    }

}
