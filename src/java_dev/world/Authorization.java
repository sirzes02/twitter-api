package java_dev.world;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.swing.JOptionPane;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;
import twitter4j.conf.ConfigurationBuilder;

public class Authorization {
  public Authorization() {
    ConfigurationBuilder configBuilder = new ConfigurationBuilder();

    configBuilder.setDebugEnabled(false).setOAuthConsumerKey(Configurations.CONSUME_KEY)
        .setOAuthConsumerSecret(Configurations.CONSUME_SECRET_KEY);

    Twitter OAuthTwitter = new TwitterFactory(configBuilder.build()).getInstance();
    RequestToken requestToken;
    AccessToken accessToken = null;
    String url;

    do {
      try {
        requestToken = OAuthTwitter.getOAuthRequestToken();
        url = requestToken.getAuthenticationURL();

        Runtime runtime = Runtime.getRuntime();
        runtime.exec("open " + url);

        String pin = JOptionPane.showInputDialog(null, "Introduce navigator pin:", "PIN",
            JOptionPane.INFORMATION_MESSAGE);

        accessToken = pin.length() > 0 ? OAuthTwitter.getOAuthAccessToken(requestToken, pin)
            : OAuthTwitter.getOAuthAccessToken(requestToken);
      } catch (TwitterException | IOException e) {
        e.printStackTrace();
      }
    } while (accessToken == null);

    FileOutputStream fileOS;
    File file;
    String content = accessToken.getToken() + Configurations.SEPARATOR + accessToken.getTokenSecret();

    try {
      file = new File(Configurations.ROUTE_AUTH_FILES);
      fileOS = new FileOutputStream(file);

      if (!file.exists()) {
        file.createNewFile();
      }

      byte[] contentInBytes = content.getBytes();

      fileOS.write(contentInBytes);
      fileOS.flush();
      fileOS.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
