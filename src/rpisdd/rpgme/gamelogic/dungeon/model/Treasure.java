package rpisdd.rpgme.gamelogic.dungeon.model;

import android.app.Activity;

public class Treasure implements RoomContent {

	@Override
	public boolean Encounter(Activity activity) {
		// TODO this is placeholder
		// System.out.print("Get the treasure");
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

}
