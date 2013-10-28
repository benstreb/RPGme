package rpisdd.rpgme.gamelogic.quests;

import rpisdd.rpgme.gamelogic.player.StatType;
import org.joda.time.DateTime;

//The Quest class represents a quest that the player can add.
public class Quest {
	
	private String name;
	private String description;
	private QuestDifficulty difficulty;
	private StatType statType;
	private boolean isComplete;
	public DateTime deadline;
	
	public boolean getIsComplete(){ return isComplete; }
	public String getName(){ return name; }
	public String getDescription(){ return description; }
	public QuestDifficulty getDifficulty(){ return difficulty; }
	public StatType getStatType(){ return statType; }
	public boolean isTimed(){ return deadline != null; }
	public DateTime getDeadline(){ return deadline; }
	
	public boolean isFailed() {
		return isTimed() && DateTime.now().isAfter(deadline);
	}
	
	public Quest() {
	}
	
	//Create a normal quest with no special attributes.
	public Quest(String aname,String adesc,QuestDifficulty adiff,StatType atype) {
		name = aname;
		description = adesc;
		difficulty = adiff;
		statType = atype;
		isComplete = false;
	}

	/*
	//Create a normal quest with no special attributes. (Takes in complete parameter)
	public Quest(String aname,String adesc,QuestDifficulty adiff,StatType atype,boolean aIsComplete) {
		name = aname;
		description = adesc;
		difficulty = adiff;
		statType = atype;
		isComplete = aIsComplete;
	}
	
	//Create a timed quest
	public Quest(String aname,String adesc,QuestDifficulty adiff,StatType atype,DateTime aDeadline) {
		name = aname;
		description = adesc;
		difficulty = adiff;
		statType = atype;
		deadline = aDeadline;
	}
	
	//Create a timed quest
	public Quest(String aname,String adesc,QuestDifficulty adiff,StatType atype,boolean aIsComplete,DateTime aDeadline) {
		name = aname;
		description = adesc;
		difficulty = adiff;
		statType = atype;
		deadline = aDeadline;
		isComplete = aIsComplete;
	}
	*/
	
	public void completeQuest() {
		isComplete = true;
	}
	
	//Builder class to streamline quest creation
	//Usage: Quest myQuest = new QuestBuilder("Name","Desc",difficulty,type).isComplete(true).deadline(deadline).(and so on...).getQuest()
	public static class QuestBuilder {
		
		private Quest quest;
		
		public QuestBuilder(String aname,String adesc,QuestDifficulty adiff,StatType atype) {
			quest = new Quest(aname,adesc,adiff,atype);
		}
		
		public QuestBuilder isComplete(boolean aIsComplete) {
			quest.isComplete = aIsComplete;
			return this;
		}
		
		public QuestBuilder deadline(DateTime aDeadline) {
			quest.deadline = aDeadline;
			return this;
		}
		
		public Quest getQuest() {
			return quest;
		}
		
	}
	
}
