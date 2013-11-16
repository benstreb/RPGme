package rpisdd.rpgme.gamelogic.dungeon.model;

import android.app.Activity;

public class Stairs implements RoomContent {

	@Override
	public boolean Encounter(Activity activity) {
		// TODO IMPLEMENT
		return false;
	}

	@Override
	public RoomType getRoomType() {
		return RoomType.STAIRS;
	}
}
