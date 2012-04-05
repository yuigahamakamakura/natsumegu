package jp.yuigahama.natumegu.until;

import jp.yuigahama.natumegu.R;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;
import twitter4j.conf.Configuration;

/**
 *
 * Twitter4Jの処理のコアな処理のラッパーシングルトンクラスです。
 *
 * @author ono
 *
 */
public class TwitterConfig {
	private static final String CONSUMER_KEY = "vgfvL4eQ3Tc2gLJ0DrYUg";
	private static final String CONSUMER_SECRET = "CIyiSnRy1yxX7fzRQXQZE8KoaD9XdoaLIAPxiR1r8";

	/**
	 * カスタムスキームのプレフィックス com.kayac.in.twitter://authというコールバックにさせるため。
	 */
	public static final String PREFIX_CUSTOM_SCHEME = "com.kayac.natumegu";
	public static final String CALLBACK_URL = PREFIX_CUSTOM_SCHEME + "://auth";

	private static TwitterConfig instance;
	private Twitter twitter;

	private RequestToken requestToken;

	private TwitterConfig() {
		twitter = TwitterFactory.getSingleton();
		twitter.setOAuthConsumer(CONSUMER_KEY, CONSUMER_SECRET);
	}

	public static TwitterConfig getSingleTon() {
		if (instance == null) {
			instance = new TwitterConfig();
		}
		return instance;
	}

	public Configuration getConfiguration() {
		return twitter.getConfiguration();
	}

	/**
	 * 認証用のURLを取得します。
	 * @return
	 */
	public String getAuthorizationURL() {
		requestToken = null;
		try {
			twitter.setOAuthAccessToken(null);
			requestToken = twitter.getOAuthRequestToken(CALLBACK_URL);
		} catch (TwitterException e1) {
			e1.printStackTrace();
		}

		return requestToken.getAuthorizationURL();
	}

	/**
	 * アクセストークンを取得します。
	 *
	 * @param oauthVerifie
	 * @return
	 *
	 */
	public AccessToken getAccessToken(String oauthVerifie) {
		twitter = TwitterFactory.getSingleton();
		try {
			return twitter.getOAuthAccessToken(requestToken, oauthVerifie);
		} catch (TwitterException e) {
			throw new RuntimeException();
		}

	}

	public Twitter setAccessToken(String oAuthAccessToken,
			String oAuthAccessTokenSecret) {
		AccessToken accessToken = new AccessToken(oAuthAccessToken,
				oAuthAccessTokenSecret);
		twitter.setOAuthAccessToken(accessToken);
		return twitter;
	}

}
