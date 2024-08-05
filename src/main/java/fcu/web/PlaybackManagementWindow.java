package fcu.web;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PlaybackManagementWindow extends JFrame {
  private JTextArea playbackArea;
  private String currentUser;
  private List<Movie> movieList;

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

    initializeMovieList();
    setupPlaybackList();
  }

  private void initializeMovieList() {
    movieList = new ArrayList<>();
    movieList.add(new Movie(1, "機密特務：阿蓋爾", "2024-01-31", "2024-03-11", "暢銷諜報小說作家捲入真實間諜行動。", "馬修范恩", "布萊絲·達拉斯·霍華, 山姆·洛克威爾, 亨利·卡維爾, 杜娃·黎波"));
    movieList.add(new Movie(2, "窺探者", "2024-06-07", "2024-07-17", "藝術家被困愛爾蘭森林，遭神祕生物監視。", "M.奈沙馬蘭", "達科塔·芬妮, 喬吉娜·坎貝爾, 亞利斯泰·布拉姆"));
    movieList.add(new Movie(3, "邊緣禁地", "2024-08-09", "2024-09-18", "賞金獵人尋找失蹤的女孩，對抗外星怪物。", "伊萊·羅斯", "凱特·布蘭琪, 潔美·李·寇蒂斯, 凱文·哈特, 傑克·布萊克"));
    movieList.add(new Movie(4, "死侍與金鋼狼", "2024-07-01", "2024-08-10", "死侍與金鋼狼聯手對抗新威脅。", "肖恩·李維", "萊恩·雷諾斯, 休·傑克曼"));
    movieList.add(new Movie(5, "腦筋急轉彎2", "2024-06-07", "2024-07-17", "皮克斯經典動畫續集，情感角色再度回歸。", "凱爾西·曼恩", "艾米·波勒, 菲利斯·史密斯, 劉易·布萊克, 托尼·海爾, 麗莎·拉琵拉, 瑪雅·霍克, 阿尤·艾德維利, 阿黛兒·艾薩卓普洛斯, 保羅·華特·豪澤"));
    movieList.add(new Movie(6, "摩托車騎士", "2024-06-07", "2024-07-17", "摩托車幫派的犯罪故事。", "傑夫·尼科爾斯", "奧斯汀·巴特勒, 湯姆·哈迪, 喬迪·科默, 邁克爾·珊農, 諾曼·瑞杜斯, 博伊德·霍布魯克"));
    movieList.add(new Movie(7, "寂靜之地：第一天", "2024-06-07", "2024-07-17", "《寂靜之地》前傳，揭示怪物來襲的第一天。", "邁克爾·薩諾斯基", "露琵塔·尼詠歐, 約瑟夫·奎因, 亞歷克斯·沃爾夫, 吉蒙·翰蘇"));
    movieList.add(new Movie(8, "特技替身", "2024-05-01", "2024-06-10", "特技演員捲入危險任務。", "大衛·雷奇", "萊恩·高斯林, 艾蜜莉·布朗特"));
    movieList.add(new Movie(9, "狂暴之路：芙莉奧莎", "2024-05-01", "2024-06-10", "《瘋狂麥斯》前傳，講述Furiosa的故事。", "喬治·米勒", "安雅·泰勒-喬伊, 克里斯·漢斯沃"));
    movieList.add(new Movie(10, "猩球崛起：猩球王國", "2024-05-01", "2024-06-10", "猩球崛起系列新篇章。", "韋斯·波爾", "歐文·泰格, 弗蕾雅·艾倫, 彼得·麥肯, 凱文·杜蘭"));
    movieList.add(new Movie(11, "殺手", "2024-05-01", "2024-06-10", "一名殺手的雙重生活。", "理查德·林克萊特", "格倫·鮑威爾, 亞德莉亞·阿霍納"));
    movieList.add(new Movie(12, "死者不傷", "2024-05-01", "2024-06-10", "西部片，講述一名女人的復仇故事。", "維果·莫特森", "維果·莫特森, 薇姬·克里普斯"));
    movieList.add(new Movie(13, "姐妹情深", "2024-05-01", "2024-06-10", "一群女性的友情故事。", "帕梅拉·阿德隆", "黛西·雷德利, 奧利維亞·庫克"));
    movieList.add(new Movie(14, "暴力本性", "2024-05-01", "2024-06-10", "從殺手視角展開的慢節奏驚悚片。", "帕布羅·拉雷恩", "貝妮西奧·狄托羅, 露西亞·斯特拉克"));
    movieList.add(new Movie(15, "海上的年輕女子", "2024-05-01", "2024-06-10", "女性游泳運動員的奮鬥故事。", "查理·麥克道威爾", "黛西·雷德利"));
    movieList.add(new Movie(16, "阿比蓋爾", "2024-04-01", "2024-05-11", "一部怪物電影，講述一名女性的生存之戰。", "奧利弗·雷曼", "米亞·華希科沃斯卡, 喬爾·埃哲頓"));
    movieList.add(new Movie(17, "阿卡迪亞", "2024-04-01", "2024-05-11", "尼可拉斯·凱奇主演的動作混合片。", "未知", "尼可拉斯·凱奇"));
    movieList.add(new Movie(18, "凶兆：起源", "2024-04-01", "2024-05-11", "《凶兆》前傳，探索惡魔的起源。", "安德烈·艾芙道斯基", "比爾·斯卡斯加德, 安雅·泰勒-喬伊"));
    movieList.add(new Movie(19, "內戰", "2024-04-01", "2024-05-11", "A24首部票房冠軍電影，講述內戰故事。", "大衛·羅威", "艾莉絲·恩格勒特, 亞當·崔佛"));
    movieList.add(new Movie(20, "挑戰者", "2024-04-01", "2024-05-11", "Zendaya主演的運動劇情片。", "盧卡·瓜達尼諾", "Zendaya"));
  }

  private void setupPlaybackList() {
    List<String> schedules = getSchedulesByDateAndCinema("2024-08-05", currentUser);
    StringBuilder sb = new StringBuilder();

    Random random = new Random();
    for (String schedule : schedules) {
      Movie randomMovie = movieList.get(random.nextInt(movieList.size()));
      sb.append(schedule).append(" - ").append(randomMovie.getTitle()).append("\n");
    }

    playbackArea.setText(sb.toString());
  }

  private List<String> getSchedulesByDateAndCinema(String date, String userName) {
    List<String> schedules = new ArrayList<>();

    // 模擬根據使用者權限生成的資料
    if ("Admin".equals(userName) || "老虎城".equals(userName)) {
      schedules.add("老虎城: 10:00-12:00");
      schedules.add("老虎城: 12:30-14:30");
    }
    if ("Admin".equals(userName) || "大遠百".equals(userName)) {
      schedules.add("大遠百: 15:00-17:00");
      schedules.add("大遠百: 17:30-19:30");
    }
    if ("Admin".equals(userName) || "新時代".equals(userName)) {
      schedules.add("新時代: 20:00-22:00");
    }

    return schedules;
  }
}