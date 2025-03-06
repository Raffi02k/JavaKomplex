package mapper;

import dto.CreatFilm;
import dto.FilmResponse;
import entity.Film;

public class FilmMapper {
    private FilmMapper() {}

    public static FilmResponse map(Film film) {
        if( null == film )
            return null;
        return new FilmResponse(film.getYear(), film.getTitle(), film.getDirector());
    }

    public static Film map(CreatFilm film) {
        if( null == film )
            return null;
        Film newFilm = new Film();
        newFilm.setTitle(film.title());
        return newFilm;
    }

}
