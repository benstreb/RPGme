package rpisdd.rpgme.gamelogic.player;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.support.v4.app.Fragment;
import rpisdd.rpgme.activities.MainActivity;
import rpisdd.rpgme.gamelogic.items.Inventory;
import rpisdd.rpgme.gamelogic.quests.QuestManager;

public class Player {
	final static int EXP_PER_LEVEL = 100;

	private static Player player = null;
	private String name;
	private String classs;
	private int avatarId;

	public QuestManager questManager;
	public Stats stats;
	public Inventory inventory;

	private int gold;
	private int energy;

	public Player(CharSequence name, CharSequence classs, int avatarId) {
		this.name = name.toString();
		this.classs = classs.toString();
		this.avatarId = avatarId;
		this.questManager = new QuestManager();
		this.inventory = new Inventory();
		this.stats = new Stats();
		this.gold = 100;
		this.energy = 10;
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
	public int getEnergy() {
		return stats.getBaseEnergy();
	}

	/*
	 * Increases the player's current energy
	 */
	public void addEnergy(int amount) {
		energy += amount;
		if (energy > getMaxEnergy()) {
			energy = getMaxEnergy();
		}
	}

	/*
	 * Decreases the player's current energy
	 */
	public void deductEnergy(int amount) {
		energy -= amount;
		if (energy < 0) {
			energy = 0;
		}
	}

	/*
	 * Returns exp to next level up
	 */
	public int getExpForLevel(int aLevel) {
		return EXP_PER_LEVEL * aLevel;
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
		if (stats.getExp() > getExpForLevel(getLevel())) {
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
	}

	// Stat functions/////////////////////////////////////////////

	/*
	 * Returns player's max energy
	 */
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

	/*
	 * Increases the player's spirit
	 */
	public void incSpirit(int amount) {
		stats.incBaseSpr(amount);
	}

	// ///////////////////////////////////////////////

	public static Player getPlayer() {
		return player;
	}

	// Todo: Eliminate this function, and replace all occurrences of
	// getPlayer() with getPlayer()
	public static Player getPlayer(Fragment fragment) {
		return player;
	}

	public void savePlayer(Activity activity) {
		questManager.saveQuestsToDatabase(activity);
		SharedPreferences p = activity.getPreferences(Context.MODE_PRIVATE);
		Editor e = p.edit();
		stats.save(e);
		e.putString("name", name);
		e.putString("class", classs);
		e.putInt("avatarId", avatarId);
		e.commit();
	}

	public static void createPlayer(CharSequence name, CharSequence classs,
			int avatarId) {
		player = new Player(name, classs, avatarId);
	}

}
