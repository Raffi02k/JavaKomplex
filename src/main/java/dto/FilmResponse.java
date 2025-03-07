package dto;

import entity.Film;

public record FilmResponse(int year, String title, String director) {
    public FilmResponse(Film film) {
        this(film.getYear(), film.getTitle(), film.getDirector());
    }

    public static FilmResponse map(Film film) {
        return new FilmResponse(film.getYear(), film.getTitle(), film.getDirector());
    }
}
