package main;

import dto.FilmResponse;
import entity.Film;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import lombok.extern.java.Log;
import repository.FilmRepository;
import service.FilmService;

import java.util.ArrayList;
import java.util.List;

/**
 * REST resource class for Film entities.
 * Provides endpoints for CRUD operations on films.
 */
@Path("films")
@Log
public class FilmResources {

    @Inject
    private FilmRepository filmRepository;

    @Inject
    private FilmService filmService;

    @PersistenceContext
    private EntityManager entityManager;

    @Context
    private UriInfo uriInfo;

    /**
     * Constructor with dependency injection.
     *
     * @param filmRepository Repository for film data access
     * @param filmService Service for film business logic
     */
    @Inject
    public FilmResources(FilmRepository filmRepository, FilmService filmService) {
        this.filmRepository = filmRepository;
        this.filmService = filmService;
    }

    /**
     * Default constructor required by CDI.
     */
    public FilmResources() {
    }

    /**
     * Get all films as Film entities.
     *
     * @return List of all films
     */
    @GET
    @Path("all")
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    public List<Film> getAllFilms() {
        return filmRepository.findAll().toList();
    }

    /**
     * Get a sample list of films with count.
     *
     * @return Films record containing sample films and count
     */
    @GET
    @Path("many")
    @Produces(MediaType.APPLICATION_JSON)
    public Films getSampleFilms() {
        List<Film> films = new ArrayList<>();
        films.add(new Film("title", 2002));
        films.add(new Film("title", 2003));
        return new Films(films, 2);
    }

    /**
     * Record to represent a collection of films with count.
     */
    public record Films(List<Film> values, int totalFilms) {
    }

    /**
     * Get a specific film by ID.
     *
     * @param id Film ID
     * @return Film entity
     */
    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Film getOneMovie(@PathParam("id") Long id) {
        return filmService.getFilmById(id);
    }

    /**
     * Get all films as FilmResponse DTOs.
     *
     * @return List of film DTOs
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<FilmResponse> getMovies() {
        return filmService.getAllFilms();
    }

    /**
     * Create a new film.
     *
     * @param film Film entity to create
     * @return Response with location header
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createNewFilm(Film film) {
        // Using UriInfo instead of passing 'this' to avoid circular dependency
        return filmService.createFilm(film, uriInfo);
    }

    /**
     * Update an existing film.
     *
     * @param film Updated film data
     * @param id ID of film to update
     * @return Response with status
     */
    @PUT
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateFilm(Film film, @PathParam("id") Long id) {
        return filmService.updateFilms(film, id);
    }
}
