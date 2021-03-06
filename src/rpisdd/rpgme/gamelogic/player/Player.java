package rpisdd.rpgme.gamelogic.player;

import rpisdd.rpgme.R;
import rpisdd.rpgme.activities.MainActivity;
import rpisdd.rpgme.gamelogic.dungeon.model.Combat;
import rpisdd.rpgme.gamelogic.dungeon.model.Dungeon;
import rpisdd.rpgme.gamelogic.dungeon.model.HasHealth;
import rpisdd.rpgme.gamelogic.dungeon.model.Room;
import rpisdd.rpgme.gamelogic.items.Inventory;
import rpisdd.rpgme.gamelogic.quests.QuestManager;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Log;

public class Player implements HasHealth {
	final static int EXP_PER_LEVEL = 100;
	final static int EXP_SCALING_BASE = 25;
	final static double EXP_SCALING_RATIO = 1;

	private static Player player = null;
	private final String name;
	private final String classs;
	private final int avatarId;

	private final QuestManager questManager;
	private final Stats stats;
	private final Inventory inventory;
	private final Dungeon dungeon;

	private int gold;
	private int energy;

	private int roomX;
	private int roomY;

	// Stores the last room the player was in. Note that this is only a
	// one-layer deep stack.
	private int lastRoomX;
	private int lastRoomY;

	private MainActivity activity;

	public Player(CharSequence name, CharSequence classs, int avatarId) {
		this.name = name.toString();
		this.classs = classs.toString();
		this.avatarId = avatarId;
		this.questManager = new QuestManager();
		this.inventory = new Inventory();
		this.stats = new Stats();
		this.dungeon = new Dungeon(0);
		this.gold = 100;
		this.energy = 10;
		this.roomX = -1;
		this.roomY = -1;
		this.lastRoomX = -1;
		this.lastRoomY = -1;
	}

	public void setRoomPos(int x, int y) {
		if (this.roomX == -1) {
			this.lastRoomX = x;
			this.lastRoomY = y;
		} else {
			this.lastRoomX = this.roomX;
			this.lastRoomY = this.roomY;
		}
		this.roomX = x;
		this.roomY = y;
	}

	public int getRoomX() {
		return roomX;
	}

	public int getRoomY() {
		return roomY;
	}

	public Room getCurrentRoom() {
		if (this.dungeon == null) {
			Log.wtf("Logic", "called getCurrentRoom, but dungeon is null!");
			return null;
		} else {
			return dungeon.getRoom(roomX, roomY);
		}
	}

	// Clear the current room and visit it again. This ensures that surrounding
	// rooms will pop up.
	public void clearCurrentRoom() {
		if (dungeon == null) {
			Log.d("Debug", "Tried to clearCurrentRoom but no dungeon");
		} else {
			getCurrentRoom().clearContent();
			dungeon.visitRoom(roomX, roomY, null);
		}
	}

	public void goToLastRoom() {
		this.roomX = lastRoomX;
		this.roomY = lastRoomY;
	}

	public String getLastRooms() {
		return String.format("%d,%d", lastRoomX, lastRoomY);
	}

	public void setActivity(MainActivity activity) {
		this.activity = activity;
	}

	public int getAvatar() {
		return this.avatarId;
	}

	public QuestManager getQuestManager() {
		return questManager;
	}

	public Stats getStats() {
		return stats;
	}

	public Inventory getInventory() {
		return inventory;
	}

	public Dungeon getDungeon() {
		return dungeon;
	}

	public String getName() {
		return name;
	}

	public String getClasss() {
		return classs;
	}

	public int getGold() {
		return gold;
	}

	public void addGold(int amount) {
		gold += amount;
	}

	public void deductGold(int amount) {
		gold -= amount;
		if (gold < 0) {
			gold = 0;
		}
	}

	/*
	 * returns the player's current energy
	 */
	@Override
	public int getEnergy() {
		return energy;
	}

	/*
	 * Returns true if the player is unconscious and false otherwise.
	 */
	public boolean isConscious() {
		return energy > 0;
	}

	/*
	 * Increases the player's current energy
	 */
	public void addEnergy(int amount) {
		energy += amount;
		if (energy > getMaxEnergy()) {
			energy = getMaxEnergy();
		}
		activity.updateEnergyBar();
	}

	/*
	 * Decreases the player's current energy
	 */
	public void deductEnergy(int amount) {
		if (!isConscious()) {
			Log.w("player", "Player is losing energy while unconscious.");
		}
		energy -= amount;
		if (energy < 0) {
			energy = 0;
		}
		activity.updateEnergyBar();
	}

