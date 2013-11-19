package rpisdd.rpgme.gamelogic.dungeon.model;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

import rpisdd.rpgme.R;
import rpisdd.rpgme.activities.BattleMenu;
import rpisdd.rpgme.activities.MainActivity;
import rpisdd.rpgme.activities.TransitionFragment;
import rpisdd.rpgme.gamelogic.player.Player;
import rpisdd.rpgme.gamelogic.player.StatType;
import android.app.Activity;
import android.content.Context;
import android.util.Log;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;

//Each monster is alligned with 1 Stat Type
//Attacks with that stat type with provided damage
//Defends against stat type with full defense, half against others
public class Monster implements RoomContent, HasHealth {

	private static final double HP_SCALING = 2.0;
	private static final double DAMAGE_SCALING = 1.0;
	private static final double DEFENSE_SCALING = 1.0;

	private final String name;
	private final String imageName;

	private int health;
	private final int maxHealth;

	private final int damage;
	private final int defense;
	private final StatType type;

	private final int treasureLevel;

	private static final ArrayList<Monster> monsters = new ArrayList<Monster>();

	private Monster(JsonObject o) {
		this(o.get("name").getAsString(), o.get("imagename").getAsString(), o
				.get("health").getAsInt(), o.get("health").getAsInt(), o.get(
				"power").getAsInt(), o.get("defense").getAsInt(), StatType
				.stringToType(o.get("type").getAsString()));
	}

	public Monster(String _name, String _imageName, int _health,
			int _maxHealth, int _damage, int _defense, StatType _type) {
		this(_name, _imageName, _health, _maxHealth, _damage, _defense, _type,
				1);
	}

	public Monster(String _name, String _imageName, int _health,
			int _maxHealth, int _damage, int _defense, StatType _type,
			int _treasureLevel) {
		name = _name;
		imageName = _imageName;
		health = _health;
		maxHealth = _maxHealth;
		damage = _damage;
		defense = _defense;
		type = _type;
		treasureLevel = _treasureLevel;
	}

	public Monster(Monster otherMon) {
		name = otherMon.name;
		imageName = otherMon.imageName;
		health = otherMon.health;
		maxHealth = otherMon.maxHealth;
		damage = otherMon.damage;
		defense = otherMon.defense;
		type = otherMon.type;
		treasureLevel = otherMon.treasureLevel;
	}

	private Monster copy() {
		return new Monster(name, imageName, health, maxHealth, damage, defense,
				type);
	}

	// Returns an int pair of the StatType of the attack,
	// and the base damage this monster deals
	public Combat.Attack MakeAttack() {
		return new Combat.Attack(this.type, this.damage);
	}

	public String getImagePath() {
		return "file:///android_asset/Monsters/" + imageName;
	}

	// takes an attack, checks it against the monster's defense and returns the
	// value
	public int RecieveAttack(Combat.Attack atk) {
		int damage;
		if (atk.type == this.type) {
			damage = Combat.CalculateAttackDamage(atk.power, defense);
		} else {
			damage = Combat.CalculateAttackDamage(atk.power, defense / 2);
		}
		return damage;
	}

	// Damages the monster and returns weather the monster died or not
	public boolean RecieveDamage(int damage) {
		this.health -= damage;
		if (health < 1) {
			health = 0;
			return true;
		}
		return false;
	}

	//
	public void die() {
		// Fairly certain this is never called
		Log.d("MonsterDebug", "Monster.die was called");
		Player.getPlayer().clearCurrentRoom();
	}

	@Override
	public boolean Encounter(Activity activity) {
		BattleMenu battleMenu = new BattleMenu();
		battleMenu.setMonster(this);

		TransitionFragment trans = new TransitionFragment();
		trans.setValues(battleMenu, false);
		((MainActivity) activity).changeFragment(trans);

		return false;
	}

	@Override
	public RoomType getRoomType() {
		return RoomType.MONSTER;
	}

	@Override
	public int getEnergy() {
		return health;
	}

	@Override
	public int getMaxEnergy() {
		return maxHealth;
	}

	public int getTreasureLevel() {
		return treasureLevel;
	}

	public boolean isDead() {
		return this.health <= 0;
	}

	@Override
	public String getStringRepresentation() {
		return "MONSTER" + "," + this.name + "," + imageName + "," + health
				+ "," + maxHealth + "," + damage + "," + defense + "," + type
				+ "," + treasureLevel;
	}

	public static Monster getFromStringRepresentation(String[] contentArgs) {
		if (contentArgs.length < 9) {
			Log.wtf("DungeonLoad", "Not enough args for a MONSTER");
		}
		String name = contentArgs[1];
		String imagePath = contentArgs[2];
		int health = Integer.parseInt(contentArgs[3]);
		int maxHealth = Integer.parseInt(contentArgs[4]);
		int damage = Integer.parseInt(contentArgs[5]);
		int defense = Integer.parseInt(contentArgs[6]);
		StatType monTypeResult = StatType.stringToType(contentArgs[7]);
		int tLevel = Integer.parseInt(contentArgs[8]);
		Monster newMon = new Monster(name, imagePath, health, maxHealth,
				damage, defense, monTypeResult, tLevel);
		return newMon;
	}

	public static void load(Context c) {
		InputStreamReader r = new InputStreamReader(c.getResources()
				.openRawResource(R.raw.monsters));
		JsonReader jr = new JsonReader(new BufferedReader(r));
		JsonParser jp = new JsonParser();
		JsonArray jItems = jp.parse(jr).getAsJsonArray();
		for (Iterator<JsonElement> jIter = jItems.iterator(); jIter.hasNext();) {
			JsonObject jItem = jIter.next().getAsJsonObject();
			Monster mon = new Monster(jItem);
			monsters.add(mon);
		}
	}

	public static Monster selectMonster(Dungeon d) {
		Monster baseMon = null;
		Monster resultMon = null;
		// select the monster
		Random monPicker = new Random();
		int monIndex = monPicker.nextInt(monsters.size());
		baseMon = monsters.get(monIndex).copy();
		// scale it
		resultMon = scaleMonsterWithLevel(baseMon, d.getLevel());
		// return it
		return resultMon;
	}

	public static Monster scaleMonsterWithLevel(Monster m, int nLvl) {
		if (nLvl < 1) {
			Log.wtf("MonsterDebug", "Somehow, level is under 1...");
		}
		Log.d("MonsterDebug", "Generating monster level: " + nLvl);
		// level 1 is base
		int lvl = nLvl - 1;
		// Unchanged by level
		String name = m.name;
		String imageName = m.imageName;
		StatType type = m.type;

		// Scales with level
		int maxHealth = (int) (m.maxHealth + lvl * HP_SCALING);
		int health = maxHealth;
		int damage = (int) (m.damage + lvl * DAMAGE_SCALING);
		int defense = (int) (m.defense + lvl * DEFENSE_SCALING);

		// Create the result and return it
		Monster resultMonster = new Monster(name, imageName, health, maxHealth,
				damage, defense, type, nLvl);
		return resultMonster;
	}
}