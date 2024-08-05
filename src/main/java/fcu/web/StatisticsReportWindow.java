package fcu.web;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class StatisticsReportWindow extends JFrame {
    private JTextArea reportArea;
    private List<Movie> movieList;
    private JFrame parentFrame;

    public StatisticsReportWindow(List<Movie> movieList, JFrame parentFrame) {
        this.movieList = movieList;
        this.parentFrame = parentFrame;
        setTitle("統計報表");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        reportArea = new JTextArea();
        reportArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(reportArea);

        JButton backButton = new JButton("返回");
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                parentFrame.setVisible(true);
                dispose();
            }
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(backButton);

        setLayout(new BorderLayout());
        add(scrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

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