package rpisdd.rpgme.gamelogic.quests;

public enum QuestDifficulty
{
	EASY, NORMAL, HARD, ERROR;
	
	//Convert a string to a difficulty. Used by Spinners of the CreateQuests menu.
	public static QuestDifficulty stringToDifficulty(String string){
		if(string.equalsIgnoreCase("Easy")){
			return EASY;
		}
		else if(string.equalsIgnoreCase("Normal")){
			return NORMAL;
		}
		else if(string.equalsIgnoreCase("Hard")){
			return HARD;
		}
		return ERROR;
	}
	
}
