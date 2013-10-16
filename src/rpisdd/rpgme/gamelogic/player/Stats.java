package rpisdd.rpgme.gamelogic.player;

public class Stats 
{
	//basic player info
	public String playerName;
	
	//levels and experience
	public int level;
	public int toNextExp;
	public int totalExp;
	
	//The player's base stats. To be displayed in "stats" menu
	public int baseStrength;
	public int baseIntelligence;
	public int baseWill;
	public int baseSpirit;
	
	//The player's modified stats by the weapon he is carrying. 
	public int netStrength;
	public int netIntelligence;
	public int netWill;
	public int netSpirit;
	
	public Stats(){}
	
	public Stats(int aStrength,int aIntel,int aWill,int aSpirit){
		baseStrength = aStrength;
		baseIntelligence = aIntel;
		baseWill = aWill;
		baseSpirit = aSpirit;
	}
	
	public Stats(String aName, int aLevel, int aToNext, int aTotal, int aStrength,int aIntel,int aWill,int aSpirit){
		playerName = aName;
		level = aLevel;
		toNextExp = aToNext;
		totalExp = aTotal;
		
		baseStrength = aStrength;
		baseIntelligence = aIntel;
		baseWill = aWill;
		baseSpirit = aSpirit;
	}
	
	public int getBaseStr()
	{
		return baseStrength;
	}
	
	
}
