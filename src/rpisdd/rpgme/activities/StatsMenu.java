package rpisdd.rpgme.activities;

import rpisdd.rpgme.R;
import rpisdd.rpgme.gamelogic.player.Player;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class StatsMenu extends Fragment {

	public StatsMenu() {
	}

	ImageView avatarImage;
	ImageView weaponImage;
	ImageView armorImage;

	TextView strengthWidget;
	TextView intWidget;
	TextView willWidget;
	TextView spiritWidget;

	TextView weaponWidget;
	TextView armorWidget;

	TextView nameWidget;
	TextView levelWidget;
	TextView toNextWidget;
	TextView totalExpWidget;

	TextView goldWidget;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.stats_menu, container, false);

		Player thePlayer = Player.getPlayer();

		// get stats from player
		int currentStr = thePlayer.getStrength();
		int currentInt = thePlayer.getInt();
		int currentWill = thePlayer.getWill();
		int currentSpr = thePlayer.getSpirit();

		int level = thePlayer.getLevel();
		int toNext = thePlayer.getNextExp();// playerStats.toNextExp;
		int total = thePlayer.getTotalExp();

		int gold = thePlayer.getGold();
		int avatarId = thePlayer.getAvatar();

		String playerName = thePlayer.getName();

		// draw the avatar and their equipment
		avatarImage = (ImageView) v.findViewById(R.id.avatarView);
		avatarImage.setImageResource(avatarId);

		weaponImage = (ImageView) v.findViewById(R.id.weaponView);
		String weaponPath = "";
		if (thePlayer.getInventory().getWeapon() != null) {
			weaponPath = thePlayer.getInventory().getWeapon().getImagePath();
			Picasso.with(getActivity()).load(weaponPath).into(weaponImage);
		} else {
			weaponPath = "invalid.png";
			weaponImage.setVisibility(View.INVISIBLE);
		}

		armorImage = (ImageView) v.findViewById(R.id.armorView);
		String armorPath = "";
		if (thePlayer.getInventory().getArmor() != null) {
			armorPath = thePlayer.getInventory().getArmor().getImagePath();
			Picasso.with(getActivity()).load(armorPath).into(armorImage);
		} else {
			armorPath = "invalid.png";
			armorImage.setVisibility(View.INVISIBLE);
		}

		// display equipment stats
		String weaponName = "None";
		if (thePlayer.getInventory().getWeapon() != null) {
			weaponName = thePlayer.getInventory().getWeapon().getName();
		}
		weaponWidget = (TextView) v.findViewById(R.id.weaponDisplay);
		weaponWidget.setText("Weapon: " + weaponName);

		String armorName = "None";
		if (thePlayer.getInventory().getArmor() != null) {
			armorName = thePlayer.getInventory().getArmor().getName();
		}
		armorWidget = (TextView) v.findViewById(R.id.armorDisplay);
		armorWidget.setText("Armor: " + armorName);

		// display the stats
		strengthWidget = (TextView) v.findViewById(R.id.strengthDisplay);
		strengthWidget.setText("Strength: " + currentStr);

		intWidget = (TextView) v.findViewById(R.id.intDisplay);
		intWidget.setText("Int: " + currentInt);

		willWidget = (TextView) v.findViewById(R.id.willDisplay);
		willWidget.setText("Will: " + currentWill);

		spiritWidget = (TextView) v.findViewById(R.id.spiritDisplay);
		spiritWidget.setText("Spirit: " + currentSpr);

		nameWidget = (TextView) v.findViewById(R.id.nameDisplay);
		nameWidget.setText("Name: " + playerName);

		levelWidget = (TextView) v.findViewById(R.id.levelDisplay);
		levelWidget.setText("Lv: " + level);

		toNextWidget = (TextView) v.findViewById(R.id.nextExpDisplay);
		toNextWidget.setText("To Next: " + toNext);

		totalExpWidget = (TextView) v.findViewById(R.id.totalExpDisplay);
		totalExpWidget.setText("Total exp: " + total);

		goldWidget = (TextView) v.findViewById(R.id.statPageGoldDisplay);
		goldWidget.setText("Gold: " + gold);

		// Inflate the layout for this fragment
		return v;
	}
}
