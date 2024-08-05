package fcu.web;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainDashboard extends JFrame {

  private DatabaseManager dbManager;
  private String currentUser;

  public MainDashboard(String currentUser) {
    this.currentUser = currentUser;
    dbManager = new DatabaseManager();

    setTitle("電影管理系統主控制面板");
    setSize(600, 400);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setLocationRelativeTo(null); // 窗口居中

    // 設定布局
    setLayout(new GridLayout(3, 2, 10, 10));

    // 建立按鈕
    JButton accountManagementButton = new JButton("帳號管理");
    JButton movieManagementButton = new JButton("影片管理");
    JButton scheduleManagementButton = new JButton("場次管理");
    JButton playbackManagementButton = new JButton("播放管理");
    JButton reportButton = new JButton("統計報表");

    // 添加按鈕至主視窗
    add(accountManagementButton);
    add(movieManagementButton);
    add(scheduleManagementButton);
    add(playbackManagementButton);
    add(reportButton);

    // 添加按鈕監聽器
    accountManagementButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        if ("Admin".equals(currentUser)) {
          new AccountManagementWindow().setVisible(true);
        } else {
          JOptionPane.showMessageDialog(null, "您沒有權限進行此操作！");
        }
      }
    });

    movieManagementButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        // 打開影片管理視窗或功能
        JOptionPane.showMessageDialog(null, "影片管理");
      }
    });

    scheduleManagementButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        // 打開場次管理視窗或功能
        JOptionPane.showMessageDialog(null, "場次管理");
      }
    });

    playbackManagementButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        // 打開播放管理視窗或功能
        JOptionPane.showMessageDialog(null, "播放管理");
      }
    });

    reportButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        // 打開統計報表視窗或功能
        JOptionPane.showMessageDialog(null, "統計報表");
      }
    });
  }
}
