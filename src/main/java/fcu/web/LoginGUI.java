package fcu.web;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginGUI extends JFrame {

  private JTextField usernameField;
  private JPasswordField passwordField;
  private JButton loginButton;
  private JLabel messageLabel;
  private DatabaseManager dbManager;

  public LoginGUI() {
    dbManager = new DatabaseManager();

    // 設定窗口標題
    setTitle("電影管理系統登入");
    setSize(400, 200);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setLocationRelativeTo(null); // 窗口居中

    // 設定布局
    setLayout(new GridBagLayout());
    GridBagConstraints constraints = new GridBagConstraints();
    constraints.insets = new Insets(5, 5, 5, 5);

    // 建立帳號標籤與輸入框
    JLabel usernameLabel = new JLabel("帳號:");
    constraints.gridx = 0;
    constraints.gridy = 0;
    add(usernameLabel, constraints);

    usernameField = new JTextField(20);
    constraints.gridx = 1;
    add(usernameField, constraints);

    // 建立密碼標籤與輸入框
    JLabel passwordLabel = new JLabel("密碼:");
    constraints.gridx = 0;
    constraints.gridy = 1;
    add(passwordLabel, constraints);

    passwordField = new JPasswordField(20);
    constraints.gridx = 1;
    add(passwordField, constraints);

    // 建立登入按鈕
    loginButton = new JButton("登入");
    constraints.gridx = 1;
    constraints.gridy = 2;
    constraints.gridwidth = 2;
    constraints.anchor = GridBagConstraints.CENTER;
    add(loginButton, constraints);

    // 建立訊息標籤
    messageLabel = new JLabel();
    constraints.gridx = 1;
    constraints.gridy = 3;
    add(messageLabel, constraints);

    // 添加按鈕監聽器
    loginButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        handleLogin();
      }
    });
  }

  private void handleLogin() {
    String username = usernameField.getText();
    String password = new String(passwordField.getPassword());

    // 驗證帳號與密碼
    if (dbManager.authenticate(username, password)) {
      messageLabel.setText("登入成功！");
      // 開啟新的主控制面板視窗，並傳遞當前使用者名稱
      new MainDashboard(username).setVisible(true);
      this.dispose();
    } else {
      messageLabel.setText("帳號或密碼錯誤！");
    }
  }
  public static void main(String[] args) {
    SwingUtilities.invokeLater(new Runnable() {
      @Override
      public void run() {
        new LoginGUI().setVisible(true);
      }
    });
  }
}
