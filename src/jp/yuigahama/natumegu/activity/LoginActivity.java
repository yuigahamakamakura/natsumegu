package jp.yuigahama.natumegu.activity;

import jp.yuigahama.natumegu.R;
import jp.yuigahama.natumegu.until.TwitterConfig;
import jp.yuigahama.natumegu.value.PreferenceValue;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

public class LoginActivity extends FragmentActivity implements
		LoaderCallbacks<Uri> {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
	}

	@Override
	protected void onResume() {
		super.onResume();
		SharedPreferences preferences = PreferenceManager
				.getDefaultSharedPreferences(this);

		// すでにプレファレンスにデータがある場合
		if ((preferences.contains(PreferenceValue.PREFARENCE_ACCESS_TOKEN) || preferences
				.contains(PreferenceValue.PREFARENCE_ACCESS_TOKEN_SECRET))) {
			Intent intent = new Intent(this, PhraseSendActivity.class);
			startActivity(intent);
			finish();
			return;
		}

		getSupportLoaderManager().initLoader(0, null, this);

		OnClickListener clickListener = new OnClickListener() {
			public void onClick(View v) {
				getSupportLoaderManager().getLoader(0).forceLoad();
			}
		};
		findViewById(R.id.natumegu_activity_login_button).setOnClickListener(
				clickListener);

	}

	private static class TwitterAuthorizationURLLoader extends
			AsyncTaskLoader<Uri> {

		public TwitterAuthorizationURLLoader(Context context) {
			super(context);
		}

		@Override
		public Uri loadInBackground() {
			TwitterConfig twitterConfig = TwitterConfig.getSingleTon();
			Uri uri = null;
			try {
				uri = Uri.parse(twitterConfig.getAuthorizationURL());
			} catch (Exception e) {

			}
			return uri;
		}
	}

	public Loader<Uri> onCreateLoader(int arg0, Bundle arg1) {
		return new TwitterAuthorizationURLLoader(this);
	}

	public void onLoadFinished(Loader<Uri> loader, Uri uri) {
		if (uri == null) {
			Toast.makeText(this, "認証のURLを取得できませんでした", Toast.LENGTH_LONG).show();
			return;
		}
		Intent intent = new Intent(Intent.ACTION_VIEW);
		intent.setData(uri);
		startActivity(intent);
	}

	public void onLoaderReset(Loader<Uri> arg0) {

	}
}