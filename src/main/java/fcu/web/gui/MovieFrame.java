package fcu.web.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MovieFrame extends JFrame {
    private MovieManager movieManager;

    public MovieFrame() {
        movieManager = new MovieManager();

        setTitle("Movie Management System");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("Menu");
        JMenuItem listMoviesMenuItem = new JMenuItem("List Movies");
        JMenuItem addMovieMenuItem = new JMenuItem("Add Movie");

        listMoviesMenuItem.addActionListener(e -> movieManager.showMovieList());
        addMovieMenuItem.addActionListener(e -> movieManager.showAddMovieForm());

        menu.add(listMoviesMenuItem);
        menu.add(addMovieMenuItem);
        menuBar.add(menu);
        setJMenuBar(menuBar);
    }
}
