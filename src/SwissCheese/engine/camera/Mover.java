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
public class Mover {
	private AtomicBoolean moveleft = new AtomicBoolean(false);
	private AtomicBoolean moveright = new AtomicBoolean(false);
	private AtomicBoolean moveforward = new AtomicBoolean(false);
	private AtomicBoolean movebackward = new AtomicBoolean(false);

	private AtomicBoolean panleft = new AtomicBoolean(false);
	private AtomicBoolean panright = new AtomicBoolean(false);

	public final double MOVE_SPEED = .08;
	public final double ROTATION_SPEED = .045;

	private View view;
	private Map map;
	
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
		this.map = map;
	}

	public synchronized void update(View view) {
		this.view = view;
	}

	/**
	 * Creates and delegates a thread to do a loop of move forward calculations
	 * until stopForward() void is called and the thread is interrupted.
	 */
	public synchronized void moveForward() {
		moveforward.set(true);
		moveforwardthread = new Thread(new MoveForward());
		moveforwardthread.start();
	}

	/**
	 * Sets moveforward to false, interrupting the moveForward thread.
	 * @throws InterruptedException 
	 */
	public synchronized void stopForward() throws InterruptedException {
		moveforward.set(false);
		
		moveforwardthread.join();
		
	}
	
	class MoveForward implements Runnable {
		@Override
		public void run() {
			while (moveforward.get()) {
				if (map.getMap()[(int) (view.getxPos() + view.getxDir() * MOVE_SPEED)][(int) view.getyPos()] == 0) {
					view.setxPos((int) (view.getxPos() + view.getxDir() * MOVE_SPEED));
				}
				if (map.getMap()[(int) view.getxPos()][(int) (view.getyPos() + view.getyDir() * MOVE_SPEED)] == 0)
					view.setyPos((int) (view.getyPos() + view.getyDir() * MOVE_SPEED));
				System.out.println("Moving forward");
			}
		}
	}
	

	/**
	 * Creates and delegates a thread to calculate backward movement.
	 */
	public synchronized void moveBackward() {
		movebackward.set(true);
		Thread move = new Thread(new MoveBackward());
		move.start();
	}

	/**
	 * Sets movebackward to false, ending the backward movement.
	 * @throws InterruptedException 
	 */
	public synchronized void stopBackward() throws InterruptedException {
		movebackward.set(false);
		movebackwardthread.join();
	}
	

	class MoveBackward implements Runnable {
		@Override
		public void run() {
			while (movebackward.get()) {
				if (map.getMap()[(int) (view.getxPos() - view.getxDir() * MOVE_SPEED)][(int) view.getyPos()] == 0)
					view.setxPos((int) (view.getxPos() - view.getxDir() * MOVE_SPEED));
				if (map.getMap()[(int) view.getxPos()][(int) (view.getyPos() - view.getyDir() * MOVE_SPEED)] == 0)
					view.setyPos((int) (view.getyPos() - view.getyDir() * MOVE_SPEED));
				System.out.println("Moving back");

			}
		}
	}

	/**
	 * Moves the camera to the left. Math is likely incorrect.
	 */
	public synchronized void moveLeft() {
		moveleft.set(true);
		Thread move = new Thread(new MoveLeft());
		move.start();
	}

	/**
	 * Stops the camera left movement
	 * @throws InterruptedException 
	 */
	public synchronized void stopLeft() throws InterruptedException {
		moveleft.set(false);
		moveleftthread.join();
	}
	

	class MoveLeft implements Runnable {
		@Override
		public void run() {
			while (moveleft.get()) {
				if (map.getMap()[(int) (view.getxPos() + view.getyDir() * MOVE_SPEED)][(int) view.getyPos()] == 0) {
					view.setxPos((int) (view.getxPos() + view.getyDir() * MOVE_SPEED));
				}
				if (map.getMap()[(int) view.getxPos()][(int) (view.getyPos() + view.getxDir() * MOVE_SPEED)] == 0)
					view.setyPos((int) (view.getyPos() + view.getxDir() * MOVE_SPEED));
				System.out.println("Moving left");
			}
		}
	}

	/**
	 * Moves the camera to the right. Math is likely incorrect
	 */
	public synchronized void moveRight() {
		moveright.set(true);

		Thread move = new Thread(new MoveRight());
		move.start();

	}

	/**
	 * Stops the camera from moving to the right
	 * @throws InterruptedException 
	 */
	public synchronized void stopRight() throws InterruptedException {
		moveright.set(false);
		moverightthread.join();
	}
	

	class MoveRight implements Runnable {
		@Override
		public void run() {
			while (moveright.get()) {
				if (map.getMap()[(int) (view.getyPos() + view.getxDir() * MOVE_SPEED)][(int) view.getyPos()] == 0) {
					view.setyPos((int) (view.getyPos() + view.getxDir() * MOVE_SPEED));
				}
				if (map.getMap()[(int) (view.getxPos())][(int) (view.getxPos() + view.getyDir() * MOVE_SPEED)] == 0)
					view.setxPos((int) (view.getxPos() + view.getyDir() * MOVE_SPEED));
				System.out.println("Moving right");
			}
		}
	}

	/**
	 * Pans camera to the left
	 */
	public synchronized void panLeft() {
		panleft.set(true);

		Thread pan = new Thread(new PanLeft());
		pan.start();
	}

	/**
	 * Stops left panning
	 * @throws InterruptedException 
	 */
	public synchronized void stopPanLeft() throws InterruptedException {
		panleft.set(false);
		panleftthread.join();
	}
	

	class PanLeft implements Runnable{
		@Override
		public void run() {
			double oldxDir;
			double oldxPlane;

			while (panleft.get()) {
				oldxDir = view.getxDir();
				view.setxDir((float) (view.getxDir() * Math.cos(ROTATION_SPEED)
						- view.getyDir() * Math.sin(ROTATION_SPEED)));
				view.setyDir(
						(float) (oldxDir * Math.sin(ROTATION_SPEED) + view.getyDir() * Math.cos(ROTATION_SPEED)));
				oldxPlane = view.getxPlane();
				view.setxPlane((float) (view.getxPlane() * Math.cos(ROTATION_SPEED)
						- view.getyPlane() * Math.sin(ROTATION_SPEED)));
				view.setyPlane((float) (oldxPlane * Math.sin(ROTATION_SPEED)
						+ view.getyPlane() * Math.cos(ROTATION_SPEED)));
			}
		}
	}

	public synchronized void panRight() {
		panright.set(true);
		Thread pan = new Thread(new PanRight());
		pan.start();
	}

	public synchronized void stopPanRight() throws InterruptedException {
		panright.set(false);
		panrightthread.join();
	}
	

	class PanRight implements Runnable {
		@Override
		public void run() {
			double oldxDir;
			double oldxPlane;

			while (panright.get()) {
				oldxDir = view.getxDir();
				view.setxDir((float) (view.getxDir() * Math.cos(-ROTATION_SPEED)
						- view.getyDir() * Math.sin(-ROTATION_SPEED)));
				view.setyDir(
						(float) (oldxDir * Math.sin(-ROTATION_SPEED) + view.getyDir() * Math.cos(-ROTATION_SPEED)));
				oldxPlane = view.getxPlane();
				view.setxPlane((float) (view.getxPlane() * Math.cos(-ROTATION_SPEED)
						- view.getyPlane() * Math.sin(-ROTATION_SPEED)));
				view.setyPlane((float) (oldxPlane * Math.sin(-ROTATION_SPEED)
						+ view.getyPlane() * Math.cos(-ROTATION_SPEED)));
			}
		}
	}
	
	
	/**
	 * Stops all threads.
	 * 
	 * @throws InterruptedException
	 */
	public static synchronized void stopAllThreads() throws InterruptedException {	
		moveleftthread.join();
		moverightthread.join();
		moveforwardthread.join();
		movebackwardthread.join();
		panleftthread.join();
		panrightthread.join();
	}

	public synchronized AtomicBoolean isMoveleft() {
		return moveleft;
	}

	public synchronized AtomicBoolean isMoveright() {
		return moveright;
	}

	public synchronized AtomicBoolean isMoveforward() {
		return moveforward;
	}

	public synchronized AtomicBoolean isMovebackward() {
		return movebackward;
	}

	public synchronized AtomicBoolean isPanleft() {
		return panleft;
	}

	public synchronized AtomicBoolean isPanright() {
		return panright;
	}

	public synchronized View getView() {
		return view;
	}

}
