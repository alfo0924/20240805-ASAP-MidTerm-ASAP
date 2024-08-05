package fcu.web;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class StatisticsReportWindow extends JFrame {
    private JTextArea reportArea;
    private List<Movie> movieList;

    public StatisticsReportWindow(List<Movie> movieList) {
        this.movieList = movieList;
        setTitle("統計報表");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        reportArea = new JTextArea();
        reportArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(reportArea);

        setLayout(new BorderLayout());
        add(scrollPane, BorderLayout.CENTER);

        generateReport();
    }

    private void generateReport() {
        StringBuilder sb = new StringBuilder();
        sb.append("電影熱門統計報表:\n");

        for (Movie movie : movieList) {
            movie.generateRandomBoxOffice(); // Generate a new random box office value
            sb.append(movie.getTitle()).append(" 的票房: $").append(String.format("%,d", movie.getBoxOffice())).append("\n");
        }

        reportArea.setText(sb.toString());
    }
}