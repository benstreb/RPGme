package rpisdd.rpgme.popups;

import rpisdd.rpgme.R;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

public class LevelupPopup {

	public static void show(int levelBefore, int levelAfter, Activity activity,
			DialogInterface.OnClickListener action) {

		AlertDialog.Builder builder1 = new AlertDialog.Builder(activity);
		builder1.setMessage("Level up!");
		builder1.setCancelable(false);

		View popup = LayoutInflater.from(activity).inflate(
				R.layout.reward_popup, null);
		TextView rewardText = (TextView) popup.findViewById(R.id.rewardText);

		rewardText.setText("New level: " + Integer.toString(levelAfter));

		builder1.setView(popup);

		if (action == null) {
			builder1.setPositiveButton("OK",
					new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int id) {
							dialog.cancel();
						}
					});
		} else {
			builder1.setPositiveButton("OK", action);
		}
		AlertDialog alert11 = builder1.create();
		alert11.show();

	}

}
