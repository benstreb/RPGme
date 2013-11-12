package rpisdd.rpgme.gamelogic.dungeon.viewcontrol;

import rpisdd.rpgme.R;
import rpisdd.rpgme.gamelogic.player.Player;
import rpisdd.rpgme.utils.BitmapUtil;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

//Class that represents the image of the avatar on the canvas
public class AvatarView extends ViewObject {

	Bitmap weaponBitmap;
	Bitmap armorBitmap;

	public AvatarView(float x, float y, Activity activity) {

		super(x, y);

		bitmap = BitmapFactory.decodeResource(activity.getResources(),
				R.drawable.av_f1_avatar);

		if (Player.getPlayer().getInventory().getArmor() != null) {
			String armorPath = Player.getPlayer().getInventory().getArmor()
					.getImagePath();
			armorBitmap = BitmapUtil.getBitmapFromAsset(activity, armorPath);

		}
		if (Player.getPlayer().getInventory().getWeapon() != null) {
			String weaponPath = Player.getPlayer().getInventory().getWeapon()
					.getImagePath();
			weaponBitmap = BitmapUtil.getBitmapFromAsset(activity, weaponPath);
		}

		float scaleFactor = (RoomView.WIDTH * 0.8f) / bitmap.getHeight();

		setScale(scaleFactor);
	}

	@Override
	public void update() {

	}

	@Override
	public void setScale(float ascale) {

		scale = ascale;

		int srcWidth = bitmap.getWidth();
		int srcHeight = bitmap.getHeight();
		int dstWidth = (int) (srcWidth * ascale);
		int dstHeight = (int) (srcHeight * ascale);

		bitmap = Bitmap.createScaledBitmap(bitmap, dstWidth, dstHeight, true);

		bitmap = Bitmap.createScaledBitmap(bitmap, dstWidth, dstHeight, true);

		if (armorBitmap != null) {

			srcWidth = armorBitmap.getWidth();
			srcHeight = armorBitmap.getHeight();
			dstWidth = (int) (srcWidth * ascale);
			dstHeight = (int) (srcHeight * ascale);

			armorBitmap = Bitmap.createScaledBitmap(armorBitmap, dstWidth,
					dstHeight, false);
		}

		if (weaponBitmap != null) {

			srcWidth = weaponBitmap.getWidth();
			srcHeight = weaponBitmap.getHeight();
			dstWidth = (int) (srcWidth * ascale);
			dstHeight = (int) (srcHeight * ascale);

			weaponBitmap = Bitmap.createScaledBitmap(weaponBitmap, dstWidth,
					dstHeight, false);
		}

	}

	@Override
	public void draw(Canvas canvas) {
		canvas.drawBitmap(bitmap, x - (bitmap.getWidth() / 2f),
				y - (bitmap.getHeight() / 2f), null);

		if (weaponBitmap != null) {
			canvas.drawBitmap(weaponBitmap, x - (bitmap.getWidth() / 2f), y
					- (bitmap.getHeight() / 2f), null);
		}
		if (armorBitmap != null) {
			canvas.drawBitmap(armorBitmap, x - (bitmap.getWidth() / 2f), y
					- (bitmap.getHeight() / 2f), null);
		}
	}

}