	/*
	 * Returns exp to next level up
	 */
	public static int getExpForLevel(int aLevel) {
		// EXP_PER_LEVEL
		// EXP_SCALING_BASE
		// EXP_SCALING_RATIO
		int prev = 0;
		if (aLevel < 1) {
			return 0;
		}
		prev = getExpForLevel(aLevel - 1);
		return prev + EXP_PER_LEVEL + (aLevel - 1) * EXP_SCALING_BASE;
	}

	public int getNextExp() {
		return getExpForLevel(getLevel()) - getTotalExp();
	}

	/*
	 * Returns total exp accumulated.
	 */
	public int getTotalExp() {
		return stats.getExp();
	}

	/*
	 * Increases earned exp
	 */
	public void addExp(int amount) {
		stats.incExp(amount);
		if (stats.getExp() >= getExpForLevel(getLevel())) {
			levelUp();
		}
	}

	/*
	 * Returns the player's level
	 */
	public int getLevel() {
		return stats.getLevel();
	}

	/*
	 * Levels up the player
	 */
	public void levelUp() {
		incMaxEnergy(5);
		stats.incrementLevel();

		// recover energy on level up
		this.addEnergy(stats.getBaseEnergy());
	}

	// Stat functions/////////////////////////////////////////////

	/*
	 * Returns player's max energy
	 */
	@Override
	public int getMaxEnergy() {
		return stats.getBaseEnergy();
	}

	/*
	 * Increases the player's max energy
	 */
	public void incMaxEnergy(int amount) {
		stats.incBaseEnergy(amount);
	}

	/*
	 * Returns the player's strength
	 */
	public int getStrength() {
		return stats.getBaseStr();
	}

	public int getStrAtk() {
		int total = stats.getBaseStr();
		if (inventory.getWeapon() != null) {
			total += inventory.getWeapon().getMod().str;
		}
		return total;
	}

	public int getStrDef() {
		int total = stats.getBaseStr();
		if (inventory.getArmor() != null) {
			total += inventory.getArmor().getMod().str;
		}
		return total;
	}

	/*
	 * Increases the player's strength
	 */
	public void incStrength(int amount) {
		stats.incBaseStr(amount);
	}

	/*
	 * Returns the player's intelligence
	 */
	public int getInt() {
		return stats.getBaseInt();
	}

	public int getIntAtk() {
		int total = stats.getBaseInt();
		if (inventory.getWeapon() != null) {
			total += inventory.getWeapon().getMod().intel;
		}
		return total;
	}

	public int getIntDef() {
		int total = stats.getBaseInt();
		if (inventory.getArmor() != null) {
			total += inventory.getArmor().getMod().intel;
		}
		return total;
	}

	/*
	 * Increases the player's intelligence
	 */
	public void incInt(int amount) {
		stats.incBaseInt(amount);
	}

	/*
	 * Returns the player's will
	 */
	public int getWill() {
		return stats.getBaseWill();
	}

	public int getWillAtk() {
		int total = stats.getBaseWill();
		if (inventory.getWeapon() != null) {
			total += inventory.getWeapon().getMod().will;
		}
		return total;
	}

	public int getWillDef() {
		int total = stats.getBaseWill();
		if (inventory.getArmor() != null) {
			total += inventory.getArmor().getMod().will;
		}
		return total;
	}

	/*
	 * Increases the player's will
	 */
	public void incWill(int amount) {
		stats.incBaseWill(amount);
	}

	/*
	 * Returns the player's spirit
	 */
	public int getSpirit() {
		return stats.getBaseSpr();
	}

	public int getSprAtk() {
		int total = stats.getBaseSpr();
		Log.d("PlayerDebug", "getSprAtk base: " + total + "\n");
		if (inventory.getWeapon() != null) {
			total += inventory.getWeapon().getMod().spirit;
		}
		Log.d("PlayerDebug", "getSprAtk post weapon: " + total + "\n");
		return total;
	}

	public int getSprDef() {
		int total = stats.getBaseSpr();
		if (inventory.getArmor() != null) {
			total += inventory.getArmor().getMod().spirit;
		}
		return total;
	}

	/*
	 * Increases the player's spirit
	 */
	public void incSpirit(int amount) {
		stats.incBaseSpr(amount);
	}

	public int getStat(StatType type) {

		switch (type) {
		case STRENGTH:
			return getStrength();
		case INTELLIGENCE:
			return getInt();
		case WILL:
			return getWill();
		case SPIRIT:
			return getSpirit();
		default:
			return -1;
		}
	}

