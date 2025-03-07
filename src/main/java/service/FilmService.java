package service;

import dto.FilmResponse;
import entity.Film;
import exaption.NotFound;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import repository.FilmRepository;

import java.util.List;
import java.util.Objects;

/**
 * Service class for Film business logic.
 */
@ApplicationScoped
public class FilmService {

    private final FilmRepository filmRepository;

    /**
     * Constructor with dependency injection.
     *
     * @param filmRepository Repository for film data access
     */
    @Inject
    public FilmService(FilmRepository filmRepository) {
        this.filmRepository = filmRepository;
    }

    /**
     * Default constructor required by CDI.
     */
    protected FilmService() {
        this.filmRepository = null;
    }

    /**
     * Get all films as DTOs.
     *
     * @return List of film DTOs
     */
    public List<FilmResponse> getAllFilms() {
        return filmRepository.findAll()
                .map(FilmResponse::new)
                .filter(Objects::nonNull)
                .toList();
    }

    /**
     * Get a specific film by ID.
     *
     * @param id Film ID
     * @return Film entity
     * @throws NotFound if film not found
     */
    public Film getFilmById(Long id) {
        return filmRepository.findById(id).orElseThrow(
                () -> new NotFound("Movie with ID: " + id + " not found")
        );
    }

    /**
     * Create a new film.
     *
     * @param film Film to create
     * @param uriInfo URI information for building location header
     * @return Response with location header
     */
    public Response createFilm(Film film, UriInfo uriInfo) {
        var newFilm = filmRepository.save(film);
        return Response
                .status(Response.Status.CREATED)
                .header("Location", uriInfo.getBaseUri() + "films/" + newFilm.getId())
                .build();
    }

    /**
     * Update an existing film.
     *
     * @param film Updated film data
     * @param id ID of film to update
     * @return Response with status
     * @throws NotFound if film not found
     */
    public Response updateFilms(Film film, Long id) {
        filmRepository.findById(id).orElseThrow(
                () -> new NotFound("Movie with ID: " + id + " not found")
        );
        film.setId(id);
        filmRepository.save(film);
        return Response.noContent().build();
    }
}
