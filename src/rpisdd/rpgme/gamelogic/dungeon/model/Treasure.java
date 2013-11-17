package rpisdd.rpgme.gamelogic.dungeon.model;

import rpisdd.rpgme.R;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class Treasure implements RoomContent {

	private boolean isOpened;

	@Override
	public boolean Encounter(Activity activity) {

		if (!isOpened) {
			isOpened = true;
			treasurePopup(activity);
		}

		return true;
	}

	@Override
	public RoomType getRoomType() {
		return RoomType.TREASURE;
	}

	@Override
	public String getStringRepresentation() {
		return "TREASURE";
	}

	public boolean getIsOpened() {
		return isOpened;
	}

	public void treasurePopup(Activity activity) {

		TreasureRoom tr = new TreasureRoom();

		View popup = LayoutInflater.from(activity).inflate(
				R.layout.treasure_room, null);

		TextView treasureText = (TextView) popup
				.findViewById(R.id.treasureText);

		treasureText.setText("You found a " + tr.getTreasureName() + "!");

		ImageView treasureImg = (ImageView) popup
				.findViewById(R.id.treasureImg);

		// TO DO: figure out how to get the images for any kind of item
		// TO DO: draw item on top of the treasure chest
		treasureImg.setImageResource(R.drawable.sword1_red);

		AlertDialog.Builder builder2 = new AlertDialog.Builder(activity);
		builder2.setPositiveButton("OK", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int id) {
				dialog.cancel();

			}
		});
		builder2.setCancelable(true);
		builder2.setView(popup);

		AlertDialog alert2 = builder2.create();
		alert2.show();
	}

}
