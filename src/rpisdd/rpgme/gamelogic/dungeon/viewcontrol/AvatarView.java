package rpisdd.rpgme.gamelogic.dungeon.viewcontrol;

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

	private DamageText damageText;

	public AvatarView(float x, float y, boolean isMini, Activity activity) {

		super(x, y);

		bitmap = BitmapFactory.decodeResource(activity.getResources(), Player
				.getPlayer().getAvatar());

		if (Player.getPlayer().getInventory().getArmor() != null) {
			String armorPath = Player.getPlayer().getInventory().getArmor()
					.getOffsetImagePath();
			armorBitmap = BitmapUtil.getBitmapFromAsset(activity, armorPath);
		}
		if (Player.getPlayer().getInventory().getWeapon() != null) {
			String weaponPath = Player.getPlayer().getInventory().getWeapon()
					.getOffsetImagePath();
			weaponBitmap = BitmapUtil.getBitmapFromAsset(activity, weaponPath);
		}

		float scaleFactor = 0;
		if (isMini) {
			scaleFactor = (RoomView.WIDTH * 0.8f) / bitmap.getHeight();
		} else {
			scaleFactor = 0.5f * SCALE_FACTOR * (200f / bitmap.getHeight());
		}

		setScale(scaleFactor);
	}

	public void setDamageText(int damage) {
		damageText = new DamageText(x, y - (getHeight() / 2f), damage, 1, 20);
	}

	@Override
	public void update(ViewThread thread) {
		if (damageText != null) {
			damageText.update(thread);
			if (damageText.getDestroySelf()) {
				damageText = null;
			}
		}
	}

	@Override
	public void setScale(float ascale) {

		int srcWidth = bitmap.getWidth();
		int srcHeight = bitmap.getHeight();
		int dstWidth = (int) (srcWidth * ascale);
		int dstHeight = (int) (srcHeight * ascale);

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
		if (damageText != null) {
			damageText.draw(canvas);
		}
	}

}
