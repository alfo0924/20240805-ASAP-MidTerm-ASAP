package fcu.web.repository;

import fcu.web.model.Movie;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MovieRepository {
    private List<Movie> movies = new ArrayList<>();
    private long idCounter = 1;

    public List<Movie> findAll() {
        return movies;
    }

    public Optional<Movie> findById(Long id) {
        return movies.stream().filter(movie -> movie.getId().equals(id)).findFirst();
    }

    public Movie save(Movie movie) {
        if (movie.getId() == null) {
            movie.setId(idCounter++);
            movies.add(movie);
        } else {
            deleteById(movie.getId());
            movies.add(movie);
        }
        return movie;
    }

    public void deleteById(Long id) {
        movies.removeIf(movie -> movie.getId().equals(id));
    }
}
