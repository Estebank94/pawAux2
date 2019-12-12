package ar.edu.itba.paw.webapp.api.v1;

import javax.json.Json;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.util.Map;

public class BaseApiController {
    public URI buildBaseURI(UriInfo uriInfo) {
        return URI.create(String.valueOf(uriInfo.getBaseUri()) +
                UriBuilder.fromResource(this.getClass()).build() +
                "/");
    }

    public String errorMessageToJSON(String message) {
        return Json.createObjectBuilder()
                .add("errors", message)
                .build()
                .toString();
    }

    public String jsonField(String field, String value){
        return Json.createObjectBuilder()
                .add(field, value)
                .build()
                .toString();
    }

    public String MessageToJSON(Boolean valid){
        return Json.createObjectBuilder()
                .add("canReview", valid)
                .build()
                .toString();
    }
}
