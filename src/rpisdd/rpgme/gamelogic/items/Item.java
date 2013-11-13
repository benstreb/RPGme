package rpisdd.rpgme.gamelogic.items;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import rpisdd.rpgme.R;
import rpisdd.rpgme.gamelogic.player.Player;
import android.content.Context;
import android.util.Log;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;

public abstract class Item {
	public static final Item INVALID = new Item("M.", -100, "invalid.png",
			"WILD M. APPEARED") {
		@Override
		public void useMe(Player p, int index) {
			Log.wtf("items", "Trying to use invalid item.");
		}
	};

	private final String name;
	private final int price;
	private final String imagePath;
	private final String description;

	protected interface Factory {
		public Item fromJsonObject(JsonObject o);
	}

	private static Map<String, Item> loadItems(Context c, Factory f,
			int resource) {
		Map<String, Item> items = new HashMap<String, Item>();
		InputStreamReader r = new InputStreamReader(c.getResources()
				.openRawResource(resource));
		JsonReader jr = new JsonReader(new BufferedReader(r));
		JsonParser jp = new JsonParser();
		JsonArray jItems = jp.parse(jr).getAsJsonArray();
		for (Iterator<JsonElement> jIter = jItems.iterator(); jIter.hasNext();) {
			JsonObject jItem = jIter.next().getAsJsonObject();
			Item item = f.fromJsonObject(jItem);
			items.put(item.getName(), item);
		}
		return items;
	}

	public static void load(Context c) {
		if (!allItems.isEmpty()) {
			Log.e("items", "Items being loaded multiple times from database");
		}
		allItems.putAll(loadItems(c, new Factory() {
			@Override
			public Item fromJsonObject(JsonObject o) {
				return new Equipment(o);
			}
		}, R.raw.equipment));
		allItems.putAll(loadItems(c, new Factory() {
			@Override
			public Item fromJsonObject(JsonObject o) {
				return new Consumable(o);
			}
		}, R.raw.consumables));
		allItems.putAll(loadItems(c, new Factory() {
			@Override
			public Item fromJsonObject(JsonObject o) {
				return new Boondogle(o);
			}
		}, R.raw.boondogles));
	}

	private static final Map<String, Item> allItems = new HashMap<String, Item>();

	public static Item createItemFromName(String aname) {
		if (allItems.containsKey(aname)) {
			return allItems.get(aname);
		} else {
			Log.e("items", "Trying to create an item that doesn't exist.");
			return Item.INVALID;
		}
	}

	private Item(String name, int price, String imageName, String description) {
		this.name = name;
		this.price = price;
		this.imagePath = "file:///android_asset/Items/" + imageName;
		this.description = description;
	}

	protected Item(JsonObject o) {
		this(o.get("name").getAsString(), o.get("price").getAsInt(), o.get(
				"filename").getAsString(), o.get("description").getAsString());
	}

	public int getRefundPrice() {
		return getPrice() / 2;
	}

	public String getName() {
		return name;
	}

	public int getPrice() {
		return price;
	}

	public String getImagePath() {
		return imagePath;
	}

	public String getDescription() {
		return description;
	}

	@Override
	public String toString() {
		return name;
	}

	public boolean isUsable(Player p) {
		return false;
	}

	public boolean isEquipment() {
		return false;
	}

	public abstract void useMe(Player p, int index);

	public static void giveMeStuff(Player p, String... stuff) {
		for (String thing : stuff) {
			p.getInventory().addItem(createItemFromName(thing));
		}
	}
}
