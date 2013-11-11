package rpisdd.rpgme.gamelogic.dungeon.model;

import rpisdd.rpgme.gamelogic.player.StatType;
import android.app.Activity;

public class Dungeon {
	public static final int DUNGEON_DIMMENSION = 5;

	int level;
	Room map[][];

	public Dungeon(int _level) {
		this.level = _level;
		GenerateMap();
	}

	public Room[][] getMap() {
		return map;
	}

	private void GenerateMap() {
		map = new Room[DUNGEON_DIMMENSION][DUNGEON_DIMMENSION];
		for (int i = 0; i < DUNGEON_DIMMENSION; i++) {
			for (int j = 0; j < DUNGEON_DIMMENSION; j++) {
				map[i][j] = new Room(new Monster("MonsterName",
						"file:///android_asset/Monsters/monster1.png", 100, 5,
						5, StatType.WILL));
			}
		}
	}

	public Room getRoom(int x, int y) {
		return map[y][x];
	}

	// Visits a room. If that room is cleared
	// then allow the player to visit nearby rooms
	public void visitRoom(int x, int y, Activity activity) {
		if (roomExists(x, y)) {
			if (map[y][x].visit(activity)) {
				if (roomExists(x + 1, y)) {
					map[y][x + 1].setVisitable();
				}
				if (roomExists(x - 1, y)) {
					map[y][x - 1].setVisitable();
				}
				if (roomExists(x, y + 1)) {
					map[y + 1][x].setVisitable();
				}
				if (roomExists(x, y - 1)) {
					map[y - 1][x].setVisitable();
				}
			}

		}
	}

	public boolean roomExists(int x, int y) {
		return inBounds(y, x) && map[y][x] != null;
	}

	public static boolean inBounds(int x, int y) {
		return x >= 0 && y >= 0 && x < DUNGEON_DIMMENSION
				&& y < DUNGEON_DIMMENSION;
	}

}
