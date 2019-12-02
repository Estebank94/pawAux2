package ar.edu.itba.paw.webapp.api.v1;

import javax.json.Json;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;
import java.net.URI;

public abstract class BaseApiController {
    URI buildBaseURI(UriInfo uriInfo) {
        return URI.create(String.valueOf(uriInfo.getBaseUri()) +
                UriBuilder.fromResource(this.getClass()).build() +
                "/");
    }

    String errorMessageToJSON(String message) {
        return Json.createObjectBuilder()
                .add("errors", message)
                .build()
                .toString();
    }
}
