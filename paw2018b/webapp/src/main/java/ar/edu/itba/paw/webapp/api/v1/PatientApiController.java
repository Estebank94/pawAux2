package ar.edu.itba.paw.webapp.api.v1;

import ar.edu.itba.paw.interfaces.services.AppointmentService;
import ar.edu.itba.paw.interfaces.services.DoctorService;
import ar.edu.itba.paw.interfaces.services.EmailService;
import ar.edu.itba.paw.interfaces.services.PatientService;
import ar.edu.itba.paw.models.*;
import ar.edu.itba.paw.models.exceptions.*;
import ar.edu.itba.paw.webapp.auth.UserDetailsServiceImpl;
import ar.edu.itba.paw.webapp.dto.appointment.DoctorAppointmentDTO;
import ar.edu.itba.paw.webapp.dto.patient.PatientDTO;
import ar.edu.itba.paw.webapp.dto.patient.PatientPersonalInformationDTO;
import ar.edu.itba.paw.webapp.forms.AppointmentForm;
import ar.edu.itba.paw.webapp.forms.PatientForm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.Collections;
import java.util.List;

@Path("v1/patient")
@Controller
public class PatientApiController extends BaseApiController {
    private static final Logger LOGGER = LoggerFactory.getLogger(PatientApiController.class);

    @Autowired
    private PatientService patientService;

    @Context
    private UriInfo uriInfo;

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private String frontUrl;

    @Autowired
    private TemplateEngine htmlTemplateEngine;

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private EmailService emailService;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    private AppointmentService appointmentService;

    @Autowired
    private DoctorService doctorService;

    private static final String VERIFICATION_TOKEN_TEMPLATE_NAME = "welcomeMail.html";

    @GET
    @Path("/{id}")
    @Produces(value = {MediaType.APPLICATION_JSON})
    public Response getPatientById(@PathParam("id")final int id) {
        Patient patient = new Patient();
        try {
            patient = patientService.findPatientById(id);
        } catch (NotValidPatientIdException e){
            return Response.status(Response.Status.BAD_REQUEST).build();
        } catch (NotFoundPacientException | NotCreatePatientException e){
            return Response.status(Response.Status.NOT_FOUND)
                    .build();
        }
        if (patient == null){
            LOGGER.warn("Patient with id {} not found", id);
            return Response.status(Response.Status.NOT_FOUND)
                    .build();
        }
        return Response.ok(new PatientDTO(patient)).build();
    }

    @GET
    @Produces(value = {MediaType.APPLICATION_JSON})
    public Response getPatientByEmail(@QueryParam("email")final String email) {
        Patient patient = new Patient();
        try {
            patient = patientService.findPatientByEmail(email);
        } catch ( NotCreatePatientException | NotFoundPacientException e) {
            return Response.status(Response.Status.NOT_FOUND).build();
        } catch (NotValidEmailException | NotValidPatientIdException e){
            return Response
                    .status(Response.Status.BAD_REQUEST)
                    .entity(errorMessageToJSON(messageSource.getMessage("non_matching_passwords", null, LocaleContextHolder.getLocale())))
                    .build();
        }

        if (patient == null){
            LOGGER.warn("Patient with email {} not found", email);
            return Response.status(Response.Status.NOT_FOUND)
                    .build();
        }
        return Response.ok(new PatientDTO(patient)).build();
    }

