package rpisdd.rpgme.gamelogic.dungeon.viewcontrol;

import android.graphics.Canvas;

public interface ThreadedSurfaceView {
	public void update();

	public void render(Canvas canvas);
}
