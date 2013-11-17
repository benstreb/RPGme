package rpisdd.rpgme.gamelogic.player;

import rpisdd.rpgme.gamelogic.dungeon.model.Monster;
import rpisdd.rpgme.gamelogic.items.Item;
import rpisdd.rpgme.gamelogic.quests.Quest;

public class Reward {

	private int goldIncrease = 0;
	private int expIncrease = 0;
	private StatType statType = StatType.ERROR;
	private int statIncrease = 0;
	private int energyGained = 0;
	private int newLevel = 0;
	private boolean isLevelUp = false;
	private Item rewardItem;

	public int getGoldIncrease() {
		return goldIncrease;
	}

	public int getExpIncrease() {
		return expIncrease;
	}

	public int getStatIncrease() {
		return statIncrease;
	}

	public int getEnergyGained() {
		return energyGained;
	}

	public int getNewLevel() {
		return newLevel;
	}

	public StatType getStatType() {
		return statType;
	}

	public boolean getIsLevelUp() {
		return isLevelUp;
	}

	public Item getRewardItem() {
		return rewardItem;
	}

	public void setGoldIncrease(int goldIncrease) {
		this.goldIncrease = goldIncrease;
	}

	public void setExpIncrease(int expIncrease) {
		this.expIncrease = expIncrease;
	}

	public void setStatIncrease(int statIncrease) {
		this.statIncrease = statIncrease;
	}

	public void setEnergyGained(int energyGained) {
		this.energyGained = energyGained;
	}

	public void setNewLevel(int newLevel) {
		this.newLevel = newLevel;
	}

	public void setStatType(StatType statType) {
		this.statType = statType;
	}

	public void setIsLevelUp(boolean isLevelUp) {
		this.isLevelUp = isLevelUp;
	}

	public void setRewardItem(Item rewardItem) {
		this.rewardItem = rewardItem;
	}

	public Reward() {
	}

	// Give a reward after killing a monster based on that monster
	public static Reward monsterReward(Monster monster) {
		Reward r = new Reward();
		r.setExpIncrease(5);
		r.setGoldIncrease(25);
		return r;
	}

	// Give a reward after completing a quest based on that quest
	public static Reward questReward(Quest quest) {

		Reward reward = new Reward();

		int increaseGoldBy = 0;
		int increaseStatBy = 0;
		int increaseExpBy = 0;

		switch (quest.getDifficulty()) {
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

		reward.setGoldIncrease(increaseGoldBy);
		reward.setExpIncrease(increaseExpBy);
		reward.setStatIncrease(increaseStatBy);
		reward.setStatType(quest.getStatType());

		return reward;

	}

	// Apply this reward to the player
	public void applyReward() {

		Player.getPlayer().addGold(goldIncrease);
		Player.getPlayer().addExp(expIncrease);
		Player.getPlayer().getInventory().addItem(rewardItem);

		switch (statType) {
		case STRENGTH:
			Player.getPlayer().incStrength(statIncrease);
			break;
		case SPIRIT:
			Player.getPlayer().incSpirit(statIncrease);
			break;
		case INTELLIGENCE:
			Player.getPlayer().incInt(statIncrease);
			break;
		case WILL:
			Player.getPlayer().incWill(statIncrease);
			break;
		default:
			break;
		}

	}
}