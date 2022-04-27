package contrast.scan;


import org.apache.commons.text.StringEscapeUtils;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Path("/messages")
public class MessageRessource {

    public static final List<String> msgs = new ArrayList<>();

    @GET
    @Produces(MediaType.TEXT_HTML)
    public String messages() {
        if (msgs.size() > 0) {
            return String.join("<br />", msgs);
        } else {
            return "<i>No messages</i>";
        }
    }

    @POST
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.TEXT_PLAIN)
    public String post(String msg) {
        var stmt = Instant.now().toString();
        //var message = stmt + " - " + StringEscapeUtils.escapeHtml4(msg); // Sanitizer
        var message = stmt + " - " + msg; // Sanitizer
        msgs.add(message);
        return messages();
    }


}