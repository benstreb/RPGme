package rpisdd.rpgme.gamelogic.dungeon.viewcontrol;

import rpisdd.rpgme.activities.BattleMenu;
import rpisdd.rpgme.gamelogic.dungeon.model.Combat;
import rpisdd.rpgme.gamelogic.dungeon.model.Monster;
import rpisdd.rpgme.gamelogic.player.Player;
import rpisdd.rpgme.gamelogic.player.Reward;
import rpisdd.rpgme.gamelogic.player.StatType;
import rpisdd.rpgme.popups.AnnoyingPopup;
import rpisdd.rpgme.popups.RewardPopup;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class BattleSurfaceView extends SurfaceView implements
		SurfaceHolder.Callback, ThreadedSurfaceView {

	enum State {
		CHOOSE, PLAYER_ATTACK, MONSTER_TURN, PLAYER_DEAD, TRANSITION, DONE
	};

	State state = State.CHOOSE;

	private BitmapDrawable background;
	private ViewThread thread;

	private AvatarView avatar;
	private Monster monsterModel;
	private MonsterView monster;

	private HealthBar avatarHealth;
	private HealthBar monsterHealth;

	// Delay in seconds between attacks
	private final float attackDelay = 1;
	private float attackTimer = 0;

	public BattleMenu battleMenu;

	StatType attackType;

	public BattleSurfaceView(Context context) {
		super(context);
		init(context);
	}

	public BattleSurfaceView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
		init(context);
	}

	public BattleSurfaceView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(context);
	}

	public int getCanvasWidth() {

		DisplayMetrics metrics = getResources().getDisplayMetrics();
		return metrics.widthPixels;
	}

	public int getCanvasHeight() {

		DisplayMetrics metrics = getResources().getDisplayMetrics();

		return metrics.heightPixels;
	}

	private void init(Context context) {

		getHolder().addCallback(this);

		thread = new ViewThread(getHolder(), this);

		setFocusable(true);

		avatar = new AvatarView(getCanvasWidth() / 4f, getCanvasHeight() / 3f,
				false, (Activity) getContext());

		avatarHealth = new HealthBar(avatar.x, avatar.y
				- (80 * ViewObject.SCALE_FACTOR), 50, 10, Player.getPlayer());
	}

	public void setMonster(Monster monster) {

		monsterModel = monster;

		this.monster = new MonsterView(getCanvasWidth() * (3 / 4f),
				getCanvasHeight() / 3f, monster, (Activity) getContext());

		monsterHealth = new HealthBar(this.monster.x, this.monster.y
				- (80 * ViewObject.SCALE_FACTOR), 50, 10, monster);
	}

	/**
	 * This is the game update method. It iterates through all the objects and
	 * calls their update method if they have one or calls specific engine's
	 * update method.
	 */
	@Override
	public void update() {

		switch (state) {
		case CHOOSE:
			break;
		case PLAYER_ATTACK:
			if (attackTimer >= attackDelay) {
				attackTimer = 0;
				state = State.MONSTER_TURN;
				break;
			}
			attackTimer += thread.deltaTime();
			break;
		case MONSTER_TURN:
			if (attackTimer == 0) {
				monsterTurn();
				attackTimer += thread.deltaTime();
				break;
			}
			if (attackTimer >= attackDelay) {
				attackTimer = 0;
				if (Player.getPlayer().getEnergy() <= 0) {
					knockedUnconscious();
				} else {
					returnToDungeon(false);
				}
				state = State.TRANSITION;
				break;
			}
			attackTimer += thread.deltaTime();
			break;
		default:
			break;
		}

		avatar.update(thread);
		monster.update(thread);
	}

	public void setPlayerAttack(StatType type) {
		System.out.println("Player attacks");
		state = State.PLAYER_ATTACK;
		// Damage the monster here
		int powerValue;
		switch (type) {
		case STRENGTH:
			powerValue = Player.getPlayer().getStrAtk();
			break;
		case INTELLIGENCE:
			powerValue = Player.getPlayer().getIntAtk();
			break;
		case WILL:
			powerValue = Player.getPlayer().getWillAtk();
			break;
		case SPIRIT:
			powerValue = Player.getPlayer().getSprAtk();
			break;
		default:
			powerValue = 0;
		}

		Combat.Attack atk = new Combat.Attack(type, powerValue);
		int damage = monsterModel.RecieveAttack(atk);
		monsterModel.RecieveDamage(damage);
		monster.setDamageText(damage);
	}

	public void monsterTurn() {

		System.out.println("Monster's turn");

		if (monsterModel.getEnergy() <= 0) {

			state = State.TRANSITION;

			final Reward reward = Reward.monsterReward(monsterModel);

			((Activity) getContext()).runOnUiThread(new Runnable() {
				@Override
				public void run() {

					RewardPopup.show("You defeated the monster!", reward,
							(Activity) getContext(),
							new DialogInterface.OnClickListener() {
								@Override
								public void onClick(DialogInterface dialog,
										int id) {
									dialog.cancel();
									returnToDungeon(true);
								}
							});
				}

			});
		} else {
			attackPlayer(monsterModel.MakeAttack());
		}
	}

	private void attackPlayer(Combat.Attack atk) {
		System.out.println("Player's being damaged!");
		int dmg = Player.getPlayer().takeDamage(atk);

		avatar.setDamageText(dmg);

	}

	public void returnToDungeon(final boolean isVictory) {
		((Activity) getContext()).runOnUiThread(new Runnable() {
			@Override
			public void run() {
				battleMenu.returnToDungeon(isVictory);
			}
		});
	}

	public void knockedUnconscious() {

		((Activity) getContext()).runOnUiThread(new Runnable() {
			@Override
			public void run() {

				AnnoyingPopup.notice((Activity) getContext(),
						"You've been knocked unconscious...",
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int id) {
								dialog.cancel();

								battleMenu.redirectToStats();
							}
						});

			}
		});

	}

	@Override
	public void render(Canvas canvas) {
		canvas.drawColor(Color.BLACK);

		// background.setBounds(new Rect(0, 0, 1000, 1000));
		// background.draw(canvas);

		monsterHealth.draw(canvas);
		avatarHealth.draw(canvas);

		monster.draw(canvas);
		avatar.draw(canvas);
	}

	@Override
	public void onLayout(boolean changed, int left, int top, int right,
			int bottom) {
		if (changed) {
			// (this).layout(0, 0, (int) floor.width, (int) floor.height);
		}
	}

	@Override
	protected void onSizeChanged(int xNew, int yNew, int xOld, int yOld) {
		super.onSizeChanged(xNew, yNew, xOld, yOld);
		// avatar.y = getCanvasHeight() / 2f;
		// monster.y = getCanvasHeight() / 2f;

	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		thread = new ViewThread(getHolder(), this);
		thread.setRunning(true);
		thread.start();
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		Log.d("Debug", "Surface is being destroyed");
		stopThread();
	}

	public void stopThread() {
		// tell the thread to shut down and wait for it to finish
		// this is a clean shutdown
		boolean retry = true;
		while (retry) {
			try {
				thread.setRunning(false);
				thread.join();
				// ((Activity) getContext()).finish();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			retry = false;
		}
		Log.d("Debug", "Thread was shut down cleanly");
	}
}