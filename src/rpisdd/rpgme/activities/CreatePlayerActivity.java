package rpisdd.rpgme.activities;

import rpisdd.rpgme.R;
import rpisdd.rpgme.gamelogic.player.Player;
import rpisdd.rpgme.popups.AnnoyingPopup;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class CreatePlayerActivity extends Activity {
	private int avatarId = R.id.avatar1;
	private final Activity thisActivity = this;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_create_player);
		// Show the Up button in the action bar.
		setupActionBar();
		// Register the onClick listener with the implementation above
		((ImageButton) findViewById(R.id.avatar1))
				.setOnClickListener(avatar1Listener);
		((ImageButton) findViewById(R.id.avatar2))
				.setOnClickListener(avatar2Listener);
		((Button) findViewById(R.id.submitButton))
				.setOnClickListener(submitListener);

		// set default character image
		setAvatarId(R.drawable.av_f1_avatar);
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

	private final OnClickListener avatar1Listener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			setAvatarId(R.drawable.av_f1_avatar);
		}
	};
	private final OnClickListener avatar2Listener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			setAvatarId(R.drawable.av_m1_avatar);
		}
	};
	private final OnClickListener submitListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			if (((TextView) findViewById(R.id.playerName)).getText().length() < 1) {
				// error
				badInputPopup("Please enter a name");
			} else if (((TextView) findViewById(R.id.playerClass)).getText()
					.length() < 1) {
				// error
				badInputPopup("Please enter a class");
			} else {
				CharSequence playerName = ((TextView) findViewById(R.id.playerName))
						.getText();
				String message = playerName
						+ ", are you ready to begin your adventure?";
				String doMessage = "Begin";
				AnnoyingPopup.doDont(thisActivity, message, doMessage,
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int id) {
								dialog.cancel();
								Player.createPlayer(
										((TextView) findViewById(R.id.playerName))
												.getText(),
										((TextView) findViewById(R.id.playerClass))
												.getText(), avatarId);
								Player.getPlayer().savePlayer(thisActivity);
								startActivity(new Intent(thisActivity,
										MainActivity.class));
							}
						});
			}
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

	public void badInputPopup(String message) {

		// Bring up a confirmation prompt first
		AlertDialog.Builder builder2 = new AlertDialog.Builder(this);
		builder2.setMessage(message);
		builder2.setCancelable(true);
		builder2.setPositiveButton("OK", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int id) {
				dialog.cancel();
			}
		});

		AlertDialog alert2 = builder2.create();
		alert2.show();
	}

	private void confirmationPopup(CharSequence player_name) {
		Log.i("confirm_player", "got to confirmationPopup()");
		String message = player_name
				+ ", are you ready to begin the adventure?";
		String doMessage = "begin";
		AnnoyingPopup.doDont(thisActivity, message, doMessage,
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int id) {
						dialog.cancel();
					}
				});
	}
}
