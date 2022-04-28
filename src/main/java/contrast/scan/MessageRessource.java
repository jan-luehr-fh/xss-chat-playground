package contrast.scan;


import org.apache.commons.text.StringEscapeUtils;
import org.jboss.resteasy.reactive.RestPath;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Path("/messages")
public class MessageRessource {

    public static final List<String> msgs = new ArrayList<>();

    /**
     * Variant:
     * - Reflected XSS via room-name
     * - Stored XSS via Database msgs
     */
    @GET
    @Path("#{room}")
    @Produces(MediaType.TEXT_HTML)
    public String messages(@PathParam("room") String room) {
        String greeting = "<p>Welcome to " + room + "</p>";
        if (msgs.size() > 0) {
            greeting += String.join("<br />", msgs);
        } else {
            greeting += "<i>No messages</i>";
        }
        return greeting;
    }


    /**
     * Variant:
     * - Reflected XSS via Room-Name
     * - Reflected XSS via msg ... but that's not convincing, because hardly anybody XSS'es himself
     */
    @POST
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/#{room}")
    public String post(@PathParam("room") String room, String msg) {
        var stmt = Instant.now().toString();
        //var message = stmt + " - " + StringEscapeUtils.escapeHtml4(msg); // Sanitizer
        var message = stmt + " - " + msg; // Sanitizer
        msgs.add(message);
        return messages(room);
    }


}