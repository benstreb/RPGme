package rpisdd.rpgme.gamelogic.dungeon.model;

public class Dungeon {
	static final int DUNGEON_DIMMENSION = 10;

	int level;
	Room map[][];

	public Dungeon(int _level) {
		this.level = _level;
		GenerateMap();
	}

	private void GenerateMap() {

	}

	public Room getRoom(int x, int y) {
		return map[y][x];
	}

	// Visits a room. If that room is cleared
	// then allow the player to visit nearby rooms
	public void visitRoom(int x, int y) {
		if (roomExists(x, y)) {
			if (map[y][x].visit()) {
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
		return map[y][x] != null;
	}

}
