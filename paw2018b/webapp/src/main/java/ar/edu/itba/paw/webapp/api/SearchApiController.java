package ar.edu.itba.paw.webapp.api;

import ar.edu.itba.paw.interfaces.SearchService;
import ar.edu.itba.paw.models.Specialty;
import ar.edu.itba.paw.webapp.dto.SpecialtyListDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javax.ws.rs.GET;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import java.util.List;

@Path("search")
@Controller
public class SearchApiController extends BaseApiController {

    private static final Logger LOGGER = LoggerFactory.getLogger(SearchApiController.class);

    @Autowired
    private SearchService searchService;

    @Context
    private UriInfo uriInfo;

    @GET
    @Path("/specialties")
    public Response getSpecialties(){
        List<Specialty> specialtyList = searchService.listSpecialties();
        LOGGER.info("Specialtys List");
        return Response.ok(new SpecialtyListDTO(specialtyList , buildBaseURI(uriInfo))).build();
    }

}
