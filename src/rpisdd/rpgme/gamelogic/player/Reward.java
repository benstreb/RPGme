package rpisdd.rpgme.gamelogic.player;

import rpisdd.rpgme.gamelogic.items.Item;

public class Reward {

	private int goldIncrease = 0;
	private int expIncrease = 0;
	private StatType statType;
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
}