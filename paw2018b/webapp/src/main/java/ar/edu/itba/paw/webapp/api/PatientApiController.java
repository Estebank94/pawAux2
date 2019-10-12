package ar.edu.itba.paw.webapp.api;

import ar.edu.itba.paw.interfaces.PatientService;
import ar.edu.itba.paw.models.Patient;
import ar.edu.itba.paw.models.exceptions.NotCreatePatientException;
import ar.edu.itba.paw.models.exceptions.NotFoundPacientException;
import ar.edu.itba.paw.models.exceptions.NotValidEmailException;
import ar.edu.itba.paw.models.exceptions.NotValidPatientIdException;
import ar.edu.itba.paw.webapp.dto.PatientDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

@Path("patient")
@Controller
public class PatientApiController extends BaseApiController{
    private static final Logger LOGGER = LoggerFactory.getLogger(PatientApiController.class);

    @Autowired
    private PatientService patientService;

    @Context
    private UriInfo uriInfo;

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
    @Path("/email/{email}")
    @Produces(value = {MediaType.APPLICATION_JSON})
    public Response getPatientByEmail(@PathParam("email")final String email) throws NotValidPatientIdException, NotFoundPacientException, NotCreatePatientException {
        Patient patient = new Patient();
        try {
            patient = patientService.findPatientByEmail(email);
        } catch (NotFoundPacientException e){
            e.printStackTrace();
        } catch (NotValidEmailException e) {
            e.printStackTrace();
        }
        System.out.println(patient.toString());
        if (patient == null){
            LOGGER.warn("Patient with email {} not found", email);
            return Response.status(Response.Status.NOT_FOUND)
                    .build();
        }
        return Response.ok(new PatientDTO(patient)).build();
    }

}
