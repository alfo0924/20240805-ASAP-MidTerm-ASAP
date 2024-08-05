package fcu.web;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MovieManagementWindow extends JFrame {
    private DatabaseManager dbManager;
    private JTable movieTable;
    private MovieTableModel tableModel;
    private JTextField titleField;
    private JTextField directorField;
    private JTextField yearField;
    private JTextField genreField;
    private JLabel messageLabel;

    public MovieManagementWindow(DatabaseManager dbManager) {
        this.dbManager = dbManager;
        setTitle("電影管理");
        setSize(800, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        tableModel = new MovieTableModel();
        movieTable = new JTable(tableModel);
        refreshMovieTable();
        JScrollPane scrollPane = new JScrollPane(movieTable);
        add(scrollPane, BorderLayout.CENTER);

        JPanel inputPanel = new JPanel(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(5, 5, 5, 5);

        JLabel titleLabel = new JLabel("標題:");
        constraints.gridx = 0;
        constraints.gridy = 0;
        inputPanel.add(titleLabel, constraints);
        titleField = new JTextField(20);
        constraints.gridx = 1;
        inputPanel.add(titleField, constraints);

        JLabel directorLabel = new JLabel("導演:");
        constraints.gridx = 0;
        constraints.gridy = 1;
        inputPanel.add(directorLabel, constraints);
        directorField = new JTextField(20);
        constraints.gridx = 1;
        inputPanel.add(directorField, constraints);

        JLabel yearLabel = new JLabel("年份:");
        constraints.gridx = 0;
        constraints.gridy = 2;
        inputPanel.add(yearLabel, constraints);
        yearField = new JTextField(20);
        constraints.gridx = 1;
        inputPanel.add(yearField, constraints);

        JLabel genreLabel = new JLabel("類型:");
        constraints.gridx = 0;
        constraints.gridy = 3;
        inputPanel.add(genreLabel, constraints);
        genreField = new JTextField(20);
        constraints.gridx = 1;
        inputPanel.add(genreField, constraints);

        JButton addMovieButton = new JButton("新增電影");
        constraints.gridx = 1;
        constraints.gridy = 4;
        inputPanel.add(addMovieButton, constraints);

        JButton deleteMovieButton = new JButton("刪除電影");
        constraints.gridx = 1;
        constraints.gridy = 5;
        inputPanel.add(deleteMovieButton, constraints);

        messageLabel = new JLabel();
        constraints.gridx = 1;
        constraints.gridy = 6;
        inputPanel.add(messageLabel, constraints);

        add(inputPanel, BorderLayout.SOUTH);

        addMovieButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleAddMovie();
            }
        });

        deleteMovieButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleDeleteMovie();
            }
        });
    }

    private void refreshMovieTable() {
        tableModel.clear();
        try (ResultSet rs = dbManager.queryMovies()) {
            if (rs != null) {
                while (rs.next()) {
                    int id = rs.getInt("id");
                    String title = rs.getString("title");
                    String director = rs.getString("director");
                    int year = rs.getInt("year");
                    String genre = rs.getString("genre");
                    tableModel.addMovie(id, title, director, year, genre);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void handleAddMovie() {
        String title = titleField.getText();
        String director = directorField.getText();
        int year = Integer.parseInt(yearField.getText());
        String genre = genreField.getText();
        if (dbManager.insertMovie(title, director, year, genre)) {
            messageLabel.setText("電影新增成功！");
            refreshMovieTable();
        } else {
            messageLabel.setText("電影新增失敗！");
        }
    }

    private void handleDeleteMovie() {
        int selectedRow = movieTable.getSelectedRow();
        if (selectedRow >= 0) {
            int id = (int) movieTable.getValueAt(selectedRow, 0);
            if (dbManager.deleteMovie(id)) {
                messageLabel.setText("電影刪除成功！");
                refreshMovieTable();
            } else {
                messageLabel.setText("電影刪除失敗！");
            }
        } else {
            messageLabel.setText("請選擇一部電影！");
        }
    }
}
