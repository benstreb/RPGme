package rpisdd.rpgme.gamelogic.player;

import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class Stats 
{
	//levels and experience
	public int level;
	public int totalExp;
	
	//The player's base stats. To be displayed in "stats" menu
	public int baseStrength;
	public int baseIntelligence;
	public int baseWill;
	public int baseSpirit;
	
	public Stats(){}
	
	public Stats(int aStrength,int aIntel,int aWill,int aSpirit){
		baseStrength = aStrength;
		baseIntelligence = aIntel;
		baseWill = aWill;
		baseSpirit = aSpirit;
	}
	public Stats(int aLevel, int aTotal, int aStrength,int aIntel,int aWill,int aSpirit) {
		level = aLevel;
		totalExp = aTotal;
		
		baseStrength = aStrength;
		baseIntelligence = aIntel;
		baseWill = aWill;
		baseSpirit = aSpirit;
	}
	
	//aName and aToNext are just ignored, so don't use this function. I'll remove it later.
	@Deprecated
	public Stats(String aName, int aLevel, int aToNext, int aTotal, int aStrength,int aIntel,int aWill,int aSpirit){
		level = aLevel;
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
	
	public static Stats load(SharedPreferences p) {
		return new Stats(
				p.getInt("level", 1),
				p.getInt("exp", 0),
				p.getInt("str", 1),
				p.getInt("int", 1),
				p.getInt("wil", 1),
				p.getInt("spi", 1)
				);
	}

	public void save(Editor e) {
		e.putInt("level", level);
		e.putInt("exp", totalExp);
		e.putInt("str", baseStrength);
		e.putInt("int", baseIntelligence);
		e.putInt("wil", baseWill);
		e.putInt("spi", baseSpirit);
	}
	
	
}
