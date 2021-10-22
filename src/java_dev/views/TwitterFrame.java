package java_dev.views;

import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Date;
import java.util.Objects;
import javax.swing.DefaultListModel;
import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.LayoutStyle;
import javax.swing.WindowConstants;

import kotlin_dev.world.Configurations;
import twitter4j.Paging;
import twitter4j.ResponseList;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.User;

public class TwitterFrame extends JFrame {
  Twitter twitter;
  DefaultListModel<Status> statuses = new DefaultListModel<>();
  private Connection conn;
  private JLabel jLabel1;
  private JTextField jTextField1;

  public TwitterFrame(Twitter twitter) {
    try {
      conn = DriverManager.getConnection(Configurations.DB_CONNECTION, Configurations.DB_USER, Configurations.DB_PASSWORD);
    } catch (SQLException e) {
      e.printStackTrace();
    }

    initComponents();
    setTitle("Twitter - Big Data");
    this.twitter = twitter;

    User user;

    try {
      user = twitter.showUser(twitter.getId());

      URL urlIMG = new URL(user.getProfileImageURL());
      ImageIcon img = new ImageIcon(urlIMG);

      jLabel1.setIcon(img);
      update();
      setVisible(true);
    } catch (IllegalStateException | TwitterException | MalformedURLException e) {
      e.printStackTrace();
    }
  }

  private void initComponents() {
    jLabel1 = new JLabel();
    JButton jButton1 = new JButton();
    jTextField1 = new JTextField();
    JScrollPane jScrollPane1 = new JScrollPane();
    JList<Status> jList1 = new JList<>();

    setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

    jLabel1.setText("icon");
    jLabel1.setMaximumSize(new java.awt.Dimension(48, 48));
    jLabel1.setMinimumSize(new java.awt.Dimension(48, 48));
    jLabel1.setPreferredSize(new java.awt.Dimension(48, 48));

    jButton1.setText("Tweet");
    jButton1.addActionListener(evt -> {
      if (!Objects.equals(jTextField1.getText(), "")) {
        try {
          twitter.updateStatus(jTextField1.getText());
          jTextField1.setText("");

          update();

          JOptionPane.showMessageDialog(null, "New tweet", "You have done a new tweet",
              JOptionPane.INFORMATION_MESSAGE);
        } catch (TwitterException e) {
          e.printStackTrace();
        }
      }
    });

    jList1.setModel(statuses);
    jList1.setCellRenderer(new PanelTweets());
    jScrollPane1.setViewportView(jList1);

    GroupLayout layout = new GroupLayout(getContentPane());
    getContentPane().setLayout(layout);
    layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout
        .createSequentialGroup().addContainerGap()
        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, GroupLayout.DEFAULT_SIZE, 576, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jLabel1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(jTextField1).addGap(18, 18, 18)
                .addComponent(jButton1)))
        .addContainerGap()));
    layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
        .addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup().addContainerGap()
            .addComponent(jScrollPane1, GroupLayout.DEFAULT_SIZE, 191, Short.MAX_VALUE)
            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
            .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                .addComponent(jLabel1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addComponent(jButton1).addComponent(jTextField1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
                    GroupLayout.PREFERRED_SIZE))
            .addContainerGap()));
    pack();
  }

  public void update() {
    try {
      Paging pagination = new Paging();
      pagination.setCount(50);
      ResponseList<Status> list;
      list = twitter.getHomeTimeline(pagination);

      for (Status status : list) {
        statuses.addElement(status);
        saveInDB(status.getText(), status.getUser().getScreenName(), status.getCreatedAt());
      }
    } catch (TwitterException e) {
      e.printStackTrace();
    }
  }

  private void saveInDB(String text, String screenName, Date createdAt) {
    try {
      PreparedStatement stmt = conn.prepareStatement("INSERT INTO tweets VALUES (NULL, ?, ?, ?)");
      stmt.setString(1, screenName);
      stmt.setString(2, text);
      stmt.setString(3, createdAt.toString());
      stmt.executeUpdate();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }
}