package rpisdd.rpgme.activities;

import rpisdd.rpgme.R;
import rpisdd.rpgme.gamelogic.player.Player;
import rpisdd.rpgme.gamelogic.player.Stats;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Button;
import android.widget.TextView;

public class StatsMenu extends Fragment {
	
	public StatsMenu(){}
	
	TextView strengthWidget;
	TextView intWidget;
	TextView willWidget;
	TextView spiritWidget;
	
	TextView nameWidget;
	TextView levelWidget;
	TextView toNextWidget;
	TextView totalExpWidget;
	
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
        Bundle savedInstanceState) {
    	View v = inflater.inflate(R.layout.stats_menu, container, false);
    	
    	Stats playerStats = Player.getPlayer(this).stats;
    	
    	//get stats from player
    	int currentStr = playerStats.baseStrength;
    	int currentInt = playerStats.baseIntelligence;
    	int currentWill = playerStats.baseWill;
    	int currentSpr = playerStats.baseSpirit;
    	
    	int level = playerStats.level;
    	int toNext = playerStats.toNextExp;
    	int total = playerStats.level;
    	
    	String playerName = "Kevin";
    		
    	//display the stats
    	strengthWidget = (TextView) v.findViewById(R.id.strengthDisplay);
    	strengthWidget.setText("Strength: " + currentStr);
    	
    	intWidget = (TextView) v.findViewById(R.id.intDisplay);
    	intWidget.setText("Int: " + currentInt);
    	
    	willWidget = (TextView) v.findViewById(R.id.willDisplay);
    	willWidget.setText("Will: " + currentWill);
    	
    	spiritWidget = (TextView) v.findViewById(R.id.spiritDisplay);
    	spiritWidget.setText("Spirit: " + currentSpr); 	
    	
    	nameWidget = (TextView) v.findViewById(R.id.nameDisplay);
    	nameWidget.setText(playerName); 
    	
    	levelWidget = (TextView) v.findViewById(R.id.levelDisplay);
    	levelWidget.setText("Lv: " + level); 
    	
    	toNextWidget = (TextView) v.findViewById(R.id.nextExpDisplay);
    	toNextWidget.setText("To Next: " + toNext); 
    	
    	totalExpWidget = (TextView) v.findViewById(R.id.totalExpDisplay);
    	totalExpWidget.setText("Total exp: " + total); 
    	
        // Inflate the layout for this fragment
    	return v;
    }
}
