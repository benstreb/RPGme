package rpisdd.rpgme.gamelogic.items;

import rpisdd.rpgme.gamelogic.player.Player;

public abstract class Item {
	public static Item createItemFromName(String aname) {
		Item newItem = null;
		if(aname.equals("Energy Potion")){
			newItem = (Item)(new EnergyPotion());
		}
		return newItem;
	}
	
	public int getRefundPrice() {
		return getPrice()/2;
	}
	
	public abstract String getName();
	public abstract int getPrice();
	public abstract String getImagePath();
	public abstract void useMe(Player p);
}
