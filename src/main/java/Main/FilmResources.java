package Main;

import entity.Film;
import exaption.NotFound;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import lombok.extern.java.Log;
import repository.FilmRepositoory;

import java.util.ArrayList;
import java.util.List;

@Path("films")
@Log
public class FilmResources {

    private final FilmRepositoory filmRepositoory;
    @PersistenceContext
    private EntityManager entityManager;

    @jakarta.inject.Inject
    public FilmResources(FilmRepositoory filmRepositoory) {
        this.filmRepositoory = filmRepositoory;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    public Film firstTest() {
        Film film = new Film();
        entityManager.persist(new Film());
        entityManager.flush();
//        var film = entityManager.find(Film.class,1L);
        log.info(film.toString());
        return new Film("", 1);
    }

    @GET
    @Path("many")
    @Produces
    public Films secondTest() {
        List<Film> films = new ArrayList<>();
        films.add(new Film("title", 2002));
        films.add(new Film("title", 2003));
        return new Films(films, 2);
    }

    public record Films(List<Film> values, int totalFilms) {

    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Film getOneMovie(@PathParam("id") Long id){
        return filmRepositoory.findById(id).orElseThrow(
                () -> new NotFound("Movie with ID: " + id +" not found")
        );
    }
}

