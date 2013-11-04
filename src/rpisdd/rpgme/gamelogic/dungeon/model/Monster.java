package rpisdd.rpgme.gamelogic.dungeon.model;

import rpisdd.rpgme.gamelogic.player.StatType;

//Each monster is alligned with 1 Stat Type
//Attacks with that stat type with provided damage
//Defends against stat type with full defense, half against others
public class Monster implements RoomContent {

	private String name;
	private String imagePath;

	private int health;
	private int damage;
	private int defense;
	private StatType type;

	public Monster(String _name, String _path, int _health, int _damage,
			int _defense, StatType _type) {
		name = _name;
		imagePath = _path;
		health = _health;
		damage = _damage;
		defense = _defense;
		type = _type;
	}

	// Returns an int pair of the StatType of the attack,
	// and the base damage this monster deals
	public int[] MakeAttack() {
		int result[] = { type.getValue(), damage };
		return result;
	}

	// TODO reference damage equation so it's constant across all sources
	// TODO make a class for attacks so we don't have to assume int array
	// takes an attack, checks it against the monster's defense and returns the
	// value
	public int RecieveAttack(int attack[]) {
		int damage;
		if (attack[0] == this.type.getValue()) {
			damage = attack[1] - defense;
		} else {
			damage = attack[1] - defense / 2;
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
	public void die() {

	}

	@Override
	public boolean Encounter() {
		return false;
	}

}
