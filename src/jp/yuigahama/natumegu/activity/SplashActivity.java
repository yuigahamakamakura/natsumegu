package jp.yuigahama.natumegu.activity;

import jp.yuigahama.natumegu.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;

public class SplashActivity extends Activity {
	private static final int SPLASH_TIME_MILLSECONDS = 2 * 1000;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splash_activity);

		Handler handler = new Handler();
		Runnable runnable = new Runnable() {
			public void run() {
				if (isFinishing() == false) {

					Intent intent = new Intent(SplashActivity.this,
							LoginActivity.class);
					startActivity(intent);
					finish();
				}
			}
		};
		handler.postDelayed(runnable, SPLASH_TIME_MILLSECONDS);

	}

}
