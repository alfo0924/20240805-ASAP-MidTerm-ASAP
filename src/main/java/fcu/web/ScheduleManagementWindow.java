package fcu.web;

import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
public class ScheduleManagementWindow extends JFrame {

  private JTextField movieNameField;
  private JTextField dateField;
  private JComboBox<String> timeField;
  private JComboBox<String> cinemaField;
  private DatabaseManager dbManager;
  private String currentUser;

  public ScheduleManagementWindow(String userName) {
    dbManager = new DatabaseManager();
    currentUser = userName;

    setTitle("場次管理");
    setSize(400, 300);
    setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    setLocationRelativeTo(null);

    setLayout(new GridLayout(5, 2, 10, 10));

    setupUIComponents();
  }

  private void setupUIComponents() {
    JLabel movieNameLabel = new JLabel("電影名稱:");
    movieNameField = new JTextField();

    JLabel dateLabel = new JLabel("日期 (YYYY-MM-DD):");
    dateField = new JTextField();

    JLabel timeLabel = new JLabel("時段:");
    String[] times = {"10:00-12:00", "12:30-14:30", "15:00-17:00", "17:30-19:30", "20:00-22:00"};
    timeField = new JComboBox<>(times);

    JLabel cinemaLabel = new JLabel("影城:");
    setupCinemaField();

    JButton addButton = new JButton("新增場次");
    addButton.addActionListener(e -> handleAddSchedule());

    add(movieNameLabel);
    add(movieNameField);
    add(dateLabel);
    add(dateField);
    add(timeLabel);
    add(timeField);
    add(cinemaLabel);
    add(cinemaField);
    add(new JLabel());
    add(addButton);
  }

  private void setupCinemaField() {
    List<String> availableCinemas = new ArrayList<>();
    boolean errorOccurred = false;

    try {
      if (dbManager.getUserFeature(currentUser, "feature1")) {
        availableCinemas.add("老虎城");
      }
      if (dbManager.getUserFeature(currentUser, "feature2")) {
        availableCinemas.add("大遠百");
      }
      if (dbManager.getUserFeature(currentUser, "feature3")) {
        availableCinemas.add("新時代");
      }
    } catch (SQLException e) {
      errorOccurred = true;
      String errorMessage = "獲取用戶權限時發生錯誤: " + e.getMessage();
      JOptionPane.showMessageDialog(this, errorMessage, "資料庫錯誤", JOptionPane.ERROR_MESSAGE);
      System.err.println(errorMessage);
      e.printStackTrace();
    }

    if (availableCinemas.isEmpty() || errorOccurred) {
      availableCinemas.clear();  // 確保列表為空
      availableCinemas.add("無可用影城");
    }

    cinemaField = new JComboBox<>(availableCinemas.toArray(new String[0]));
    cinemaField.setEnabled(!errorOccurred && !availableCinemas.get(0).equals("無可用影城"));
  }

  private void handleAddSchedule() {
    String movieName = movieNameField.getText();
    String date = dateField.getText();
    String time = (String) timeField.getSelectedItem();
    String cinema = (String) cinemaField.getSelectedItem();

    if (movieName.isEmpty() || date.isEmpty() || "無可用影城".equals(cinema)) {
      JOptionPane.showMessageDialog(this, "請填寫所有欄位並確保有可用影城！", "錯誤", JOptionPane.ERROR_MESSAGE);
    } else {
      dbManager.addSchedule(movieName, date, time, cinema);
      JOptionPane.showMessageDialog(this, "成功新增場次！", "成功", JOptionPane.INFORMATION_MESSAGE);
      clearFields();
    }
  }

  private void clearFields() {
    movieNameField.setText("");
    dateField.setText("");
    timeField.setSelectedIndex(0);
    setupCinemaField();  // 重新設置影城選項
  }
}