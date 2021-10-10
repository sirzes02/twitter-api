package kotlin_dev.world

import twitter4j.TwitterFactory
import twitter4j.auth.AccessToken
import twitter4j.auth.RequestToken
import twitter4j.conf.ConfigurationBuilder
import java.io.File
import java.io.PrintWriter
import javax.swing.JOptionPane

class Authentication {
    init {
        val configBuilder = ConfigurationBuilder()
        configBuilder
            .setDebugEnabled(false)
            .setOAuthConsumerKey(Configurations.CONSUME_KEY)
            .setOAuthConsumerSecret(Configurations.CONSUME_SECRET_KEY)

        val oAuthTwitter = TwitterFactory(configBuilder.build()).instance
        var requestToken: RequestToken?
        var accessToken: AccessToken?
        var url: String

        do {
            requestToken = oAuthTwitter.oAuthRequestToken
            url = requestToken.authenticationURL

            val runtime = Runtime.getRuntime()
            runtime.exec("open $url")

            val pin =
                JOptionPane.showInputDialog(null, "Introduce navigator pin:", "PIN", JOptionPane.INFORMATION_MESSAGE)

            accessToken = if (pin.isNotEmpty()) oAuthTwitter.getOAuthAccessToken(
                requestToken,
                pin
            ) else oAuthTwitter.getOAuthAccessToken(requestToken)
        } while (accessToken == null)

        val content = "${accessToken.token}${Configurations.SEPARATOR}${accessToken.tokenSecret}"
        val pw: PrintWriter
        val file = File(Configurations.ROUTE_AUTH_FILES)

        if (!file.exists()) {
            file.createNewFile()
        }

        pw = PrintWriter(file)
        pw.write(content)
        pw.close()
    }
}