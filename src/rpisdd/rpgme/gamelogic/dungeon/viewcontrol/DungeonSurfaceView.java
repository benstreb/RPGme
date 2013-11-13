package rpisdd.rpgme.gamelogic.dungeon.viewcontrol;

import rpisdd.rpgme.gamelogic.dungeon.model.Dungeon;
import rpisdd.rpgme.gamelogic.player.Player;
import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class DungeonSurfaceView extends SurfaceView implements
		SurfaceHolder.Callback, ThreadedSurfaceView {

	private BitmapDrawable background;
	private ViewThread thread;
	private AvatarView avatar;
	private FloorView floor;

	public DungeonSurfaceView(Context context) {
		super(context);
		init(context);
	}

	public DungeonSurfaceView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
		init(context);
	}

	public DungeonSurfaceView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(context);
	}

	private void init(Context context) {

		getHolder().addCallback(this);

		// create the game loop thread
		if (thread == null) {
			thread = new ViewThread(getHolder(), this);
		}

		/*
		 * background = new BitmapDrawable(getResources(),
		 * BitmapFactory.decodeResource(getResources(),
		 * R.drawable.test_dungeon_background));
		 */

		setFocusable(true);
	}

	public void setFloorView(Dungeon dungeon) {

		avatar = new AvatarView(0, 0, (Activity) getContext());

		floor = new FloorView(dungeon, getResources());

	}

	/**
	 * This is the game update method. It iterates through all the objects and
	 * calls their update method if they have one or calls specific engine's
	 * update method.
	 */
	@Override
	public void update() {

		floor.update();
		avatar.update(thread);

		avatar.x = FloorView.indexToCoord(Player.getPlayer().roomX);
		avatar.y = FloorView.indexToCoord(Player.getPlayer().roomY);

	}

	@Override
	public void render(Canvas canvas) {
		canvas.drawColor(Color.BLACK);

		// background.setBounds(new Rect(0, 0, 1000, 1000));

		// background.draw(canvas);
		floor.draw(canvas);
		avatar.draw(canvas);
	}

	@Override
	public void onLayout(boolean changed, int left, int top, int right,
			int bottom) {
		if (changed) {
			if (floor == null) {
				System.out.println("Waring: Floor's null!");
			} else {
				(this).layout(0, 0, (int) floor.width, (int) floor.height);
			}
		}
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		thread.setRunning(true);
		thread.start();
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		Log.d("Debug", "Surface is being destroyed");
		// tell the thread to shut down and wait for it to finish
		// this is a clean shutdown
		boolean retry = true;
		while (retry) {
			try {
				thread.setRunning(false);
				thread.join();
				// ((Activity) getContext()).finish();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			retry = false;
		}
		Log.d("Debug", "Thread was shut down cleanly");
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {

		if (event.getAction() == MotionEvent.ACTION_DOWN
				|| event.getAction() == MotionEvent.ACTION_MOVE) {
			int touchX = (int) event.getX();
			int touchY = (int) event.getY();

			// System.out.println(Integer.toString(touchX) + ","
			// + Integer.toString(touchY));

			floor.setRoomTouched(touchX, touchY, (Activity) getContext());

		}
		if (event.getAction() == MotionEvent.ACTION_UP) {
		}

		return true;
	}

}