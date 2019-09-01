package ar.edu.itba.paw.webapp.api;

import ar.edu.itba.paw.interfaces.DoctorService;
import ar.edu.itba.paw.models.Doctor;
import ar.edu.itba.paw.models.exceptions.NotFoundDoctorException;
import ar.edu.itba.paw.models.exceptions.NotValidIDException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.awt.*;
import java.util.Optional;

@Path("doctor")
@Controller
public class DoctorApiController{
    @Autowired
    private DoctorService doctorService;

    @GET
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Doctor getDoctorById(@PathParam("id") final int id) throws NotFoundDoctorException, NotValidIDException {
        System.out.println("Hola");

        Optional<Doctor> doctorOptional = doctorService.findDoctorById(id + "");
        if (doctorOptional.isPresent()){
            System.out.println(doctorOptional.get().getFirstName() + " ," + doctorOptional.get().getLastName());
            return doctorOptional.get();
        }
        System.out.println("No encontro a Doctor");
        return null;
    }

}
