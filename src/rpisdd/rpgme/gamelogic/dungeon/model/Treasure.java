package rpisdd.rpgme.gamelogic.dungeon.model;

import rpisdd.rpgme.R;
import rpisdd.rpgme.gamelogic.items.Item;
import rpisdd.rpgme.gamelogic.player.Reward;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class Treasure implements RoomContent {

	private boolean isOpened;
	private int treasureLevel;

	public Treasure(boolean opened, int treasureLevel_) {
		this.isOpened = opened;
		this.treasureLevel = treasureLevel_;
	}

	public Treasure(boolean opened) {
		this(opened, 1);
	}

	public Treasure() {
		this(false);
	}

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

	public void setTreasureLevel(int nLvl) {
		this.treasureLevel = nLvl;
	}

	@Override
	public String getStringRepresentation() {
		return "TREASURE" + "," + this.isOpened;
	}

	public static Treasure getFromStringRepresentation(String[] contentArgs) {
		if (contentArgs.length < 2) {
			Log.wtf("DungeonLoad", "Not enough args for a TREASURE");
		}
		boolean isOpened = Boolean.parseBoolean(contentArgs[1]);
		Treasure newTreasure = new Treasure(isOpened);
		return newTreasure;
	}

	public boolean getIsOpened() {
		return isOpened;
	}

	public void treasurePopup(Activity activity) {
		// TODO
		// what happens if inventory is full

		// figure out what the chest has
		Reward tReward = Reward.chestReward(this.treasureLevel);
		Item rewardItem = tReward.getRewardItem();

		// give the award to the player
		tReward.applyReward();

		// display the award to the player;
		View popup = LayoutInflater.from(activity).inflate(
				R.layout.treasure_room, null);

		TextView treasureText = (TextView) popup
				.findViewById(R.id.treasureText);

		treasureText.setText("You found a " + rewardItem.getName() + "!");

		ImageView treasureImg = (ImageView) popup
				.findViewById(R.id.treasureImg);

		// Load the image
		String tImagePath = "";
		tImagePath = rewardItem.getImagePath();
		Picasso.with(activity).load(tImagePath).into(treasureImg);

		// Display the popup
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
