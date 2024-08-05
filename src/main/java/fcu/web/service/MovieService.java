package fcu.web.service;

import fcu.web.model.Movie;
import fcu.web.repository.MovieRepository;

import java.util.List;
import java.util.Optional;

public class MovieService {
    private MovieRepository movieRepository = new MovieRepository();

    public List<Movie> getAllMovies() {
        return movieRepository.findAll();
    }

    public Optional<Movie> getMovieById(Long id) {
        return movieRepository.findById(id);
    }

    public Movie addMovie(Movie movie) {
        return movieRepository.save(movie);
    }

    public Movie updateMovie(Long id, Movie movieDetails) {
        Movie movie = movieRepository.findById(id).orElseThrow(() -> new RuntimeException("Movie not found"));
        movie.setTitle(movieDetails.getTitle());
        movie.setDescription(movieDetails.getDescription());
        movie.setPoster(movieDetails.getPoster());
        movie.setReleaseDate(movieDetails.getReleaseDate());
        return movieRepository.save(movie);
    }

    public void deleteMovie(Long id) {
        movieRepository.deleteById(id);
    }
}
