package fcu.web;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.io.FileWriter;
import java.io.IOException;

public class StatisticsReportWindow extends JFrame {
    private JTextArea reportArea;
    private List<Movie> movieList;
    private JFrame parentFrame;

    public StatisticsReportWindow(List<Movie> movieList, JFrame parentFrame) {
        this.movieList = new ArrayList<>(movieList);
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

        JButton downloadButton = new JButton("下載 CSV");
        downloadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                downloadCSV();
            }
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(backButton);
        buttonPanel.add(downloadButton);

        setLayout(new BorderLayout());
        add(scrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        generateReport();
    }

    private void generateReport() {
        StringBuilder sb = new StringBuilder();
        sb.append("電影票房總計:\n\n");

        for (Movie movie : this.movieList) {
            movie.generateRandomBoxOffice();
        }

        Collections.sort(this.movieList, new Comparator<Movie>() {
            @Override
            public int compare(Movie m1, Movie m2) {
                return Integer.compare(m2.getBoxOffice(), m1.getBoxOffice());
            }
        });

        for (int i = 0; i < this.movieList.size(); i++) {
            Movie movie = this.movieList.get(i);
            sb.append(String.format("%d. %s : $%,d\n", i + 1, movie.getTitle(), movie.getBoxOffice()));
        }

        reportArea.setText(sb.toString());
    }

    private void downloadCSV() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("保存 CSV 文件");
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

        int userSelection = fileChooser.showSaveDialog(this);

        if (userSelection == JFileChooser.APPROVE_OPTION) {
            String filePath = fileChooser.getSelectedFile().getAbsolutePath();
            if (!filePath.toLowerCase().endsWith(".csv")) {
                filePath += ".csv";
            }

            try (FileWriter writer = new FileWriter(filePath)) {
                writer.write("排名,電影名稱,票房\n");
                for (int i = 0; i < this.movieList.size(); i++) {
                    Movie movie = this.movieList.get(i);
                    writer.write(String.format("%d,%s,%d\n", i + 1, movie.getTitle(), movie.getBoxOffice()));
                }
                JOptionPane.showMessageDialog(this, "CSV 文件已成功保存！", "下載成功", JOptionPane.INFORMATION_MESSAGE);
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "保存 CSV 文件時發生錯誤：" + ex.getMessage(), "錯誤", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}