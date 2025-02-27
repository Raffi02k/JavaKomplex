package Main;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("films")
public class FilmResources {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Film firstTest() {
        return new Film("title", 2002);
    }
}
