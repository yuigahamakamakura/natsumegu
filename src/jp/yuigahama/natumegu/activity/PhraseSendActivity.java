package jp.yuigahama.natumegu.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;

import jp.yuigahama.natumegu.R;
import jp.yuigahama.natumegu.activity.loader.TranslationLoader;
import jp.yuigahama.natumegu.activity.loader.TweetSender;
import jp.yuigahama.natumegu.model.LanguageModel;
import jp.yuigahama.natumegu.until.TwitterConfig;
import jp.yuigahama.natumegu.value.PreferenceValue;
import jp.yuigahama.natumegu.value.Strings;
import twitter4j.Twitter;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.LoaderManager;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.Loader;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

public class PhraseSendActivity extends FragmentActivity implements
		LoaderCallbacks<String> {

	private static final String BUNDLE_TWEET_TEXT = "BUNDLE_TWEET_TEXT";
	private static final String CALL_NUMBER = "tel:08043990827";

	private static final String TRANCE_SOURCE_TEXT = "TRANCE_SOURCE_TEXT";

	private MyDialogFragment dialogFragment;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		//キーボードを出さない。
		getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

		setContentView(R.layout.phrase_activity);

		dialogFragment = new MyDialogFragment();
		dialogFragment.setRetainInstance(true);

		ViewGroup rootView = (ViewGroup) findViewById(R.id.command_view_activity_root);

		ArrayList<LanguageModel> commandArray = Strings.LANGUAGE_LIST;

		for (final LanguageModel command : commandArray) {

			Button button = new Button(this);

			String displayString = getMatchString(command);

			button.setText(displayString);

			button.setOnClickListener(new View.OnClickListener() {

				public void onClick(View v) {
					Bundle bundle = new Bundle();
					bundle.putString(BUNDLE_TWEET_TEXT, command.ja);

					LoaderManager loaderManager = getSupportLoaderManager();
					loaderManager.restartLoader(0, bundle,
							PhraseSendActivity.this);

				}
			});
			// Create Button

			LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
					LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
			layoutParams.setMargins(0, 40, 0, 0);

			rootView.addView(button, layoutParams);
		}

		findViewById(R.id.tweet_button).setOnClickListener(
				new View.OnClickListener() {

					public void onClick(View v) {
						LoaderManager loaderManager = getSupportLoaderManager();
						EditText editText = (EditText) findViewById(R.id.tweet_edit_text);
						Bundle bundle = new Bundle();
						bundle.putString(TRANCE_SOURCE_TEXT, editText
								.getEditableText().toString());

						loaderManager.restartLoader(1, bundle,
								new TransLationLoaderCallback());

					}
				});
	}

	private String getMatchString(LanguageModel tuple) {
		Locale locale = Locale.getDefault();
		HashMap<String, String> hashMap = new HashMap<String, String>();
		hashMap.put(Locale.JAPANESE.getLanguage(), tuple.ja);
		hashMap.put(Locale.FRENCH.getLanguage(), tuple.fr);
		hashMap.put(Locale.CHINESE.getLanguage(), tuple.zh);
		hashMap.put("fi", tuple.fi);
		hashMap.put("vi", tuple.vi);
		hashMap.put("es", tuple.es);
		hashMap.put("et", tuple.et);

		if (hashMap.containsKey(locale.getLanguage())) {
			return hashMap.get(locale.getLanguage());
		}
		// other
		return tuple.en;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.main_activity_menu, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		if (item.getItemId() == R.id.menu_call_to_natume) {
			Intent intent = new Intent(Intent.ACTION_DIAL,
					Uri.parse(CALL_NUMBER));
			try {
				startActivity(intent);
			} catch (ActivityNotFoundException e) {
				Toast.makeText(this, R.string.could_not_phone,
						Toast.LENGTH_LONG).show();

			}
		}

		return super.onMenuItemSelected(featureId, item);
	}

	public Loader<String> onCreateLoader(int i, Bundle bundle) {
		// プリファレンスからデータ読み込み
		SharedPreferences preferences = PreferenceManager
				.getDefaultSharedPreferences(this);
		String oAuthAccessToken = preferences.getString(
				PreferenceValue.PREFARENCE_ACCESS_TOKEN, "");
		String oAuthAccessTokenSecret = preferences.getString(
				PreferenceValue.PREFARENCE_ACCESS_TOKEN_SECRET, "");

		TwitterConfig twitterConfig = TwitterConfig.getSingleTon();
		final Twitter twitter = twitterConfig.setAccessToken(oAuthAccessToken,
				oAuthAccessTokenSecret);

		TweetSender tweetLoader = new TweetSender(this, twitter,
				bundle.getString(BUNDLE_TWEET_TEXT));

		tweetLoader.forceLoad();

		Handler handler = new Handler();
		// DialogFragmentを扱うときこうやるらしい
		handler.post(new Runnable() {

			public void run() {
				FragmentManager manager = getSupportFragmentManager();
				dialogFragment.show(manager, "dialog");

			}
		});

		return tweetLoader;
	}

	public void onLoadFinished(Loader<String> loader, String tweetString) {

		// ダイアログを消す。
		FragmentManager manager = getSupportFragmentManager();
		manager.beginTransaction();
		Handler handler = new Handler();
		// DialogFragmentを扱うときこうやるらしい
		handler.post(new Runnable() {

			public void run() {
				dialogFragment.onDismiss(dialogFragment.getDialog());
			}
		});

		if (tweetString == null) {
			// ツイートに失敗した。
			Toast.makeText(this, R.string.failed_to_tweet, Toast.LENGTH_LONG)
					.show();
			return;
		}
		// ツイートに成功した。
		Toast.makeText(getApplicationContext(),
				getString(R.string.tweet_phrase, tweetString),
				Toast.LENGTH_LONG).show();

	}

	public void onLoaderReset(Loader<String> loader) {

	}

	private class TransLationLoaderCallback implements
			LoaderManager.LoaderCallbacks<String> {

		public Loader<String> onCreateLoader(int arg0, Bundle bundle) {
			TranslationLoader translationLoader = new TranslationLoader(

			PhraseSendActivity.this, bundle.getString(TRANCE_SOURCE_TEXT));
			translationLoader.forceLoad();
			return translationLoader;
		}

		public void onLoadFinished(Loader<String> arg0, final String string) {
			if (string == null) {
				Toast.makeText(PhraseSendActivity.this, R.string.failed_to_trancelate,
						Toast.LENGTH_LONG).show();
				return;

			}

			Bundle bundle = new Bundle();
			bundle.putString(BUNDLE_TWEET_TEXT, string);

			LoaderManager loaderManager = getSupportLoaderManager();
			loaderManager.restartLoader(0, bundle, PhraseSendActivity.this);

		}

		public void onLoaderReset(Loader<String> arg0) {

		}

	}

	private static class MyDialogFragment extends DialogFragment {
		@Override
		public Dialog onCreateDialog(Bundle savedInstanceState) {
			ProgressDialog dialog = new ProgressDialog(getActivity());

			dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
			dialog.setMessage(getString(R.string.tweeting));
			return dialog;
		}
	}

}
