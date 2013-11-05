package rpisdd.rpgme.activities;

import rpisdd.rpgme.R;
import rpisdd.rpgme.gamelogic.dungeon.model.TreasureRoom;
import android.app.AlertDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class DungeonMenu extends Fragment implements OnClickListener {

	private Button enterTreasure;

	public DungeonMenu() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View v = inflater.inflate(R.layout.dungeon_menu, container, false);

		enterTreasure = (Button) v.findViewById(R.id.treasureEnterButton);
		enterTreasure.setOnClickListener(this);
		return v;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.treasureEnterButton: {
			Log.i("my_message", "we entered the treasure!");
			enterTreasure();
			break;
		}
		default:
			break;
		}
	}

	public void enterTreasure() {
		TreasureRoom tr = new TreasureRoom();

		View popup = LayoutInflater.from(getActivity()).inflate(
				R.layout.treasure_room, null);

		TextView treasureText = (TextView) popup
				.findViewById(R.id.treasureText);

		treasureText.setText("You found a " + tr.getTreasureName() + "!");

		ImageView treasureImg = (ImageView) popup
				.findViewById(R.id.treasureImg);

		// TO DO: figure out how to get the images for any kind of item
		// TO DO: draw item on top of the treasure chest
		treasureImg.setImageResource(R.drawable.sword1_red);
		AlertDialog.Builder builder2 = new AlertDialog.Builder(getActivity());
		builder2.setView(popup);

		AlertDialog alert2 = builder2.create();
		alert2.show();
	}

	/**** experimental treasure room pop-up functionality ****/
	// private Button enterTreasureBtn;
	//
	// public DungeonMenu() {
	// }
	//
	// @Override
	// public View onCreateView(LayoutInflater inflater, ViewGroup container,
	// Bundle savedInstanceState) {
	//
	// View v = inflater.inflate(R.layout.dungeon_menu, container, false);
	//
	// enterTreasureBtn = (Button) v.findViewById(R.id.treasureEnterButton);
	// enterTreasureBtn.setOnClickListener(this);
	// return v;
	// }
	//
	// @Override
	// public void onClick(View v) {
	// switch (v.getId()) {
	// case R.id.treasureEnterButton: {
	// Log.i("my_message", "we entered the treasure!");
	// enterTreasure();
	// break;
	// }
	// default:
	// break;
	// }
	// }
	//
	// public void enterTreasure() {
	// View popup = LayoutInflater.from(getActivity()).inflate(
	// R.layout.treasure_room, null);
	//
	// AlertDialog.Builder builder2 = new AlertDialog.Builder(getActivity());
	// builder2.setView(popup);
	//
	// AlertDialog alert2 = builder2.create();
	// alert2.show();
	// }
}
