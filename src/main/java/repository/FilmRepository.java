package repository;

import entity.Film;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class FilmRepository {

    @PersistenceContext
    private EntityManager em;

    public List<Film> findAll() {
        return em.createQuery("SELECT f FROM Film f", Film.class).getResultList();
    }

    public Optional<Film> findById(Long id) {
        Film film = em.find(Film.class, id);
        return Optional.ofNullable(film);
    }

    @Transactional
    public Film save(Film film) {
        if (film.getId() == null) {
            em.persist(film);
            return film;
        } else {
            return em.merge(film);
        }
    }

    @Transactional
    public void delete(Film film) {
        if (em.contains(film)) {
            em.remove(film);
        } else {
            em.remove(em.merge(film));
        }
    }

}
