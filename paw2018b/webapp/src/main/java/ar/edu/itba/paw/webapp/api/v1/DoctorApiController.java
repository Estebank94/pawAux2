package ar.edu.itba.paw.webapp.api.v1;

import ar.edu.itba.paw.interfaces.services.DoctorService;
import ar.edu.itba.paw.models.Doctor;
import ar.edu.itba.paw.models.exceptions.NotFoundDoctorException;
import ar.edu.itba.paw.models.exceptions.NotValidIDException;
import ar.edu.itba.paw.webapp.api.BaseApiController;
import ar.edu.itba.paw.webapp.dto.DoctorDTO;
import ar.edu.itba.paw.webapp.dto.DoctorListDTO;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.List;
import java.util.Optional;

@Path("v1/doctor")
@Controller
public class DoctorApiController extends BaseApiController {

    private static final Logger LOGGER = LoggerFactory.getLogger(DoctorApiController.class);

    @Autowired
    private DoctorService doctorService;

    @Context
    private UriInfo uriInfo;

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
        return Response.ok(new DoctorListDTO(doctorList)).build();
    }

    @GET
    @Path("/all")
    public Response allDoctors() {
        List<Doctor> doctorList = doctorService.listDoctors();
        return Response.ok(new DoctorListDTO(doctorList)).build();
    }
}
