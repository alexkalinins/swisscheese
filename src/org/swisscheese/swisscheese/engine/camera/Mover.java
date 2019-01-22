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
package org.swisscheese.swisscheese.engine.camera;

import javax.swing.JOptionPane;

import org.swisscheese.swisscheese.annotations.ThreadSafe;
import org.swisscheese.swisscheese.engine.display.Window;
import org.swisscheese.swisscheese.map.Map;

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
 * is started, a boolean flag is set to true. The {@code update} method calls
 * private calculating methods to calculate the movement.
 * <p>
 * Movement and rotation speed are set as final floats. There is a possibility
 * of passing them through in future versions of {@code Mover} in order to
 * select control sensitivity.
 * 
 * @author Alex Kalinins
 * @since 2018-12-10
 * @since v0.2
 * @version v1.0
 *
 */
@ThreadSafe
public class Mover {
	private boolean moveF;
	private boolean moveB;
	private boolean moveL;
	private boolean moveR;
	private boolean panL;
	private boolean panR;
	/** Boolean determining if the mover is stopped */
	private static boolean usable = true;
	/** Map used */
	private final int[][] map;
	/** View */
	private View view;
	/** Movement speed constant */
	private static final float MOVE_SPEED = 0.1f;
	/** Rotation speed constant */
	private static final float ROTATION_SPEED = 0.1f;
	/** counts down each frame to death of {@link Mover} */
	private int deathCounter = 200;
	/**full stop*/
	private boolean fullStop = false;

	/**
	 * Constructor
	 * 
	 * @param view a {@link org.swisscheese.swisscheese.engine.camera.View} object
	 *             containing Vectors of the players location and POV
	 * @param map  a {@link org.swisscheese.swisscheese.map.Map} object containing
	 *             the map the player is in.
	 */
	public Mover(View view, Map map) {
		setView(view);
		this.map = map.getMap(); // caching map
	}

	public void setView(View view) {
		this.view = view;
	}

	public View getView() {
		return view;
	}

	/**
	 * Updates {@code View} base on which movement/panning flags are set to
	 * {@code true}.
	 */
	public synchronized void update() {
		if (usable || deathCounter > 0) {
			if (!usable)
				deathCounter--;
			if (moveF) {
				moveForwardCalc();
			}
			if (moveB) {
				moveBackwardCalc();
			}
			if (moveL) {
				moveLeftCalc();
			}
			if (moveR) {
				moveRightCalc();
			}
			if (panL) {
				panLeftCalc();
			}
			if (panR) {
				panRightCalc();
			}
		}else if(deathCounter == 0&&!fullStop){
			fullStop = true;
			JOptionPane.showMessageDialog(Window.getWindow(), "You completed the game!!!", "Finished Game!", JOptionPane.PLAIN_MESSAGE);
		}
	}

	/**
	 * Shuts down {@code Mover} and prevents the user from moving
	 */
	public static void close() {
		usable = false;
	}

	/**
	 * Does calculation for forward movement.
	 */
	private void moveForwardCalc() {
		float xMove;
		float yMove;

		if (map[(int) (xMove = view.getxPos() + view.getxDir() * MOVE_SPEED)][(int) (view.getyPos())] == 0) {
			view.setxPos(xMove);
		}
		if (map[(int) (view.getxPos())][(int) (yMove = view.getyPos() + view.getyDir() * MOVE_SPEED)] == 0) {
			view.setyPos(yMove);
		}
	}

	/**
	 * Does calculation for backward movement.
	 */
	private void moveBackwardCalc() {
		float xMove;
		float yMove;

		if (map[(int) (xMove = view.getxPos() - view.getxDir() * MOVE_SPEED)][(int) (view.getyPos())] == 0) {
			view.setxPos(xMove);
		}
		if (map[(int) (view.getxPos())][(int) (yMove = view.getyPos() - view.getyDir() * MOVE_SPEED)] == 0) {
			view.setyPos(yMove);
		}

	}