	// Receive an attack. Calculate damage based on the
	// type of attack, the base power of the attack, and
	// the appropriate defense stat of the player. Then
	// damage the player's energy by that amount.
	public int takeDamage(Combat.Attack atk) {
		int defenseValue;
		switch (atk.type) {
		case STRENGTH:
			defenseValue = this.getStrDef();
			break;
		case INTELLIGENCE:
			defenseValue = this.getIntDef();
			break;
		case WILL:
			defenseValue = this.getWillDef();
			break;
		case SPIRIT:
			defenseValue = this.getSprDef();
			break;
		default:
			defenseValue = 0;
		}
		int damage = Combat.CalculateAttackDamage(atk.power, defenseValue);
		deductEnergy(damage);
		return damage;
	}

	// ///////////////////////////////////////////////

	/*
	 * Player is implemented as a singleton. This returns the current player.
	 * There will always only be one (it will be created when the game loads, or
	 * when the user creates the profile for the first time.)
	 */
	public static Player getPlayer() {
		return player;
	}

	public void savePlayer(Activity activity) {
		questManager.saveQuestsToDatabase(activity);
		inventory.saveItemsToDatabase(activity);
		SharedPreferences p = activity.getSharedPreferences("player",
				Context.MODE_PRIVATE);
		Editor e = p.edit();
		stats.save(e);
		dungeon.save(e);
		e.putString("name", name);
		e.putString("class", classs);
		e.putInt("avatarId", avatarId);
		e.putBoolean("playerExists", true);
		e.putInt("gold", gold);
		e.putInt("energy", energy);
		e.putInt("roomX", this.roomX);
		e.putInt("roomY", this.roomY);
		e.putInt("weaponIndex", inventory.getWeaponIndex());
		e.putInt("armorIndex", inventory.getArmorIndex());
		e.commit();
	}

	// This save is necessary if the app exists in a battle.
	// It moves the player's room to the previous room.
	// Otherwise when they restart, they'll end up in the same room as the
	// monster, but without initiating a battle scene.
	public void saveRoomAsPrev(Activity activity) {
		SharedPreferences p = activity.getSharedPreferences("player",
				Context.MODE_PRIVATE);
		Editor e = p.edit();
		e.putInt("roomX", lastRoomX);
		e.putInt("roomY", lastRoomY);
		e.commit();
	}

	public static void loadPlayer(Activity activity) {

		SharedPreferences pref = activity.getSharedPreferences("player",
				Context.MODE_PRIVATE);
		assert pref.getBoolean("playerExists", false);
		Player p = new Player(pref.getString("name", "Missingno"),
				pref.getString("class", "Bird, Water"), pref.getInt("avatarId",
						R.drawable.splash_screen));
		p.questManager.loadQuestsFromDatabase(activity);
		p.inventory.loadItemsFromDatabase(activity);
		p.stats.load(pref);
		p.dungeon.load(pref);
		p.gold = pref.getInt("gold", 100);
		p.energy = pref.getInt("energy", 1);
		p.roomX = pref.getInt("roomX", -1);
		p.roomY = pref.getInt("roomY", -1);
		p.lastRoomX = p.roomX;
		p.lastRoomY = p.roomY;

		int weaponIndex = pref.getInt("weaponIndex", -1);
		int armorIndex = pref.getInt("armorIndex", -1);

		if (weaponIndex != -1) {
			p.inventory.equipWeapon(weaponIndex);
		}
		if (armorIndex != -1) {
			p.inventory.equipArmor(armorIndex);
		}

		player = p;

		// validate the information
		// ////////////////////////////////////////////////
		// check to make sure player is in a valid position
		if (!p.StartPosSet()
				|| !p.dungeon.roomExists(p.getRoomX(), p.getRoomY())) {
			if (!p.StartPosSet()) {
				Log.d("DungeonMenu",
						"Player pos not set yet. Setting to start\n");
			} else {
				Log.e("DungeonMenu",
						"Player in an invalid room. Setting to start");
			}
			int dunStartX = p.dungeon.start_x;
			int dunStartY = p.dungeon.start_y;
			p.setRoomPos(dunStartX, dunStartY);
		}
	}

	public boolean StartPosSet() {
		return (!(this.roomX == -1 || this.roomY == -1));
	}

	public static void createPlayer(CharSequence name, CharSequence classs,
			int avatarId) {
		assert player == null;
		player = new Player(name, classs, avatarId);
	}

}
