package rpisdd.rpgme.gamelogic.dungeon.viewcontrol;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ScrollView;

public class VertScrollView extends ScrollView {

	public float width;
	public float height;

	public VertScrollView(Context context) {
		super(context);
	}

	public VertScrollView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public VertScrollView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	@Override
	public void onLayout(boolean changed, int left, int top, int right,
			int bottom) {
		if (changed) {
			(this).layout(0, 0, (int) width, (int) height);

		}
	}

}