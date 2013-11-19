package rpisdd.rpgme.gamelogic.dungeon.model;

import rpisdd.rpgme.gamelogic.player.Player;
import android.app.Activity;
import android.util.Log;

public class Room {
	boolean visited;
	boolean canVisit;
	RoomContent content;

	private final int x;
	private final int y;

	public Room(RoomContent content_, int x_, int y_, boolean visited_,
			boolean canVist_) {
		this.visited = visited_;
		this.canVisit = canVist_;
		this.content = content_;
		x = x_;
		y = y_;
	}

	public Room(RoomContent content_, int x_, int y_) {
		this(content_, x_, y_, false, false);
	}

	public Room(RoomContent content_) {
		this(content_, 0, 0);
	}

	public boolean visit(Activity activity) {

		this.setVisitable();
		this.visited = true;
		Player.getPlayer().setRoomPos(x, y);

		if (this.hasContent() && activity != null) {
			return this.content.Encounter(activity);
		}
		return true;
	}

	public void setVisitable() {
		this.canVisit = true;
	}

	public void clearContent() {
		this.content = null;
	}

	public boolean hasContent() {
		return this.content != null;
	}

	public RoomContent getContent() {
		return this.content;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public String getStringRepresentation() {
		String contentRep;
		if (this.content == null) {
			contentRep = "NONE";
		} else {
			contentRep = this.content.getStringRepresentation();
		}
		return "ROOM" + "#" + this.visited + "#" + this.canVisit + "#" + this.x
				+ "#" + this.y + "#" + contentRep;
	}

	public static Room getFromStringRepresentation(String[] roomArgs) {
		if (roomArgs.length < 5) {
			Log.e("Load", "FAILED DUGEON LOAD. NUM ROOM ARGS: "
					+ roomArgs.length + "\n");
			return null;
		}
		boolean visited_ = Boolean.parseBoolean(roomArgs[1]);
		boolean canVisit_ = Boolean.parseBoolean(roomArgs[2]);
		int x_ = Integer.parseInt(roomArgs[3]);
		int y_ = Integer.parseInt(roomArgs[4]);

		// figure out content
		String[] contentArgs = roomArgs[5].split(",");
		RoomContent newContent = null;
		Log.d("Debug", "Content string: " + roomArgs[5] + "\n");
		String type = contentArgs[0];
		Log.d("Debug", "Content Type: " + type + "\n");
		if (type.compareTo("NONE") == 0) {
			newContent = null;
		} else if (type.compareTo("STAIRS") == 0) {
			newContent = Stairs.getFromStringRepresentation(contentArgs);
		} else if (type.compareTo("TREASURE") == 0) {
			newContent = Treasure.getFromStringRepresentation(contentArgs);
		} else if (type.compareTo("MONSTER") == 0) {
			newContent = Monster.getFromStringRepresentation(contentArgs);
		} else {
			newContent = null;
		}

		// make the new room and return
		Room newRoom = new Room(newContent, x_, y_, visited_, canVisit_);
		return newRoom;
	}

	public boolean getCanVisit() {
		return this.canVisit;
	}

}
