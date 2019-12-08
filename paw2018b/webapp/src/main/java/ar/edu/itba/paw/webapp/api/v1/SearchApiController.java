package ar.edu.itba.paw.webapp.api.v1;

import ar.edu.itba.paw.interfaces.services.SearchService;
import ar.edu.itba.paw.models.Insurance;
import ar.edu.itba.paw.models.InsurancePlan;
import ar.edu.itba.paw.models.Specialty;
import ar.edu.itba.paw.webapp.dto.*;
import ar.edu.itba.paw.webapp.dto.insurance.InsuranceListDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.List;

@Path("v1/")
@Controller
public class SearchApiController extends BaseApiController {

    private static final Logger LOGGER = LoggerFactory.getLogger(SearchApiController.class);

    @Autowired
    private SearchService searchService;

    @Context
    private UriInfo uriInfo;

    @GET
    @Path("specialties")
    @Produces(value = {MediaType.APPLICATION_JSON})
    public Response getSpecialties(){
        List<Specialty> specialtyList = searchService.listSpecialties();
        LOGGER.info("Specialtys List");
        return Response.ok(new SpecialtyListDTO(specialtyList)).build();
    }

    @GET
    @Path("insurances")
    @Produces(value = {MediaType.APPLICATION_JSON})
    public Response getInsurances(){
        List<Insurance> insuranceList = searchService.listInsurances();
        LOGGER.info("Insurance List");
        return Response.ok(new InsuranceListDTO(insuranceList)).build();
    }

    @GET
    @Path("homeinfo")
    @Produces(value = {MediaType.APPLICATION_JSON})
    public Response getHomeInformation () {
        LOGGER.info("Home Information");
        List<InsurancePlan> insurancePlanList = searchService.listInsuranceWithDoctors();
        List<Specialty> specialtyList = searchService.listSpecialtiesWithDoctors();
        return Response.ok(new HomeInformationDTO(specialtyList, insurancePlanList)).build();
    }

    @GET
    @Path("futureDays")
    @Produces(value = {MediaType.APPLICATION_JSON})
    public Response getFutureDays () {
        List<String> futureDays = searchService.getFutureDays();
        LOGGER.info("futureDays");
        return Response.ok(new FutureDayListDTO(futureDays)).build();
    }
}
