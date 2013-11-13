package rpisdd.rpgme.gamelogic.dungeon.viewcontrol;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class DamageText extends ViewObject {

	public float maxTime;
	public float timer;
	public float floatDist;
	public String damage;
	public float startY;
	Paint paint;
	public boolean destroySelf;

	public DamageText(float x, float y, int damage, float maxTime,
			float floatDist) {
		this.x = x;
		this.y = y;
		this.startY = y;
		this.damage = Integer.toString(damage);
		this.maxTime = maxTime;
		this.floatDist = floatDist;

		paint = new Paint();
		paint.setColor(Color.RED);
		paint.setTextSize(12);
	}

	@Override
	public void draw(Canvas canvas) {
		// System.out.println("Damage text drawn");
		canvas.drawText(damage, x, y, paint);
	}

	@Override
	public void update(ViewThread thread) {
		float deltaTime = thread.deltaTime();
		timer += deltaTime;
		if (timer > maxTime) {
			destroySelf = true;
		}

		y -= (floatDist * (deltaTime / maxTime));

		// paint.setAlpha((int) (255f * (timer / maxTime)));

		// System.out.format("y:%f , alpha:%d", y, paint.getAlpha());
	}

}
