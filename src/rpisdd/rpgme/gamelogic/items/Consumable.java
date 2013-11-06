package rpisdd.rpgme.gamelogic.items;

import rpisdd.rpgme.gamelogic.player.Player;
import android.util.Log;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public final class Consumable extends Item {
	private static Doer doerFromJsonArray(JsonArray doesWhat) {
		String doerType = doesWhat.get(0).getAsString();
		if (doerType.equals("heal energy")) {
			return new EnergyHealer(doesWhat.get(1).getAsInt()) {
				@Override
				public boolean isUsable(Player p) {
					return p.getEnergy() > 0;
				}
			};
		} else if (doerType.equals("revive")) {
			return new EnergyHealer(doesWhat.get(1).getAsInt()) {
				@Override
				public boolean isUsable(Player p) {
					return p.getEnergy() <= 0;
				}
			};
		} else {
			Log.wtf("items", "Invalid kind of consumable");
			return new InvalidDoer();
		}
	}

	private final Doer action;

	protected Consumable(JsonObject o) {
		super(o);
		this.action = doerFromJsonArray(o.get("effect").getAsJsonArray());
	}

	@Override
	public boolean isUsable(Player p) {
		return action.isUsable(p);
	}

	@Override
	public void useMe(Player p, int index) {
		assert p.getInventory().getItems().get(index) == this;
		action.doIt(p);
		p.getInventory().removeAt(index);
	}
}

interface Doer {
	public void doIt(Player p);

	public boolean isUsable(Player p);
}

abstract class EnergyHealer implements Doer {
	private final float healFactor;

	public EnergyHealer(int percent) {
		this.healFactor = ((float) percent) / percent;
	}

	@Override
	public void doIt(Player p) {
		p.addEnergy((int) (p.getMaxEnergy() * healFactor));
	}
}

final class InvalidDoer implements Doer {
	@Override
	public void doIt(Player p) {
		Log.wtf("items", "Using an invalid consumable.");
	}

	@Override
	public boolean isUsable(Player p) {
		return false;
	}
}