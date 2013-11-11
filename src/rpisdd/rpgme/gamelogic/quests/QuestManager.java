package rpisdd.rpgme.gamelogic.quests;

import java.util.ArrayList;

import org.joda.time.DateTime;

import rpisdd.rpgme.gamelogic.player.Player;
import rpisdd.rpgme.gamelogic.player.Reward;
import rpisdd.rpgme.gamelogic.player.StatType;
import rpisdd.rpgme.gamelogic.quests.Quest.QuestBuilder;
import rpisdd.rpgme.gamelogic.quests.QuestDatabase.QuestFeedEntry;
import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

//The QuestManager manages the player's current quests, much like with how
//the Inventory manages the player's items.
//It provides modular utility functions such as adding and deleting quests.
//A player will contain a reference to one instance of this.
//This will eventually have more advanced functions (i.e. checking the number 
//of timed quests, etc.)
public class QuestManager {

	// The maximum number of quests (static and constant)
	public final static int maxQuests = 8;

	// The list of quests that the user has open (i.e. not yet completed but
	// still avaiable to complete)
	// Unless specified, all method will refer to these quests.
	private ArrayList<Quest> quests;

	// The list of completed quests (allows a "Journal" to be kept)
	private final ArrayList<Quest> completedQuests;

	// List of aborted or failed quests
	// ArrayList<Quest> failedQuests;

	public QuestManager() {
		quests = new ArrayList<Quest>();
		completedQuests = new ArrayList<Quest>();
	}

	public int numQuests() {
		return quests.size();
	}

	// Is the name used in one of the quests?
	public boolean isNameUsed(String name) {
		for (int i = 0; i < quests.size(); i++) {
			if (quests.get(i).getName().equals(name)) {
				return true;
			}
		}
		return false;
	}

	public Quest getQuestFromName(String name) {
		for (int i = 0; i < quests.size(); i++) {
			if (quests.get(i).getName().equals(name)) {
				return quests.get(i);
			}
		}
		return null;
	}

	// Are we at the max number of quests?
	public boolean atMaxNumQuests() {
		return numQuests() >= maxQuests;
	}

	public ArrayList<Quest> getQuests() {
		return quests;
	}

	public ArrayList<Quest> getCompleteQuests() {
		return completedQuests;
	}

	// Returns an arraylist that includes all quests.
	public ArrayList<Quest> getAllQuests() {
		ArrayList<Quest> all = new ArrayList<Quest>();
		for (int i = 0; i < quests.size(); i++) {
			all.add(quests.get(i));
		}
		for (int i = 0; i < completedQuests.size(); i++) {
			all.add(completedQuests.get(i));
		}
		return all;
	}

	public void addQuest(Quest quest) {
		quests.add(quest);
	}

	public void removeQuest(Quest quest) {
		quests.remove(quest);
	}

	// Complete the quest, rewarding the player.
	public Reward completeQuest(Player player, Quest quest) {
		Reward reward = quest.completeQuest();

		if (!quest.isRecurring()) {

			System.out.println("Completing non-temp");
			// Add to completed quests, remove from current quests
			completedQuests.add(quest);
			quests.remove(quest);
		} else {
			System.out.println("Completing temp");
			quest.tempComplete();
		}
		return reward;
	}

	// Return a string array of the quest names.
	public String[] getQuestNames() {
		String[] names = new String[numQuests()];
		for (int i = 0; i < quests.size(); i++) {
			names[i] = quests.get(i).getName();
		}
		return names;
	}

	// Return a string array of the completed quest names.
	public String[] getCompleteQuestNames() {
		String[] names = new String[completedQuests.size()];
		for (int i = 0; i < completedQuests.size(); i++) {
			names[i] = completedQuests.get(i).getName();
		}
		return names;
	}

