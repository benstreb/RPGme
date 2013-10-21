package rpisdd.rpgme.activities;

import rpisdd.rpgme.R;
import rpisdd.rpgme.gamelogic.player.Player;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.support.v4.app.NavUtils;

public class CreatePlayerActivity extends Activity {
	private int avatarId = R.id.avatar1;
	private Activity thisActivity = this;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_create_player);
		// Show the Up button in the action bar.
		setupActionBar();
	    // Register the onClick listener with the implementation above
	    ((ImageButton) findViewById(R.id.avatar1)).setOnClickListener(avatar1Listener);
	    ((ImageButton) findViewById(R.id.avatar2)).setOnClickListener(avatar2Listener);
	    ((Button) findViewById(R.id.submitButton)).setOnClickListener(submitListener);
	}

	/**
	 * Set up the {@link android.app.ActionBar}.
	 */
	private void setupActionBar() {
		getActionBar().setDisplayHomeAsUpEnabled(true);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.create_player, menu);
		return true;
	}
	
	private OnClickListener avatar1Listener = new OnClickListener() {
	    public void onClick(View v) {
	    	setAvatarId(R.drawable.ic_drawer);
	    }
	};
	private OnClickListener avatar2Listener = new OnClickListener() {
	    public void onClick(View v) {
	    	setAvatarId(R.drawable.ic_drawer);
	    }
	};
	private OnClickListener submitListener = new OnClickListener() {
	    public void onClick(View v) {
	    	Player.createPlayer(
	    			((TextView) findViewById(R.id.playerName)).getText(),
	    			((TextView) findViewById(R.id.playerClass)).getText(),
	    			avatarId);
	    	startActivity(new Intent(thisActivity, MainActivity.class));
	    }
	};

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			// This ID represents the Home or Up button. In the case of this
			// activity, the Up button is shown. Use NavUtils to allow users
			// to navigate up one level in the application structure. For
			// more details, see the Navigation pattern on Android Design:
			//
			// http://developer.android.com/design/patterns/navigation.html#up-vs-back
			//
			NavUtils.navigateUpFromSameTask(this);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	private int setAvatarId(int id) {
		this.avatarId = id;
		ImageView avatarView = ((ImageView) findViewById(R.id.playerAvatar));
		avatarView.setImageResource(avatarId);
		return id;
	}
}
