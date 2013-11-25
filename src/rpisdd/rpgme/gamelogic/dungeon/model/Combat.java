package rpisdd.rpgme.gamelogic.dungeon.model;

import java.util.Random;

import rpisdd.rpgme.gamelogic.player.StatType;
import android.util.Log;

//A class to hold functions related to combat
public class Combat {

	public static double ATTACK_VARIANCE = 0;

	// Represents an attack delivered by any source
	// Holds the stattype associated with the attack
	// and the base power of that attack
	public static class Attack {
		public StatType type;
		public int power;

		public Attack(StatType t, int p) {
			type = t;
			power = p;
			Log.d("CombatDebug", "New Attack type: " + t + "\n");
			Log.d("CombatDebug", "New Attack power: " + p + "\n");
		}

	}

	// The equation to use for calculating damage
	public static int CalculateAttackDamage(int atk_, int def_) {
		Log.d("CombatDebug", "Combat: base damage: " + atk_ + "\n");
		Log.d("CombatDebug", "Combat: defense: " + def_ + "\n");

		// add range of damage to make combat for interesting
		double atk = atk_ + AttackVariance(atk_);
		double def = def_;

		// how attack and defense relate to determine damage
		atk = atk * (atk / (atk + def));
		if (atk < 1) {
			atk = 1;
		}
		Log.d("CombatDebug", "Combat: result post def: " + atk + "\n");
		return (int) (atk);
	}

	public static int AttackVariance(int atk_) {
		Random gen = new Random();

		// by dividing by 3, we force 99.7% of results
		// to be within the variance range
		double atk = atk_;
		double effectiveVariance = ATTACK_VARIANCE / 3.0;

		int variance = (int) (gen.nextGaussian() * atk * effectiveVariance);
		Log.d("CombatDebug", "Variance: " + variance + "\n");
		return variance;
	}

}
