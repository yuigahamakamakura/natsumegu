package jp.yuigahama.natumegu.activity;

import jp.yuigahama.natumegu.R;
import jp.yuigahama.natumegu.activity.loader.AccessTokenLoader;
import jp.yuigahama.natumegu.until.TwitterConfig;
import jp.yuigahama.natumegu.value.PreferenceValue;
import twitter4j.auth.AccessToken;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;

public class GetAccessTokenActivity extends FragmentActivity implements
		LoaderManager.LoaderCallbacks<AccessToken> {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getSupportLoaderManager().initLoader(0, null, this);

	}

	public Loader<AccessToken> onCreateLoader(int arg0, Bundle arg1) {
		Intent intent = getIntent();
		Uri uri = intent.getData();
		AccessTokenLoader accessTokenLoader;
		if (uri != null
				&& uri.toString()
						.startsWith(TwitterConfig.PREFIX_CUSTOM_SCHEME)) {
			String verifier = uri.getQueryParameter("oauth_verifier");

			accessTokenLoader = new AccessTokenLoader(this, verifier);
			accessTokenLoader.forceLoad();
		} else {
			return null;
		}
		return accessTokenLoader;
	}

	public void onLoadFinished(Loader<AccessToken> loader, AccessToken at) {

		SharedPreferences preferences = PreferenceManager
				.getDefaultSharedPreferences(this);

		// プレファレンスに書き出し
		Editor edit = preferences.edit();
		edit.putString(PreferenceValue.PREFARENCE_ACCESS_TOKEN, at.getToken());

		edit.putString(PreferenceValue.PREFARENCE_ACCESS_TOKEN_SECRET,
				at.getTokenSecret());
		try {
			edit.commit();
			Intent nextActivityIntent = new Intent(this,
					PhraseSendActivity.class);
			// 次のアクティビティにジャンプ
			startActivity(nextActivityIntent);
			// このアクティビティ終了
			finish();
		} finally {

		}
	}

	public void onLoaderReset(Loader<AccessToken> arg0) {

	}

}
