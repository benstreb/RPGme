package rpisdd.rpgme.gamelogic.quests;

import rpisdd.rpgme.gamelogic.player.StatType;

//The Quest class represents a quest that the player can add.
public class Quest {
	
	String name;
	String description;
	QuestDifficulty difficulty;
	StatType statType;
	boolean isComplete;
	
	public boolean getIsComplete(){ return isComplete; }
	public String getName(){ return name; }
	public String getDescription(){ return description; }
	public QuestDifficulty getDifficulty(){ return difficulty; }
	public StatType getStatType(){ return statType; }
	
	//Create a normal quest with no special attributes.
	public Quest(String aname,String adesc,QuestDifficulty adiff,StatType atype){
		name = aname;
		description = adesc;
		difficulty = adiff;
		statType = atype;
		isComplete = false;
	}
	
	//Create a normal quest with no special attributes. (Takes in complete parameter)
	public Quest(String aname,String adesc,QuestDifficulty adiff,StatType atype,boolean aIsComplete){
		name = aname;
		description = adesc;
		difficulty = adiff;
		statType = atype;
		isComplete = aIsComplete;
	}
	
	public void completeQuest(){
		isComplete = true;
	}
	
}
