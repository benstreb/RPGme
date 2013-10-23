package rpisdd.rpgme.gamelogic.player;

import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class Stats 
{
	//levels and experience
	private int level;
	private int totalExp;
	
	//The player's base stats. To be displayed in "stats" menu
	private int baseEnergy;
	private int baseStrength;
	private int baseIntelligence;
	private int baseWill;
	private int baseSpirit;
	
	public Stats(){}
	
	public Stats(int aStrength,int aIntel,int aWill,int aSpirit){
		this(1, 0, aStrength, aIntel, aWill, aSpirit);
		baseEnergy = 10;
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
	
	public int getLevel(){
		return level;
	}
	
	public void incrementLevel(){
		level++;
	}
	
	public int getExp(){
		return totalExp;
	}
	
	public void incExp(int amount){
		totalExp += amount;
	}
	
	public int getBaseEnergy(){
		return baseEnergy;
	}
	
	public void incBaseEnergy(int amount){
		baseEnergy += amount;
	}
	
	public int getBaseStr(){
		return baseStrength;
	}
	
	public void incBaseStr(int amount){
		baseStrength = amount;
	}
	
	public int getBaseInt(){
		return baseIntelligence;
	}
	
	public void incBaseInt(int amount){
		baseIntelligence = amount;
	}
	
	public int getBaseWill(){
		return baseWill;
	}
	
	public void incBaseWill(int amount){
		baseWill = amount;
	}
	
	public int getBaseSpr(){
		return baseSpirit;
	}
	
	public void incBaseSpr(int amount){
		baseSpirit = amount;
	}
	
	public Stats load(SharedPreferences p) {
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
