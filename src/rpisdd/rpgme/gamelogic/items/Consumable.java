package rpisdd.rpgme.gamelogic.items;

import rpisdd.rpgme.gamelogic.player.Player;

public class Consumable extends Item {
	public static final Consumable ENERGY_POTION = new Consumable("Energy Potion", 50,
			"potion_energy.gif", new Doer() {
				public void doIt(Player p) {
					p.addEnergy(100);
				}
			});
	
	public static final void loadConsumables() {
	}

	private final Doer action;

	protected Consumable(String name, int price, String imageName, Doer action) {
		super(name, price, imageName);
		this.action = action;
	}

	public interface Doer {
		public void doIt(Player p);
	}

	@Override
	public void useMe(Player p) {
		assert p.getInventory().getItems().contains(this);
		action.doIt(p);
		p.getInventory().removeItem(this);
	}
}
