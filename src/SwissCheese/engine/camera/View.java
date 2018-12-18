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

import SwissCheese.annotations.ThreadSafe;
import SwissCheese.math.GeomVector2D;

/**
 * View object for storing players location on the map and the vectors of the
 * direction in which they are looking.
 * <p>
 * {@code View} values are backed by {@code GeomVector2D}
 * 
 * @author Alex Kalinins
 * @since 2018-12-10
 * @since v0.2
 * @version v0.3
 *
 */
@ThreadSafe
public class View {
	private GeomVector2D<Float> pos;
	private GeomVector2D<Float> dir;
	private GeomVector2D<Float> plane;

	/**
	 * Constructor for View Object
	 * 
	 * @param xPos   x-location of a player on the map
	 * @param yPos   y-location of a player on the map
	 * @param xDir   x component of vector for direction of the camera
	 * @param yDir   y component of vector for direction of the camera
	 * @param xPlane x plane of vector
	 * @param yPlane y plane of vector (a control of FOV)
	 */
	public View(float xPos, float yPos, float xDir, float yDir, float xPlane, float yPlane) {
		updateView(xPos, yPos, xDir, yDir, xPlane, yPlane);
	}

	/**
	 * Constructor using {@code GeomVector2D} objects
	 * 
	 * @param pos   position vector - players position on the map
	 * @param dir   direction vector - the direction in which the player is looking
	 * @param plane plane vector - the camera plane, everything the camera sees.
	 */
	public View(GeomVector2D<Float> pos, GeomVector2D<Float> dir, GeomVector2D<Float> plane) {
		updateView(pos, dir, plane);
	}

	/**
	 * Updates View object. Used if multiple values need to be changed at once.
	 * 
	 * @param xPos   x-location of a player on the map
	 * @param yPos   y-location of a player on the map
	 * @param xDir   x component of vector for direction of the camera
	 * @param yDir   y component of vector for direction of the camera
	 * @param xPlane x plane of vector
	 * @param yPlane y plane of vector
	 */
	public synchronized void updateView(float xPos, float yPos, float xDir, float yDir, float xPlane, float yPlane) {
		updateView(new GeomVector2D<Float>(xPos, xPos), new GeomVector2D<Float>(xDir, yDir), new GeomVector2D<Float>(xPlane, yPlane));
	}

	public synchronized void updateView(GeomVector2D<Float> pos, GeomVector2D<Float> dir, GeomVector2D<Float> plane) {
		this.dir = dir;
		this.plane = plane;
		this.pos = pos;
	}

	public synchronized float getxPos() {
		return pos.getX();
	}

	public synchronized void setxPos(float xPos) {
		setPos(new GeomVector2D<Float>(xPos, pos.getY()));
	}

	public synchronized float getyPos() {
		return pos.getY();
	}

	public synchronized void setyPos(float yPos) {
		setPos(new GeomVector2D<Float>(pos.getY(), yPos));
	}

	public synchronized float getxDir() {
		return dir.getX();
	}

	public synchronized void setxDir(float xDir) {
		setDir(new GeomVector2D<Float>(xDir, dir.getY()));
	}

	public synchronized float getyDir() {
		return dir.getY();
	}

	public synchronized void setyDir(float yDir) {
		setDir(new GeomVector2D<Float>(dir.getX(), yDir));
	}

	public synchronized float getxPlane() {
		return plane.getX();
	}

	public synchronized void setxPlane(float xPlane) {
		setPlane(new GeomVector2D<Float>(xPlane, dir.getY()));
	}

	public synchronized float getyPlane() {
		return plane.getY();
	}

	public synchronized void setyPlane(float yPlane) {
		setDir(new GeomVector2D<Float>(dir.getX(), yPlane));

	}

	public synchronized GeomVector2D<Float> getPos() {
		return pos;
	}

	public synchronized void setPos(GeomVector2D<Float> pos) {
		this.pos = pos;
	}

	public synchronized GeomVector2D<Float> getDir() {
		return dir;
	}

	public synchronized void setDir(GeomVector2D<Float> dir) {
		this.dir = dir;
	}

	public synchronized GeomVector2D<Float> getPlane() {
		return plane;
	}

	public synchronized void setPlane(GeomVector2D<Float> plane) {
		this.plane = plane;
	}

}
