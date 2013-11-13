package rpisdd.rpgme.activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class TransitionFragment extends Fragment {

	public Fragment nextFragment;
	public boolean showOrHide;

	public TransitionFragment() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		((MainActivity) getActivity()).enableNavigationDrawer(showOrHide);
		((MainActivity) getActivity()).enableActionBar(showOrHide);

		((MainActivity) getActivity()).changeFragment(nextFragment);

		return null;

	}

}
