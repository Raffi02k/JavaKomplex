package dto;

public record FilmResponse(int year, String title,  String director, String actors) {
//    public FilmResponse(Film film) {
//        this.year = film.getYear();
//        this.title = film.getTitle();
//        this.director = film.getDirector();
//
//    }
//
//    public static FilmResponse map(Film film) {
//        return new FilmResponse(film.getId(), film.getTitle(), film.getDirector());
//    }

}
