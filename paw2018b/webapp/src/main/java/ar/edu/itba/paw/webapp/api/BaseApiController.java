package ar.edu.itba.paw.webapp.api;

import javax.json.Json;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;
import java.net.URI;

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
}
