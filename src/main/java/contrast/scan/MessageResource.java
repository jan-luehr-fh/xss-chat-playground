package contrast.scan;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Path("/messages")
@ApplicationScoped
public class MessageResource {

    @GET
    @Path("/{room}")
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    public Response messages(String room, @Context EntityManager em2) {
        TypedQuery<String> query = em2.createQuery("select me.content from MessageEntity me",String.class);
        List<String> msgs = query.getResultList(); // source, tagged db-source
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
    @Transactional
    public Response post(String msg, @Context EntityManager em) {
        var stmt = Instant.now().toString();
        MessageEntity m = new MessageEntity();
        m.setContent(stmt + " - " + msg);
        em.persist(m);
        return Response.noContent().build();

    }

}