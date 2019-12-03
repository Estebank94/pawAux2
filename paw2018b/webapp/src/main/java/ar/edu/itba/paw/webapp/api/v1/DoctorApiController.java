package ar.edu.itba.paw.webapp.api.v1;

import ar.edu.itba.paw.interfaces.services.DoctorService;
import ar.edu.itba.paw.interfaces.services.EmailService;
import ar.edu.itba.paw.interfaces.services.PatientService;
import ar.edu.itba.paw.models.*;

import ar.edu.itba.paw.models.exceptions.NotFoundDoctorException;
import ar.edu.itba.paw.models.exceptions.NotValidIDException;
import ar.edu.itba.paw.models.exceptions.NotValidPageException;

import ar.edu.itba.paw.models.exceptions.*;



import ar.edu.itba.paw.webapp.dto.DoctorDTO;
import ar.edu.itba.paw.webapp.dto.DoctorListDTO;
import ar.edu.itba.paw.webapp.dto.PatientDTO;

import ar.edu.itba.paw.webapp.forms.PersonalForm;
import ar.edu.itba.paw.webapp.forms.ProfessionalForm;
import com.fasterxml.jackson.databind.deser.Deserializers;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.thymeleaf.TemplateEngine;

import javax.print.Doc;
import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.ws.rs.*;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.net.URI;

import java.util.List;
import java.util.Map;
import java.util.Optional;


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
        Optional<Doctor> doctorOptional = Optional.empty();
        try {
            doctorOptional = doctorService.findDoctorById(id + "");
        } catch (NotFoundDoctorException | NotValidIDException e) {
            e.printStackTrace();
        }
        if (doctorOptional.isPresent()){
            // TODO: LIMPIAR ESTA PARTE
            // DoctorDTO doctorDTO = new DoctorDTO(doctorOptional.get(), buildBaseURI(uriInfo));
            // System.out.println(doctorDTO.toString());
            // System.out.println(Response.ok(new DoctorDTO(doctorOptional.get(), buildBaseURI(uriInfo))).build().toString());
            return Response.ok(new DoctorDTO(doctorOptional.get()))
                    .build();
        }
        // System.out.println("No encontro a Doctor");
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    @GET
    @Path("/list")
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
                            .entity(errorMessageToJSON("DayWeek is not an Digit between 1-7")).build();
                }

                if ((int) dayIterator.charAt(0) > 7 || (int) dayIterator.charAt(0) < 1){
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


    @GET
    @Path("/search")
    public Response searchDoctors (@Context UriInfo uriInfo) {
        String result = "";
        for (Map.Entry entry: uriInfo.getQueryParameters().entrySet()){
            result += entry.getKey() + "=" + entry.getValue() + ", ";
        }
        /*
        Search search = new Search();
        List<Doctor> doctorList = doctorService.listDoctors(search , pageNumber)
        Long totalPageCount = doctorService.getLastPage(search);
        */
        return Response.ok(result).build();
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
            doctor = doctorService.createDoctor(userForm.getFirstName(), userForm.getLastName(), userForm.getPhoneNumber(),
                    userForm.getSex(), userForm.getLicence(), null, userForm.getAddress());
            patient = patientService.createPatient(userForm.getFirstName(), userForm.getLastName(),
                    userForm.getPhoneNumber(), userForm.getEmail(), userForm.getPassword());
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


//        Description description = new Description(professionalForm.getCertificate(), professionalForm.getLanguages(),
//                professionalForm.getEducation());
//
//        List<WorkingHours> workingHours = professionalForm.workingHoursList();
//
//        doctor.setDescription(description);
//        doctor.setWorkingHours(workingHours);

        final URI uri = uriInfo.getAbsolutePathBuilder().path(String.valueOf(patient.getId())).build();

        //Todo: aca estamos creando un patientDTO, pero estamos en doc ... esto esta bien, que onda?
        return Response.created(uri).entity(new PatientDTO(patient, buildBaseURI(uriInfo))).build();
    }


    //TODO: Dudoso que tengamos codigo repetido.  No se si no le estoy pifiando en algo que tiene que ser
    // doctor y esta puesto como patient
    @GET
    @Path("/confirm")
    @Produces(value = { MediaType.APPLICATION_JSON, })
    public Response confirmUser(@QueryParam("token") @DefaultValue("") String token) throws VerificationNotFoundException {

        final Verification verification = patientService.findToken(token).orElseThrow(VerificationNotFoundException::new);
        final Patient patient = verification.getPatient();
        patientService.enableUser(patient);

        /* Auto-Login */
        Authentication authentication = new UsernamePasswordAuthenticationToken(patient.getEmail(), patient.getPassword());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        final URI uri = uriInfo.getAbsolutePathBuilder().path(String.valueOf(patient.getId())).build();

        return Response.created(uri).entity(new PatientDTO(patient, buildBaseURI(uriInfo))).build();
    }

}
