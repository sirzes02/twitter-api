package kotlin_dev.views

import kotlin_dev.world.Configurations
import twitter4j.*
import java.awt.Dimension
import java.net.URL
import java.sql.DriverManager
import java.util.*
import javax.swing.*

class TwitterFrame(private val twitter: Twitter) : JFrame() {
    private var conn = DriverManager.getConnection(Configurations.DB_CONNECTION, Configurations.DB_USER, Configurations.DB_PASSWORD)
    private var statuses = DefaultListModel<Status>()
    private var jButton1 = JButton("Tweet")
    private var jLabel1 = JLabel("icon")
    private var jList1 = JList<Status>()
    private var jScrollPane1 = JScrollPane()
    private var jTextField1 = JTextField()

    init {
        initComponents()

        title = "Twitter - Big Data"

        val user: User = twitter.showUser(twitter.id)
        val urlIMG = URL(user.profileImageURL)
        val img = ImageIcon(urlIMG)

        jLabel1.icon = img

        update()

        isVisible = true
    }

    private fun initComponents() {
        defaultCloseOperation = EXIT_ON_CLOSE

        jLabel1.maximumSize = Dimension(48, 48)
        jLabel1.minimumSize = Dimension(48, 48)
        jLabel1.preferredSize = Dimension(48, 48)

        jButton1.addActionListener {
            if (jTextField1.text !== "") {
                twitter.updateStatus(jTextField1.text)
                jTextField1.text = ""
                update()

                JOptionPane.showMessageDialog(
                    null, "New tweet", "You have done a new tweet",
                    JOptionPane.INFORMATION_MESSAGE
                )
            }
        }

        jList1.model = statuses
        jList1.cellRenderer = PanelTweets()
        jScrollPane1.setViewportView(jList1)

        val layout = GroupLayout(contentPane)
        contentPane.layout = layout
        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(
                layout
                    .createSequentialGroup().addContainerGap()
                    .addGroup(
                        layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1, GroupLayout.DEFAULT_SIZE, 576, Short.MAX_VALUE.toInt())
                            .addGroup(
                                layout.createSequentialGroup()
                                    .addComponent(
                                        jLabel1,
                                        GroupLayout.PREFERRED_SIZE,
                                        GroupLayout.DEFAULT_SIZE,
                                        GroupLayout.PREFERRED_SIZE
                                    )
                                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(jTextField1)
                                    .addGap(18, 18, 18)
                                    .addComponent(jButton1)
                            )
                    )
                    .addContainerGap()
            )
        )
        layout.setVerticalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGroup(
                    GroupLayout.Alignment.TRAILING, layout.createSequentialGroup().addContainerGap()
                        .addComponent(jScrollPane1, GroupLayout.DEFAULT_SIZE, 191, Short.MAX_VALUE.toInt())
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(
                            layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(
                                    jLabel1,
                                    GroupLayout.PREFERRED_SIZE,
                                    GroupLayout.DEFAULT_SIZE,
                                    GroupLayout.PREFERRED_SIZE
                                )
                                .addComponent(jButton1).addComponent(
                                    jTextField1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
                                    GroupLayout.PREFERRED_SIZE
                                )
                        )
                        .addContainerGap()
                )
        )
        pack()
    }

    private fun update() {
        val pagination = Paging()
        pagination.count = 500

        val list: ResponseList<Status> = twitter.getHomeTimeline(pagination)

        for (status in list) {
            statuses.addElement(status)
            saveInDB(status.text, status.user.screenName, status.createdAt)
        }
    }

    private fun saveInDB(text: String, screenName: String, createdAt: Date) {
        val stmt = conn.prepareStatement("INSERT INTO tweets VALUES (NULL, ?, ?, ?)")
        stmt.setString(1, screenName)
        stmt.setString(2, text)
        stmt.setString(3, createdAt.toString())
        stmt.executeUpdate()
    }
}