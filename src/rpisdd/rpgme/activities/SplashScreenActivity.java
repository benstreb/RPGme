package rpisdd.rpgme.activities;

import rpisdd.rpgme.R;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

public class SplashScreenActivity extends Activity implements OnTouchListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash_screen);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.splash_screen, menu);
		return true;
	}

	@Override
	public boolean onTouchEvent(MotionEvent arg1) {
		startActivity(new Intent(this, CreatePlayerActivity.class));
		return true;
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		return false;
	}

}
