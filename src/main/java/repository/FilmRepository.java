package repository;

import entity.Film;
import jakarta.data.repository.CrudRepository;
import jakarta.data.repository.Repository;

@Repository
public interface FilmRepository extends CrudRepository<Film, Long> {


}
