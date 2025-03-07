package entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;

import java.time.LocalDate;
import java.util.Objects;

@Entity
public class Film {
    public Film() {
    }

    @NotBlank(message = "Title is required")
    private String title;

    @Min(value = 1888, message = "Year must be after the first film was created")
    private int year;

    @NotBlank(message = "Director is required")
    private String director;

    @Min(value = 1, message = "Duration must be greater than 0")
    private int duration;

    @Past
    private LocalDate releaseDate;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    public Film(String title, int year) {
        this.title = title;
        this.year = year;
    }

    public Film(String title, int year, String director, int duration, LocalDate releaseDate) {
        this.title = title;
        this.year = year;
        this.director = director;
        this.duration = duration;
        this.releaseDate = releaseDate;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }

    @Override
    public String toString() {
        return "Film{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", year=" + year +
                ", director='" + director + '\'' +
                ", duration=" + duration +
                '}';
    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Film film = (Film) o;
        return id != null && Objects.equals(id, film.id);
    }

    @Override
    public final int hashCode() {
        return getClass().hashCode();
    }
}
