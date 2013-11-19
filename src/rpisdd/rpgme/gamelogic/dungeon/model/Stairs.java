package rpisdd.rpgme.gamelogic.dungeon.model;

import rpisdd.rpgme.activities.DescendStairs;
import rpisdd.rpgme.activities.MainActivity;
import rpisdd.rpgme.activities.TransitionFragment;
import android.app.Activity;

public class Stairs implements RoomContent {

	@Override
	public boolean Encounter(Activity activity) {
		// TODO IMPLEMENT
		TransitionFragment trans = new TransitionFragment();
		trans.setValues(new DescendStairs(), true);
		((MainActivity) activity).changeFragment(trans);
		return false;
	}

	@Override
	public RoomType getRoomType() {
		return RoomType.STAIRS;
	}

	@Override
	public String getStringRepresentation() {
		return "STAIRS";
	}

	public static Stairs getFromStringRepresentation(String[] contentArgs) {
		return new Stairs();
	}
}
