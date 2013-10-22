package rpisdd.rpgme.gamelogic.player;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.support.v4.app.Fragment;
import rpisdd.rpgme.R;
import rpisdd.rpgme.activities.MainActivity;
import rpisdd.rpgme.gamelogic.items.Inventory;
import rpisdd.rpgme.gamelogic.quests.QuestManager;

public class Player {
	private static Player player = null;
	String name;
	String classs;
	int avatarId;
	
	public QuestManager questManager;
	public Stats stats;
	public Inventory inventory;
	
	private int gold;
	
	public Player(CharSequence name, CharSequence classs, int avatarId) {
		this.name = name.toString();
		this.classs = classs.toString();
		this.avatarId = avatarId;
		this.questManager = new QuestManager();
		this.inventory = new Inventory();
		this.stats = new Stats();
		this.gold = 100;
	}
	
	public String getName(){ return name; }
	public String getClasss(){ return classs; }
	public int getGold(){ return gold; }
	
	public void addGold(int amount){
		gold += amount;
	}
	
	public void deductGold(int amount){
		gold -= amount;
		if(gold < 0){
			gold = 0;
		}
	}
	
	public static Player getPlayer() {
		return player;
	}
	
	//Todo: Eliminate this function, and replace all occurrences of getPlayer(this) with getPlayer()
	public static Player getPlayer(Fragment fragment){
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
		e.putBoolean("playerExists", true);
		e.commit();
	}
	
	public static void loadPlayer(Activity activity) {
		SharedPreferences pref = activity.getPreferences(Context.MODE_PRIVATE);
		assert pref.getBoolean("playerExists", false);
		Player p = new Player(
				pref.getString("name", "Missingno"),
				pref.getString("class", "Bird, Water"),
				pref.getInt("avatarId", R.drawable.splash_screen)
				);
		p.questManager.loadQuestsFromDatabase(activity);
		p.inventory.loadItemsFromDatabase(activity);
		p.stats = Stats.load(pref);
		player = p;
	}
	
	public static void createPlayer(CharSequence name, CharSequence classs, int avatarId) {
		assert player == null;
		player = new Player(name, classs, avatarId);
	}
	
}
