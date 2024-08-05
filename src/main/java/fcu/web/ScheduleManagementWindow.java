package fcu.web;

import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.*;
import java.awt.*;
import java.util.List;

public class ScheduleManagementWindow extends JFrame {

  private JComboBox<String> movieNameField;
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

  private void setupMovieNameField() {
    List<Movie> movieList = getMovieList();
    List<String> movieNames = new ArrayList<>();
    for (Movie movie : movieList) {
      movieNames.add(movie.getTitle());
    }
    movieNameField = new JComboBox<>(movieNames.toArray(new String[0]));
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
    String movieName = (String) movieNameField.getSelectedItem();
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
    movieNameField.setSelectedIndex(0);
    dateField.setText("");
    timeField.setSelectedIndex(0);
    setupCinemaField();  // 重新設置影城選項
  }

  private List<Movie> getMovieList() {
    List<Movie> movieList = new ArrayList<>();
    movieList.add(new Movie("機密特務：阿蓋爾", "2024-01-31", "2024-03-11", "暢銷諜報小說作家捲入真實間諜行動。", "馬修范恩", "布萊絲·達拉斯·霍華, 山姆·洛克威爾, 亨利·卡維爾, 杜娃·黎波"));
    movieList.add(new Movie("窺探者", "2024-06-07", "2024-07-17", "藝術家被困愛爾蘭森林，遭神祕生物監視。", "M.奈沙馬蘭", "達科塔·芬妮, 喬吉娜·坎貝爾, 亞利斯泰·布拉姆"));
    movieList.add(new Movie("邊緣禁地", "2024-08-09", "2024-09-18", "賞金獵人尋找失蹤的女孩，對抗外星怪物。", "伊萊·羅斯", "凱特·布蘭琪, 潔美·李·寇帝斯, 凱文·哈特, 傑克·布萊克"));
    movieList.add(new Movie("死侍與金鋼狼", "2024-07-01", "2024-08-10", "死侍與金鋼狼聯手對抗新威脅。", "肖恩·李維", "萊恩·雷諾斯, 休·傑克曼"));
    movieList.add(new Movie("腦筋急轉彎2", "2024-06-07", "2024-07-17", "皮克斯經典動畫續集，情感角色再度回歸。", "彼得·多克特", "艾米·波勒, 菲利斯·史密斯"));
    movieList.add(new Movie("騎士們", "2024-06-07", "2024-07-17", "摩托車幫派的犯罪故事。", "傑夫·尼科爾斯", "奧斯汀·巴特勒, 湯姆·哈迪, 喬迪·科默"));
    movieList.add(new Movie("寂靜之地：第一天", "2024-06-07", "2024-07-17", "《寂靜之地》前傳，揭示怪物來襲的第一天。", "邁克爾·薩諾斯基", "露琵塔·尼詠歐, 約瑟夫·奎因"));
    movieList.add(new Movie("替身演員", "2024-05-01", "2024-06-10", "特技演員捲入危險任務。", "大衛·雷奇", "萊恩·高斯林, 艾蜜莉·布朗特"));
    movieList.add(new Movie("瘋狂麥斯：弗瑞歐莎", "2024-05-01", "2024-06-10", "《瘋狂麥斯》前傳，講述Furiosa的故事。", "喬治·米勒", "安雅·泰勒-喬伊, 克里斯·漢斯沃"));
    movieList.add(new Movie("猩球崛起：王國", "2024-05-01", "2024-06-10", "猩球崛起系列新篇章。", "韋斯·波爾", "歐文·泰格, 弗蕾雅·艾倫"));
    movieList.add(new Movie("殺手", "2024-05-01", "2024-06-10", "一名殺手的雙重生活。", "理查德·林克萊特", "格倫·鮑威爾"));
    movieList.add(new Movie("死者不會受傷", "2024-05-01", "2024-06-10", "西部片，講述一名女人的復仇故事。", "維果·莫特森", "維果·莫特森, 薇姬·克里普斯"));
    movieList.add(new Movie("寶貝們", "2024-05-01", "2024-06-10", "一群女性的友情故事。", "帕梅拉·阿德隆", "黛西·雷德利, 奧利維亞·庫克"));
    movieList.add(new Movie("暴力本質", "2024-05-01", "2024-06-10", "從殺手視角展開的慢節奏驚悚片。", "未知", "未知"));
    movieList.add(new Movie("年輕女子與大海", "2024-05-01", "2024-06-10", "女性游泳運動員的奮鬥故事。", "查理·麥克道威爾", "黛西·雷德利"));
    movieList.add(new Movie("艾比蓋兒", "2024-04-01", "2024-05-11", "一部怪物電影，講述一名女性的生存之戰。", "未知", "未知"));
    movieList.add(new Movie("阿卡迪亞", "2024-04-01", "2024-05-11", "尼可拉斯·凱奇主演的動作混合片。", "未知", "尼可拉斯·凱奇"));
    movieList.add(new Movie("凶兆：起源", "2024-04-01", "2024-05-11", "《凶兆》前傳，探索惡魔的起源。", "未知", "未知"));
    movieList.add(new Movie("內戰", "2024-04-01", "2024-05-11", "A24首部票房冠軍電影，講述內戰故事。", "未知", "未知"));
    movieList.add(new Movie("挑戰者", "2024-04-01", "2024-05-11", "Zendaya主演的運動劇情片。", "盧卡·瓜達尼諾", "Zendaya"));
    return movieList;
  }
}