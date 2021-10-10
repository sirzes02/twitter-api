package java_dev.world;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java_dev.views.TwitterFrame;

import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

public class TwitterJavaGT {
  public TwitterJavaGT() {
    Twitter twitter;
    ConfigurationBuilder configBuilder = new ConfigurationBuilder();
    String Token;
    String TokenSecret;
    BufferedReader br;

    try {
      br = new BufferedReader(new FileReader(Configurations.ROUTE_AUTH_FILES));

      String[] credentials = br.readLine().split(Configurations.SEPARATOR);
      Token = credentials[Configurations.TOKEN];
      TokenSecret = credentials[Configurations.TOKEN_SECRET];

      br.close();

      configBuilder.setDebugEnabled(true).setOAuthConsumerKey(Configurations.CONSUME_KEY)
          .setOAuthConsumerSecret(Configurations.CONSUME_SECRET_KEY).setOAuthAccessToken(Token)
          .setOAuthAccessTokenSecret(TokenSecret);
      twitter = new TwitterFactory(configBuilder.build()).getInstance();

      new TwitterFrame(twitter);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public static void main(String[] args) {
    new Authorization();
    new TwitterJavaGT();
  }
}