package jp.yuigahama.natumegu.activity.loader;

import java.util.Calendar;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

public class TweetSender extends AsyncTaskLoader<String> {
	private static final String MENTION_USER = "@Natsumeg_k ";
	private final String phraseForJa;
	private final Twitter twitter;

	public TweetSender(Context context, Twitter twitter, String phraseForJa) {
		super(context);
		this.twitter = twitter;
		this.phraseForJa = phraseForJa;

	}

	@Override
	public String loadInBackground() {
		// TimeStampを付加した。
//		String tweetString = String.format(
//				"%s  【お知らせ】 「%s」 #由比が浜寮 http://bit.ly/Hi6SQl id=%d",
//				MENTION_USER, phraseForJa, Calendar.getInstance()
//						.getTimeInMillis() % 1000);
		String tweetString = String.format(
		"%s  【お知らせ】 「%s」 #由比が浜寮 http://bit.ly/Hi6RvI id=%d",
		MENTION_USER, phraseForJa, Calendar.getInstance()
				.getTimeInMillis() % 1000);



		try {
			twitter.updateStatus(tweetString);
			return tweetString;
		} catch (TwitterException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}

		return null;
	}

}
