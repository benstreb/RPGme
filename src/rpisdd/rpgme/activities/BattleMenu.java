package rpisdd.rpgme.activities;

import rpisdd.rpgme.R;
import rpisdd.rpgme.gamelogic.dungeon.model.Monster;
import rpisdd.rpgme.gamelogic.dungeon.viewcontrol.BattleSurfaceView;
import rpisdd.rpgme.gamelogic.player.StatType;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

public class BattleMenu extends Fragment implements OnClickListener {

	Button strengthAtk;
	Button spiritAtk;
	Button willAtk;
	Button intelAtk;

	Button runAway;
	BattleSurfaceView battleView;

	public BattleMenu() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View v = inflater.inflate(R.layout.battle_menu, container, false);

		Monster monster = new Monster("MonsterName",
				"file:///android_asset/Monsters/monster1.png", 100, 1, 1,
				StatType.WILL);

		battleView = (BattleSurfaceView) v.findViewById(R.id.battleSurfaceView);
		battleView.setMonster(monster);
		battleView.battleMenu = this;

		strengthAtk = (Button) v.findViewById(R.id.strengthAtkButton);
		spiritAtk = (Button) v.findViewById(R.id.spiritAtkButton);
		willAtk = (Button) v.findViewById(R.id.willAtkButton);
		intelAtk = (Button) v.findViewById(R.id.intelAtkButton);
		runAway = (Button) v.findViewById(R.id.runAwayButton);

		strengthAtk.setOnClickListener(this);
		spiritAtk.setOnClickListener(this);
		willAtk.setOnClickListener(this);
		intelAtk.setOnClickListener(this);
		runAway.setOnClickListener(this);

		return v;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.strengthAtkButton: {
			battleView.setPlayerAttack(StatType.STRENGTH);
			disableButtons();

			break;
		}
		case R.id.spiritAtkButton: {
			battleView.setPlayerAttack(StatType.SPIRIT);
			disableButtons();
			break;
		}
		case R.id.willAtkButton: {
			battleView.setPlayerAttack(StatType.WILL);
			disableButtons();
			break;
		}
		case R.id.intelAtkButton: {
			battleView.setPlayerAttack(StatType.SPIRIT);
			disableButtons();
			break;
		}
		case R.id.runAwayButton: {
			disableButtons();
			returnToDungeon();
			break;
		}
		default:
			break;
		}
	}

	public void disableButtons() {
		// ((MainActivity)getActivity()).blockMenuAccess();
		strengthAtk.setEnabled(false);
		spiritAtk.setEnabled(false);
		willAtk.setEnabled(false);
		intelAtk.setEnabled(false);
		runAway.setEnabled(false);
	}

	/*
	 * public void returnToDungeon() { ((MainActivity)
	 * getActivity()).enableNavigationDrawer(true); ((MainActivity)
	 * getActivity()).enableActionBar(true); ((MainActivity)
	 * getActivity()).changeFragment(new DungeonMenu()); }
	 */

	public void returnToDungeon() {

		TransitionFragment trans = new TransitionFragment();
		trans.setValues(new DungeonMenu(), true);
		((MainActivity) getActivity()).changeFragment(trans);
	}

	public void redirectToStats() {
		((MainActivity) getActivity()).enableNavigationDrawer(true);
		((MainActivity) getActivity()).enableActionBar(true);
		((MainActivity) getActivity()).changeFragment(new StatsMenu());

	}

}
