package kotlin_dev.world

import twitter4j.Twitter
import twitter4j.TwitterFactory
import twitter4j.conf.ConfigurationBuilder
import kotlin_dev.views.TwitterFrame
import java.io.BufferedReader
import java.io.FileReader

class TwitterKotlinGT {
    init {
        val twitter: Twitter
        val configurationBuilder = ConfigurationBuilder()
        val token: String
        val tokenSecret: String

        val br = BufferedReader(FileReader(Configurations.ROUTE_AUTH_FILES))
        val credentials = br.readLine().split(Configurations.SEPARATOR)
        token = credentials[Configurations.TOKEN]
        tokenSecret = credentials[Configurations.TOKEN_SECRET]

        br.close()

        configurationBuilder
            .setDebugEnabled(true)
            .setOAuthConsumerKey(Configurations.CONSUME_KEY)
            .setOAuthConsumerSecret(Configurations.CONSUME_SECRET_KEY)
            .setOAuthAccessToken(token)
            .setOAuthAccessTokenSecret(tokenSecret)
        twitter = TwitterFactory(configurationBuilder.build()).instance

        TwitterFrame(twitter)
    }
}

fun main() {
    Authentication()
    TwitterKotlinGT()
}