package fcu.web.controller;

import fcu.web.model.Movie;
import fcu.web.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/movies")
public class MovieController {

    @Autowired
    private MovieService movieService;

    @GetMapping
    public String getAllMovies(Model model) {
        List<Movie> movies = movieService.getAllMovies();
        model.addAttribute("movies", movies);
        return "movie-list";
    }

    @GetMapping("/add")
    public String showAddMovieForm(Model model) {
        model.addAttribute("movie", new Movie());
        return "add-movie";
    }

    @PostMapping("/add")
    public String addMovie(@ModelAttribute Movie movie) {
        movieService.addMovie(movie);
        return "redirect:/movies";
    }

    @GetMapping("/edit/{id}")
    public String showEditMovieForm(@PathVariable Long id, Model model) {
        Movie movie = movieService.getMovieById(id).orElseThrow(() -> new RuntimeException("Movie not found"));
        model.addAttribute("movie", movie);
        return "edit-movie";
    }

    @PostMapping("/edit/{id}")
    public String updateMovie(@PathVariable Long id, @ModelAttribute Movie movie) {
        movieService.updateMovie(id, movie);
        return "redirect:/movies";
    }

    @GetMapping("/delete/{id}")
    public String deleteMovie(@PathVariable Long id) {
        movieService.deleteMovie(id);
        return "redirect:/movies";
    }
}