package ar.edu.itba.paw.webapp.api.v1;

import ar.edu.itba.paw.interfaces.services.*;
import ar.edu.itba.paw.models.*;

import ar.edu.itba.paw.models.exceptions.NotFoundDoctorException;
import ar.edu.itba.paw.models.exceptions.NotValidIDException;
import ar.edu.itba.paw.models.exceptions.NotValidPageException;

import ar.edu.itba.paw.models.exceptions.*;


import ar.edu.itba.paw.webapp.auth.UserDetailsServiceImpl;

import ar.edu.itba.paw.webapp.dto.doctor.DoctorDTO;
import ar.edu.itba.paw.webapp.dto.doctor.DoctorListDTO;
import ar.edu.itba.paw.webapp.dto.doctor.DoctorPersonalDTO;
import ar.edu.itba.paw.webapp.dto.patient.PatientDTO;
import ar.edu.itba.paw.webapp.dto.reviews.BasicReviewDTO;
import ar.edu.itba.paw.webapp.dto.workingHours.WorkingHoursDTO;
import ar.edu.itba.paw.webapp.forms.*;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.thymeleaf.TemplateEngine;

import javax.validation.Valid;
import javax.ws.rs.*;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.net.URI;

import java.time.LocalDate;
import java.util.*;


@Path("v1/doctor")
@Controller
public class DoctorApiController extends BaseApiController {

    private static final Logger LOGGER = LoggerFactory.getLogger(DoctorApiController.class);

    @Autowired
    private DoctorService doctorService;

    @Autowired
    private PatientService patientService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private AppointmentService appointmentService;

    @Autowired
    private ReviewService reviewService;


    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Context
    private UriInfo uriInfo;

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private TemplateEngine htmlTemplateEngine;

    @Autowired
    private ApplicationContext applicationContext;

    private static final String VERIFICATION_TOKEN_TEMPLATE_NAME = "welcomeMail.html";

    @Autowired
    private String frontUrl;


