package rpisdd.rpgme.gamelogic.quests;

import java.util.ArrayList;
import java.util.List;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.util.Log;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;

//Helper class for quest-related database queries.
public class QuestDatabase extends SQLiteOpenHelper  {
	
	/* Inner class that defines the table contents */
    public static abstract class QuestFeedEntry implements BaseColumns {
        public static final String TABLE_NAME = "quests";
        public static final String COLUMN_NAME_QUEST_NAME = "name";
        public static final String COLUMN_NAME_QUEST_DESC = "description";
        public static final String COLUMN_NAME_QUEST_TYPE = "stat_type";
        public static final String COLUMN_NAME_QUEST_DIFFICULTY = "difficulty";
        public static final String COLUMN_NAME_IS_COMPLETED = "completed";	//0 = false, 1 = true
        public static final String COLUMN_NAME_DEADLINE = "deadline";
    }

	private String TEXT_TYPE = " TEXT";
	private String COMMA_SEP = ",";
	private String SQL_CREATE_ENTRIES =
	    "CREATE TABLE IF NOT EXISTS " + QuestFeedEntry.TABLE_NAME + " (" +
	    QuestFeedEntry._ID + " INTEGER PRIMARY KEY," +
	    QuestFeedEntry.COLUMN_NAME_QUEST_NAME + TEXT_TYPE + COMMA_SEP +
	    QuestFeedEntry.COLUMN_NAME_QUEST_DESC + TEXT_TYPE + COMMA_SEP +
	    QuestFeedEntry.COLUMN_NAME_QUEST_TYPE + TEXT_TYPE + COMMA_SEP +
	    QuestFeedEntry.COLUMN_NAME_QUEST_DIFFICULTY + TEXT_TYPE + COMMA_SEP +
	    QuestFeedEntry.COLUMN_NAME_IS_COMPLETED + TEXT_TYPE + COMMA_SEP +
	    QuestFeedEntry.COLUMN_NAME_DEADLINE + TEXT_TYPE +
	    " )";

	private String SQL_DELETE_ENTRIES =
	    "DROP TABLE IF EXISTS " + QuestFeedEntry.TABLE_NAME;
	
	// If you change the database schema, you must increment the database version.
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "FeedReader.db";

    public QuestDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }
    //Call this from code whenever you changed the database structure in code and need it recreated.
    public void dropTable(SQLiteDatabase db){
    	db.execSQL(SQL_DELETE_ENTRIES);
    	onCreate(db);
    }

    //This method will add any columns to the database that don't exist.
    //These won't wipe out the current data stored, so don't need to worry about backing up data.
    public void updateColumns(SQLiteDatabase db) {
    	
    	Cursor cursor = db.rawQuery( "SELECT * FROM " + QuestFeedEntry.TABLE_NAME + " LIMIT 0", null );

    	//If there's no deadline column: add it
    	if(cursor.getColumnIndex(QuestFeedEntry.COLUMN_NAME_DEADLINE) == -1) {
    		db.execSQL("ALTER TABLE " + QuestFeedEntry.TABLE_NAME + " ADD COLUMN" + QuestFeedEntry.COLUMN_NAME_DEADLINE + "TEXT");
    	}
    	
    }
    
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }
}