    @POST
    @Path("/register")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(value = {MediaType.APPLICATION_JSON})
    public Response createUser(@Valid final PatientForm userForm) {

        if (userForm == null)
            return Response.status(Response.Status.BAD_REQUEST).build();

        if (!userForm.getPassword().equals(userForm.getPasswordConfirmation()))
            return Response
                    .status(Response.Status.BAD_REQUEST)
                    .entity(errorMessageToJSON(messageSource.getMessage("non_matching_passwords", null, LocaleContextHolder.getLocale())))
                    .build();

        Patient patient = null;

        try {
            patient = patientService.createPatient(userForm.getFirstName(), userForm.getLastName(),
                    userForm.getPhoneNumber(), userForm.getEmail(), userForm.getPassword());
        } catch (RepeatedEmailException e){
            return Response.status(Response.Status.CONFLICT)
                    .entity(errorMessageToJSON("Duplicated email")).build();
        } catch (NotValidFirstNameException | NotValidLastNameException | NotValidPhoneNumberException |
                NotValidEmailException | NotValidPasswordException e){
            return Response.status(Response.Status.CONFLICT)
                    .entity(errorMessageToJSON("There is an error on the form information")).build();
        } catch(NotCreatePatientException e){
            return Response.status(Response.Status.CONFLICT)
                    .entity(errorMessageToJSON("Patient not created")).build();
        }


        final Verification verification = patientService.createToken(patient);

        // Prepare the evaluation context
        final org.thymeleaf.context.Context ctx = new org.thymeleaf.context.Context(LocaleContextHolder.getLocale());
        final String confirmationUrl = frontUrl + "confirm?token=" + verification.getToken();
        ctx.setVariable("patientName", patient.getFirstName());
        ctx.setVariable("confirmationUrl", confirmationUrl);
        final String htmlContent = this.htmlTemplateEngine.process(VERIFICATION_TOKEN_TEMPLATE_NAME, ctx);
        final String subject = applicationContext.getMessage("mail.subject", null, LocaleContextHolder.getLocale());

        // send welcome email
        emailService.sendEmail(patient.getEmail(), subject, htmlContent);

        final URI uri = uriInfo.getAbsolutePathBuilder().path(String.valueOf(patient.getId())).build();

        return Response.created(uri).entity(new PatientDTO(patient, buildBaseURI(uriInfo))).build();
    }

    @GET
    @Path("/confirm")
    @Produces(value = { MediaType.APPLICATION_JSON, })
    public Response confirmUser(@QueryParam("token") @DefaultValue("") String token) throws VerificationNotFoundException {

        Patient patient = null;
        try{
            patient = patientService.confirmUser(token);
        } catch (VerificationNotFoundException e){
            return Response.status(Response.Status.CONFLICT)
                    .entity(errorMessageToJSON("You have already verified your email")).build();
        }

        if (patient == null){
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(errorMessageToJSON("Patient not found")).build();
        }
        //final Patient patient = verification.getPatient();
        //patientService.enableUser(patient);

        /* Auto Login - todo: se esta rompiendo ? */
        Authentication authentication = new UsernamePasswordAuthenticationToken(patient.getEmail(), patient.getPassword());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        SecurityContextHolder.getContext().setAuthentication(authentication);

        final URI uri = uriInfo.getAbsolutePathBuilder().path(String.valueOf(patient.getId())).build();

        return Response.created(uri).entity(new PatientDTO(patient, buildBaseURI(uriInfo))).build();
    }

    @GET
    @Path("/me")
    @Produces(value = { MediaType.APPLICATION_JSON, })
    public Response loggedUser() {
        final Patient patient;
        try {
            patient = userDetailsService.getLoggedUser();
        } catch (NotFoundPacientException | NotValidEmailException e) {
            return Response
                    .status(Response.Status.NOT_FOUND)
                    .build();
        }

        return Response.ok(new PatientDTO(patient, false)).build();
    }

