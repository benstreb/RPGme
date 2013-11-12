package rpisdd.rpgme.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;

public final class AnnoyingPopup {
	private AnnoyingPopup() {
	}

	public static void doDont(Activity activity, String message,
			String doMessage, DialogInterface.OnClickListener doit) {
		AlertDialog.Builder builder1 = new AlertDialog.Builder(activity);
		builder1.setMessage(message);
		builder1.setCancelable(true);
		builder1.setPositiveButton(doMessage, doit);

		builder1.setNegativeButton("Don't " + doMessage,
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int id) {
						dialog.cancel();
					}
				});

		AlertDialog alert11 = builder1.create();
		alert11.show();
	}

	public static void notice(Activity activity, String message) {
		AlertDialog.Builder builder1 = new AlertDialog.Builder(activity);
		builder1.setMessage(message);
		builder1.setCancelable(true);
		builder1.setNeutralButton("OK", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int id) {
				dialog.cancel();
			}
		});

		AlertDialog alert11 = builder1.create();
		alert11.show();
	}

	public static void notice(Activity activity, String message,
			DialogInterface.OnClickListener doit) {
		AlertDialog.Builder builder1 = new AlertDialog.Builder(activity);
		builder1.setMessage(message);
		builder1.setCancelable(true);
		builder1.setNeutralButton("OK", doit);

		AlertDialog alert11 = builder1.create();
		alert11.show();
	}
}
