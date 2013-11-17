package rpisdd.rpgme.gamelogic.dungeon.model;

import java.util.ArrayList;

import rpisdd.rpgme.activities.BattleMenu;
import rpisdd.rpgme.activities.MainActivity;
import rpisdd.rpgme.activities.TransitionFragment;
import rpisdd.rpgme.gamelogic.player.Player;
import rpisdd.rpgme.gamelogic.player.Reward;
import rpisdd.rpgme.gamelogic.player.StatType;
import android.app.Activity;
import android.content.Context;

//Each monster is alligned with 1 Stat Type
//Attacks with that stat type with provided damage
//Defends against stat type with full defense, half against others
public class Monster implements RoomContent, HasHealth {

	private final String name;
	private final String imageName;

	private int health;
	private final int maxHealth;

	private final int damage;
	private final int defense;
	private final StatType type;

	private static final ArrayList<Monster> monsters = new ArrayList<Monster>();

	private Monster(String _name, String _imageName, int _health, int _damage,
			int _defense, StatType _type) {
		this(_name, _imageName, _health, _health, _damage, _defense, _type);
	}

	public Monster(String _name, String _imageName, int _health,
			int _maxHealth, int _damage, int _defense, StatType _type) {
		name = _name;
		imageName = _imageName;
		health = _health;
		maxHealth = _maxHealth;
		damage = _damage;
		defense = _defense;
		type = _type;
	}

	private Monster copy() {
		return new Monster(name, imageName, health, maxHealth, damage, defense,
				type);
	}

	// Returns an int pair of the StatType of the attack,
	// and the base damage this monster deals
	public Combat.Attack MakeAttack() {
		return new Combat.Attack(this.type, this.damage);
	}

	public String getImagePath() {
		return "file:///android_asset/Monsters/" + imageName;
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
			health = 0;
			die();
			return true;
		}
		return false;
	}

	//
	public Reward die() {

		Player.getPlayer().clearCurrentRoom();

		Reward reward = new Reward();
		reward.setGoldIncrease(10);
		return reward;
	}

	@Override
	public boolean Encounter(Activity activity) {
		BattleMenu battleMenu = new BattleMenu();
		battleMenu.setMonster(this);

		TransitionFragment trans = new TransitionFragment();
		trans.setValues(battleMenu, false);
		((MainActivity) activity).changeFragment(trans);

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

	@Override
	public String getStringRepresentation() {
		return "MONSTER" + "," + this.name + "," + imageName + "," + health
				+ "," + maxHealth + "," + damage + "," + defense + "," + type;
	}

	public static void load(Context c) {
		monsters.add(new Monster("Slime", "slime.png", 10, 1, 10, StatType.WILL));
	}

	public static Monster selectMonster(Dungeon d) {
		return monsters.get(0).copy();
	}
}