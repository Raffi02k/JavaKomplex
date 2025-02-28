package Main;

import entity.Film;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@Path("films")
public class FilmResources {

    private static final Logger logger = Logger.getLogger(FilmResources.class.getName());

    @PersistenceContext
    private EntityManager entityManager;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    public Film firstTest() {
        entityManager.persist(new Film());
        entityManager.flush();
//        var film = entityManager.find(Film.class,1L);
        return new Film("", 1);
    }

    @GET
    @Path("many")
    @Produces
    public Films secondTest() {
        List<Film> films = new ArrayList<>();
        films.add(new Film("title", 2002));
        films.add(new Film("title", 2003));
        return new Films (films, 2);
    }

    public record Films (List<Film> values, int totalFilms) {

    }


}
