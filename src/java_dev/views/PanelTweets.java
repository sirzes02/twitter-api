package java_dev.views;

import java.awt.*;
import java.net.MalformedURLException;
import java.net.URL;

import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.LayoutStyle;
import javax.swing.ListCellRenderer;
import twitter4j.Status;

public class PanelTweets extends JPanel implements ListCellRenderer<Status> {
  private JLabel jLabel1;
  private JLabel jLabel2;
  private JLabel jLabel3;
  private JTextField jTextField1;

  public PanelTweets() {
    initComponents();
  }

  private void initComponents() {
    jLabel1 = new JLabel();
    jLabel2 = new JLabel();
    jTextField1 = new JTextField();
    jLabel3 = new JLabel();

    jLabel1.setText("jLabel1");
    jLabel2.setText("jLabel2");
    jLabel2.setMaximumSize(new java.awt.Dimension(48, 48));
    jLabel2.setMinimumSize(new java.awt.Dimension(48, 48));
    jLabel2.setPreferredSize(new java.awt.Dimension(48, 48));
    jTextField1.setText("jTextField1");
    jLabel3.setFont(new java.awt.Font("Ubuntu", Font.ITALIC, 12));
    jLabel3.setText("jLabel3");

    GroupLayout layout = new GroupLayout(this);

    this.setLayout(layout);
    layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout
        .createSequentialGroup().addContainerGap()
        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup().addComponent(jLabel1).addGap(0, 0, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addComponent(jLabel2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup().addComponent(jLabel3).addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jTextField1, GroupLayout.DEFAULT_SIZE, 310, Short.MAX_VALUE))))
        .addContainerGap()));
    layout
        .setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup().addContainerGap().addComponent(jLabel1).addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
                        GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
                        GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 9, Short.MAX_VALUE).addComponent(jLabel3)
                .addContainerGap()));
  }

  @Override
  public Component getListCellRendererComponent(JList<? extends Status> list, Status value, int index,
      boolean isSelected, boolean cellHasFocus) {
    jLabel1.setText("@" + value.getUser().getScreenName());
    jLabel3.setText(value.getCreatedAt() + "");
    jTextField1.setText(value.getText());

    URL urlIMG;

    try {
      urlIMG = new URL(value.getUser().getProfileImageURL());

      ImageIcon img = new ImageIcon(urlIMG);
      jLabel2.setIcon(img);
    } catch (MalformedURLException e) {
      e.printStackTrace();
    }

    return this;
  }
}