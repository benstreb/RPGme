package rpisdd.rpgme.gamelogic.items;

import rpisdd.rpgme.gamelogic.player.Player;
import android.util.Log;

import com.google.gson.JsonObject;

public final class Boondogle extends Item {
	protected Boondogle(JsonObject o) {
		super(o);
	}

	@Override
	public void useMe(Player p, int index) {
		Log.e("items", "Trying to use a boondogle.");
	}
}
