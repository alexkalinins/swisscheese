/**
 * Copyright (C) 2018 Alex Kalinins
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package SwissCheese.engine.camera;

import java.util.concurrent.atomic.AtomicBoolean;

import SwissCheese.annotations.NotThreadSafe;
import SwissCheese.game.GameLoop.WindowTimer;
import SwissCheese.map.Map;

/**
 * Moves the camera:
 * <p>
 * <li>Forward</li>
 * <li>Backward</li>
 * <li>Left</li>
 * <li>Right</li>
 * <p>
 * Pans the camera:
 * <p>
 * <li>Left</li>
 * <li>Right</li>
 * <p>
 * Each type of movement will have two methods: start and stop. When a movement
 * is started, an new thread is created to run a while loop for said movement.
 * The stop method would interrupt the while loop, thus stopping the movement
 * <p>
 * Left and right movement may have incorrect calculations and will be figured
 * out once the graphics load and are working.
 * 
 * @author Alex Kalinins
 * @since 2018-12-10
 * @since v0.2
 * @version v0.3
 *
 */
@NotThreadSafe
public class Mover {
	private AtomicBoolean moveleft = new AtomicBoolean(false);
	private AtomicBoolean moveright = new AtomicBoolean(false);
	private AtomicBoolean moveforward = new AtomicBoolean(false);
	private AtomicBoolean movebackward = new AtomicBoolean(false);

	private AtomicBoolean panleft = new AtomicBoolean(false);
	private AtomicBoolean panright = new AtomicBoolean(false);

	public final float MOVE_SPEED = .0005f;
	public final float ROTATION_SPEED = .0005f;

	private View view;
	private Map mapObj;
	private final int[][] map;

	private static Thread moveleftthread;
	private static Thread moverightthread;
	private static Thread moveforwardthread;
	private static Thread movebackwardthread;
	private static Thread panleftthread;
	private static Thread panrightthread;

	/**
	 * Constructor for Mover class
	 * 
	 * @param view the initial view of the player
	 */
	public Mover(View view, Map map) {
		super();
		update(view);
		this.mapObj = map;
		this.map = mapObj.getMap(); // caching map
	}

	public void update(View view) {
		this.view = view;
	}

	/**
	 * Creates and delegates a thread to do a loop of move forward calculations
	 * until stopForward() void is called and the thread is interrupted.
	 */
	public void moveForward() {
		moveforward.set(true);
		moveforwardthread = new Thread(new MoveForward());
		moveforwardthread.start();
	}

