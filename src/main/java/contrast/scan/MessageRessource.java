package contrast.scan;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Path("/messages")
public class MessageRessource {

    public static final List<String> msgs = new ArrayList<>();

    @GET
    @Path("/{room}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response messages(String room) {
        ChatMessage result = new ChatMessage();
        result.room = room; // Sanitized: StringEscapeUtils.escapeHtml4(room);
        if (msgs.size() > 0) {
            result.msgs = String.join("<br />", msgs);  // Sanitized: StringEscapeUtils.escapeHtml4( String.join("<br />", msgs););
        } else {
            result.msgs += "<i>No messages</i>";
        }
        return Response.ok(result).build();
    }


    /**
     * Variant:
     * - Reflected XSS via Room-Name
     * - Reflected XSS via msg ... but that's not convincing, because hardly anybody XSS'es himself
     */
    @POST
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.TEXT_PLAIN)
    public Response post(String msg) {
        var stmt = Instant.now().toString();
        msgs.add(stmt + " - " + msg);
        return Response.noContent().build();

    }


}