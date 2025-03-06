package main;

import entity.Film;
import exaption.NotFound;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
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
    public Film getOneMovie(@PathParam("id") Long id) {
        return filmRepositoory.findById(id).orElseThrow(
                () -> new NotFound("Movie with ID: " + id + " not found")
        );
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Film> getMovies() {
        return filmRepositoory.findAll().toList();
    }


    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createNewFilm(Film film) {
        var newFilm = filmRepositoory.save(film);
        return Response
                .status(Response.Status.CREATED)
                .header("Location", "/api/films" + newFilm.getId())
                .build();
    }

    @PUT
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateFilm(Film film, @PathParam("id") Long id) {
        filmRepositoory.findById(id).orElseThrow(() -> new NotFound("Movie with ID: " + id + " not found"));
        film.setId(id);
        filmRepositoory.save(film);
        return Response.noContent().build();
    }
}
