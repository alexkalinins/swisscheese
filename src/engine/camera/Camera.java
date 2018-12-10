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

import java.awt.geom.Point2D;

import map.Map;

/**
 * Camera object. INCOMPLETE
 * 
 * @author Alex Kalinins
 * @since 2018-12-10
 * @since v0.2
 * @version 0.1
 */
public class Camera {
	private final int width;
	private final int height;
	private Mover mover;
	private Map map;

	//TODO pass through other stuff.
	public Camera(int width, int height, Map map, Point2D start) {
		this.width = width;
		this.height = height;

		// TODO change from 0s
		mover = new Mover(new View((int)start.getX(), (int)start.getX(), 0, 0, 0, 0), map);
		this.map = map;
	}
	
}
