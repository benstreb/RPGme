package rpisdd.rpgme.activities;

import rpisdd.rpgme.R;
import rpisdd.rpgme.gamelogic.items.Equipment;
import rpisdd.rpgme.gamelogic.player.Player;
import rpisdd.rpgme.gamelogic.player.Stats;
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

	TextView strengthAtkWidget;
	TextView intAtkWidget;
	TextView willAtkWidget;
	TextView spiritAtkWidget;

	TextView strengthDefWidget;
	TextView intDefWidget;
	TextView willDefWidget;
	TextView spiritDefWidget;

	TextView weaponWidget;
	TextView armorWidget;

	TextView nameWidget;
	TextView levelWidget;
	TextView expWidget;

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
		int toNext = thePlayer.getExpForLevel(level);// playerStats.toNextExp;
		int total = thePlayer.getTotalExp();

		int gold = thePlayer.getGold();
		int avatarId = thePlayer.getAvatar();

		String playerName = thePlayer.getName();
		Equipment weapon = thePlayer.getInventory().getWeapon();
		Equipment armor = thePlayer.getInventory().getArmor();

		// draw the avatar and their equipment
		avatarImage = (ImageView) v.findViewById(R.id.avatarView);
		avatarImage.setImageResource(avatarId);

		weaponImage = (ImageView) v.findViewById(R.id.weaponView);
		String weaponPath = "";
		if (weapon != null) {
			weaponPath = weapon.getImagePath();
			Picasso.with(getActivity()).load(weaponPath).into(weaponImage);
		} else {
			weaponPath = "invalid.png";
			weaponImage.setVisibility(View.INVISIBLE);
		}

		armorImage = (ImageView) v.findViewById(R.id.armorView);
		String armorPath = "";
		if (armor != null) {
			armorPath = armor.getImagePath();
			Picasso.with(getActivity()).load(armorPath).into(armorImage);
		} else {
			armorPath = "invalid.png";
			armorImage.setVisibility(View.INVISIBLE);
		}
		// display the stats
		strengthWidget = (TextView) v.findViewById(R.id.strengthDisplay);
		strengthWidget.setText("Strength: " + currentStr);

		intWidget = (TextView) v.findViewById(R.id.intelligenceDisplay);
		intWidget.setText("Intelligence: " + currentInt);

		willWidget = (TextView) v.findViewById(R.id.willDisplay);
		willWidget.setText("Will: " + currentWill);

		spiritWidget = (TextView) v.findViewById(R.id.spiritDisplay);
		spiritWidget.setText("Spirit: " + currentSpr);

		nameWidget = (TextView) v.findViewById(R.id.nameDisplay);
		nameWidget.setText(playerName);

		levelWidget = (TextView) v.findViewById(R.id.levelDisplay);
		levelWidget.setText("Level: " + level);

		expWidget = (TextView) v.findViewById(R.id.expDisplay);
		expWidget.setText(total + " / " + toNext + " exp");

		goldWidget = (TextView) v.findViewById(R.id.statPageGoldDisplay);

		goldWidget.setText("" + gold);

		// display the weapon stats
		if (weapon != null) {
			Stats.Mod weapon_mod = weapon.getMod();

			strengthAtkWidget = (TextView) v
					.findViewById(R.id.strengthAtkDisplay);
			strengthAtkWidget.setText("+" + weapon_mod.str);

			intAtkWidget = (TextView) v
					.findViewById(R.id.intelligenceAtkDisplay);
			intAtkWidget.setText("+" + weapon_mod.intel);

			willAtkWidget = (TextView) v.findViewById(R.id.willAtkDisplay);
			willAtkWidget.setText("+" + weapon_mod.will);

			spiritAtkWidget = (TextView) v.findViewById(R.id.spiritAtkDisplay);
			spiritAtkWidget.setText("+" + weapon_mod.spirit);
		} else {
			strengthAtkWidget = (TextView) v
					.findViewById(R.id.strengthAtkDisplay);
			strengthAtkWidget.setText("+0");

			intAtkWidget = (TextView) v
					.findViewById(R.id.intelligenceAtkDisplay);
			intAtkWidget.setText("+0");

			willAtkWidget = (TextView) v.findViewById(R.id.willAtkDisplay);
			willAtkWidget.setText("+0");

			spiritAtkWidget = (TextView) v.findViewById(R.id.spiritAtkDisplay);
			spiritAtkWidget.setText("+0");
		}

		// display the armor stats
		if (armor != null) {
			Stats.Mod armor_mod = armor.getMod();

			strengthDefWidget = (TextView) v
					.findViewById(R.id.strengthDefDisplay);
			strengthDefWidget.setText("+" + armor_mod.str);

			intDefWidget = (TextView) v
					.findViewById(R.id.intelligenceDefDisplay);
			intDefWidget.setText("+" + armor_mod.intel);

			willDefWidget = (TextView) v.findViewById(R.id.willDefDisplay);
			willDefWidget.setText("+" + armor_mod.will);

			spiritDefWidget = (TextView) v.findViewById(R.id.spiritDefDisplay);
			spiritDefWidget.setText("+" + armor_mod.spirit);
		} else {
			strengthDefWidget = (TextView) v
					.findViewById(R.id.strengthDefDisplay);
			strengthDefWidget.setText("+0");

			intDefWidget = (TextView) v
					.findViewById(R.id.intelligenceDefDisplay);
			intDefWidget.setText("+0");

			willDefWidget = (TextView) v.findViewById(R.id.willDefDisplay);
			willDefWidget.setText("+0");

			spiritDefWidget = (TextView) v.findViewById(R.id.spiritDefDisplay);
			spiritDefWidget.setText("+0");
		}

		// Inflate the layout for this fragment
		return v;
	}
}
