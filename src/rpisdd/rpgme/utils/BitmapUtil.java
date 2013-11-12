package rpisdd.rpgme.utils;

import java.io.IOException;
import java.io.InputStream;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class BitmapUtil {

	// Takes a path in Assets folder, and converts it into a bitmap.
	public static Bitmap getBitmapFromAsset(Context context, String strName) {

		// Remove assets prefix of the file name
		String stripped = strName.substring(22);

		System.out.println(stripped);

		AssetManager assetManager = context.getAssets();

		InputStream istr;
		Bitmap bitmap = null;
		try {
			istr = assetManager.open(stripped);
			bitmap = BitmapFactory.decodeStream(istr);
		} catch (IOException e) {
			return null;
		}

		return bitmap;
	}
}