	/**
	 * Sets moveforward to false, interrupting the moveForward thread.
	 */
	public void stopForward() {
		moveforward.set(false);
		try {
			moveforwardthread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}

	class MoveForward implements Runnable {
		@Override
		public void run() {
			while (moveforward.get()) {
				// for caching movement:
				float xMove;
				float yMove;
				if (WindowTimer.isUpdating().get()) {
					System.out.println("Moving forward");
					/**
					 * Move calculations done in if condition to reduce the number of calculations
					 * necessary in each move from 4 to 2.
					 */
					if (map[(int) (xMove = view.getxPos() + view.getxDir() * MOVE_SPEED)][(int) view.getyPos()] == 0) {
						view.setxPos(xMove);
					}
					if (map[(int) view.getxPos()][(int) (yMove = view.getyPos() + view.getyDir() * MOVE_SPEED)] == 0) {
						view.setyPos(yMove);
					}
				}
			}
		}
	}

	/**
	 * Creates and delegates a thread to calculate backward movement.
	 */
	public void moveBackward() {
		movebackward.set(true);
		movebackwardthread = new Thread(new MoveBackward());
		movebackwardthread.start();
	}

	/**
	 * Sets movebackward to false, ending the backward movement.
	 */
	public void stopBackward() {
		movebackward.set(false);
		try {
			movebackwardthread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	class MoveBackward implements Runnable {
		@Override
		public void run() {
			float xMove;
			float yMove;
			while (movebackward.get()) {
				// limiting movement to the fps
				if (WindowTimer.isUpdating().get()) {
					if (map[(int) (xMove = view.getxPos() - view.getxDir() * MOVE_SPEED)][(int) view.getyPos()] == 0) {
						view.setxPos(xMove);
					}
					if (map[(int) view.getxPos()][(int) (yMove = view.getyPos() - view.getyDir() * MOVE_SPEED)] == 0) {
						view.setyPos(yMove);
					}
					System.out.println("Moving back");
				}
			}
		}
	}

	/**
	 * Moves the camera to the left. Math is likely incorrect.
	 */
	public void moveLeft() {
		moveleft.set(true);
		moveleftthread = new Thread(new MoveLeft());
		moveleftthread.start();
	}

	/**
	 * Stops the camera left movement
	 */
	public void stopLeft() {
		moveleft.set(false);
		try {
			moveleftthread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	class MoveLeft implements Runnable {
		@Override
		public void run() {
			float xMove;
			float yMove;
			while (moveleft.get()) {
				if(WindowTimer.isUpdating().get()) {
					if (map[(int) (xMove = view.getxPos() + view.getyDir() * MOVE_SPEED)][(int) view.getyPos()] == 0) {
						view.setxPos(xMove);
					}
					if (map[(int) view.getxPos()][(int) (yMove = view.getyPos() + view.getxDir() * MOVE_SPEED)] == 0) {
						view.setyPos(yMove);
					}
					System.out.println("Moving left");
				}
			}
		}
	}

	/**
	 * Moves the camera to the right. Math is likely incorrect
	 */
	public void moveRight() {
		moveright.set(true);
		moverightthread = new Thread(new MoveRight());
		moverightthread.start();

	}

	/**
	 * Stops the camera from moving to the right.
	 */
	public void stopRight() {
		moveright.set(false);
		try {
			moverightthread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	class MoveRight implements Runnable {
		@Override
		public void run() {
			float xMove;
			float yMove;
			while (moveright.get()) {
				if (WindowTimer.isUpdating().get()) {
					if (map[(int) (xMove = view.getxPos() - view.getyDir() * MOVE_SPEED)][(int) view.getyPos()] == 0) {
						view.setxPos(xMove);
					}
					if (map[(int) view.getxPos()][(int) (yMove = view.getyPos() - view.getxDir() * MOVE_SPEED)] == 0) {
						view.setyPos(yMove);
					}
					System.out.println("Moving right");
				}
			}
		}
	}

	/**
	 * Pans camera to the left.
	 */
	public void panLeft() {
		panleft.set(true);

		panleftthread = new Thread(new PanLeft());
		panleftthread.start();
	}

	/**
	 * Stops left panning.
	 */
	public void stopPanLeft() {
		panleft.set(false);
		try {
			panleftthread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	class PanLeft implements Runnable {
		@Override
		public void run() {
			double oldxDir;
			double oldxPlane;

			while (panleft.get() && WindowTimer.isUpdating().get()) {
				System.out.println("Panning left");
				oldxDir = view.getxDir();
				view.setxDir((float) (view.getxDir() * Math.cos(ROTATION_SPEED)
						- view.getyDir() * Math.sin(ROTATION_SPEED)));
				view.setyDir((float) (oldxDir * Math.sin(ROTATION_SPEED) + view.getyDir() * Math.cos(ROTATION_SPEED)));

				oldxPlane = view.getxPlane();

				view.setxPlane((float) (view.getxPlane() * Math.cos(ROTATION_SPEED)
						- view.getyPlane() * Math.sin(ROTATION_SPEED)));
				view.setyPlane(
						(float) (oldxPlane * Math.sin(ROTATION_SPEED) + view.getyPlane() * Math.cos(ROTATION_SPEED)));
			}
		}
	}

	public void panRight() {
		panright.set(true);
		panrightthread = new Thread(new PanRight());
		panrightthread.start();
	}

	public void stopPanRight() {
		panright.set(false);
		try {
			panrightthread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	class PanRight implements Runnable {
		@Override
		public void run() {
			double oldxDir;
			double oldxPlane;

			while (panright.get() && WindowTimer.isUpdating().get()) {
				System.out.println("panning right");
				oldxDir = view.getxDir();

				view.setxDir((float) (view.getxDir() * Math.cos(-ROTATION_SPEED)
						- view.getyDir() * Math.sin(-ROTATION_SPEED)));
				view.setyDir(
						(float) (oldxDir * Math.sin(-ROTATION_SPEED) + view.getyDir() * Math.cos(-ROTATION_SPEED)));

				oldxPlane = view.getxPlane();

				view.setxPlane((float) (view.getxPlane() * Math.cos(-ROTATION_SPEED)
						- view.getyPlane() * Math.sin(-ROTATION_SPEED)));
				view.setyPlane(
						(float) (oldxPlane * Math.sin(-ROTATION_SPEED) + view.getyPlane() * Math.cos(-ROTATION_SPEED)));
			}
		}
	}

	/**
	 * Stops all moving threads.
	 */
	public static void stopAllThreads() {
		try {
			moveleftthread.join();
		} catch (InterruptedException | NullPointerException e) {

		}
		try {
			moverightthread.join();
		} catch (InterruptedException | NullPointerException e) {

		}
		try {
			moveforwardthread.join();
		} catch (InterruptedException | NullPointerException e) {

		}
		try {
			movebackwardthread.join();
		} catch (InterruptedException | NullPointerException e) {
		}
		try {
			panleftthread.join();
		} catch (InterruptedException | NullPointerException e) {
		}
		try {
			panrightthread.join();
		} catch (InterruptedException | NullPointerException e) {
		}
	}

	public AtomicBoolean isMoveleft() {
		return moveleft;
	}

	public AtomicBoolean isMoveright() {
		return moveright;
	}

	public AtomicBoolean isMoveforward() {
		return moveforward;
	}

	public AtomicBoolean isMovebackward() {
		return movebackward;
	}

	public AtomicBoolean isPanleft() {
		return panleft;
	}

	public AtomicBoolean isPanright() {
		return panright;
	}

	public View getView() {
		return view;
	}

}
