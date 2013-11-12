package rpisdd.rpgme.gamelogic.dungeon.model;

import android.app.Activity;

public class Room {
	boolean visited;
	boolean canVisit;
	RoomContent content;

	public Room(RoomContent content_) {
		this.visited = false;
		this.canVisit = false;
		this.content = content_;
	}

	public boolean visit(Activity activity) {
		this.visited = true;

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

}
