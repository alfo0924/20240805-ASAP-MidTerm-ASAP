package fcu.web.gui;

import fcu.web.model.Movie;
import fcu.web.service.MovieService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Optional;

public class MovieManager {
    private MovieService movieService = new MovieService();
    private JFrame frame;

    public void showMovieList() {
        JFrame movieListFrame = new JFrame("Movie List");
        movieListFrame.setSize(600, 400);
        movieListFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        String[] columnNames = {"ID", "Title", "Description", "Poster", "Release Date", "Actions"};
        List<Movie> movies = movieService.getAllMovies();
        Object[][] data = new Object[movies.size()][6];

        for (int i = 0; i < movies.size(); i++) {
            Movie movie = movies.get(i);
            data[i][0] = movie.getId();
            data[i][1] = movie.getTitle();
            data[i][2] = movie.getDescription();
            data[i][3] = movie.getPoster();
            data[i][4] = movie.getReleaseDate();
            data[i][5] = new JButton("Edit");
            ((JButton) data[i][5]).addActionListener(e -> showEditMovieForm(movie.getId()));
        }

        JTable table = new JTable(data, columnNames);
        JScrollPane scrollPane = new JScrollPane(table);
        movieListFrame.add(scrollPane, BorderLayout.CENTER);
        movieListFrame.setVisible(true);
    }

    public void showAddMovieForm() {
        JFrame addMovieFrame = new JFrame("Add Movie");
        addMovieFrame.setSize(400, 300);
        addMovieFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        addMovieFrame.setLayout(new GridLayout(5, 2));

        JTextField titleField = new JTextField();
        JTextArea descriptionField = new JTextArea();
        JTextField posterField = new JTextField();
        JTextField releaseDateField = new JTextField();

        addMovieFrame.add(new JLabel("Title:"));
        addMovieFrame.add(titleField);
        addMovieFrame.add(new JLabel("Description:"));
        addMovieFrame.add(descriptionField);
        addMovieFrame.add(new JLabel("Poster URL:"));
        addMovieFrame.add(posterField);
        addMovieFrame.add(new JLabel("Release Date (yyyy-mm-dd):"));
        addMovieFrame.add(releaseDateField);

        JButton addButton = new JButton("Add Movie");
        addButton.addActionListener(e -> {
            Movie movie = new Movie();
            movie.setTitle(titleField.getText());
            movie.setDescription(descriptionField.getText());
            movie.setPoster(posterField.getText());
            // Parse release date from input
            try {
                movie.setReleaseDate(java.sql.Date.valueOf(releaseDateField.getText()));
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(addMovieFrame, "Invalid date format");
                return;
            }
            movieService.addMovie(movie);
            addMovieFrame.dispose();
        });
        addMovieFrame.add(addButton);

        addMovieFrame.setVisible(true);
    }

    private void showEditMovieForm(Long id) {
        Optional<Movie> movieOpt = movieService.getMovieById(id);
        if (movieOpt.isPresent()) {
            Movie movie = movieOpt.get();
            JFrame editMovieFrame = new JFrame("Edit Movie");
            editMovieFrame.setSize(400, 300);
            editMovieFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            editMovieFrame.setLayout(new GridLayout(5, 2));

            JTextField titleField = new JTextField(movie.getTitle());
            JTextArea descriptionField = new JTextArea(movie.getDescription());
            JTextField posterField = new JTextField(movie.getPoster());
            JTextField releaseDateField = new JTextField(movie.getReleaseDate().toString());

            editMovieFrame.add(new JLabel("Title:"));
            editMovieFrame.add(titleField);
            editMovieFrame.add(new JLabel("Description:"));
            editMovieFrame.add(descriptionField);
            editMovieFrame.add(new JLabel("Poster URL:"));
            editMovieFrame.add(posterField);
            editMovieFrame.add(new JLabel("Release Date (yyyy-mm-dd):"));
            editMovieFrame.add(releaseDateField);

            JButton updateButton = new JButton("Update Movie");
            updateButton.addActionListener(e -> {
                Movie updatedMovie = new Movie();
                updatedMovie.setId(id);
                updatedMovie.setTitle(titleField.getText());
                updatedMovie.setDescription(descriptionField.getText());
                updatedMovie.setPoster(posterField.getText());
                // Parse release date from input
                try {
                    updatedMovie.setReleaseDate(java.sql.Date.valueOf(releaseDateField.getText()));
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(editMovieFrame, "Invalid date format");
                    return;
                }
                movieService.updateMovie(id, updatedMovie);
                editMovieFrame.dispose();
            });
            editMovieFrame.add(updateButton);

            editMovieFrame.setVisible(true);
        } else {
            JOptionPane.showMessageDialog(frame, "Movie not found");
        }
    }
}
