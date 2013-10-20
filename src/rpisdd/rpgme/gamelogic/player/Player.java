package rpisdd.rpgme.gamelogic.player;
import android.support.v4.app.Fragment;
import rpisdd.rpgme.gamelogic.quests.QuestManager;

public class Player {
	private static Player player = null;
	String name;
	String classs;
	int avatarId;
	
	
	public QuestManager questManager;
	public Stats stats;
	
	public Player(CharSequence name, CharSequence classs, int avatarId) {
		this.name = name.toString();
		this.classs = classs.toString();
		this.avatarId = avatarId;
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
	
	public static void createPlayer(CharSequence name, CharSequence classs, int avatarId) {
		player = new Player(name, classs, avatarId);
	}
	
}
