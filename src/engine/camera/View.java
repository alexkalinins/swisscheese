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

/**
 * View object for storing players location on the map and the vectors of the
 * direction in which they are looking.
 * 
 * @author Alex Kalinins
 * @since 2018-12-10
 * @since v0.2
 * @version v0.1
 *
 */
public class View {
	private float xPos, yPos, xDir, yDir, xPlane, yPlane;

	/**
	 * Constructor for View Object
	 * 
	 * @param xPos x-location of a player on the map
	 * @param yPos y-location of a player on the map
	 * @param xDir x component of vector for direction of the camera
	 * @param yDir y component of vector for direction of the camera
	 * @param xPlane x plane of vector
	 * @param yPlane y plane of vector
	 */
	public View(float xPos, float yPos, float xDir, float yDir, float xPlane, float yPlane) {
		updateView(xPos, yPos, xDir, yDir, xPlane, yPlane);
	}

	/**
	 * Updates View object. Used if multiple values need to be changed at once.
	 * 
	 * @param xPos x-location of a player on the map
	 * @param yPos y-location of a player on the map
	 * @param xDir x component of vector for direction of the camera
	 * @param yDir y component of vector for direction of the camera
	 * @param xPlane x plane of vector
	 * @param yPlane y plane of vector
	 */
	public synchronized void updateView(float xPos, float yPos, float xDir, float yDir, float xPlane, float yPlane) {
		this.xPos = xPos;
		this.yPos = yPos;
		this.xDir = xDir;
		this.yDir = yDir;
		this.xPlane = xPlane;
		this.yPlane = yPlane;
	}

	public synchronized float getxPos() {
		return xPos;
	}

	public synchronized void setxPos(float xPos) {
		this.xPos = xPos;
	}

	public synchronized float getyPos() {
		return yPos;
	}

	public synchronized void setyPos(float yPos) {
		this.yPos = yPos;
	}

	public synchronized float getxDir() {
		return xDir;
	}

	public synchronized void setxDir(float xDir) {
		this.xDir = xDir;
	}

	public synchronized float getyDir() {
		return yDir;
	}

	public synchronized void setyDir(float yDir) {
		this.yDir = yDir;
	}

	public synchronized float getxPlane() {
		return xPlane;
	}

	public synchronized void setxPlane(float xPlane) {
		this.xPlane = xPlane;
	}

	public synchronized float getyPlane() {
		return yPlane;
	}

	public synchronized void setyPlane(float yPlane) {
		this.yPlane = yPlane;
	}

}
