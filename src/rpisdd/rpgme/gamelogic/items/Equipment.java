package rpisdd.rpgme.gamelogic.items;

import rpisdd.rpgme.gamelogic.player.Player;
import rpisdd.rpgme.gamelogic.player.Stats;
import android.util.Log;

import com.google.gson.JsonObject;

public final class Equipment extends Item {
	public static enum Type {
		WEAPON, ARMOR;
		public static Type fromString(String s) {
			if (s.equals("weapon")) {
				return WEAPON;
			} else if (s.equals("armor")) {
				return ARMOR;
			} else {
				throw new RuntimeException("Invalid type of equipment");
			}
		}
	}

	final Stats.Mod statMod;
	final Type type;

	protected Equipment(JsonObject o) {
		super(o);
		this.type = Type.fromString(o.get("type").getAsString());
		this.statMod = Stats.Mod.fromJsonObject(o.get("effect")
				.getAsJsonObject());
	}

	@Override
	public void useMe(Player p, int index) {
		if (p.getInventory().getItems().get(index) != this) {
			Log.wtf("items", "Item not in inventory before using it.");
		}
		if (type == Type.WEAPON) {
			Equipment oldWeapon = p.getInventory().equipWeapon(this);
			if (oldWeapon != null) {
				p.getInventory().addItem(oldWeapon);
			}
		} else if (type == Type.ARMOR) {
			Equipment oldArmor = p.getInventory().equipArmor(this);
			if (oldArmor != null) {
				p.getInventory().addItem(oldArmor);
			}
		}
		p.getInventory().removeAt(index);
	}

	@Override
	public boolean isUsable(Player p) {
		return true;
	}

	@Override
	public boolean isEquipment() {
		return true;
	}

	public Stats.Mod getMod() {
		return this.statMod;
	}

}
