package rpisdd.rpgme.gamelogic.dungeon.viewcontrol;

import rpisdd.rpgme.R;
import rpisdd.rpgme.gamelogic.dungeon.model.Room;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

//Class that represents the image of a room on the canvas
public class RoomView extends ViewObject {

	public static int WIDTH = 50; // Specifies room width in pixels
	private final Room room; // The room model object of which this view object
								// uses

	private boolean isSelected = false;

	private static Bitmap roomBitmap;
	private static Bitmap monsterRoomBitmap;
	private static Bitmap treasureRoomBitmap;
	private static Bitmap stairRoomBitmap;

	private Bitmap roomIconBitmap;

	public Room getRoom() {
		return room;
	}

	public static void setWidth() {
		WIDTH = (int) (50 * ViewObject.SCALE_FACTOR);
	}

	public static void setRoomBitmap(Resources res) {

		roomBitmap = BitmapFactory.decodeResource(res, R.drawable.dungeon_room);
		roomBitmap = Bitmap.createScaledBitmap(roomBitmap, WIDTH, WIDTH, false);

		monsterRoomBitmap = BitmapFactory.decodeResource(res,
				R.drawable.monster_img);
		monsterRoomBitmap = Bitmap.createScaledBitmap(monsterRoomBitmap, WIDTH,
				WIDTH, false);

		treasureRoomBitmap = BitmapFactory.decodeResource(res,
				R.drawable.treasure_img);
		treasureRoomBitmap = Bitmap.createScaledBitmap(treasureRoomBitmap,
				WIDTH, WIDTH, false);

		stairRoomBitmap = BitmapFactory.decodeResource(res,
				R.drawable.stairs_img);
		stairRoomBitmap = Bitmap.createScaledBitmap(stairRoomBitmap, WIDTH,
				WIDTH, false);
	}

	public boolean getIsSelected() {
		return isSelected;
	}

	public void setSelected(boolean option) {
		isSelected = option;
	}

	public RoomView(Room room, Resources res) {
		this.room = room;
	}

	@Override
	public void draw(Canvas canvas) {

		float sx = x - (roomBitmap.getWidth() / 2f);
		float sy = y - (roomBitmap.getHeight() / 2f);

		canvas.drawBitmap(roomBitmap, sx, sy, null);

		if (room.getContent() != null) {

			switch (room.getContent().getRoomType()) {
			case MONSTER:
				canvas.drawBitmap(monsterRoomBitmap, sx, sy, null);
				break;
			case TREASURE:
				canvas.drawBitmap(treasureRoomBitmap, sx, sy, null);
				break;
			case STAIRS:
				canvas.drawBitmap(stairRoomBitmap, sx, sy, null);
				break;
			default:
				break;
			}

		}
		if (isSelected) {
			Paint paint = new Paint();
			paint.setColor(Color.GREEN);
			canvas.drawRect(new Rect((int) sx, (int) sy, (int) sx + WIDTH,
					(int) sy + WIDTH), paint);
		}
	}

	public boolean contains(int ax, int ay) {
		float sx = x - (roomBitmap.getWidth() / 2f);
		float sy = y - (roomBitmap.getHeight() / 2f);
		float ex = x + (roomBitmap.getWidth() / 2f);
		float ey = y + (roomBitmap.getHeight() / 2f);

		return ax >= sx && ax <= ex && ay >= sy && ay <= ey;

	}
}
