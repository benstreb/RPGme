package rpisdd.rpgme.gamelogic.items;

import rpisdd.rpgme.gamelogic.player.Player;

public class EnergyPotion extends Item {
	private String name;
	private int price;
	private String imagePath;	//Path to image in assets folder

	public EnergyPotion() {
		name = "Energy Potion";
		price = 50;
		imagePath = "file:///android_asset/Items/potion.gif";
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return name;
	}

	@Override
	public int getPrice() {
		// TODO Auto-generated method stub
		return price;
	}

	@Override
	public String getImagePath() {
		// TODO Auto-generated method stub
		return imagePath;
	}

	@Override
	public void useMe(Player p) {
		// TODO Auto-generated method stub
		
	}
	
	
}
