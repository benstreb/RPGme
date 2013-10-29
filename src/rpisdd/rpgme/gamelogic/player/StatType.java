package rpisdd.rpgme.gamelogic.player;


//Represents a stat type that the player can have.
public enum StatType {
	STRENGTH, INTELLIGENCE, WILL, SPIRIT, ERROR;

	// Convert a string to a stat type. Used by Spinners of the CreateQuests
	// menu.
	public static StatType stringToType(String string) {
		if (string.equalsIgnoreCase("STRENGTH")) {
			return STRENGTH;
		} else if (string.equalsIgnoreCase("INTELLIGENCE")) {
			return INTELLIGENCE;
		} else if (string.equalsIgnoreCase("WILL")) {
			return WILL;
		} else if (string.equalsIgnoreCase("SPIRIT")) {
			return SPIRIT;
		}
		return ERROR;
	}

}
