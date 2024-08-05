package fcu.web;

import java.sql.*;

public class DatabaseManager {
  public final String DB_URL = "jdbc:sqlite:user.db";

  public DatabaseManager() {
    createTableIfNotExists();
  }

  private void createTableIfNotExists() {
    try (Connection conn = DriverManager.getConnection(DB_URL);
        Statement stmt = conn.createStatement()) {
      String sql = "CREATE TABLE IF NOT EXISTS user_data (" +
          "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
          "user_name TEXT NOT NULL UNIQUE, " +
          "password TEXT NOT NULL, " +
          "active BOOLEAN NOT NULL DEFAULT 1, " +
          "feature1 BOOLEAN NOT NULL DEFAULT 0, " +
          "feature2 BOOLEAN NOT NULL DEFAULT 0, " +
          "feature3 BOOLEAN NOT NULL DEFAULT 0)";
      stmt.execute(sql);
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  public boolean authenticate(String userName, String password) {
    String query = "SELECT * FROM user_data WHERE user_name = ? AND password = ? AND active = 1";
    try (Connection conn = DriverManager.getConnection(DB_URL);
        PreparedStatement pstmt = conn.prepareStatement(query)) {
      pstmt.setString(1, userName);
      pstmt.setString(2, password);
      ResultSet rs = pstmt.executeQuery();
      return rs.next();
    } catch (SQLException e) {
      e.printStackTrace();
      return false;
    }
  }

  public boolean insertUser(String userName, String password) {
    String insertSQL = "INSERT INTO user_data (user_name, password, active, feature1, feature2, feature3) VALUES (?, ?, 1, 0, 0, 0)";
    try (Connection conn = DriverManager.getConnection(DB_URL);
        PreparedStatement pstmt = conn.prepareStatement(insertSQL)) {
      pstmt.setString(1, userName);
      pstmt.setString(2, password);
      pstmt.executeUpdate();
      return true;
    } catch (SQLException e) {
      if (e.getMessage().contains("UNIQUE constraint failed: user_data.user_name")) {
        System.out.println("Error: User name already exists.");
      } else {
        e.printStackTrace();
      }
      return false;
    }
  }

  public void updatePassword(String userName, String newPassword) throws SQLException {
    String updateSQL = "UPDATE user_data SET password = ? WHERE user_name = ?";
    try (Connection conn = DriverManager.getConnection(DB_URL);
        PreparedStatement pstmt = conn.prepareStatement(updateSQL)) {
      pstmt.setString(1, newPassword);
      pstmt.setString(2, userName);
      pstmt.executeUpdate();
    }
  }

  public void cancelAccount(String userName) throws SQLException {
    String updateSQL = "UPDATE user_data SET active = 0 WHERE user_name = ?";
    try (Connection conn = DriverManager.getConnection(DB_URL);
        PreparedStatement pstmt = conn.prepareStatement(updateSQL)) {
      pstmt.setString(1, userName);
      pstmt.executeUpdate();
    }
  }

  public void restoreAccount(String userName) throws SQLException {
    String updateSQL = "UPDATE user_data SET active = 1 WHERE user_name = ?";
    try (Connection conn = DriverManager.getConnection(DB_URL);
        PreparedStatement pstmt = conn.prepareStatement(updateSQL)) {
      pstmt.setString(1, userName);
      pstmt.executeUpdate();
    }
  }

  public boolean getUserFeature(String userName, String featureName) throws SQLException {
    String sql = "SELECT " + featureName + " FROM user_data WHERE user_name = ?";
    try (Connection conn = DriverManager.getConnection(DB_URL);
        PreparedStatement pstmt = conn.prepareStatement(sql)) {
      pstmt.setString(1, userName);
      try (ResultSet rs = pstmt.executeQuery()) {
        if (rs.next()) {
          return rs.getBoolean(featureName);
        }
      }
    }
    return false; // 如果用戶不存在或發生錯誤，默認返回 false
  }

  public boolean getUserFeature1(String userName) throws SQLException {
    String sql = "SELECT feature1 FROM user_data WHERE user_name = ?";
    try (Connection conn = DriverManager.getConnection(DB_URL);
        PreparedStatement pstmt = conn.prepareStatement(sql)) {
      pstmt.setString(1, userName);
      try (ResultSet rs = pstmt.executeQuery()) {
        if (rs.next()) {
          return rs.getBoolean("feature1");
        }
      }
    }
    return false;
  }

  public void updateUserFeatures(String userName, boolean active, boolean feature1, boolean feature2, boolean feature3) throws SQLException {
    String updateSQL = "UPDATE user_data SET active = ?, feature1 = ?, feature2 = ?, feature3 = ? WHERE user_name = ?";
    try (Connection conn = DriverManager.getConnection(DB_URL);
        PreparedStatement pstmt = conn.prepareStatement(updateSQL)) {
      pstmt.setBoolean(1, active);
      pstmt.setBoolean(2, feature1);
      pstmt.setBoolean(3, feature2);
      pstmt.setBoolean(4, feature3);
      pstmt.setString(5, userName);
      pstmt.executeUpdate();
    }
  }

  public void addSchedule(String movieName, String date, String time, String cinema) {
  }
}
