package fcu.web;

import java.sql.*;

public class DatabaseManager {
  public final String DB_URL = "jdbc:sqlite:user.db";

  public DatabaseManager() {
    try {
      // 加載JDBC驅動程式
      Class.forName("org.sqlite.JDBC");
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    }
    createTablesIfNotExists();
  }

  private void createTablesIfNotExists() {
    try (Connection conn = DriverManager.getConnection(DB_URL); Statement stmt = conn.createStatement()) {
      String userTableSQL = "CREATE TABLE IF NOT EXISTS user_data (" +
              "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
              "user_name TEXT NOT NULL UNIQUE, " +
              "password TEXT NOT NULL, " +
              "active BOOLEAN NOT NULL DEFAULT 1, " +
              "feature1 BOOLEAN NOT NULL DEFAULT 0, " +
              "feature2 BOOLEAN NOT NULL DEFAULT 0, " +
              "feature3 BOOLEAN NOT NULL DEFAULT 0)";
      stmt.execute(userTableSQL);

      String movieTableSQL = "CREATE TABLE IF NOT EXISTS movies (" +
              "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
              "title TEXT NOT NULL, " +
              "director TEXT, " +
              "year INTEGER, " +
              "genre TEXT)";
      stmt.execute(movieTableSQL);
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  public boolean insertMovie(String title, String director, int year, String genre) {
    String insertSQL = "INSERT INTO movies (title, director, year, genre) VALUES (?, ?, ?, ?)";
    try (Connection conn = DriverManager.getConnection(DB_URL);
         PreparedStatement pstmt = conn.prepareStatement(insertSQL)) {
      pstmt.setString(1, title);
      pstmt.setString(2, director);
      pstmt.setInt(3, year);
      pstmt.setString(4, genre);
      pstmt.executeUpdate();
      return true;
    } catch (SQLException e) {
      e.printStackTrace();
      return false;
    }
  }

  public boolean deleteMovie(int id) {
    String deleteSQL = "DELETE FROM movies WHERE id = ?";
    try (Connection conn = DriverManager.getConnection(DB_URL);
         PreparedStatement pstmt = conn.prepareStatement(deleteSQL)) {
      pstmt.setInt(1, id);
      pstmt.executeUpdate();
      return true;
    } catch (SQLException e) {
      e.printStackTrace();
      return false;
    }
  }

  public ResultSet queryMovies() {
    String querySQL = "SELECT * FROM movies";
    try {
      Connection conn = DriverManager.getConnection(DB_URL);
      Statement stmt = conn.createStatement();
      return stmt.executeQuery(querySQL);
    } catch (SQLException e) {
      e.printStackTrace();
      return null;
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
}