	/**
	 * Does calculation for left movement.
	 */
	private void moveLeftCalc() {
		float xMove;
		float yMove;

		if (map[(int) (xMove = view.getxPos() - view.getxPlane() * MOVE_SPEED)][(int) (view.getyPos())] == 0) {
			view.setxPos(xMove);
		}
		if (map[(int) (view.getxPos())][(int) (yMove = view.getyPos() - view.getyPlane() * MOVE_SPEED)] == 0) {
			view.setyPos(yMove);
		}
	}

	/**
	 * Does calculation for right movement.
	 */
	private void moveRightCalc() {
		float xMove;
		float yMove;

		if (map[(int) (xMove = view.getxPos() + view.getxPlane() * MOVE_SPEED)][(int) (view.getyPos())] == 0) {
			view.setxPos(xMove);
		}
		if (map[(int) (view.getxPos())][(int) (yMove = view.getyPos() + view.getyPlane() * MOVE_SPEED)] == 0) {
			view.setyPos(yMove);
		}
	}

	/**
	 * Does calculation for left panning.
	 */
	private void panLeftCalc() {
		float oldxDir = view.getxDir();
		view.setxDir((float) (view.getxDir() * Math.cos(ROTATION_SPEED) - view.getyDir() * Math.sin(ROTATION_SPEED)));
		view.setyDir((float) (oldxDir * Math.sin(ROTATION_SPEED) + view.getyDir() * Math.cos(ROTATION_SPEED)));

		float oldxPlane = view.getxPlane();

		view.setxPlane(
				(float) (view.getxPlane() * Math.cos(ROTATION_SPEED) - view.getyPlane() * Math.sin(ROTATION_SPEED)));
		view.setyPlane((float) (oldxPlane * Math.sin(ROTATION_SPEED) + view.getyPlane() * Math.cos(ROTATION_SPEED)));
	}

	/**
	 * Does calculation for right panning
	 */
	private void panRightCalc() {
		float oldxDir = view.getxDir();
		view.setxDir((float) (view.getxDir() * Math.cos(-ROTATION_SPEED) - view.getyDir() * Math.sin(-ROTATION_SPEED)));
		view.setyDir((float) (oldxDir * Math.sin(-ROTATION_SPEED) + view.getyDir() * Math.cos(-ROTATION_SPEED)));

		float oldxPlane = view.getxPlane();

		view.setxPlane(
				(float) (view.getxPlane() * Math.cos(-ROTATION_SPEED) - view.getyPlane() * Math.sin(-ROTATION_SPEED)));
		view.setyPlane((float) (oldxPlane * Math.sin(-ROTATION_SPEED) + view.getyPlane() * Math.cos(-ROTATION_SPEED)));
	}

	/**
	 * Sets flag for moving forward
	 */
	public void moveForward() {
		moveF = true;
	}

	/**
	 * Sets flag to stop moving forward.
	 */
	public void stopForward() {
		moveF = false;
	}

	/**
	 * Sets flag to move backward
	 */
	public void moveBackward() {
		moveB = true;
	}

	/**
	 * Sets flag to stop moving backward
	 */
	public void stopBackward() {
		moveB = false;
	}

	/**
	 * sets flag to move left.
	 */
	public void moveLeft() {
		moveL = true;
	}

	/**
	 * Sets flag to stop moving left
	 */
	public void stopLeft() {
		moveL = false;
	}

	/**
	 * Sets flag to move right
	 */
	public void moveRight() {
		moveR = true;
	}

	/**
	 * Sets flag to stop moving right
	 */
	public void stopRight() {
		moveR = false;
	}

	/**
	 * Sets flag to pan right.
	 */
	public void panRight() {
		panR = true;
	}

	/**
	 * sets flag to stop panning right.
	 */
	public void stopPanRight() {
		panR = false;
	}

	/**
	 * Sets flag to pan left.
	 */
	public void panLeft() {
		panL = true;
	}

	/**
	 * Sets flag to stop panning left.
	 */
	public void stopPanLeft() {
		panL = false;
	}

}