	// Get only the quests that aren't completed yet.
	public ArrayList<Quest> getIncompleteQuests() {

		ArrayList<Quest> incompletes = new ArrayList<Quest>();

		for (int i = 0; i < quests.size(); i++) {
			if (!quests.get(i).isTempComplete()) {
				incompletes.add(quests.get(i));
			}
		}
		return incompletes;

	}

	public void reRecurQuests() {
		System.out.println("Rerecur the quests");
		for (int i = 0; i < quests.size(); i++) {
			System.out.println(quests.get(i).getRecCompDate() != null ? quests
					.get(i).getRecCompDate().toString() : "No completion date");
			if (quests.get(i).isTempComplete()) {
				System.out.println("A quest is temp completed");
				quests.get(i).updateTempDone();
			}
		}
	}

	// Will extract all the stored quests in sqlite database into an arraylist,
	// and put them into the quests/
	// completedQuests/failedQuests arraylist.
	// Only call this after this quest manager has been initiated.
	public void loadQuestsFromDatabase(Activity activity) {

		if (quests == null) {
			quests = new ArrayList<Quest>();
		}

		QuestDatabase mDbHelper = new QuestDatabase(activity);
		Log.i("Debug:", "1");
		SQLiteDatabase db = mDbHelper.getReadableDatabase();
		Log.i("Debug:", "2");

		mDbHelper.onCreate(db);

		// Make sure current database is up to date by updating the columns.
		mDbHelper.updateColumns(db);

		// Define a projection that specifies which columns from the database
		// you will actually use after this query.
		String[] projection = { QuestFeedEntry.COLUMN_NAME_QUEST_NAME,
				QuestFeedEntry.COLUMN_NAME_QUEST_DESC,
				QuestFeedEntry.COLUMN_NAME_QUEST_TYPE,
				QuestFeedEntry.COLUMN_NAME_QUEST_DIFFICULTY,
				QuestFeedEntry.COLUMN_NAME_IS_COMPLETED,
				QuestFeedEntry.COLUMN_NAME_DEADLINE,
				QuestFeedEntry.COLUMN_NAME_RECURRENCE,
				QuestFeedEntry.COLUMN_NAME_REC_COMP_DATE };

		Log.i("Debug:", "3");
		Cursor cursor = db.query(QuestFeedEntry.TABLE_NAME, // The table to
															// query
				projection, // The columns to return
				null, // The columns for the WHERE clause
				null, // The values for the WHERE clause
				null, // don't group the rows
				null, // don't filter by row groups
				null // The sort order
				);
		Log.i("Debug:", "4");
		cursor.moveToFirst();
		quests.clear();
		while (!cursor.isAfterLast()) {
			String name = cursor.getString(0);
			String desc = cursor.getString(1);
			StatType type = StatType.stringToType(cursor.getString(2));
			QuestDifficulty diff = QuestDifficulty.stringToDifficulty(cursor
					.getString(3));
			int completed = cursor.getInt(4);
			String deadlineStr = cursor.getString(5);
			Recurrence rec = Recurrence.NONE;
			String recStr = cursor.getString(6);
			if (recStr != null) {
				rec = Recurrence.stringToRecurrence(cursor.getString(6));
			}

			String recDateStr = cursor.getString(7);

			DateTime deadline = null;
			if (deadlineStr != null) {
				deadline = new DateTime(deadlineStr);
			}
			DateTime recCompDate = null;
			if (recDateStr != null) {
				recCompDate = new DateTime(recDateStr);
			}

			Log.i("Debug:", "Loaded quest '" + name + "' from database:");
			Log.i("Debug:", "	Name= '" + name);
			Log.i("Debug:", "	Description= '" + desc);
			Log.i("Debug:", "	Quest Type= '" + diff.toString());
			Log.i("Debug:", "	Difficulty= '" + type.toString());
			Log.i("Debug:", "	Completed= '" + Integer.toString(completed));
			Log.i("Debug:", "   Recurrence = " + rec.toString());

			if (recCompDate != null) {
				Log.i("Debug:", "   Rec Comp Date = " + recCompDate.toString());
			} else {
				Log.i("Debug:", "   Rec Comp Date = (NONE)");
			}

			Quest quest = new QuestBuilder(name, desc, diff, type, rec)
					.isComplete(completed != 0).deadline(deadline)
					.recurCompleteDate(recCompDate).build();

			if (completed == 0) {
				quests.add(quest);
			} else {
				completedQuests.add(quest);
			}

			cursor.moveToNext();
		}
		// make sure to close the cursor and database when done.
		cursor.close();
		db.close();

		Log.i("Debug:", "5");
	}

