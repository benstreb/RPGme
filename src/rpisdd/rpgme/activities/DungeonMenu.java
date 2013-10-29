package rpisdd.rpgme.activities;

import rpisdd.rpgme.R;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class DungeonMenu extends Fragment {

	public DungeonMenu() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View v = inflater.inflate(R.layout.dungeon_menu, container, false);

		return v;
	}
}
