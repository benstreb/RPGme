package rpisdd.rpgme.gamelogic.dungeon.model;

import rpisdd.rpgme.gamelogic.items.Item;

public class TreasureRoom {

	private final Item treasureItem;

	public TreasureRoom() {
		treasureItem = Item.createItemFromName("Small Sword");
	}

	public Item getTreasure() {
		return treasureItem;
	}

	public String getTreasureName() {
		return treasureItem.getName();
	}

	public String getTreasureImage() {
		return treasureItem.getImagePath();
	}

}
