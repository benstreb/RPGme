package rpisdd.rpgme.gamelogic.quests;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

//Helper class for quest-related database queries.
public class QuestDatabase extends SQLiteOpenHelper {

	/* Inner class that defines the table contents */
	public static abstract class QuestFeedEntry implements BaseColumns {
		public static final String TABLE_NAME = "quests";
		public static final String COLUMN_NAME_QUEST_NAME = "name";
		public static final String COLUMN_NAME_QUEST_DESC = "description";
		public static final String COLUMN_NAME_QUEST_TYPE = "stat_type";
		public static final String COLUMN_NAME_QUEST_DIFFICULTY = "difficulty";
		public static final String COLUMN_NAME_IS_COMPLETED = "completed";
		public static final String COLUMN_NAME_DEADLINE = "deadline";
		public static final String COLUMN_NAME_RECURRENCE = "recurrence";
		public static final String COLUMN_NAME_REC_COMP_DATE = "rec_comp_date";
	}

	private final String TEXT_TYPE = " TEXT";
	private final String COMMA_SEP = ",";
	private final String SQL_CREATE_ENTRIES = "CREATE TABLE IF NOT EXISTS "
			+ QuestFeedEntry.TABLE_NAME + " (" + QuestFeedEntry._ID
			+ " INTEGER PRIMARY KEY," + QuestFeedEntry.COLUMN_NAME_QUEST_NAME
			+ TEXT_TYPE + COMMA_SEP + QuestFeedEntry.COLUMN_NAME_QUEST_DESC
			+ TEXT_TYPE + COMMA_SEP + QuestFeedEntry.COLUMN_NAME_QUEST_TYPE
			+ TEXT_TYPE + COMMA_SEP
			+ QuestFeedEntry.COLUMN_NAME_QUEST_DIFFICULTY + TEXT_TYPE
			+ COMMA_SEP + QuestFeedEntry.COLUMN_NAME_IS_COMPLETED + TEXT_TYPE
			+ COMMA_SEP + QuestFeedEntry.COLUMN_NAME_DEADLINE + TEXT_TYPE
			// + COMMA_SEP + QuestFeedEntry.COLUMN_NAME_RECURRENCE + TEXT_TYPE
			// + COMMA_SEP + QuestFeedEntry.COLUMN_NAME_REC_COMP_DATE +
			// TEXT_TYPE
			+ " )";

	private final String SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS "
			+ QuestFeedEntry.TABLE_NAME;

	// If you change the database schema, you must increment the database
	// version.
	public static final int DATABASE_VERSION = 1;
	public static final String DATABASE_NAME = "FeedReader.db";

	public QuestDatabase(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(SQL_CREATE_ENTRIES);
	}

	// Remove the table from the database.
	public void dropTable(SQLiteDatabase db) {
		db.execSQL(SQL_DELETE_ENTRIES);
	}

	// Clear all entries in the table but don't touch the columns
	public void clearEntries(SQLiteDatabase db) {
		db.delete(QuestFeedEntry.TABLE_NAME, null, null);
	}

	// This method will add any columns to the database that don't exist.
	// These won't wipe out the current data stored, so don't need to worry
	// about backing up data.
	public void updateColumns(SQLiteDatabase db) {

		Cursor cursor = db.rawQuery("SELECT * FROM "
				+ QuestFeedEntry.TABLE_NAME + " LIMIT 0", null);

		String query = "";

		// If there's no deadline column: add it
		if (cursor.getColumnIndex(QuestFeedEntry.COLUMN_NAME_DEADLINE) == -1) {
			db.execSQL("ALTER TABLE " + QuestFeedEntry.TABLE_NAME
					+ " ADD COLUMN " + QuestFeedEntry.COLUMN_NAME_DEADLINE
					+ TEXT_TYPE + ";");
		}

		// If there's no recurrence column: Add it
		if (cursor.getColumnIndex(QuestFeedEntry.COLUMN_NAME_RECURRENCE) == -1) {
			System.out.println("Adding recurrence column");
			query = ("ALTER TABLE " + QuestFeedEntry.TABLE_NAME
					+ " ADD COLUMN " + QuestFeedEntry.COLUMN_NAME_RECURRENCE
					+ TEXT_TYPE + ";");

			System.out.println(query);
			db.execSQL(query);
		}

		// If there's no rec_comp_date column: Add it
		if (cursor.getColumnIndex(QuestFeedEntry.COLUMN_NAME_REC_COMP_DATE) == -1) {
			System.out.println("Adding recurrence date column");
			query = ("ALTER TABLE " + QuestFeedEntry.TABLE_NAME
					+ " ADD COLUMN " + QuestFeedEntry.COLUMN_NAME_REC_COMP_DATE
					+ TEXT_TYPE + ";");
			System.out.println(query);
			db.execSQL(query);
		}

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		updateColumns(db);
	}

	//
	@Override
	public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// onUpgrade(db, oldVersion, newVersion);
	}
}
