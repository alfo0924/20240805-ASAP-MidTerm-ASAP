package fcu.web;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class AccountManagementWindow extends JFrame {
  private DatabaseManager dbManager;
  private JTable userTable;
  private UserTableModel tableModel;
  private JButton addUserButton;
  private JButton updatePasswordButton;
  private JButton cancelAccountButton;
  private JButton restoreAccountButton;
  private JButton confirmChangesButton;
  private JButton backButton;
  private JTextField userNameField;
  private JPasswordField passwordField;
  private JLabel messageLabel;
  private JFrame mainDashboard;

  public AccountManagementWindow(DatabaseManager dbManager, JFrame mainDashboard) {
    this.dbManager = dbManager;
    this.mainDashboard = mainDashboard;

    setTitle("帳號管理");
    setSize(800, 500);
    setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    setLocationRelativeTo(null); // 窗口居中

    setLayout(new BorderLayout());

    // 使用者列表表格模型
    tableModel = new UserTableModel();
    userTable = new JTable(tableModel);
    refreshUserTable();

    JScrollPane scrollPane = new JScrollPane(userTable);
    add(scrollPane, BorderLayout.CENTER);

    JPanel inputPanel = new JPanel(new GridBagLayout());
    GridBagConstraints constraints = new GridBagConstraints();
    constraints.insets = new Insets(5, 5, 5, 5);

    // 使用者名稱
    JLabel userNameLabel = new JLabel("使用者名稱:");
    constraints.gridx = 0;
    constraints.gridy = 0;
    inputPanel.add(userNameLabel, constraints);

    userNameField = new JTextField(20);
    constraints.gridx = 1;
    inputPanel.add(userNameField, constraints);

    // 密碼
    JLabel passwordLabel = new JLabel("密碼:");
    constraints.gridx = 0;
    constraints.gridy = 1;
    inputPanel.add(passwordLabel, constraints);

    passwordField = new JPasswordField(20);
    constraints.gridx = 1;
    inputPanel.add(passwordField, constraints);

    // 新增使用者按鈕
    addUserButton = new JButton("新增使用者");
    constraints.gridx = 1;
    constraints.gridy = 2;
    inputPanel.add(addUserButton, constraints);

    // 修改密碼按鈕
    updatePasswordButton = new JButton("修改密碼");
    constraints.gridx = 1;
    constraints.gridy = 3;
    inputPanel.add(updatePasswordButton, constraints);

    // 取消帳號按鈕
    cancelAccountButton = new JButton("取消帳號");
    constraints.gridx = 1;
    constraints.gridy = 4;
    inputPanel.add(cancelAccountButton, constraints);

    // 恢復帳號按鈕
    restoreAccountButton = new JButton("恢復帳號");
    constraints.gridx = 1;
    constraints.gridy = 5;
    inputPanel.add(restoreAccountButton, constraints);

    // 確認更改按鈕
    confirmChangesButton = new JButton("確認更改");
    constraints.gridx = 1;
    constraints.gridy = 6;
    inputPanel.add(confirmChangesButton, constraints);

    // 返回按鈕
    backButton = new JButton("返回");
    constraints.gridx = 1;
    constraints.gridy = 7;
    inputPanel.add(backButton, constraints);

    // 訊息標籤
    messageLabel = new JLabel();
    constraints.gridx = 1;
    constraints.gridy = 8;
    inputPanel.add(messageLabel, constraints);

    add(inputPanel, BorderLayout.SOUTH);

    // 添加按鈕監聽器
    addUserButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        handleAddUser();
      }
    });

    updatePasswordButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        handleUpdatePassword();
      }
    });

    cancelAccountButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        handleCancelAccount();
      }
    });

    restoreAccountButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        handleRestoreAccount();
      }
    });

    confirmChangesButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        handleConfirmChanges();
      }
    });

    backButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        mainDashboard.setVisible(true);
        dispose();
      }
    });
  }

  private void refreshUserTable() {
    tableModel.clear();
    try (Connection conn = DriverManager.getConnection(dbManager.DB_URL);
         Statement stmt = conn.createStatement();
         ResultSet rs = stmt.executeQuery("SELECT user_name, active FROM user_data")) {
      while (rs.next()) {
        String userName = rs.getString("user_name");
        boolean isActive = rs.getBoolean("active");
        tableModel.addUser(userName, isActive);
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  private void handleAddUser() {
    String userName = userNameField.getText();
    String password = new String(passwordField.getPassword());
    if (dbManager.insertUser(userName, password)) {
      messageLabel.setText("使用者新增成功！");
      refreshUserTable();
    } else {
      messageLabel.setText("使用者名稱已存在！");
    }
  }

  private void handleUpdatePassword() {
    String userName = userNameField.getText();
    String password = new String(passwordField.getPassword());
    try {
      dbManager.updatePassword(userName, password);
      messageLabel.setText("密碼修改成功！");
    } catch (SQLException e) {
      e.printStackTrace();
      messageLabel.setText("密碼修改失敗！");
    }
  }

  private void handleCancelAccount() {
    int selectedRow = userTable.getSelectedRow();
    if (selectedRow >= 0) {
      String userName = (String) userTable.getValueAt(selectedRow, 0);
      try {
        dbManager.cancelAccount(userName);
        messageLabel.setText("帳號已取消！");
        refreshUserTable();
      } catch (SQLException e) {
        e.printStackTrace();
        messageLabel.setText("帳號取消失敗！");
      }
    } else {
      messageLabel.setText("請選擇一個使用者！");
    }
  }

  private void handleRestoreAccount() {
    int selectedRow = userTable.getSelectedRow();
    if (selectedRow >= 0) {
      String userName = (String) userTable.getValueAt(selectedRow, 0);
      try {
        dbManager.restoreAccount(userName);
        messageLabel.setText("帳號已恢復！");
        refreshUserTable();
      } catch (SQLException e) {
        e.printStackTrace();
        messageLabel.setText("帳號恢復失敗！");
      }
    } else {
      messageLabel.setText("請選擇一個使用者！");
    }
  }

  private void handleConfirmChanges() {
    for (int i = 0; i < tableModel.getRowCount(); i++) {
      String userName = (String) tableModel.getValueAt(i, 0);
      boolean isActive = (Boolean) tableModel.getValueAt(i, 1);
      boolean feature1 = (Boolean) tableModel.getValueAt(i, 2);
      boolean feature2 = (Boolean) tableModel.getValueAt(i, 3);
      boolean feature3 = (Boolean) tableModel.getValueAt(i, 4);
      try {
        dbManager.updateUserFeatures(userName, isActive, feature1, feature2, feature3);
      } catch (SQLException e) {
        e.printStackTrace();
        messageLabel.setText("更新功能權限失敗！");
      }
    }
    messageLabel.setText("功能權限更新成功！");
    refreshUserTable();
  }

  public static void main(String[] args) {
    SwingUtilities.invokeLater(new Runnable() {
      @Override
      public void run() {
        AccountManagementWindow window = new AccountManagementWindow(new DatabaseManager(), new JFrame());
        window.setVisible(true);
      }
    });
  }
}
