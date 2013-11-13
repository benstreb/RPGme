package rpisdd.rpgme.gamelogic.player;

import rpisdd.rpgme.R;

//Represents a stat type that the player can have.
public enum StatType {
	STRENGTH(0), INTELLIGENCE(1), WILL(2), SPIRIT(3), ERROR(4);

	private final int value;

	private StatType(int val) {
		this.value = val;
	}

	public int getValue() {
		return this.value;
	}

	// Maybe we do this a better way? But it will return the location of
	// the icon!
	public int getImgPath() {
		if (this.value == 0) {
			return R.drawable.ic_strength;
		}
		if (this.value == 1) {
			return R.drawable.ic_intelligence;
		}
		if (this.value == 2) {
			return R.drawable.ic_will;
		}
		if (this.value == 3) {
			return R.drawable.ic_spirit;
		} else {
			return -1;
		}
	}

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
