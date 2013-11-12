package rpisdd.rpgme.gamelogic.dungeon.model;

import android.app.Activity;

public interface RoomContent {

	// Holds code for what to do if the player enters the room,
	// and whether that room is cleared on entry.
	public boolean Encounter(Activity activity);

	public RoomType getRoomType();
}
