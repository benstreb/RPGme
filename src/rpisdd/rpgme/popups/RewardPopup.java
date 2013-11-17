package rpisdd.rpgme.popups;

import rpisdd.rpgme.R;
import rpisdd.rpgme.gamelogic.player.Player;
import rpisdd.rpgme.gamelogic.player.Reward;
import rpisdd.rpgme.gamelogic.player.StatType;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

public final class RewardPopup {

	public static void show(String message, Reward reward,
			final Activity activity,
			final DialogInterface.OnClickListener action) {

		AlertDialog.Builder builder1 = new AlertDialog.Builder(activity);
		builder1.setMessage(message);
		builder1.setCancelable(false);

		View popup = LayoutInflater.from(activity).inflate(
				R.layout.reward_popup, null);
		TextView rewardText = (TextView) popup.findViewById(R.id.rewardText);

		final int levelBefore = Player.getPlayer().getLevel();
		int statBefore = Player.getPlayer().getStat(reward.getStatType());

		reward.applyReward();

		final int levelAfter = Player.getPlayer().getLevel();
		int statAfter = Player.getPlayer().getStat(reward.getStatType());

		String text = "";
		text += "You gained " + reward.getExpIncrease()
				+ " experience points.\n";

		text += "You gained " + reward.getGoldIncrease() + " gold.\n";

		if (reward.getStatType() != StatType.ERROR) {
			text += reward.getStatType().toString() + " increased: "
					+ Integer.toString(statBefore) + " > "
					+ Integer.toString(statAfter) + "\n";
		}

		if (reward.getRewardItem() != null) {
			text += "You found an " + reward.getRewardItem().getName() + "\n";
		}

		rewardText.setText(text);

		final boolean leveledUp = levelAfter > levelBefore;

		builder1.setView(popup);

		if (action == null) {
			builder1.setPositiveButton("OK",
					new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int id) {

							if (leveledUp) {
								LevelupPopup.show(levelBefore, levelAfter,
										activity, null);
							}

							dialog.cancel();
						}
					});
		} else {

			if (leveledUp) {
				builder1.setPositiveButton("OK",
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int id) {

								LevelupPopup.show(levelBefore, levelAfter,
										activity, action);

								dialog.cancel();
							}
						});
			} else {
				builder1.setPositiveButton("OK", action);
			}
		}
		AlertDialog alert11 = builder1.create();
		alert11.show();

	}

}
