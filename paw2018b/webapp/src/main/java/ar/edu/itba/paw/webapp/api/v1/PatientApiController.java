package ar.edu.itba.paw.webapp.api.v1;

import ar.edu.itba.paw.interfaces.services.DoctorService;
import ar.edu.itba.paw.interfaces.services.EmailService;
import ar.edu.itba.paw.interfaces.services.PatientService;
import ar.edu.itba.paw.models.*;
import ar.edu.itba.paw.models.exceptions.NotCreatePatientException;
import ar.edu.itba.paw.models.exceptions.NotFoundPacientException;
import ar.edu.itba.paw.models.exceptions.NotValidEmailException;
import ar.edu.itba.paw.models.exceptions.NotValidPatientIdException;
import ar.edu.itba.paw.models.exceptions.*;
import ar.edu.itba.paw.webapp.auth.UserDetailsServiceImpl;
import ar.edu.itba.paw.webapp.dto.doctor.DoctorPersonalDTO;
import ar.edu.itba.paw.webapp.dto.patient.BasicPatientDTO;
import ar.edu.itba.paw.webapp.dto.patient.PatientDTO;
import ar.edu.itba.paw.webapp.dto.patient.PatientPersonalInformationDTO;
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

        final Verification verification = patientService.findToken(token).orElseThrow(VerificationNotFoundException::new);
        final Patient patient = verification.getPatient();
        patientService.enableUser(patient);

        /* Auto Login - todo: se esta rompiendo ? */
        Authentication authentication = new UsernamePasswordAuthenticationToken(patient.getEmail(), patient.getPassword());
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

        return Response.ok(new PatientDTO(patient)).build();
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
            reviews = doctor.getReviews();
            return Response.ok(new PatientPersonalInformationDTO(historicalAppointments, futureAppointments, favorites,
                    futureAppointmentsDoctor, historicalAppointmentsDoctor, reviews)).build();
        }

        return Response.ok(new PatientPersonalInformationDTO(historicalAppointments, futureAppointments, favorites)).build();
    }

}
