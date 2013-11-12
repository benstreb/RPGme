package rpisdd.rpgme.gamelogic.quests;

public enum Recurrence {

	NONE, DAILY, WEEKLY, MONTHLY;

	// Convert a string to a difficulty. Used by Spinners of the CreateQuests
	// menu.
	public static Recurrence stringToRecurrence(String string) {
		if (string.equalsIgnoreCase("NONE")) {
			return NONE;
		} else if (string.equalsIgnoreCase("DAILY")) {
			return DAILY;
		} else if (string.equalsIgnoreCase("WEEKLY")) {
			return WEEKLY;
		} else if (string.equalsIgnoreCase("MONTHLY")) {
			return MONTHLY;
		}
		return NONE;
	}

}
