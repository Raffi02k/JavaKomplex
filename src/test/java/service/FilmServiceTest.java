package service;

import dto.FilmResponse;
import entity.Film;
import exaption.NotFound;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriBuilder;
import jakarta.ws.rs.core.UriInfo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import repository.FilmRepository;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FilmServiceTest {

    @Mock
    private FilmRepository filmRepository;

    @Mock
    private UriInfo uriInfo;

    @InjectMocks
    private FilmService filmService;

    private Film testFilm;

    @BeforeEach
    void setUp() {
        // Create a test film to use in multiple tests
        testFilm = new Film("Test Film", 2023, "Test Director", 120, LocalDate.of(2023, 1, 1));
        testFilm.setId(1L);

        // Setup UriInfo mock
        URI baseUri = URI.create("http://localhost:8080/api/");
        when(uriInfo.getBaseUri()).thenReturn(baseUri);
    }

    @Test
    @DisplayName("Should return a list of film responses when films exist")
    void getAllFilms_ShouldReturnFilmResponses_WhenFilmsExist() {
        // Arrange
        Film film1 = new Film("Film 1", 2021, "Director 1", 120, LocalDate.of(2021, 1, 1));
        Film film2 = new Film("Film 2", 2022, "Director 2", 130, LocalDate.of(2022, 2, 2));
        when(filmRepository.findAll()).thenReturn(Stream.of(film1, film2));

        // Act
        List<FilmResponse> result = filmService.getAllFilms();

        // Assert
        assertEquals(2, result.size());
        assertEquals("Film 1", result.get(0).title());
        assertEquals("Film 2", result.get(1).title());
        assertEquals(2021, result.get(0).year());
        assertEquals(2022, result.get(1).year());
        verify(filmRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Should return an empty list when no films exist")
    void getAllFilms_ShouldReturnEmptyList_WhenNoFilmsExist() {

    }

    @Test
    @DisplayName("Should return film when film exists with given ID")
    void getFilmById_ShouldReturnFilm_WhenFilmExists() {

    }

    @Test
    @DisplayName("Should throw NotFound exception when film does not exist with given ID")
    void getFilmById_ShouldThrowNotFound_WhenFilmDoesNotExist() {

    }

    @Test
    @DisplayName("Should create film and return response with location header")
    void createFilm_ShouldCreateFilmAndReturnResponse() {

    }

    @Test
    @DisplayName("Should update film when film exists with given ID")
    void updateFilms_ShouldUpdateFilm_WhenFilmExists() {

    }

    @Test
    @DisplayName("Should throw NotFound exception when updating non-existent film")
    void updateFilms_ShouldThrowNotFound_WhenFilmDoesNotExist() {

    }
}
