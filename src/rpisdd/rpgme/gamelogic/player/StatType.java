package rpisdd.rpgme.gamelogic.player;

import rpisdd.rpgme.R;
import android.util.Log;

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
		} else if (this.value == 1) {
			return R.drawable.ic_intelligence;
		} else if (this.value == 2) {
			return R.drawable.ic_will;
		} else if (this.value == 3) {
			return R.drawable.ic_spirit;
		} else {
			Log.wtf("StatType",
					"Trying to get the image path of an invalid stat type");
			return -1;
		}
	}

	// Convert a string to a stat type.
	public static StatType stringToType(String string) {
		if (string.equalsIgnoreCase("STRENGTH")) {
			return STRENGTH;
		} else if (string.equalsIgnoreCase("INTELLIGENCE")) {
			return INTELLIGENCE;
		} else if (string.equalsIgnoreCase("WILL")) {
			return WILL;
		} else if (string.equalsIgnoreCase("SPIRIT")) {
			return SPIRIT;
		} else {
			Log.wtf("StatType", "Loading an invalid stat type");
			return ERROR;
		}
	}

}
