package rpisdd.rpgme.gamelogic.player;
import android.support.v4.app.Fragment;
import rpisdd.rpgme.activities.MainActivity;
import rpisdd.rpgme.gamelogic.quests.QuestManager;

public class Player {
	private static Player player = null;
	String name;
	String classs;
	int img_attr;
	
	
	public QuestManager questManager;
	public Stats stats;
	
	public Player(String name, String classs, int img_attr) {
		this.name = name;
		this.classs = classs;
		this.img_attr = img_attr;
		this.questManager = new QuestManager();
		this.stats = new Stats();
	}
	
	public static Player getPlayer() {
		return player;
	}
	
	//This static function takes in a fragment and returns the player. Good for accessing the player
	//from a fragment.
	public static Player getPlayer(Fragment fragment){
		return player;
	}
	
	public static void createPlayer(String name, String classs, int img_attr) {
		player = new Player(name, classs, img_attr);
	}
	
}
