package rpisdd.rpgme.gamelogic.dungeon.viewcontrol;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Point;
import android.view.Display;
import android.view.WindowManager;

//Superclass of all objects drawn on the screen.
public class ViewObject {

	protected Bitmap bitmap; // the actual bitmap
	protected float x; // the X coordinate
	protected float y; // the Y coordinate

	protected float scale = 1;
	protected float angle;

	public static float SCALE_FACTOR = 1;

	public static void setScaleFactor(Context context) {

		WindowManager wm = (WindowManager) context
				.getSystemService(Context.WINDOW_SERVICE);
		Display display = wm.getDefaultDisplay();
		Point size = new Point();
		display.getSize(size);

		SCALE_FACTOR = (size.y / 480f);
	}

	public ViewObject() {
	}

	public ViewObject(Bitmap bitmap) {
		this.bitmap = bitmap;
	}

	public ViewObject(float x, float y) {
		this.x = x;
		this.y = y;
	}

	public ViewObject(Bitmap bitmap, float x, float y) {
		this.bitmap = bitmap;
		this.x = x;
		this.y = y;
	}

	public Bitmap getBitmap() {
		return bitmap;
	}

	public void setBitmap(Bitmap bitmap, float ascale) {
		this.bitmap = bitmap;
		setScale(ascale);
	}

	public float getScale() {
		return scale;
	}

	public void setScale(float ascale) {

		scale = ascale;

		int srcWidth = bitmap.getWidth();
		int srcHeight = bitmap.getHeight();
		int dstWidth = (int) (srcWidth * ascale);
		int dstHeight = (int) (srcHeight * ascale);

		if (bitmap != null) {
			bitmap = Bitmap.createScaledBitmap(bitmap, dstWidth, dstHeight,
					true);
		}

	}

	public float getWidth() {
		if (bitmap != null) {
			return bitmap.getWidth();
		} else {
			return 0;
		}
	}

	public float getHeight() {
		if (bitmap != null) {
			return bitmap.getHeight();
		} else {
			return 0;
		}
	}

	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}

	public void draw(Canvas canvas) {
		canvas.drawBitmap(bitmap, x - (bitmap.getWidth() / 2f),
				y - (bitmap.getHeight() / 2f), null);
	}

	public void update(ViewThread thread) {
	}

}
