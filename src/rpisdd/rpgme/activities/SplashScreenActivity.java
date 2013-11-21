package rpisdd.rpgme.activities;

import rpisdd.rpgme.R;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

public class SplashScreenActivity extends Activity implements OnTouchListener {

	Handler handler;
	Runnable runnable;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash_screen);

		SharedPreferences pref = this.getSharedPreferences("player",
				Context.MODE_PRIVATE);
		if (!pref.getBoolean("playerExists", false)) {

			int interval = 2500; // Move on in 2.5 seconds
			handler = new Handler();
			final Activity activity = this;

			runnable = new Runnable() {
				@Override
				public void run() {
					startActivity(new Intent(activity,
							CreatePlayerActivity.class));
				}
			};

			handler.postAtTime(runnable, System.currentTimeMillis() + interval);
			handler.postDelayed(runnable, interval);

		} else {
			startActivity(new Intent(this, MainActivity.class));
		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.splash_screen, menu);
		return true;
	}

	@Override
	public boolean onTouchEvent(MotionEvent arg1) {

		SharedPreferences pref = this.getSharedPreferences("player",
				Context.MODE_PRIVATE);
		if (!pref.getBoolean("playerExists", false)) {
			handler.removeCallbacks(runnable);
			startActivity(new Intent(this, CreatePlayerActivity.class));
		} else {
			startActivity(new Intent(this, MainActivity.class));
		}

		return true;
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		return false;
	}

}
