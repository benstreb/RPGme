package rpisdd.rpgme.gamelogic.items;

import android.util.Log;
import rpisdd.rpgme.gamelogic.player.Player;

public class Boondogle extends Item {
	public static final Boondogle INVALID =
			new Boondogle("M.", -100, "invalid.png");
	
	public static final void loadBoondogles() {
	}
	
	private Boondogle(String name, int price, String imagePath) {
		super(name, price, imagePath);
	}
	@Override
	public boolean isUsable() {
		return false;
	}
	@Override
	public void useMe(Player p) {
		Log.e("items", "Trying to use a boondogle.");
	}

}