	// Will save all current quests in quests arraylist into sqlite database
	public void saveQuestsToDatabase(Activity activity) {

		QuestDatabase mDbHelper = new QuestDatabase(activity);

		// Gets the data repository in write mode
		SQLiteDatabase db = mDbHelper.getWritableDatabase();

		// Clear the table first so that we can recreate it with all quests
		// loaded in the arraylist
		mDbHelper.clearEntries(db);

		ArrayList<Quest> allQuests = getAllQuests();

		for (int i = 0; i < allQuests.size(); i++) {

			Quest quest = allQuests.get(i);

			// For now, don't save completed quests so it doesn't clog up the
			// database when debugging.
			if (quest.getIsComplete()) {
				continue;
			}

			// Create a new map of values, where column names are the keys
			ContentValues values = new ContentValues();
			values.put(QuestFeedEntry.COLUMN_NAME_QUEST_NAME, quest.getName());
			values.put(QuestFeedEntry.COLUMN_NAME_QUEST_DESC,
					quest.getDescription());
			values.put(QuestFeedEntry.COLUMN_NAME_QUEST_TYPE, quest
					.getStatType().toString());
			values.put(QuestFeedEntry.COLUMN_NAME_QUEST_DIFFICULTY, quest
					.getDifficulty().toString());
			values.put(QuestFeedEntry.COLUMN_NAME_IS_COMPLETED,
					(quest.getIsComplete() ? 1 : 0));

			if (quest.isTimed()) {
				values.put(QuestFeedEntry.COLUMN_NAME_DEADLINE, quest
						.getDeadline().toString());
			} else {
				values.putNull(QuestFeedEntry.COLUMN_NAME_DEADLINE);
			}

			values.put(QuestFeedEntry.COLUMN_NAME_RECURRENCE, quest
					.getRecurrence().toString());

			if (quest.isTempComplete()) {
				values.put(QuestFeedEntry.COLUMN_NAME_REC_COMP_DATE, quest
						.getRecCompDate().toString());
			} else {
				values.putNull(QuestFeedEntry.COLUMN_NAME_REC_COMP_DATE);
			}

			Log.i("Debug:", "Saved quest '" + quest.getName()
					+ "' in database:");
			Log.i("Debug:", "	Name= '" + quest.getName());
			Log.i("Debug:", "	Description= '" + quest.getDescription());
			Log.i("Debug:", "	Quest Type= '" + quest.getStatType().toString());
			Log.i("Debug:", "	Difficulty= '" + quest.getDifficulty().toString());
			Log.i("Debug:", "	Completed= '"
					+ (quest.getIsComplete() ? "true" : "false"));
			Log.i("Debug:", "   Recurrence= "
					+ quest.getRecurrence().toString());

			if (quest.getRecCompDate() != null) {
				Log.i("Debug:", "   Rec Comp Date = "
						+ quest.getRecCompDate().toString());
			} else {
				Log.i("Debug:", "   Rec Comp Date = (NONE)");
			}

			// Insert the new row, returning the primary key value of the new
			// row
			long newRowId;
			newRowId = db.insert(QuestFeedEntry.TABLE_NAME, "null", values);
		}

		// Make sure to close the database when done
		db.close();

	}
}
