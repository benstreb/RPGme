package rpisdd.rpgme.gamelogic.dungeon.viewcontrol;

import rpisdd.rpgme.gamelogic.dungeon.model.Dungeon;
import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

//Class that represents the view of the dungeon, drawn on the canvas.
public class FloorView {

	Dungeon dungeon;
	RoomView[][] rooms;

	float width;
	float height;

	RoomView selectedRoom;

	public FloorView(Dungeon dungeon, Resources res) {

		this.dungeon = dungeon;

		RoomView.setRoomBitmap(res);

		rooms = new RoomView[Dungeon.DUNGEON_DIMMENSION][Dungeon.DUNGEON_DIMMENSION];

		for (int i = 0; i < Dungeon.DUNGEON_DIMMENSION; i++) {
			for (int j = 0; j < Dungeon.DUNGEON_DIMMENSION; j++) {
				if (dungeon.getMap()[i][j] != null) {
					rooms[i][j] = new RoomView(dungeon.getMap()[i][j], res);
					rooms[i][j].setX(indexToCoord(j));
					rooms[i][j].setY(indexToCoord(i));
				}
			}
		}

		width = indexToCoord(Dungeon.DUNGEON_DIMMENSION)
				- (0.5f * RoomView.WIDTH);
		height = indexToCoord(Dungeon.DUNGEON_DIMMENSION)
				- (0.5f * RoomView.WIDTH);

	}

	public boolean setRoomTouched(int x, int y, AvatarView avatar,
			Activity activity) {
		// Right now, loops through entire array.
		// Could be more efficient
		for (int i = 0; i < Dungeon.DUNGEON_DIMMENSION; i++) {
			for (int j = 0; j < Dungeon.DUNGEON_DIMMENSION; j++) {
				if (rooms[i][j] != null && rooms[i][j].contains(x, y)) {

					if (selectedRoom != null) {
						selectedRoom.setSelected(false);
					}

					rooms[i][j].setSelected(true);
					selectedRoom = rooms[i][j];

					avatar.x = indexToCoord(j);
					avatar.y = indexToCoord(i);

					dungeon.visitRoom(j, i, activity);

					return true;
				}
			}
		}
		return false;
	}

	public void update() {

	}

	public void draw(Canvas canvas) {
		for (int i = 0; i < Dungeon.DUNGEON_DIMMENSION; i++) {
			for (int j = 0; j < Dungeon.DUNGEON_DIMMENSION; j++) {
				if (rooms[i][j] != null && rooms[i][j].getRoom().getCanVisit()) {

					rooms[i][j].draw(canvas);

					float sx, sy, ex, ey = 0;

					Paint paint = new Paint();
					paint.setColor(Color.WHITE);

					// Draw connecting lines

					// Room to the right:
					if (dungeon.roomExists(j + 1, i)
							&& rooms[i][j + 1].getRoom().getCanVisit()) {
						sx = rooms[i][j].x + (RoomView.WIDTH / 2f);
						sy = indexToCoord(i);
						ex = indexToCoord(j + 1) - (RoomView.WIDTH / 2f);
						ey = indexToCoord(i);
						canvas.drawLine(sx, sy, ex, ey, paint);
					}
					// Room to the left:
					if (dungeon.roomExists(j - 1, i)
							&& rooms[i][j - 1].getRoom().getCanVisit()) {
						sx = rooms[i][j].x - (RoomView.WIDTH / 2f);
						sy = indexToCoord(i);
						ex = indexToCoord(j - 1) + (RoomView.WIDTH / 2f);
						ey = indexToCoord(i);
						canvas.drawLine(sx, sy, ex, ey, paint);
					}
					// Room below:
					if (dungeon.roomExists(j, i + 1)
							&& rooms[i + 1][j].getRoom().getCanVisit()) {
						sx = indexToCoord(j);
						sy = rooms[i][j].y + (RoomView.WIDTH / 2f);
						ex = indexToCoord(j);
						ey = indexToCoord(i + 1) - (RoomView.WIDTH / 2f);
						canvas.drawLine(sx, sy, ex, ey, paint);
					}
					// Room above:
					if (dungeon.roomExists(j, i - 1)
							&& rooms[i - 1][j].getRoom().getCanVisit()) {
						sx = indexToCoord(j);
						sy = rooms[i][j].y - (RoomView.WIDTH / 2f);
						ex = indexToCoord(j);
						ey = indexToCoord(i - 1) + (RoomView.WIDTH / 2f);
						canvas.drawLine(sx, sy, ex, ey, paint);
					}

				}
			}
		}
	}

	public static float indexToCoord(int index) {
		return RoomView.WIDTH * ((index * 2) + 1.5f);
	}

}
