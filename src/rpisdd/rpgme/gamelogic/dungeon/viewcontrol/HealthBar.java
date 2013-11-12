package rpisdd.rpgme.gamelogic.dungeon.viewcontrol;

import rpisdd.rpgme.gamelogic.dungeon.model.HasHealth;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

public class HealthBar extends ViewObject {

	Rect foreRect;
	Rect backRect;

	HasHealth host;

	public HealthBar(float x, float y, float width, float height, HasHealth host) {
		this.x = x;
		this.y = y;
		foreRect = new Rect(0, 0, (int) width, (int) height);
		backRect = new Rect(0, 0, (int) width, (int) height);
		this.host = host;
	}

	@Override
	public void draw(Canvas canvas) {

		int offsetX = (int) x - (backRect.width() / 2);
		int offsetY = (int) y - (backRect.height() / 2);

		int remaining = (int) (foreRect.width() * ((float) host.getEnergy() / host
				.getMaxEnergy()));

		Rect offsetForeRect = new Rect(foreRect.left + offsetX, foreRect.top
				+ offsetY, foreRect.left + offsetX + remaining, foreRect.bottom
				+ offsetY);
		Rect offsetBackRect = new Rect(backRect.left + offsetX, backRect.top
				+ offsetY, backRect.right + offsetX, backRect.bottom + offsetY);

		Paint backPaint = new Paint();
		backPaint.setColor(Color.GRAY);
		Paint forePaint = new Paint();
		forePaint.setColor(Color.GREEN);

		canvas.drawRect(offsetBackRect, backPaint);
		canvas.drawRect(offsetForeRect, forePaint);

	}
}
