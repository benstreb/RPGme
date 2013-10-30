package rpisdd.rpgme.gamelogic.quests;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;

public class DateFormatter {

	// Take in a date and format it, returning a reader-friendly string.
	// Later this could use words/phrases like "Tomorrow", "In a week",
	// "In an hour", etc.
	public static String formatDate(DateTime date) {
		String formatDate = DateTimeFormat.forPattern("M/d/Y 'at' h:m a")
				.print(date);
		return formatDate;
	}

}
