package rpisdd.rpgme.gamelogic.player;
import android.support.v4.app.Fragment;
import rpisdd.rpgme.activities.MainActivity;
import rpisdd.rpgme.gamelogic.quests.QuestManager;

public class Player {
	
	public QuestManager questManager;
	public Stats stats;
	
	public Player(){
		questManager = new QuestManager();
		stats = new Stats();
	}
	
	//This static function takes in a fragment and returns the player. Good for accessing the player
	//from a fragment.
	public static Player getPlayer(Fragment fragment){
		return ((MainActivity)fragment.getActivity()).getPlayer();
	}
	
}
