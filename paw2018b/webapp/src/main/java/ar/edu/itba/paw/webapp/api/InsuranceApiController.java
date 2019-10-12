package ar.edu.itba.paw.webapp.api;

import ar.edu.itba.paw.interfaces.InsuranceService;
import ar.edu.itba.paw.models.Insurance;
import ar.edu.itba.paw.webapp.dto.InsuranceDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javax.annotation.Generated;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

@Path("insurance")
@Controller
public class InsuranceApiController extends BaseApiController {

    private static final Logger LOGGER = LoggerFactory.getLogger(InsuranceApiController.class);

    @Autowired
    private InsuranceService insuranceService;

    @Context
    private UriInfo uriInfo;

    @GET
    @Path("/{insuranceName}")
    @Produces(value = {MediaType.APPLICATION_JSON})
    public Response getInsuranceByName(@PathParam("insuranceName") final String insuranceName){
        Insurance insurance = insuranceService.getInsuranceByName(insuranceName);
        if (insurance == null){
            LOGGER.warn("Insurance with name {} not found", insuranceName);
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(new InsuranceDTO(insurance)).build();
    }
}
