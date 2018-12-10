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
package engine.camera;

import map.Map;

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
 * @version v0.1
 *
 */
public class Mover {
	private boolean moveleft;
	private boolean moveright;
	private boolean moveforward;
	private boolean movebackward;

	private boolean panleft;
	private boolean panright;

	public final double MOVE_SPEED = .08;
	public final double ROTATION_SPEED = .045;

	private View view;
	private Map map;

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
		moveforward = true;

		class Move extends Thread {
			public void run() {
				while (moveforward) {
					if (map.getMap()[(int) (view.getxPos() + view.getxDir() * MOVE_SPEED)][(int) view.getyPos()] == 0) {
						view.setxPos((int) (view.getxPos() + view.getxDir() * MOVE_SPEED));
					}
					if (map.getMap()[(int) view.getxPos()][(int) (view.getyPos() + view.getyDir() * MOVE_SPEED)] == 0)
						view.setyPos((int) (view.getyPos() + view.getyDir() * MOVE_SPEED));
				}
			}
		}

		Thread move = new Move();
		move.start();
	}

	/**
	 * Sets moveforward to false, interrupting the moveForward thread.
	 */
	public synchronized void stopForward() {
		moveforward = false;
	}

	/**
	 * Creates and delegates a thread to calculate backward movement.
	 */
	public synchronized void moveBackward() {
		movebackward = true;

		class Move extends Thread {
			public void run() {
				while (movebackward) {
					if (map.getMap()[(int) (view.getxPos() - view.getxDir() * MOVE_SPEED)][(int) view.getyPos()] == 0)
						view.setxPos((int) (view.getxPos() - view.getxDir() * MOVE_SPEED));
					if (map.getMap()[(int) view.getxPos()][(int) (view.getyPos() - view.getyDir() * MOVE_SPEED)] == 0)
						view.setyPos((int) (view.getyPos() - view.getyDir() * MOVE_SPEED));
				}
			}
		}

		Thread move = new Move();
		move.start();
	}

	/**
	 * Sets movebackward to false, ending the backward movement.
	 */
	public synchronized void stopBackward() {
		movebackward = false;
	}

	/**
	 * Moves the camera to the left. Math is likely incorrect.
	 */
	public synchronized void moveLeft() {
		moveleft = true;

		class Move extends Thread {
			public void run() {
				while (moveleft) {
					if (map.getMap()[(int) (view.getxPos() + view.getyDir() * MOVE_SPEED)][(int) view.getyPos()] == 0) {
						view.setxPos((int) (view.getxPos() + view.getyDir() * MOVE_SPEED));
					}
					if (map.getMap()[(int) view.getxPos()][(int) (view.getyPos() + view.getxDir() * MOVE_SPEED)] == 0)
						view.setyPos((int) (view.getyPos() + view.getxDir() * MOVE_SPEED));
				}
			}
		}

		Thread move = new Move();
		move.start();
	}

	/**
	 * Stops the camera left movement
	 */
	public synchronized void stopLeft() {
		moveleft = false;
	}

	/**
	 * Moves the camera to the right. Math is likely incorrect
	 */
	public synchronized void moveRight() {
		moveright = true;

		class Move extends Thread {
			public void run() {
				while (moveright) {
					if (map.getMap()[(int) (view.getyPos() + view.getxDir() * MOVE_SPEED)][(int) view.getyPos()] == 0) {
						view.setyPos((int) (view.getyPos() + view.getxDir() * MOVE_SPEED));
					}
					if (map.getMap()[(int) (view.getxPos())][(int) (view.getxPos() + view.getyDir() * MOVE_SPEED)] == 0)
						view.setxPos((int) (view.getxPos() + view.getyDir() * MOVE_SPEED));
				}
			}
		}

		Thread move = new Move();
		move.start();

	}

	/**
	 * Stops the camera from moving to the right
	 */
	public synchronized void stopRight() {
		moveright = false;
	}

	/**
	 * Pans camera to the left
	 */
	public synchronized void panLeft() {
		panleft = true;

		class Pan extends Thread {
			public void run() {
				double oldxDir;
				double oldxPlane;

				while (panleft) {
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
		
		Thread pan = new Pan();
		pan.run();
	}
	
	/**
	 * Stops left panning
	 */
	public synchronized void stopPanLeft() {
		panleft=false;
	}

	
	public synchronized void panRight() {
		panright = true;

		class Pan extends Thread {
			public void run() {
				double oldxDir;
				double oldxPlane;

				while (panright) {
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
		
		Thread pan = new Pan();
		pan.run();
	}
	
	public synchronized void stopPanRight() {
		panright = false;
	}
	
	public synchronized boolean isMoveleft() {
		return moveleft;
	}

	public synchronized boolean isMoveright() {
		return moveright;
	}

	public synchronized boolean isMoveforward() {
		return moveforward;
	}

	public synchronized boolean isMovebackward() {
		return movebackward;
	}

	public synchronized boolean isPanleft() {
		return panleft;
	}

	public synchronized boolean isPanright() {
		return panright;
	}

	public synchronized View getView() {
		return view;
	}

}
