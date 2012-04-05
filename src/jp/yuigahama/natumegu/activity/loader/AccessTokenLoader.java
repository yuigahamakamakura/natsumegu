package jp.yuigahama.natumegu.activity.loader;

import jp.yuigahama.natumegu.R;
import jp.yuigahama.natumegu.until.TwitterConfig;
import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;
import twitter4j.auth.AccessToken;

public class AccessTokenLoader extends AsyncTaskLoader<AccessToken> {

	private final String verifier;

	public AccessTokenLoader(Context context, String verifier) {
		super(context);
		this.verifier = verifier;
	}

	@Override
	public AccessToken loadInBackground() {
		TwitterConfig twitterConfig = TwitterConfig.getSingleTon();

		return twitterConfig.getAccessToken(verifier);
	}

}
