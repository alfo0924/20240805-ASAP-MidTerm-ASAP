package fcu.web;

import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.*;
import java.awt.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class ScheduleManagementWindow extends JFrame {

  private JComboBox<Movie> movieNameField;
  private JTextField dateField;
  private JComboBox<String> timeField;
  private JComboBox<String> cinemaField;
  private DatabaseManager dbManager;
  private String currentUser;
  private JFrame mainDashboard;

  public ScheduleManagementWindow(String userName, JFrame mainDashboard) {
    dbManager = new DatabaseManager();
    currentUser = userName;
    this.mainDashboard = mainDashboard;

    setTitle("場次管理");
    setSize(400, 300);
    setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    setLocationRelativeTo(null);

    setLayout(new GridLayout(6, 2, 10, 10));

    setupUIComponents();
  }

  private void setupUIComponents() {
    JLabel movieNameLabel = new JLabel("電影名稱:");
    setupMovieNameField();

    JLabel dateLabel = new JLabel("日期 (YYYY-MM-DD):");
    dateField = new JTextField();

    JLabel timeLabel = new JLabel("時段:");
    String[] times = {"10:00-12:00", "12:30-14:30", "15:00-17:00", "17:30-19:30", "20:00-22:00"};
    timeField = new JComboBox<>(times);

    JLabel cinemaLabel = new JLabel("影城:");
    setupCinemaField();

    JButton addButton = new JButton("新增場次");
    addButton.addActionListener(e -> handleAddSchedule());

    JButton backButton = new JButton("返回");
    backButton.addActionListener(e -> handleBack());

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
    add(new JLabel());
    add(backButton);
  }

  private void setupMovieNameField() {
    List<Movie> movieList = getMovieList();
    movieNameField = new JComboBox<>(movieList.toArray(new Movie[0]));
    movieNameField.setRenderer(new MovieRenderer());
    movieNameField.addActionListener(e -> {
      Movie selectedMovie = (Movie) movieNameField.getSelectedItem();
      if (selectedMovie != null && selectedMovie.getEndDate().compareTo("2024-06-01") <= 0) {
        movieNameField.setSelectedIndex(-1);
      }
    });
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
      availableCinemas.clear();
      availableCinemas.add("無可用影城");
    }

    cinemaField = new JComboBox<>(availableCinemas.toArray(new String[0]));
    cinemaField.setEnabled(!errorOccurred && !availableCinemas.get(0).equals("無可用影城"));
  }

  private void handleAddSchedule() {
    String movieName = ((Movie) movieNameField.getSelectedItem()).getTitle();
    String date = dateField.getText();
    String time = (String) timeField.getSelectedItem();
    String cinema = (String) cinemaField.getSelectedItem();

    if (movieName.isEmpty() || date.isEmpty() || "無可用影城".equals(cinema)) {
      JOptionPane.showMessageDialog(this, "請填寫所有欄位並確保有可用影城！", "錯誤", JOptionPane.ERROR_MESSAGE);
    } else {
      if (!isValidDate(date)) {
        JOptionPane.showMessageDialog(this, "日期不能小於2024-08-06！", "錯誤", JOptionPane.ERROR_MESSAGE);
      } else {
        dbManager.addSchedule(movieName, date, time, cinema);
        JOptionPane.showMessageDialog(this, "成功新增場次！", "成功", JOptionPane.INFORMATION_MESSAGE);
        clearFields();
      }
    }
  }

  private boolean isValidDate(String dateStr) {
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    sdf.setLenient(false);
    try {
      Date inputDate = sdf.parse(dateStr);
      Date minDate = sdf.parse("2024-08-06");
      return !inputDate.before(minDate);
    } catch (ParseException e) {
      return false;
    }
  }

  private void clearFields() {
    movieNameField.setSelectedIndex(0);
    dateField.setText("");
    timeField.setSelectedIndex(0);
    setupCinemaField();
  }

  private List<Movie> getMovieList() {
    List<Movie> movieList = new ArrayList<>();
    movieList.add(new Movie(1, "機密特務：阿蓋爾", "2024-01-31", "2024-03-11", "暢銷諜報小說作家捲入真實間諜行動。", "馬修范恩", "布萊絲·達拉斯·霍華, 山姆·洛克威爾, 亨利·卡維爾, 杜娃·黎波"));
    movieList.add(new Movie(2, "窺探者", "2024-06-07", "2024-07-17", "藝術家被困愛爾蘭森林，遭神祕生物監視。", "M.奈沙馬蘭", "達科塔·芬妮, 喬吉娜·坎貝爾, 亞利斯泰·布拉姆"));
    movieList.add(new Movie(3, "邊緣禁地", "2024-08-09", "2024-09-18", "賞金獵人尋找失蹤的女孩，對抗外星怪物。", "伊萊·羅斯", "凱特·布蘭琪, 潔美·李·寇蒂斯, 凱文·哈特, 傑克·布萊克"));
    movieList.add(new Movie(4, "死侍與金鋼狼", "2024-07-01", "2024-08-10", "死侍與金鋼狼聯手對抗新威脅。", "肖恩·李維", "萊恩·雷諾斯, 休·傑克曼"));
    movieList.add(new Movie(5, "腦筋急轉彎2", "2024-06-07", "2024-07-17", "皮克斯經典動畫續集，情感角色再度回歸。", "凱爾西·曼恩", "艾米·波勒, 菲利斯·史密斯, 劉易·布萊克, 托尼·海爾, 麗莎·拉琵拉, 瑪雅·霍克, 阿尤·艾德維利, 阿黛兒·艾薩卓普洛斯, 保羅·華特·豪澤"));
    movieList.add(new Movie(6, "摩托車騎士", "2024-06-07", "2024-07-17", "摩托車幫派的犯罪故事。", "傑夫·尼科爾斯", "奧斯汀·巴特勒, 湯姆·哈迪, 喬迪·科默, 邁克爾·珊農, 諾曼·瑞杜斯, 博伊德·霍布魯克"));
    movieList.add(new Movie(7, "寂靜之地：第一天", "2024-06-07", "2024-07-17", "《寂靜之地》前傳，揭示怪物來襲的第一天。", "邁克爾·薩諾斯基", "露琵塔·尼詠歐, 約瑟夫·奎因, 亞歷克斯·沃爾夫, 吉蒙·翰蘇"));
    // Add more movies from the original list if needed
    return movieList;
  }

  private void handleBack() {
    mainDashboard.setVisible(true);
    dispose();
  }

  private class MovieRenderer extends DefaultListCellRenderer {
    @Override
    public Component getListCellRendererComponent(JList<?> list, Object value, int index,
                                                  boolean isSelected, boolean cellHasFocus) {
      super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
      if (value instanceof Movie) {
        Movie movie = (Movie) value;
        setText(movie.getTitle());
        if (movie.getEndDate().compareTo("2024-06-01") <= 0) {
          setForeground(Color.GRAY);
        } else {
          setForeground(list.getForeground());
        }
      }
      return this;
    }
  }

  private class DatabaseManager {
    public boolean getUserFeature(String user, String feature) throws SQLException {
      // Simulate getting user feature from database
      return true;
    }

    public void addSchedule(String movieName, String date, String time, String cinema) {
      // Simulate adding schedule to database
      System.out.println("新增場次: " + movieName + ", 日期: " + date + ", 時間: " + time + ", 影城: " + cinema);
    }
  }

  public static void main(String[] args) {
    JFrame mainDashboard = new JFrame("Main Dashboard");
    mainDashboard.setSize(800, 600);
    mainDashboard.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    mainDashboard.setLocationRelativeTo(null);
    mainDashboard.setVisible(true);

    SwingUtilities.invokeLater(() -> {
      new ScheduleManagementWindow("testUser", mainDashboard).setVisible(true);
      mainDashboard.setVisible(false);
    });
  }
}