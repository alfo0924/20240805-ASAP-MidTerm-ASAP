package fcu.web;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class StatisticsReportWindow extends JFrame {
    private JTextArea reportArea;
    private List<Movie> movieList;
    private JFrame parentFrame;

    public StatisticsReportWindow(List<Movie> movieList, JFrame parentFrame) {
        this.movieList = new ArrayList<>(movieList); // 創建一個新的列表以避免修改原始列表
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
        sb.append("電影票房總計:\n\n");

        // 為每部電影生成隨機票房
        for (Movie movie : this.movieList) {
            movie.generateRandomBoxOffice();
        }

        // 根據票房排序電影列表（從高到低）
        Collections.sort(this.movieList, new Comparator<Movie>() {
            @Override
            public int compare(Movie m1, Movie m2) {
                return Integer.compare(m2.getBoxOffice(), m1.getBoxOffice());
            }
        });

        // 顯示排序後的電影列表
        for (int i = 0; i < this.movieList.size(); i++) {
            Movie movie = this.movieList.get(i);
            sb.append(String.format("%d. %s : $%,d\n", i + 1, movie.getTitle(), movie.getBoxOffice()));
        }

        reportArea.setText(sb.toString());
    }
}
