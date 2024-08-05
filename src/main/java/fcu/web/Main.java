package fcu.web;

import fcu.web.gui.MovieFrame;
import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MovieFrame movieFrame = new MovieFrame();
            movieFrame.setVisible(true);
        });
    }
}
