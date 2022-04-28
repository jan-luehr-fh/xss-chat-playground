package contrast.scan;

import org.apache.commons.text.StringEscapeUtils;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

@Path("/logs")
public class LogResources {

    // Idea for a server-side taint (stored XSS)
    // Same idea as for chat - but maybe: More convincing story for server-side

    @GET
    @Produces(MediaType.TEXT_HTML)
    public String messages() {
        var raw = String.join("<br />", logMessages());
        return StringEscapeUtils.escapeHtml4(raw); // Sanitizer
    }

    public List<String> logMessages() {
        try {
            // TODO actually create a log-file withXSS-content
            var handle = this.getClass().getClassLoader().getResource("apache_access.log"); // A source for this?
            assert handle != null;
            return Files.readAllLines(Paths.get(handle.toURI()));
        } catch (Exception e) {
            return Arrays.asList("<None>", e.getMessage());
        }
    }
}
