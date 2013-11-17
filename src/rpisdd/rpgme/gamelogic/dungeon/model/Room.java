package rpisdd.rpgme.gamelogic.dungeon.model;

import rpisdd.rpgme.gamelogic.player.Player;
import android.app.Activity;

public class Room {
	boolean visited;
	boolean canVisit;
	RoomContent content;

	private final int x;
	private final int y;

	public Room(RoomContent content_, int x_, int y_) {
		this.visited = false;
		this.canVisit = false;
		this.content = content_;
		x = x_;
		y = y_;
	}

	public Room(RoomContent content_) {
		this(content_, 0, 0);
	}

	public boolean visit(Activity activity) {

		this.setVisitable();
		this.visited = true;
		Player.getPlayer().setRoomPos(x, y);

		if (this.hasContent()) {
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

	public boolean getCanVisit() {
		return this.canVisit;
	}

}
