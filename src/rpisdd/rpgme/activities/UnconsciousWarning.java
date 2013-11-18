package rpisdd.rpgme.activities;

import rpisdd.rpgme.R;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class UnconsciousWarning extends Fragment {

	public UnconsciousWarning() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		// TODO Art for warning when player tries to enter dungeon with no
		// energy
		View v = inflater.inflate(R.layout.unconscious_splash_screen,
				container, false);
		return v;
	}
}
