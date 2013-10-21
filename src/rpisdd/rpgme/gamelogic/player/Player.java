package rpisdd.rpgme.gamelogic.player;
import android.support.v4.app.Fragment;
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
	
	public static void createPlayer(CharSequence name, CharSequence classs, int avatarId) {
		player = new Player(name, classs, avatarId);
	}
	
}
