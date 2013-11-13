package rpisdd.rpgme.gamelogic.dungeon.viewcontrol;

import rpisdd.rpgme.gamelogic.dungeon.model.Monster;
import rpisdd.rpgme.utils.BitmapUtil;
import android.app.Activity;
import android.graphics.Canvas;

public class MonsterView extends ViewObject {

	public Monster monster;
	public DamageText damageText;

	public MonsterView(float x, float y, Monster monster, Activity activity) {

		super(x, y);

		this.monster = monster;

		bitmap = BitmapUtil
				.getBitmapFromAsset(activity, monster.getImagePath());

		float scaleFactor = 0.1f;

		setScale(scaleFactor);
	}

	public void setDamageText(int damage) {
		damageText = new DamageText(x, y - (getHeight() / 2f), damage, 1, 20);
	}

	@Override
	public void update(ViewThread thread) {
		if (damageText != null) {
			damageText.update(thread);
			if (damageText.getDestroySelf()) {
				damageText = null;
			}
		}
	}

	@Override
	public void draw(Canvas canvas) {
		super.draw(canvas);
		if (damageText != null) {
			damageText.draw(canvas);
		}
	}

}
