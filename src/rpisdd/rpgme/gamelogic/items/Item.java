package rpisdd.rpgme.gamelogic.items;

import java.util.HashMap;

import rpisdd.rpgme.gamelogic.player.Player;
import android.util.Log;

public abstract class Item {
	private final String name;
	private final int price;
	private final String imagePath;

	// No really, we need to do this, at least for the current architecture.
	// Even though it looks really stupid if you look at the methods called...
	public static final void load() {
		Equipment.loadEquipment();
		Consumable.loadConsumables();
		Boondogle.loadBoondogles();
	}

	private static final HashMap<String, Item> allItems = new HashMap<String, Item>();

	public static Item createItemFromName(String aname) {
		if (allItems.containsKey(aname)) {
			return allItems.get(aname);
		} else {
			Log.e("items", "Trying to create an item that doesn't exist.");
			return Boondogle.INVALID;
		}
	}

	protected Item(String name, int price, String imageName) {
		this.name = name;
		this.price = price;
		this.imagePath = "file:///android_asset/Items/" + imageName;
		allItems.put(name, this);
	}

	public int getRefundPrice() {
		return getPrice() / 2;
	}

	public String getName() {
		return name;
	}

	public int getPrice() {
		return price;
	}

	public String getImagePath() {
		return imagePath;
	}

	@Override
	public String toString() {
		return name;
	}

	public boolean isUsable() {
		return true;
	}

	public boolean isEquipment() {
		return false;
	}

	public abstract void useMe(Player p, int index);
}
