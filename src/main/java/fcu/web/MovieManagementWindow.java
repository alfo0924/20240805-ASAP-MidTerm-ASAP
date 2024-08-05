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
        movieList.add(new Movie("機密特務：阿蓋爾", "2024-01-31", "2024-03-11", "暢銷諜報小說作家捲入真實間諜行動。", "馬修范恩", "布萊絲·達拉斯·霍華, 山姆·洛克威爾, 亨利·卡維爾, 杜娃·黎波"));
        movieList.add(new Movie("窺探者", "2024-06-07", "2024-07-17", "藝術家被困愛爾蘭森林，遭神祕生物監視。", "M.奈沙馬蘭", "達科塔·芬妮, 喬吉娜·坎貝爾, 亞利斯泰·布拉姆"));
        movieList.add(new Movie("邊緣禁地", "2024-08-09", "2024-09-18", "賞金獵人尋找失蹤的女孩，對抗外星怪物。", "伊萊·羅斯", "凱特·布蘭琪, 潔美·李·寇帝斯, 凱文·哈特, 傑克·布萊克"));
        movieList.add(new Movie("死侍與金鋼狼", "2024-07-01", "2024-08-10", "死侍與金鋼狼聯手對抗新威脅。", "肖恩·李維", "萊恩·雷諾斯, 休·傑克曼"));
        movieList.add(new Movie("腦筋急轉彎2", "2024-06-07", "2024-07-17", "皮克斯經典動畫續集，情感角色再度回歸。", "凱爾西·曼恩", "艾米·波勒, 菲利斯·史密斯, 劉易·布萊克, 托尼·海爾, 麗莎·拉琵拉, 瑪雅·霍克, 阿尤·艾德維利, 阿黛兒·艾薩卓普洛斯, 保羅·華特·豪澤"));
        movieList.add(new Movie("摩托車騎士", "2024-06-07", "2024-07-17", "摩托車幫派的犯罪故事。", "傑夫·尼科爾斯", "奧斯汀·巴特勒, 湯姆·哈迪, 喬迪·科默, 邁克爾·珊農, 諾曼·瑞杜斯, 博伊德·霍布魯克"));
        movieList.add(new Movie("寂靜之地：第一天", "2024-06-07", "2024-07-17", "《寂靜之地》前傳，揭示怪物來襲的第一天。", "邁克爾·薩諾斯基", "露琵塔·尼詠歐, 約瑟夫·奎因, 亞歷克斯·沃爾夫, 吉蒙·翰蘇"));
        movieList.add(new Movie("特技替身", "2024-05-01", "2024-06-10", "特技演員捲入危險任務。", "大衛·雷奇", "萊恩·高斯林, 艾蜜莉·布朗特"));
        movieList.add(new Movie("狂暴之路：芙莉奧莎", "2024-05-01", "2024-06-10", "《瘋狂麥斯》前傳，講述Furiosa的故事。", "喬治·米勒", "安雅·泰勒-喬伊, 克里斯·漢斯沃"));
        movieList.add(new Movie("猩球崛起：猩球王國", "2024-05-01", "2024-06-10", "猩球崛起系列新篇章。", "韋斯·波爾", "歐文·泰格, 弗蕾雅·艾倫, 彼得·麥肯, 凱文·杜蘭"));
        movieList.add(new Movie("殺手", "2024-05-01", "2024-06-10", "一名殺手的雙重生活。", "理查德·林克萊特", "格倫·鮑威爾, 亞德莉亞·阿霍納"));
        movieList.add(new Movie("死者不傷", "2024-05-01", "2024-06-10", "西部片，講述一名女人的復仇故事。", "維果·莫特森", "維果·莫特森, 薇姬·克里普斯"));
        movieList.add(new Movie("姐妹情深", "2024-05-01", "2024-06-10", "一群女性的友情故事。", "帕梅拉·阿德隆", "黛西·雷德利, 奧利維亞·庫克"));
        movieList.add(new Movie("暴力本性", "2024-05-01", "2024-06-10", "從殺手視角展開的慢節奏驚悚片。", "未知", "未知"));
        movieList.add(new Movie("海上的年輕女子", "2024-05-01", "2024-06-10", "女性游泳運動員的奮鬥故事。", "查理·麥克道威爾", "黛西·雷德利"));
        movieList.add(new Movie("阿比蓋爾", "2024-04-01", "2024-05-11", "一部怪物電影，講述一名女性的生存之戰。", "未知", "未知"));
        movieList.add(new Movie("阿卡迪亞", "2024-04-01", "2024-05-11", "尼可拉斯·凱奇主演的動作混合片。", "未知", "尼可拉斯·凱奇"));
        movieList.add(new Movie("凶兆：起源", "2024-04-01", "2024-05-11", "《凶兆》前傳，探索惡魔的起源。", "未知", "未知"));
        movieList.add(new Movie("內戰", "2024-04-01", "2024-05-11", "A24首部票房冠軍電影，講述內戰故事。", "未知", "未知"));
        movieList.add(new Movie("挑戰者", "2024-04-01", "2024-05-11", "Zendaya主演的運動劇情片。", "盧卡·瓜達尼諾", "Zendaya"));
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