package dto;

import rules.ValidFilm;

@ValidFilm
public record CreatFilm (int year, String title, String director) {

}
