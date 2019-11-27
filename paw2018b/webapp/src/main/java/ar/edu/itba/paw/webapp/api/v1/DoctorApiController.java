package ar.edu.itba.paw.webapp.api.v1;

import ar.edu.itba.paw.interfaces.services.DoctorService;
import ar.edu.itba.paw.interfaces.services.EmailService;
import ar.edu.itba.paw.interfaces.services.PatientService;
import ar.edu.itba.paw.models.Doctor;
import ar.edu.itba.paw.models.Patient;
import ar.edu.itba.paw.models.VerificationToken;
import ar.edu.itba.paw.models.exceptions.*;
import ar.edu.itba.paw.models.Search;
import ar.edu.itba.paw.models.exceptions.NotFoundDoctorException;
import ar.edu.itba.paw.models.exceptions.NotValidIDException;
import ar.edu.itba.paw.webapp.api.BaseApiController;
import ar.edu.itba.paw.webapp.dto.DoctorDTO;
import ar.edu.itba.paw.webapp.dto.DoctorListDTO;
import ar.edu.itba.paw.webapp.dto.PatientDTO;
import ar.edu.itba.paw.webapp.forms.PersonalForm;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import org.springframework.context.MessageSource;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
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

    @Context
    private UriInfo uriInfo;

    @Autowired
    private EmailService emailService;

    @Autowired
    private MessageSource messageSource;

    @GET
    @Path("/{id}")
    @Produces(value = {MediaType.APPLICATION_JSON})
    public Response getDoctorById(@PathParam("id") final int id){
        Optional<Doctor> doctorOptional = Optional.empty();
        try {
            doctorOptional = doctorService.findDoctorById(id + "");
        } catch (NotFoundDoctorException e) {
            e.printStackTrace();
        } catch (NotValidIDException e) {
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
    public Response listDoctors(@QueryParam("page") int page) {
        List<Doctor> doctorList = doctorService.listDoctors(page);
        Long totalPageCount = doctorService.getLastPage();
        return Response.ok(new DoctorListDTO(doctorList, totalPageCount)).build();
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
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(value = { MediaType.APPLICATION_JSON})
    public Response createDoctor(
            @Valid final PersonalForm personalForm) {
        if (personalForm == null){
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        if (!personalForm.matchingPasswords(personalForm.getPassword(), personalForm.getPasswordConfirmation())) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(errorMessageToJSON("Non Mathching passwords"))
                    .build();
        }

        if (patientService.findPatientByEmail(personalForm.getEmail()) != null) {
            LOGGER.warn("Cannor create doctor: existing username {} found", personalForm.getEmail());
            return Response
                    .status(Response.Status.CONFLICT)
                    .entity(errorMessageToJSON("Existing User with that email"))
                    .build();
        }
        Doctor doctor = null;
        try {
            doctor = doctorService.createDoctor(personalForm.getFirstName(), personalForm.getLastName(), personalForm.getPhoneNumber(),
                    personalForm.getSex(), personalForm.getLicence(), null, personalForm.getAddress());
        } catch (Error | NotValidFirstNameException | NotValidLastNameException | NotValidPhoneNumberException | NotCreateDoctorException | RepeatedLicenceException | NotValidSexException | NotValidLicenceException | NotValidAddressException e) {
            e.printStackTrace();
        }
        Patient patient = null;
        try {
            patient = patientService.createPatient(personalForm.getFirstName(), personalForm.getLastName(), personalForm.getPhoneNumber(), personalForm.getEmail(),
                    personalForm.getPassword());
        } catch (Error | RepeatedEmailException | NotValidEmailException | NotValidPasswordException | NotValidFirstNameException | NotValidLastNameException | NotValidPhoneNumberException | NotCreatePatientException e) {
            System.out.println(e); ;
        }
        try {
            patientService.setDoctorId(patient, doctor);
        } catch (Error | NotFoundDoctorException | NotValidPatientIdException | NotValidDoctorIdException | NotCreatePatientException e) {
            e.printStackTrace();
        }
        final VerificationToken token = patientService.createToken(patient);

        /*Send welcome email to new user*/
        emailService.sendMessageWithAttachment(patient.getFirstName(), patient.getEmail(), "Bienvenido a Waldoc");


        final URI location = uriInfo.getAbsolutePathBuilder().path(String.valueOf(patient.getId())).build();

        // final org.thymelead.context.Context ctx = new org.thymelead.context.Context
        return Response.created(location).entity(new PatientDTO(patient)).build();

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
}
