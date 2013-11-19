package rpisdd.rpgme.gamelogic.items;

import java.util.ArrayList;

import rpisdd.rpgme.gamelogic.items.ItemDatabase.ItemFeedEntry;
import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class Inventory {

	// The maximum number of items allowed in inventory (static and constant)
	public final static int maxInventorySlots = 8;

	// The list of quests that the user has open (i.e. not yet completed but
	// still avaiable to complete)
	// Unless specified, all method will refer to these quests.
	private ArrayList<Item> items;
	private int weaponIndex = -1;
	private int armorIndex = -1;

	public Inventory() {
		items = new ArrayList<Item>();
	}

	public int getWeaponIndex() {
		return weaponIndex;
	}

	public int getArmorIndex() {
		return armorIndex;
	}

	public void unEquipWeapon() {
		weaponIndex = -1;
	}

	public void unEquipArmor() {
		armorIndex = -1;
	}

	public Equipment getWeapon() {
		if (weaponIndex == -1) {
			return null;
		} else if (weaponIndex >= items.size()) {
			Log.w("Warning",
					"Warning: equipment index wasn't updated. >= size of items");
			return null;
		} else {
			return (Equipment) items.get(weaponIndex);
		}
	}

	public Equipment getArmor() {
		if (armorIndex == -1) {
			return null;
		} else if (armorIndex >= items.size()) {
			Log.w("Warning",
					"Warning: equipment index wasn't updated. >= size of items");
			return null;
		} else {
			return (Equipment) items.get(armorIndex);
		}
	}

	public ArrayList<Item> getItems() {
		return items;
	}

	public int numItems() {
		return items.size();
	}

	public void addNewItem(Item item) {
		System.out.println("Adding item " + item.getName());
		items.add(item);
	}

	public void removeItemAt(int at) {
		System.out.println("Removing item");
		if (items.get(at) == getWeapon()) {
			weaponIndex = -1;
		} else if (items.get(at) == getArmor()) {
			armorIndex = -1;
		}
		items.remove(at);
	}

	public Item getItemAt(int at) {
		if (at < 0 || at >= items.size()) {
			return null;
		}
		return items.get(at);
	}

	public void equipWeapon(int index) {
		weaponIndex = index;
	}

	public void equipArmor(int index) {
		armorIndex = index;
	}

	public boolean isInventoryFull() {
		return numItems() >= maxInventorySlots;
	}

	public void loadItemsFromDatabase(Activity activity) {

		if (items == null) {
			items = new ArrayList<Item>();
		}
		ItemDatabase mDbHelper = new ItemDatabase(activity);
		Log.i("Debug:", "1");
		SQLiteDatabase db = mDbHelper.getReadableDatabase();
		Log.i("Debug:", "2");

		// Only uncomment this line of code if you've made a change to the
		// database structure in code and
		// are running the updated app for the first time, or if you want to
		// erase the current database in the phone/emu.
		// In a real deployment setting if you plan to provide updates for the
		// app where new columns are added
		// in the database in code, you should have a plan to backup and save
		// the user's data first.

		// mDbHelper.dropTable(db);

		mDbHelper.onCreate(db);

		// Define a projection that specifies which columns from the database
		// you will actually use after this query.
		String[] projection = { ItemFeedEntry.COLUMN_NAME_ITEM_NAME, };
		Log.i("Debug:", "3");
		Cursor cursor = db.query(ItemFeedEntry.TABLE_NAME, // The table to query
				projection, // The columns to return
				null, // The columns for the WHERE clause
				null, // The values for the WHERE clause
				null, // don't group the rows
				null, // don't filter by row groups
				null // The sort order
				);
		Log.i("Debug:", "4");
		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			String name = cursor.getString(0);

			Item item = Item.createItemFromName(name);

			items.add(item);

			cursor.moveToNext();
		}
		// make sure to close the cursor and database when done.
		cursor.close();
		db.close();

		Log.i("Debug:", "5");
	}

	// Will save all current items into sqlite database
	public void saveItemsToDatabase(Activity activity) {

		ItemDatabase mDbHelper = new ItemDatabase(activity);

		// Gets the data repository in write mode
		SQLiteDatabase db = mDbHelper.getWritableDatabase();

		// Drop the table first so that we can recreate it with all quests
		// loaded in the arraylist
		mDbHelper.dropTable(db);

		for (int i = 0; i < items.size(); i++) {

			// Create a new map of values, where column names are the keys
			ContentValues values = new ContentValues();
			values.put(ItemFeedEntry.COLUMN_NAME_ITEM_NAME, items.get(i)
					.getName());

			// Log.i("Debug:", "Saved quest '" + items.get(i).getName() +
			// "' in database:");

			// Insert the new row, returning the primary key value of the new
			// row
			db.insert(ItemFeedEntry.TABLE_NAME, "null", values);
		}

		// Make sure to close the database when done
		db.close();

	}

}