    @GET
    @Path("/personal")
    @Produces (value = {MediaType.APPLICATION_JSON})
    public Response personalInformationWithLogged () {
        LOGGER.debug("Patient personal information");
        final Patient patient;
        try {
            patient = userDetailsService.getLoggedUser();
        } catch (NotFoundPacientException e) {
            return Response
                    .status(Response.Status.NOT_FOUND)
                    .entity(errorMessageToJSON("patient logged not found"))
                    .build();
        } catch ( NotValidEmailException e){
            return Response
                    .status(Response.Status.BAD_REQUEST)
                    .entity(errorMessageToJSON("Invalid email of patient"))
                    .build();
        }
        if (patient == null){
            Response
                    .status(Response.Status.NOT_FOUND)
                    .entity(errorMessageToJSON("patient logged not found")) //messageSource.getMessage("patient not found", null, LocaleContextHolder.getLocale())))
                    .build();
        }

        List<Appointment> historicalAppointments = patientService.getHistoricalAppointments(patient);
        List<Appointment> futureAppointments = patientService.getFutureAppointments(patient);
        List<Favorite> favorites = patientService.getFavoriteDoctors(patient);

        List<Appointment> futureAppointmentsDoctor = Collections.EMPTY_LIST;
        List<Appointment> historicalAppointmentsDoctor = Collections.EMPTY_LIST;
        List<Review> reviews = Collections.EMPTY_LIST;

        Doctor doctor = patient.getDoctor();
        if (patient.getDoctor() != null) {
            futureAppointmentsDoctor = doctorService.getFutureAppointments(doctor);
            historicalAppointmentsDoctor = doctorService.getHistoricalAppointments(doctor);
            reviews = Collections.EMPTY_LIST;
            reviews = doctorService.getReviews(doctor);
            return Response.ok(new PatientPersonalInformationDTO(historicalAppointments, futureAppointments, favorites,
                    futureAppointmentsDoctor, historicalAppointmentsDoctor, reviews)).build();
        }

        return Response.ok(new PatientPersonalInformationDTO(historicalAppointments, futureAppointments, favorites)).build();
    }

