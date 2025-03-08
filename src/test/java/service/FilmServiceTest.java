package service;

import dto.FilmResponse;
import entity.Film;
import exaption.NotFound;
import jakarta.ws.rs.core.Response;
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
import static org.mockito.ArgumentMatchers.argThat;
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

        // Removed the stubbing from here as it's only used in one test
    }

    @Test
    @DisplayName("Should return a list of film responses when films exist")
    void getAllFilms_ShouldReturnFilmResponses_WhenFilmsExist() {
        // Arrange
        Film film1 = new Film("Film 1", 2021, "Director 1", 120, LocalDate.of(2021, 1, 1));
        film1.setId(1L);
        Film film2 = new Film("Film 2", 2022, "Director 2", 130, LocalDate.of(2022, 2, 2));
        film2.setId(2L);

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
        // Arrange
        when(filmRepository.findAll()).thenReturn(Stream.empty());

        // Act
        List<FilmResponse> result = filmService.getAllFilms();

        // Assert
        assertTrue(result.isEmpty());
        verify(filmRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Should return film when film exists with given ID")
    void getFilmById_ShouldReturnFilm_WhenFilmExists() {
        // Arrange
        when(filmRepository.findById(1L)).thenReturn(Optional.of(testFilm));

        // Act
        Film result = filmService.getFilmById(1L);

        // Assert
        assertNotNull(result);
        assertEquals(testFilm.getId(), result.getId());
        assertEquals(testFilm.getTitle(), result.getTitle());
        assertEquals(testFilm.getYear(), result.getYear());
        assertEquals(testFilm.getDirector(), result.getDirector());
        verify(filmRepository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("Should throw NotFound exception when film does not exist with given ID")
    void getFilmById_ShouldThrowNotFound_WhenFilmDoesNotExist() {
        // Arrange
        when(filmRepository.findById(999L)).thenReturn(Optional.empty());

        // Act & Assert
        NotFound exception = assertThrows(NotFound.class, () -> {
            filmService.getFilmById(999L);
        });

        assertEquals("Movie with ID: 999 not found", exception.getMessage());
        verify(filmRepository, times(1)).findById(999L);
    }

    @Test
    @DisplayName("Should create film and return response with location header")
    void createFilm_ShouldCreateFilmAndReturnResponse() {
        // Arrange
        Film filmToCreate = new Film("New Film", 2023, "New Director", 110, LocalDate.of(2023, 3, 3));

        // Mock the save method to return a film with ID
        Film savedFilm = new Film("New Film", 2023, "New Director", 110, LocalDate.of(2023, 3, 3));
        savedFilm.setId(1L);

        when(filmRepository.save(any(Film.class))).thenReturn(savedFilm);

        // Setup UriInfo mock in this specific test
        when(uriInfo.getBaseUri()).thenReturn(URI.create("http://localhost:8080/api/"));

        // Act
        Response response = filmService.createFilm(filmToCreate, uriInfo);

        // Assert
        assertEquals(Response.Status.CREATED.getStatusCode(), response.getStatus());

        String locationHeader = response.getHeaderString("Location");
        assertNotNull(locationHeader);
        assertEquals("http://localhost:8080/api/films/1", locationHeader);

        verify(filmRepository, times(1)).save(any(Film.class));
    }

    @Test
    @DisplayName("Should update film when film exists with given ID")
    void updateFilms_ShouldUpdateFilm_WhenFilmExists() {
        // Arrange
        Film updatedFilm = new Film("Updated Film", 2024, "Updated Director", 130, LocalDate.of(2024, 4, 4));

        when(filmRepository.findById(1L)).thenReturn(Optional.of(testFilm));
        when(filmRepository.save(any(Film.class))).thenReturn(updatedFilm);

        // Act
        Response response = filmService.updateFilms(updatedFilm, 1L);

        // Assert
        assertEquals(Response.Status.NO_CONTENT.getStatusCode(), response.getStatus());

        // Verify repository interactions
        verify(filmRepository, times(1)).findById(1L);

        // Verify film ID was properly set before saving
        verify(filmRepository, times(1)).save(argThat(film -> {
            assertEquals(1L, film.getId());
            assertEquals("Updated Film", film.getTitle());
            assertEquals(2024, film.getYear());
            assertEquals("Updated Director", film.getDirector());
            return true;
        }));
    }

    @Test
    @DisplayName("Should throw NotFound exception when updating non-existent film")
    void updateFilms_ShouldThrowNotFound_WhenFilmDoesNotExist() {
        // Arrange
        Film updatedFilm = new Film("Updated Film", 2024, "Updated Director", 130, LocalDate.of(2024, 4, 4));

        when(filmRepository.findById(999L)).thenReturn(Optional.empty());

        // Act & Assert
        NotFound exception = assertThrows(NotFound.class, () -> {
            filmService.updateFilms(updatedFilm, 999L);
        });

        assertEquals("Movie with ID: 999 not found", exception.getMessage());
        verify(filmRepository, times(1)).findById(999L);
        verify(filmRepository, never()).save(any(Film.class));
    }
}
