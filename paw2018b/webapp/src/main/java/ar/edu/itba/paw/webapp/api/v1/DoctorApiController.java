package ar.edu.itba.paw.webapp.api.v1;

import ar.edu.itba.paw.interfaces.services.DoctorService;
import ar.edu.itba.paw.interfaces.services.EmailService;
import ar.edu.itba.paw.interfaces.services.PatientService;
import ar.edu.itba.paw.models.*;

import ar.edu.itba.paw.models.exceptions.NotFoundDoctorException;
import ar.edu.itba.paw.models.exceptions.NotValidIDException;
import ar.edu.itba.paw.models.exceptions.NotValidPageException;

import ar.edu.itba.paw.models.exceptions.*;


import ar.edu.itba.paw.webapp.auth.UserDetailsServiceImpl;
import ar.edu.itba.paw.webapp.dto.*;


import ar.edu.itba.paw.webapp.forms.BasicProfessionalForm;
import ar.edu.itba.paw.webapp.forms.PersonalForm;
import org.apache.commons.io.FilenameUtils;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.thymeleaf.TemplateEngine;

import javax.imageio.ImageIO;
import javax.validation.Valid;
import javax.ws.rs.*;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URI;

import java.util.*;
import java.util.List;


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
    public Response getDoctorById(@PathParam("id") final int id) {
        Optional<Doctor> doctorOptional = Optional.empty();
        try {
            doctorOptional = doctorService.findDoctorById(id + "");
        } catch (NotFoundDoctorException | NotValidIDException e) {
            e.printStackTrace();
        }
        if (doctorOptional.isPresent()) {
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
                                @QueryParam("insurance") String insurance,
                                @QueryParam("sex") String sex,
                                @QueryParam("insurancePlan") List<String> insurancePlan,
                                @QueryParam("days") List<String> days) {

        Search search = new Search();
        if (specialty != null) {
            if (specialty.length() == 0) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity(errorMessageToJSON("Specialty bad format")).build();
            }
            search.setSpecialty(specialty);
        }
        if (name != null) {
            if (name.length() == 0) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity(errorMessageToJSON("name bad format")).build();
            }
            search.setName(name);
        }
        if (insurance != null) {
            if (insurance.length() == 0) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity(errorMessageToJSON("Insurance bad format")).build();
            }
            search.setInsurance(insurance);
        }
        if (sex != null) {
            if (sex.length() == 0) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity(errorMessageToJSON("sex bad format")).build();
            }
            search.setSex(sex);
        }
        if (insurancePlan != null && insurancePlan.size() > 0) {
            for (String ip : insurancePlan) {
                if (ip.length() == 0) {
                    return Response.status(Response.Status.BAD_REQUEST)
                            .entity(errorMessageToJSON("insurancePlan bad format")).build();
                }
            }
            search.setInsurancePlan(insurancePlan);
        }
        if (days != null && days.size() > 0) {
            for (String dayIterator : days) {
                if (dayIterator.length() != 1) {
                    return Response.status(Response.Status.BAD_REQUEST)
                            .entity(errorMessageToJSON("DayWeek is not an Digit between 1-7")).build();
                }

                if (!Character.isDigit(dayIterator.charAt(0))) {
                    return Response.status(Response.Status.BAD_REQUEST)
                            .entity(errorMessageToJSON("DayWeek is not an Digit between 1-7")).build();
                }

                if ((int) dayIterator.charAt(0) > 7 || (int) dayIterator.charAt(0) < 1) {
                    return Response.status(Response.Status.BAD_REQUEST)
                            .entity(errorMessageToJSON("DayWeek is not an Digit between 1-7")).build();
                }
            }
            search.setDays(days);
        }

        List<Doctor> doctors;
        try {
            doctors = doctorService.listDoctors(search, String.valueOf(page));
        } catch (NotValidPageException e) {
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
    public Response searchDoctors(@Context UriInfo uriInfo) {
        String result = "";
        for (Map.Entry entry : uriInfo.getQueryParameters().entrySet()) {
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

        try {
            patient = patientService.createPatient(userForm.getFirstName(), userForm.getLastName(),
                    userForm.getPhoneNumber(), userForm.getEmail(), userForm.getPassword());
            doctor = doctorService.createDoctor(userForm.getFirstName(), userForm.getLastName(), userForm.getPhoneNumber(),
                    userForm.getSex(), userForm.getLicence(), null, userForm.getAddress());
            patientService.setDoctorId(patient, doctor);
        } catch (RepeatedEmailException e) {
            return Response.status(Response.Status.CONFLICT)
                    .entity(errorMessageToJSON("Duplicated email")).build();
        } catch (NotValidFirstNameException | NotValidLastNameException | NotValidPhoneNumberException |
                RepeatedLicenceException | NotValidLicenceException | NotValidAddressException |
                NotValidSexException | NotValidPasswordException | NotValidEmailException e) {
            return Response.status(Response.Status.CONFLICT)
                    .entity(errorMessageToJSON("There is an error on the form information")).build();
        } catch (NotFoundDoctorException | NotCreateDoctorException e) {
            return Response.status(Response.Status.CONFLICT)
                    .entity(errorMessageToJSON("Doctor not found")).build();
        } catch (NotCreatePatientException e) {
            return Response.status(Response.Status.CONFLICT)
                    .entity(errorMessageToJSON("Patient not created")).build();
        } catch (NotValidPatientIdException | NotValidDoctorIdException e) {
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
//    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(value = {MediaType.APPLICATION_JSON})
    public Response createProfessionalUser(@Valid final BasicProfessionalForm professionalForm) {


        Patient patient = null;
        try {
            patient = userDetailsService.getLoggedUser();
        } catch (NotFoundPacientException | NotValidEmailException e) {
            return Response.status(Response.Status.CONFLICT)
                    .entity(errorMessageToJSON("Doctor/Patient not found")).build();
        }

        Doctor doctor = patient.getDoctor();
        if (doctor == null) {
            return Response.status(Response.Status.CONFLICT)
                    .entity(errorMessageToJSON("Doctor is NULL")).build();
        }

        /* Avatar */
//        AvatarForm file = professionalForm.getAvatar();
//
//        doctorService.setDoctorAvatar(doctor, file.getFileBytes());

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

        if (professionalForm.getSpecialty() != null) {
            Set<Specialty> specialties = new HashSet<>();
            for (String sp : professionalForm.getSpecialty()) {
                specialties.add(new Specialty(sp));
            }
            doctorService.setDoctorSpecialty(doctor, specialties);
        }

        if (professionalForm.getDescription() != null) {
            Description description = new Description(professionalForm.getDescription().getCertificate(),
                    professionalForm.getDescription().getLanguages(), professionalForm.getDescription().getEducation());
            doctorService.setDescription(doctor, description);
        }

        if (professionalForm.getInsurancePlans() != null) {
            doctorService.setDoctorInsurancePlans(doctor, professionalForm.getInsurancePlans());
        }

        List<WorkingHours> workingHoursList = new ArrayList<>();
        for (WorkingHoursDTO w : professionalForm.getWorkingHours()) {
            WorkingHours workingHours = new WorkingHours(w.getDayOfWeek(), w.getStartTime(), w.getFinishTime());
            workingHoursList.add(workingHours);
        }

        doctorService.setWorkingHours(doctor, workingHoursList);

        final URI uri = uriInfo.getAbsolutePathBuilder().path(String.valueOf(doctor.getId())).build();

        return Response.created(uri).entity(new DoctorDTO(doctor, buildBaseURI(uriInfo))).build();
    }


    @POST
    @Path("/registerPicture")
    @Consumes({MediaType.MULTIPART_FORM_DATA})
    public Response createProfessionalUser(@FormDataParam("file") InputStream uploadedInputStream,
                                           @FormDataParam("file") FormDataContentDisposition fileDetail) throws IOException {

        Patient patient = null;
        try {
            patient = userDetailsService.getLoggedUser();
        } catch (NotFoundPacientException | NotValidEmailException e) {
            return Response.status(Response.Status.CONFLICT)
                    .entity(errorMessageToJSON("Doctor/Patient not found")).build();
        }

        Doctor doctor = patient.getDoctor();
        if (doctor == null) {
            return Response.status(Response.Status.CONFLICT)
                    .entity(errorMessageToJSON("Doctor is NULL")).build();
        }

        BufferedImage bImage = ImageIO.read(uploadedInputStream);
        String extesion = FilenameUtils.getExtension(fileDetail.getFileName());
        long size = fileDetail.getSize();

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write( bImage, "jpg", baos );
        baos.flush();
        byte[] imageInByte = baos.toByteArray();

        doctorService.setDoctorAvatar(doctor, imageInByte);
        baos.close();

        return Response.ok().build();
    }



}
