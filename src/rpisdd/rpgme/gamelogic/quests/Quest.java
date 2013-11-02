package rpisdd.rpgme.gamelogic.quests;

import org.joda.time.DateTime;

import rpisdd.rpgme.gamelogic.player.Player;
import rpisdd.rpgme.gamelogic.player.Reward;
import rpisdd.rpgme.gamelogic.player.StatType;

//The Quest class represents a quest that the player can add.
public class Quest {

	private String name;
	private String description;
	private QuestDifficulty difficulty;
	private StatType statType;
	private boolean isComplete;
	private DateTime deadline;

	public boolean getIsComplete() {
		return isComplete;
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	public QuestDifficulty getDifficulty() {
		return difficulty;
	}

	public StatType getStatType() {
		return statType;
	}

	public boolean isTimed() {
		return deadline != null;
	}

	public DateTime getDeadline() {
		return deadline;
	}

	public boolean isFailed() {
		return isTimed() && DateTime.now().isAfter(deadline);
	}

	// Create a normal quest with no special attributes.
	private Quest(String aname, String adesc, QuestDifficulty adiff,
			StatType atype, boolean aIsComplete, DateTime adeadline) {
		name = aname;
		description = adesc;
		difficulty = adiff;
		statType = atype;
		deadline = adeadline;
		isComplete = aIsComplete;
	}

	/*
	 * //Create a normal quest with no special attributes. (Takes in complete
	 * parameter) public Quest(String aname,String adesc,QuestDifficulty
	 * adiff,StatType atype,boolean aIsComplete) { name = aname; description =
	 * adesc; difficulty = adiff; statType = atype; isComplete = aIsComplete; }
	 * 
	 * //Create a timed quest public Quest(String aname,String
	 * adesc,QuestDifficulty adiff,StatType atype,DateTime aDeadline) { name =
	 * aname; description = adesc; difficulty = adiff; statType = atype;
	 * deadline = aDeadline; }
	 * 
	 * //Create a timed quest public Quest(String aname,String
	 * adesc,QuestDifficulty adiff,StatType atype,boolean aIsComplete,DateTime
	 * aDeadline) { name = aname; description = adesc; difficulty = adiff;
	 * statType = atype; deadline = aDeadline; isComplete = aIsComplete; }
	 */

	public Reward completeQuest() {
		isComplete = true;
		Player thePlayer = Player.getPlayer();
		int increaseGoldBy = 0;
		int increaseStatBy;
		int increaseExpBy;
		switch (this.difficulty) {
		case EASY:
			increaseGoldBy = 25;
			increaseStatBy = 1;
			increaseExpBy = 10;
			break;
		case NORMAL:
			increaseGoldBy = 60;
			increaseStatBy = 2;
			increaseExpBy = 20;
			break;
		case HARD:
			increaseGoldBy = 150;
			increaseStatBy = 5;
			increaseExpBy = 50;
			break;
		default:
			increaseStatBy = 0;
			increaseExpBy = 0;
		}

		switch (this.statType) {
		case STRENGTH:
			thePlayer.incStrength(increaseStatBy);
			break;
		case INTELLIGENCE:
			thePlayer.incInt(increaseStatBy);
			break;
		case WILL:
			thePlayer.incWill(increaseStatBy);
			break;
		case SPIRIT:
			thePlayer.incSpirit(increaseStatBy);
			break;
		default:
		}

		Reward reward = new Reward();

		reward.setGoldIncrease(increaseGoldBy);
		reward.setExpIncrease(increaseExpBy);
		reward.setStatIncrease(increaseStatBy);
		reward.setStatType(this.statType);

		thePlayer.addGold(increaseGoldBy);
		thePlayer.addExp(increaseExpBy, reward);

		return reward;
	}

	// Builder class to streamline quest creation
	// Usage: Quest myQuest = new
	// QuestBuilder("Name","Desc",difficulty,type).isComplete(true).deadline(deadline).(and
	// so on...).getQuest()
	public static class QuestBuilder {
		String name;
		String desc;
		QuestDifficulty difficulty;
		StatType type;
		boolean isComplete = false;
		DateTime deadline = null;

		public QuestBuilder(String aname, String adesc, QuestDifficulty adiff,
				StatType atype) {
			name = aname;
			desc = adesc;
			difficulty = adiff;
			type = atype;
		}

		public QuestBuilder isComplete(boolean aIsComplete) {
			isComplete = aIsComplete;
			return this;
		}

		public QuestBuilder deadline(DateTime aDeadline) {
			deadline = aDeadline;
			return this;
		}

		public Quest build() {
			return new Quest(name, desc, difficulty, type, isComplete, deadline);
		}

	}

}
