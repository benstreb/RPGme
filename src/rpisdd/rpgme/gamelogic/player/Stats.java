package rpisdd.rpgme.gamelogic.player;

public class Stats 
{
	//The player's base stats. To be displayed in "stats" menu
	int baseStrength;
	int baseIntelligence;
	int baseWill;
	int baseSpirit;
	
	//The player's modified stats by the weapon he is carrying. 
	int netStrength;
	int netIntelligence;
	int netWill;
	int netSpirit;
	
	public Stats(){}
	
	public Stats(int aStrength,int aIntel,int aWill,int aSpirit){
		baseStrength = aStrength;
		baseIntelligence = aIntel;
		baseWill = aWill;
		baseSpirit = aSpirit;
	}
	
	
}
