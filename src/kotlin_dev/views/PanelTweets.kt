package kotlin_dev.views

import twitter4j.Status
import java.awt.Dimension
import java.awt.Font
import java.net.URL
import javax.swing.*

class PanelTweets : ListCellRenderer<Status>, JPanel() {
    private var jLabel1: JLabel = JLabel("jLabel1")
    private var jLabel2: JLabel = JLabel("jLabel2")
    private var jLabel3: JLabel = JLabel("jLabel3")
    private var jTextField1: JTextField = JTextField("jTextField1")

    init {
        initComponents()
    }

    private fun initComponents() {
        jLabel2.maximumSize = Dimension(48, 48)
        jLabel2.minimumSize = Dimension(48, 48)
        jLabel2.preferredSize = Dimension(48, 48)
        jLabel3.font = Font("Ubuntu", 2, 12)

        val layout = GroupLayout(this)
        this.layout = layout

        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(
                layout
                    .createSequentialGroup().addContainerGap()
                    .addGroup(
                        layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                            .addGroup(
                                layout.createSequentialGroup().addComponent(jLabel1)
                                    .addGap(0, 0, Short.MAX_VALUE.toInt())
                            )
                            .addGroup(
                                layout.createSequentialGroup()
                                    .addComponent(
                                        jLabel2,
                                        GroupLayout.PREFERRED_SIZE,
                                        GroupLayout.DEFAULT_SIZE,
                                        GroupLayout.PREFERRED_SIZE
                                    )
                                    .addGap(18, 18, 18)
                                    .addGroup(
                                        layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                            .addGroup(
                                                layout.createSequentialGroup().addComponent(jLabel3)
                                                    .addGap(0, 0, Short.MAX_VALUE.toInt())
                                            )
                                            .addComponent(
                                                jTextField1,
                                                GroupLayout.DEFAULT_SIZE,
                                                310,
                                                Short.MAX_VALUE.toInt()
                                            )
                                    )
                            )
                    )
                    .addContainerGap()
            )
        )
        layout
            .setVerticalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addGroup(
                        layout.createSequentialGroup().addContainerGap().addComponent(jLabel1).addGap(18, 18, 18)
                            .addGroup(
                                layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                    .addComponent(
                                        jLabel2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
                                        GroupLayout.PREFERRED_SIZE
                                    )
                                    .addComponent(
                                        jTextField1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
                                        GroupLayout.PREFERRED_SIZE
                                    )
                            )
                            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 9, Short.MAX_VALUE.toInt())
                            .addComponent(jLabel3)
                            .addContainerGap()
                    )
            )
    }

    override fun getListCellRendererComponent(
        list: JList<out Status?>?, value: Status, index: Int,
        isSelected: Boolean, cellHasFocus: Boolean
    ): PanelTweets {
        val status: Status = value

        jLabel1.text = "@ ${status.user.screenName}"
        jLabel3.text = status.createdAt.toString()
        jTextField1.text = status.text

        val urlIMG = URL(status.user.profileImageURL)
        val img = ImageIcon(urlIMG)

        jLabel2.icon = img

        return this
    }
}