    @PUT
    @Path("/{id}/appointment/cancel")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(value = MediaType.APPLICATION_JSON)
    public Response cancelAppointment(@Valid final AppointmentForm appointmentForm, @PathParam("id") final int patientId){

        if (appointmentForm == null){
            LOGGER.debug("Appointment form is null");
            return Response
                    .status(Response.Status.BAD_REQUEST)
                    .entity(errorMessageToJSON("Appointment Form is null"))
                    .build();
        }

        if (appointmentForm.getDay() == null){
            LOGGER.debug("Appointment day is null");
            return Response
                    .status(Response.Status.BAD_REQUEST)
                    .entity(errorMessageToJSON("Appointment Day is null"))
                    .build();
        }

        LocalDate appointmentDate = null;

        try {
            appointmentDate = LocalDate.parse(appointmentForm.getDay());
        } catch (DateTimeParseException e){
            LOGGER.debug("Appointment day is not ISO FORMAT 'YYYY-MM-DD'");
            return Response
                    .status(Response.Status.BAD_REQUEST)
                    .entity(errorMessageToJSON("Appointment day is not ISO FORMAT 'YYYY-MM-DD'"))
                    .build();
        } catch (Exception e){
            LOGGER.debug("Appointment day is not ISO FORMAT 'YYYY-MM-DD'");
            return Response
                    .status(Response.Status.BAD_REQUEST)
                    .entity(errorMessageToJSON("Appointment day is not ISO FORMAT 'YYYY-MM-DD'"))
                    .build();
        }

        if (appointmentForm.getTime() == null){
            LOGGER.debug("No appointment time");
            return Response
                    .status(Response.Status.BAD_REQUEST)
                    .entity(errorMessageToJSON("No appointment time"))
                    .build();
        }
        LocalTime appointmentTime = null;
        try{
            appointmentTime = LocalTime.parse(appointmentForm.getTime());
        } catch (DateTimeParseException e){
            LOGGER.debug("Appointment time is not FORMAT 'HH:mm'");
            return Response
                    .status(Response.Status.BAD_REQUEST)
                    .entity(errorMessageToJSON("Appointment time is not FORMAT 'HH:mm'"))
                    .build();
        } catch (Exception e) {
            LOGGER.debug("Appointment time is not FORMAT 'HH:mm'");
            return Response
                    .status(Response.Status.BAD_REQUEST)
                    .entity(errorMessageToJSON("Appointment time is not FORMAT 'HH:mm'"))
                    .build();
        }

        LocalDateTime appointmentDayTime = LocalDateTime.of(appointmentDate, appointmentTime);
        LocalDateTime now = LocalDateTime.now();

        if (now.isAfter(appointmentDayTime)){
            LOGGER.debug("Appointment is not in future");
            return Response
                    .status(Response.Status.BAD_REQUEST)
                    .entity(errorMessageToJSON("Appointment is not in future"))
                    .build();
        }

        /* Patient Revision */
        Patient patient;
        try {
            patient = patientService.findPatientById(patientId);
        } catch (NotFoundPacientException e) {
            return Response
                    .status(Response.Status.NOT_FOUND)
                    .entity(errorMessageToJSON("Patient not found"))
                    .build();
        } catch (NotValidPatientIdException e) {
            return Response
                    .status(Response.Status.BAD_REQUEST)
                    .entity(errorMessageToJSON("Invalid id of patient"))
                    .build();
        } catch (NotCreatePatientException e) {
            return Response
                    .status(Response.Status.NOT_FOUND)
                    .entity(errorMessageToJSON("Patient not found"))
                    .build();
        }
        if (patient == null){
            Response
                    .status(Response.Status.NOT_FOUND)
                    .entity(errorMessageToJSON("patient logged not found")) //messageSource.getMessage("patient not found", null, LocaleContextHolder.getLocale())))
                    .build();
        }
        LOGGER.debug("Appointment patient {}", patient.getId());

        /* Doctor Revision */
        Patient doctorLogged;
        try {
            doctorLogged = userDetailsService.getLoggedUser();
        } catch (NotFoundPacientException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(errorMessageToJSON("Doctor not found"))
                    .build();
        } catch (NotValidEmailException e) {
            return Response
                    .status(Response.Status.BAD_REQUEST)
                    .entity(errorMessageToJSON("Doctor with bad id"))
                    .build();
        }
        if (doctorLogged == null){
            LOGGER.debug("Doctor not found");
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(errorMessageToJSON("Doctor not found"))
                    .build();
        }
        doctorLogged.getDoctor();
        if (doctorLogged.getDoctor() == null){
            return Response
                    .status(Response.Status.BAD_REQUEST)
                    .entity(errorMessageToJSON("Patient Logged is not a Doctor"))
                    .build();
        }

        Doctor doctor = doctorLogged.getDoctor();

        LOGGER.debug("Appointment doctor {}", doctor.getId());

        Appointment appointmentRemoved;
        try {
            appointmentRemoved = appointmentService.cancelAppointment(appointmentDate.toString(), appointmentTime.toString(), patient, doctor);
        } catch ( NotFoundAppointmentException e) {
            return Response
                    .status(Response.Status.NOT_FOUND)
                    .entity(errorMessageToJSON("Appointment not found"))
                    .build();
        } catch (Exception e) {
            return Response
                    .status(Response.Status.BAD_REQUEST)
                    .entity(errorMessageToJSON(e.getMessage()))
                    .build();
        }
        if (appointmentRemoved == null){
            return Response
                    .status(Response.Status.NOT_FOUND)
                    .entity(errorMessageToJSON("Appointment not found"))
                    .build();
        }

        return Response.ok(new DoctorAppointmentDTO(appointmentRemoved)).build();
    }

    @GET
    @Path("/email-exists")
    @Produces(value = MediaType.APPLICATION_JSON)
    public Response emailExists(@QueryParam("email")final String email){
        if(patientService.emailTaken(email)){
            return Response
                    .status(Response.Status.ACCEPTED)
                    .entity(MessageToJSONEmail(true))
                    .build();
        }else{
            return Response
                    .status(Response.Status.BAD_REQUEST)
                    .entity(MessageToJSONEmail(false))
                    .build();
        }
    }
}
