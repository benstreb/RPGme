package rpisdd.rpgme.gamelogic.dungeon.viewcontrol;

import rpisdd.rpgme.R;
import rpisdd.rpgme.gamelogic.dungeon.model.Room;
import rpisdd.rpgme.gamelogic.dungeon.model.Treasure;
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
	private static Bitmap openedTreasureBitmap;
	private static Bitmap unknownRoomBitmap;

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

		openedTreasureBitmap = BitmapFactory.decodeResource(res,
				R.drawable.treasure_inactive2_img);
		openedTreasureBitmap = Bitmap.createScaledBitmap(openedTreasureBitmap,
				WIDTH, WIDTH, false);

		stairRoomBitmap = BitmapFactory.decodeResource(res,
				R.drawable.stairs_img);
		stairRoomBitmap = Bitmap.createScaledBitmap(stairRoomBitmap, WIDTH,
				WIDTH, false);

		unknownRoomBitmap = BitmapFactory.decodeResource(res,
				R.drawable.ic_mystery);
		unknownRoomBitmap = Bitmap.createScaledBitmap(unknownRoomBitmap, WIDTH,
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

		if (room.getCanVisit()) {

			canvas.drawBitmap(roomBitmap, sx, sy, null);

			if (room.getContent() != null) {
				/*
				 * Stairs now ask if you want to take them. No longer need to
				 * show them before visiting.
				 */
				/*
				 * if (room.getContent().getRoomType() == RoomType.STAIRS) {
				 * canvas.drawBitmap(stairRoomBitmap, sx, sy, null); } else
				 */

				switch (room.getContent().getRoomType()) {
				case STAIRS:
					canvas.drawBitmap(stairRoomBitmap, sx, sy, null);
					break;
				case MONSTER:
					canvas.drawBitmap(monsterRoomBitmap, sx, sy, null);
					break;
				case TREASURE:
					if (!((Treasure) room.getContent()).getIsOpened()) {
						canvas.drawBitmap(treasureRoomBitmap, sx, sy, null);
					} else {
						canvas.drawBitmap(openedTreasureBitmap, sx, sy, null);
					}
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
	}

	public boolean contains(int ax, int ay) {

		if (!room.getCanVisit()) {
			return false;
		}

		float sx = x - (roomBitmap.getWidth() / 2f);
		float sy = y - (roomBitmap.getHeight() / 2f);
		float ex = x + (roomBitmap.getWidth() / 2f);
		float ey = y + (roomBitmap.getHeight() / 2f);

		return ax >= sx && ax <= ex && ay >= sy && ay <= ey;

	}
}
