package rpisdd.rpgme.activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class TransitionFragment extends Fragment {

	private Fragment nextFragment;
	private boolean showOrHide;

	public TransitionFragment() {
	}

	public void setValues(Fragment nextFragment, boolean showOrHide) {
		this.nextFragment = nextFragment;
		this.showOrHide = showOrHide;
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
