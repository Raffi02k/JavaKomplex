package entity;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
public class Film {
    public Film() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    public Long id;
    private String title;
    private int year;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
        return id;
    }

    public Film(String title, int year) {
        this.title = title;
        this.year = year;
    }


    @Override
    public String toString() {
        return "Film{" +
                "title='" + title + '\'' +
                '}';
    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Film film = (Film) o;
        return id != null && Objects.equals(id, film.id);
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public final int hashCode() {
        return getClass().hashCode();
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
}
