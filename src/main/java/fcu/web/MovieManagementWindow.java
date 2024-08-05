package fcu.web;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class MovieManagementWindow extends JFrame {
    private DatabaseManager dbManager;
    private JTable movieTable;
    private MovieTableModel tableModel;
    private JTextField titleField;
    private JTextField directorField;
    private JTextField yearField;
    private JTextField genreField;
    private JLabel messageLabel;
    private JFrame mainDashboard;

    public MovieManagementWindow(DatabaseManager dbManager, JFrame mainDashboard) {
        this.dbManager = dbManager;
        this.mainDashboard = mainDashboard;
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

        JButton queryMovieButton = new JButton("查詢電影");
        constraints.gridx = 1;
        constraints.gridy = 6;
        inputPanel.add(queryMovieButton, constraints);

        JButton backButton = new JButton("返回");
        constraints.gridx = 1;
        constraints.gridy = 7;
        inputPanel.add(backButton, constraints);

        messageLabel = new JLabel();
        constraints.gridx = 1;
        constraints.gridy = 8;
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

        queryMovieButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                refreshMovieTable();
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

    private void refreshMovieTable() {
        tableModel.clear();
        List<Movie> movieList = getMovieList();
        for (Movie movie : movieList) {
            tableModel.addMovie(movie);
        }
    }

    private List<Movie> getMovieList() {
        List<Movie> movieList = new ArrayList<>();
        movieList.add(new Movie("機密特務：阿蓋爾", "2024-01-31", "2024-03-11", "暢銷諜報小說作家捲入真實間諜行動。", "馬修范恩", "布萊絲達拉斯霍華, 山姆洛克威爾, 亨利卡維爾, 杜娃黎波"));
        movieList.add(new Movie("窺探者", "2024-06-07", "2024-07-17", "藝術家被困愛爾蘭森林，遭神祕生物監視。", "M.奈沙馬蘭", "達科塔芬妮, 喬吉娜坎貝爾, 亞利斯泰布拉姆"));
        movieList.add(new Movie("邊緣禁地", "2024-08-09", "2024-09-18", "賞金獵人尋找失蹤的女孩，對抗外星怪物。", "伊萊羅斯", "凱特布蘭琪, 潔美李寇帝斯, 凱文哈特, 傑克布萊克"));
        movieList.add(new Movie("死侍與金鋼狼", "2024-07-01", "2024-08-10", "死侍與金鋼狼聯手對抗新威脅。", "肖恩李維", "萊恩雷諾斯, 休傑克曼"));
        movieList.add(new Movie("Inside Out 2", "2024-06-07", "2024-07-17", "皮克斯經典動畫續集，情感角色再度回歸。", "彼得多克特", "艾米波勒, 菲利斯史密斯"));
        movieList.add(new Movie("The Bikeriders", "2024-06-07", "2024-07-17", "摩托車幫派的犯罪故事。", "傑夫尼科爾斯", "奧斯汀巴特勒, 湯姆哈迪, 喬迪科默"));
        movieList.add(new Movie("A Quiet Place: Day One", "2024-06-07", "2024-07-17", "《寂靜之地》前傳，揭示怪物來襲的第一天。", "邁克爾薩諾斯基", "露琵塔尼詠歐, 約瑟夫奎因"));
        movieList.add(new Movie("The Fall Guy", "2024-05-01", "2024-06-10", "特技演員捲入危險任務。", "大衛雷奇", "萊恩高斯林, 艾蜜莉布朗特"));
        movieList.add(new Movie("Furiosa", "2024-05-01", "2024-06-10", "《瘋狂麥斯》前傳，講述Furiosa的故事。", "喬治米勒", "安雅泰勒喬伊, 克里斯漢斯沃"));
        movieList.add(new Movie("Kingdom of the Planet of the Apes", "2024-05-01", "2024-06-10", "猩球崛起系列新篇章。", "韋斯波爾", "歐文泰格, 弗蕾雅艾倫"));
        movieList.add(new Movie("Hit Man", "2024-05-01", "2024-06-10", "一名殺手的雙重生活。", "理查德林克萊特", "格倫鮑威爾"));
        movieList.add(new Movie("The Dead Don’t Hurt", "2024-05-01", "2024-06-10", "西部片，講述一名女人的復仇故事。", "維果莫特森", "維果莫特森, 薇姬克里普斯"));
        movieList.add(new Movie("Babes", "2024-05-01", "2024-06-10", "一群女性的友情故事。", "帕梅拉阿德隆", "戴西雷德利, 奧利維亞庫克"));
        movieList.add(new Movie("In a Violent Nature", "2024-05-01", "2024-06-10", "從殺手視角展開的慢節奏驚悚片。", "未知", "未知"));
        movieList.add(new Movie("Young Woman and the Sea", "2024-05-01", "2024-06-10", "女性游泳運動員的奮鬥故事。", "查理麥克道威爾", "戴西雷德利"));
        movieList.add(new Movie("Abigail", "2024-04-01", "2024-05-11", "一部怪物電影，講述一名女性的生存之戰。", "未知", "未知"));
        movieList.add(new Movie("Arcadian", "2024-04-01", "2024-05-11", "尼可拉斯凱奇主演的動作混合片。", "未知", "尼可拉斯凱奇"));
        movieList.add(new Movie("The First Omen", "2024-04-01", "2024-05-11", "《凶兆》前傳，探索惡魔的起源。", "未知", "未知"));
        movieList.add(new Movie("Civil War", "2024-04-01", "2024-05-11", "A24首部票房冠軍電影，講述內戰故事。", "未知", "未知"));
        movieList.add(new Movie("Challengers", "2024-04-01", "2024-05-11", "Zendaya主演的運動劇情片。", "盧卡瓜達尼諾", "Zendaya"));
        return movieList;
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