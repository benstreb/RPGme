package rpisdd.rpgme.gamelogic.dungeon.model;

public enum RoomType {
	MONSTER, TREASURE, STAIRS, NONE;

	// Convert a string to a stat type. Used by Spinners of the CreateQuests
	// menu.
	public static RoomType stringToType(String string) {
		if (string.equalsIgnoreCase("MONSTER")) {
			return MONSTER;
		} else if (string.equalsIgnoreCase("TREASURE")) {
			return TREASURE;
		} else if (string.equalsIgnoreCase("STAIRS")) {
			return STAIRS;
		}
		return NONE;
	}
}