    @GET
    @Path("/{id}")
    @Produces(value = {MediaType.APPLICATION_JSON})
    public Response getDoctorById(@PathParam("id") final int id){
        Doctor doctor = null;
        try {
            doctor = doctorService.findDoctorById(id + "");
        } catch (NotFoundDoctorException | NotValidIDException e) {
            Response.status(Response.Status.NOT_FOUND).build();
            // e.printStackTrace();
        }
        if (doctor != null){
            return Response.ok(new DoctorDTO(doctor))
                    .build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    @GET
    @Path("/list")
    @Produces(value = {MediaType.APPLICATION_JSON})
    public Response listDoctors(@QueryParam("page") @DefaultValue("0") int page,
                                @QueryParam("specialty") String specialty,
                                @QueryParam("name") String name,
                                @QueryParam("insurance")String insurance,
                                @QueryParam("sex") String sex,
                                @QueryParam("insurancePlan")List<String> insurancePlan,
                                @QueryParam("days") List<String> days) {

        Search search = new Search();
        if (specialty != null) {
            if (specialty.length() == 0){
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity(errorMessageToJSON("Specialty bad format")).build();
            }
            search.setSpecialty(specialty);
        }
        if (name != null) {
            if (name.length() == 0){
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity(errorMessageToJSON("name bad format")).build();
            }
            search.setName(name);
        }
        if (insurance != null){
            if (insurance.length() == 0){
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity(errorMessageToJSON("Insurance bad format")).build();
            }
            search.setInsurance(insurance);
        }
        if (sex != null){
            if (sex.length() == 0){
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity(errorMessageToJSON("sex bad format")).build();
            }
            search.setSex(sex);
        }
        if (insurancePlan != null && insurancePlan.size() > 0){
            for (String ip : insurancePlan){
                if (ip.length() == 0){
                    return Response.status(Response.Status.BAD_REQUEST)
                            .entity(errorMessageToJSON("insurancePlan bad format")).build();
                }
            }
            search.setInsurancePlan(insurancePlan);
        }
        if (days != null && days.size() > 0){
            for (String dayIterator: days){
                if (dayIterator.length() != 1) {
                    return Response.status(Response.Status.BAD_REQUEST)
                            .entity(errorMessageToJSON("DayWeek is not an Digit between 1-7")).build();
                }

                if (!Character.isDigit(dayIterator.charAt(0))){
                    return Response.status(Response.Status.BAD_REQUEST)
                            .entity(errorMessageToJSON("DayWeek is not an Digit")).build();
                }

                if ((Integer.valueOf(dayIterator) > 7) || (Integer.valueOf(dayIterator) < 1)){
                    return Response.status(Response.Status.BAD_REQUEST)
                            .entity(errorMessageToJSON("DayWeek is not an Digit between 1-7")).build();
                }
            }
            search.setDays(days);
        }

        List<Doctor> doctors;
        try {
            doctors = doctorService.listDoctors(search, String.valueOf(page));
        } catch (NotValidPageException e){
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(errorMessageToJSON("DayWeek is not an Digit between 1-7")).build();
        }

        Long totalPageCount = doctorService.getLastPage(search);
        return Response.ok(new DoctorListDTO(doctors, totalPageCount)).build();
    }

    @GET
    @Path("/all")
    public Response allDoctors() {
        List<Doctor> doctorList = doctorService.listDoctors();
        Long totalPageCount = doctorService.getLastPage();
        return Response.ok(new DoctorListDTO(doctorList, totalPageCount)).build();
    }

    @POST
    @Path("/register")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(value = {MediaType.APPLICATION_JSON})
    public Response createUser(@Valid final PersonalForm userForm) {

        if (userForm == null)
            return Response.status(Response.Status.BAD_REQUEST).build();

        if (!userForm.getPassword().equals(userForm.getPasswordConfirmation()))
            return Response
                    .status(Response.Status.BAD_REQUEST)
                    .entity(errorMessageToJSON(messageSource.getMessage("non_matching_passwords", null, LocaleContextHolder.getLocale())))
                    .build();

        String image = userForm.getSex().equals("M") ? "https://i.imgur.com/au1zFvG.jpg" : "https://i.imgur.com/G66Hh4D.jpg";

        Doctor doctor = null;
        Patient patient = null;

        try{
            patient = patientService.createPatient(userForm.getFirstName(), userForm.getLastName(),
                    userForm.getPhoneNumber(), userForm.getEmail(), userForm.getPassword());
            doctor = doctorService.createDoctor(userForm.getFirstName(), userForm.getLastName(), userForm.getPhoneNumber(),
                    userForm.getSex(), userForm.getLicence(), null, userForm.getAddress());
            patientService.setDoctorId(patient, doctor);
        }
        catch (RepeatedEmailException e){
            return Response.status(Response.Status.CONFLICT)
                    .entity(errorMessageToJSON("Duplicated email")).build();
        }catch (NotValidFirstNameException | NotValidLastNameException | NotValidPhoneNumberException |
              RepeatedLicenceException | NotValidLicenceException | NotValidAddressException |
                NotValidSexException | NotValidPasswordException | NotValidEmailException e){
            return Response.status(Response.Status.CONFLICT)
                    .entity(errorMessageToJSON("There is an error on the form information")).build();
        }catch (NotFoundDoctorException | NotCreateDoctorException e){
            return Response.status(Response.Status.CONFLICT)
                    .entity(errorMessageToJSON("Doctor not found")).build();
        }catch(NotCreatePatientException e){
            return Response.status(Response.Status.CONFLICT)
                    .entity(errorMessageToJSON("Patient not created")).build();
        }catch(NotValidPatientIdException | NotValidDoctorIdException e){
            return Response.status(Response.Status.CONFLICT)
                    .entity(errorMessageToJSON("Id from doctor or patient not valid. Error associating")).build();
        }


        final Verification verification = patientService.createToken(patient);

        /* Prepare the evaluation context */
        final org.thymeleaf.context.Context ctx = new org.thymeleaf.context.Context(LocaleContextHolder.getLocale());
        final String confirmationUrl = frontUrl + "confirm?token=" + verification.getToken();
        ctx.setVariable("patientName", patient.getFirstName());
        ctx.setVariable("confirmationUrl", confirmationUrl);
        final String htmlContent = this.htmlTemplateEngine.process(VERIFICATION_TOKEN_TEMPLATE_NAME, ctx);
        final String subject = applicationContext.getMessage("mail.subject", null, LocaleContextHolder.getLocale());

        /* send welcome email */
        emailService.sendEmail(patient.getEmail(), subject, htmlContent);

        final URI uri = uriInfo.getAbsolutePathBuilder().path(String.valueOf(doctor.getId())).build();

        return Response.created(uri).entity(new PatientDTO(patient, buildBaseURI(uriInfo))).build();
//        return Response.created(uri).entity(new DoctorPatientDTO(patient, buildBaseURI(uriInfo))).build();
    }

    @POST
    @Path("/registerProfessional")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(value = {MediaType.APPLICATION_JSON})
    public Response createProfessionalUser(@Valid final BasicProfessionalForm professionalForm){

        LOGGER.debug("entre");
        Patient patient = null;
        try{
            patient = userDetailsService.getLoggedUser();
        } catch (NotFoundPacientException | NotValidEmailException e){
            return Response.status(Response.Status.CONFLICT)
                    .entity(errorMessageToJSON("Doctor/Patient not found")).build();
        }

        Doctor doctor = patient.getDoctor();
        if(doctor == null){
            //hacer algo
            return Response.status(Response.Status.CONFLICT)
                    .entity(errorMessageToJSON("Doctor is NULL")).build();
        }

        /* Avatar */
//        MultipartFile file = professionalForm.getAvatar();
//
//        if( file != null && file.getSize() != 0 ){
//
//            String mimetype = file.getContentType();
//            String type = mimetype.split("/")[0];
//
//            if (!type.equals("image")) {
//                LOGGER.warn("File is not an image");
//            }else {
//                try {
//                    doctorService.setDoctorAvatar(doctor, file.getBytes());
//                } catch (IOException e) {
//                    LOGGER.warn("Could not upload image");
//                }
//            }
//        }

        Description description;
        if(professionalForm.getCertificate() != null && professionalForm.getLanguages()
                != null && professionalForm.getEducation() != null){

            description = new Description(professionalForm.getCertificate(),
                    professionalForm.getLanguages(), professionalForm.getEducation());

            doctorService.setDescription(doctor,description);
        }

        if(professionalForm.getSpecialty() != null){
            Set<Specialty> specialties = new HashSet<>();
            for(String sp : professionalForm.getSpecialty()){
                specialties.add(new Specialty(sp));
            }
            doctorService.setDoctorSpecialty(doctor,specialties);
        }

        if(professionalForm.getInsurancePlan() != null){
            LOGGER.debug("ENTRE A insurances");
            LOGGER.debug("insuracePlan" + professionalForm.getInsurancePlan().size());
            doctorService.setDoctorInsurancePlans(doctor, professionalForm.getInsurancePlans());
        }

        List<WorkingHours> workingHoursList = new ArrayList<>();
        for(WorkingHoursDTO w : professionalForm.getWorkingHours()){
            WorkingHours workingHours = new WorkingHours(w.getDayOfWeek(), w.getStartTime(), w.getFinishTime());
            workingHoursList.add(workingHours);
        }

        LOGGER.debug("workH" + workingHoursList.size());
        doctorService.setWorkingHours(doctor, workingHoursList);


        final URI uri = uriInfo.getAbsolutePathBuilder().path(String.valueOf(doctor.getId())).build();

        return Response.created(uri).entity(new DoctorDTO(doctor, buildBaseURI(uriInfo))).build();
    }

    @POST
    @Path("/{id}/review")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(value = MediaType.APPLICATION_JSON)
    public Response createReview(@PathParam("id") final int doctorId , @Valid final ReviewForm reviewForm){

        LOGGER.debug("Create Review");

        /* Form revision */
        if (reviewForm == null){
            LOGGER.debug("Review Form is null");
            return Response.status(Response.Status.BAD_REQUEST).entity(errorMessageToJSON("Review can't be null")).build();
        }

        if (reviewForm.getStars() == null && reviewForm.getDescription() == null){
            LOGGER.debug("No stars or description in review");
            return Response
                    .status(Response.Status.BAD_REQUEST)
                    .entity(errorMessageToJSON("No stars or description in review"))
                    .build();
        }

        if (reviewForm.getApponintmentId() == null){
            LOGGER.debug("No appointment");
            return Response
                    .status(Response.Status.BAD_REQUEST)
                    .entity(errorMessageToJSON("No appointment"))
                    .build();
        }


        /* Patient Revision */
        Patient patient = null;
        try {
            patient = userDetailsService.getLoggedUser();
        } catch (NotFoundPacientException e) {
            LOGGER.debug("Patient not found");
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(errorMessageToJSON("Patient not found"))
                    .build();
        } catch (NotValidEmailException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(errorMessageToJSON("Patient with bad email"))
                    .build();
        }
        if (patient == null){
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(errorMessageToJSON("Patient not found"))
                    .build();
        }
        LOGGER.debug("Review patient {}", patient.getId());

        if (patient.getDoctor().getId() == doctorId){
            LOGGER.debug("Patient is the same as doctor");
            return Response
                    .status(Response.Status.BAD_REQUEST)
                    .entity(errorMessageToJSON("Cannot set review a doctor to his own"))
                    .build();
        }

        /* Doctor Revision */
        Doctor doctor = null;
        try {
            doctor = doctorService.findDoctorById(String.valueOf(doctorId));
        } catch (NotFoundDoctorException e) {
            LOGGER.debug("Doctor with id {} not found", doctorId);
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(errorMessageToJSON("Doctor not found"))
                    .build();
        } catch (NotValidIDException e) {
            return Response
                    .status(Response.Status.BAD_REQUEST)
                    .entity(errorMessageToJSON("Doctor with bad id"))
                    .build();
        }
        if (doctor == null){
            LOGGER.debug("Doctor with id {} not found", doctorId);
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(errorMessageToJSON("Doctor not found"))
                    .build();
        }
        LOGGER.debug("Review doctor {}", doctor.getId());

        /* Appointment revision */
        Appointment appointment = null;

        try {
            appointmentService.findAppointmentById(reviewForm.getApponintmentId());
        } catch (NotFoundAppointmentException e) {
            LOGGER.debug("Appointment with id {} not found", reviewForm.getApponintmentId());
            return Response.status(Response.Status.BAD_REQUEST).entity(errorMessageToJSON("appointment not found")).build();
        }
        if (appointment == null){
            LOGGER.debug("Appointment with id {} not found", reviewForm.getApponintmentId());
            return Response.status(Response.Status.BAD_REQUEST).entity(errorMessageToJSON("appointment not found")).build();
        }

        if (appointment.getDoctor().getId() != doctorId || appointment.getPatient().getId() != patient.getId()){
            LOGGER.debug("Appointment with id {} found with others doctors/patients", reviewForm.getApponintmentId());
            return Response.status(Response.Status.BAD_REQUEST).entity(errorMessageToJSON("appointment found with others doctors/patients")).build();
        }
        LocalDate appointmentDay = LocalDate.parse(appointment.getAppointmentDay());
        if (LocalDate.now().isBefore(appointmentDay)){
            LOGGER.debug("Appointment with id {} found still not happen", reviewForm.getApponintmentId());
            return Response.status(Response.Status.BAD_REQUEST).entity(errorMessageToJSON("appointment found still not happen")).build();
        }

        if (appointment.getAppointmentCancelled()){
            LOGGER.debug("Appointment is cancelled", reviewForm.getApponintmentId());
            return Response.status(Response.Status.BAD_REQUEST).entity(errorMessageToJSON("appointment is cancelled")).build();
        }

        if (appointment.getReview() != null) {
            LOGGER.debug("Appointment already has review", reviewForm.getApponintmentId());
            return Response.status(Response.Status.BAD_REQUEST).entity(errorMessageToJSON("Appointment already has review")).build();
        }
            Review review = null;
        try {
            review = reviewService.createReview(reviewForm.getStars(), reviewForm.getDescription(), doctor, patient, appointment);
        } catch (NotValidReviewException e) {
            LOGGER.debug("Not valid review");
            return Response.status(Response.Status.BAD_REQUEST).entity(errorMessageToJSON("Not valid Review")).build();
        }
        if (review == null){
            Response.status(Response.Status.BAD_REQUEST).entity(errorMessageToJSON("Not created Review")).build();
        }

        return Response.ok(new BasicReviewDTO(review)).build();
    }

    @PUT
    @Path("/{id}/appointment/add")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(value = MediaType.APPLICATION_JSON)
    public Response addAppointment(@Valid final AppointmentForm appointmentForm){
        return Response.status(Response.Status.NOT_IMPLEMENTED).build();
    }

    @PUT
    @Path("/{id}/appointment/cancel")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(value = MediaType.APPLICATION_JSON)
    public Response cancelAppointment(@Valid final AppointmentForm appointmentForm){
        return Response.status(Response.Status.NOT_IMPLEMENTED).build();
    }

    @PUT
    @Path("/{id}/favorite/add")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(value = MediaType.APPLICATION_JSON)
    public Response addFavorite(@Valid final FavoriteForm favoriteForm){
        return Response.status(Response.Status.NOT_IMPLEMENTED).build();
    }

    @PUT
    @Path("/{id}/favorite/cancel")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(value = MediaType.APPLICATION_JSON)
    public Response cancelFavorite(@Valid final FavoriteForm favoriteForm){
        return Response.status(Response.Status.NOT_IMPLEMENTED).build();
    }


}
