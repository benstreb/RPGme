package rpisdd.rpgme.gamelogic.dungeon.model;

import rpisdd.rpgme.activities.BattleMenu;
import rpisdd.rpgme.activities.MainActivity;
import rpisdd.rpgme.gamelogic.player.Reward;
import rpisdd.rpgme.gamelogic.player.StatType;
import android.app.Activity;

//Each monster is alligned with 1 Stat Type
//Attacks with that stat type with provided damage
//Defends against stat type with full defense, half against others
public class Monster implements RoomContent, HasHealth {

	private final String name;
	private final String imagePath;

	private int health;
	private final int maxHealth;

	private final int damage;
	private final int defense;
	private final StatType type;

	public Monster(String _name, String _path, int _health, int _damage,
			int _defense, StatType _type) {
		name = _name;
		imagePath = _path;
		health = _health;
		maxHealth = _health;
		damage = _damage;
		defense = _defense;
		type = _type;
	}

	// Returns an int pair of the StatType of the attack,
	// and the base damage this monster deals
	public Combat.Attack MakeAttack() {
		return new Combat.Attack(this.type, this.damage);
	}

	public String getImagePath() {
		return imagePath;
	}

	// TODO make a class for attacks so we don't have to assume int array
	// takes an attack, checks it against the monster's defense and returns the
	// value
	public int RecieveAttack(Combat.Attack atk) {
		int damage;
		if (atk.type == this.type) {
			damage = Combat.CalculateAttackDamage(atk.power, defense);
		} else {
			damage = Combat.CalculateAttackDamage(atk.power, defense / 2);
		}
		return damage;
	}

	// Damages the monster and returns weather the monster died or not
	public boolean RecieveDamage(int damage) {
		this.health -= damage;
		if (health < 1) {
			die();
			return true;
		}
		return false;
	}

	//
	public Reward die() {
		Reward reward = new Reward();
		reward.setGoldIncrease(10);
		return reward;
	}

	@Override
	public boolean Encounter(Activity activity) {

		// Switch fragments to Battle fragment
		BattleMenu battle = new BattleMenu();

		((MainActivity) activity).enterBattle();

		return false;
	}

	@Override
	public RoomType getRoomType() {
		return RoomType.MONSTER;
	}

	@Override
	public int getEnergy() {
		return health;
	}

	@Override
	public int getMaxEnergy() {
		return maxHealth;
	}
}
