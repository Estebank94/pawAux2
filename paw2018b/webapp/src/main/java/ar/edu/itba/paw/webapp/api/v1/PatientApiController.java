package ar.edu.itba.paw.webapp.api.v1;

import ar.edu.itba.paw.interfaces.services.EmailService;
import ar.edu.itba.paw.interfaces.services.PatientService;
import ar.edu.itba.paw.models.Patient;

import ar.edu.itba.paw.models.Verification;
import ar.edu.itba.paw.models.exceptions.*;
//import ar.edu.itba.paw.webapp.api.BaseApiController;
import ar.edu.itba.paw.webapp.dto.PatientDTO;
import ar.edu.itba.paw.webapp.forms.PatientForm;
import ar.edu.itba.paw.webapp.forms.PersonalForm;
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

    private static final String VERIFICATION_TOKEN_TEMPLATE_NAME = "welcomeMail.html";

    @GET
    @Path("/{id}")
    @Produces(value = {MediaType.APPLICATION_JSON})
    public Response getPatientById(@PathParam("id")final int id) throws NotValidPatientIdException, NotFoundPacientException, NotCreatePatientException {
        Patient patient = new Patient();
        try {
            patient = patientService.findPatientById(id);
        } catch (NotValidPatientIdException e){
            e.printStackTrace();
        } catch (NotFoundPacientException e){
            e.printStackTrace();
        } catch (NotCreatePatientException e){
            e.printStackTrace();
        }
        System.out.println(patient.toString());
        if (patient == null){
            LOGGER.warn("Patient with id {} not found", id);
            return Response.status(Response.Status.NOT_FOUND)
                    .build();
        }
        return Response.ok(new PatientDTO(patient)).build();
    }

    @GET
    @Produces(value = {MediaType.APPLICATION_JSON})
    public Response getPatientByEmail(@QueryParam("email")final String email) throws NotValidPatientIdException, NotFoundPacientException, NotCreatePatientException {
        Patient patient = new Patient();
        patient = patientService.findPatientByEmail(email);

        /*
        try {
            patient = patientService.findPatientByEmail(email);
        } catch (NotFoundPacientException e){
            e.printStackTrace();
        } catch (NotValidEmailException e) {
            e.printStackTrace();
        }
         */

        System.out.println(patient.toString());
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
    public Response createUser(@Valid final PatientForm userForm) throws NotCreatePatientException, NotValidPhoneNumberException, NotValidLastNameException, RepeatedEmailException, NotValidPasswordException, NotValidFirstNameException, NotValidEmailException {

        if (userForm == null)
            return Response.status(Response.Status.BAD_REQUEST).build();

        if (!userForm.getPassword().equals(userForm.getPasswordConfirmation()))
            return Response
                    .status(Response.Status.BAD_REQUEST)
                    .entity(errorMessageToJSON(messageSource.getMessage("non_matching_passwords", null, LocaleContextHolder.getLocale())))
                    .build();

        final Patient patient = patientService.createPatient(userForm.getFirstName(), userForm.getLastName(),
                userForm.getPhoneNumber(), userForm.getEmail(), userForm.getPassword());

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

        /* Auto Login */
        Authentication authentication = new UsernamePasswordAuthenticationToken(patient.getEmail(), patient.getPassword());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        final URI uri = uriInfo.getAbsolutePathBuilder().path(String.valueOf(patient.getId())).build();

        return Response.created(uri).entity(new PatientDTO(patient, buildBaseURI(uriInfo))).build();
    }


}
