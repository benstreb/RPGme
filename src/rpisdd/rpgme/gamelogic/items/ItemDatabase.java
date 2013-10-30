package rpisdd.rpgme.gamelogic.items;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

public class ItemDatabase extends SQLiteOpenHelper {

	/* Inner class that defines the table contents */
	public static abstract class ItemFeedEntry implements BaseColumns {
		public static final String TABLE_NAME = "items";
		public static final String COLUMN_NAME_ITEM_NAME = "name";
	}

	private String TEXT_TYPE = " TEXT";
	private String COMMA_SEP = ",";
	private String SQL_CREATE_ENTRIES = "CREATE TABLE IF NOT EXISTS "
			+ ItemFeedEntry.TABLE_NAME + " (" + ItemFeedEntry._ID
			+ " INTEGER PRIMARY KEY," + ItemFeedEntry.COLUMN_NAME_ITEM_NAME
			+ TEXT_TYPE + " )";

	private String SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS "
			+ ItemFeedEntry.TABLE_NAME;

	// If you change the database schema, you must increment the database
	// version.
	public static final int DATABASE_VERSION = 1;
	public static final String DATABASE_NAME = "FeedReader.db";

	public ItemDatabase(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(SQL_CREATE_ENTRIES);
	}

	// Call this from code whenever you changed the database structure in code
	// and need it recreated.
	public void dropTable(SQLiteDatabase db) {
		db.execSQL(SQL_DELETE_ENTRIES);
		onCreate(db);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// This database is only a cache for online data, so its upgrade policy
		// is
		// to simply to discard the data and start over
		db.execSQL(SQL_DELETE_ENTRIES);
		onCreate(db);
	}

	@Override
	public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		onUpgrade(db, oldVersion, newVersion);
	}
}
