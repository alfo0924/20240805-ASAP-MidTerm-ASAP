package fcu.web;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class PlaybackManagementWindow extends JFrame {
  private JTextArea playbackArea;
  private String currentUser;

  public PlaybackManagementWindow(String currentUser) {
    this.currentUser = currentUser;
    setTitle("播放管理");
    setSize(500, 400);
    setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    setLocationRelativeTo(null);

    playbackArea = new JTextArea();
    playbackArea.setEditable(false);
    JScrollPane scrollPane = new JScrollPane(playbackArea);

    setLayout(new BorderLayout());
    add(scrollPane, BorderLayout.CENTER);

    setupPlaybackList();
  }

  private void setupPlaybackList() {
    List<String> schedules = getSchedulesByDateAndCinema("2024-08-05", currentUser); // 模擬資料
    StringBuilder sb = new StringBuilder();

    for (String schedule : schedules) {
      sb.append(schedule).append("\n");
    }

    playbackArea.setText(sb.toString());
  }

  private List<String> getSchedulesByDateAndCinema(String date, String userName) {
    List<String> schedules = new ArrayList<>();

    // 模擬根據使用者權限生成的資料
    if ("Admin".equals(userName) || "老虎城".equals(userName)) {
      schedules.add("老虎城: 電影A 10:00-12:00");
      schedules.add("老虎城: 電影B 12:30-14:30");
    }
    if ("Admin".equals(userName) || "大遠百".equals(userName)) {
      schedules.add("大遠百: 電影C 15:00-17:00");
      schedules.add("大遠百: 電影D 17:30-19:30");
    }
    if ("Admin".equals(userName) || "新時代".equals(userName)) {
      schedules.add("新時代: 電影E 20:00-22:00");
    }

    return schedules;
  }
}
