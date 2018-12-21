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

import java.awt.geom.Point2D;

import SwissCheese.annotations.ThreadSafe;
import SwissCheese.map.Map;

/**
 * Camera object. INCOMPLETE
 * 
 * @author Alex Kalinins
 * @since 2018-12-10
 * @since v0.2
 * @version v0.3
 */
@ThreadSafe
public final class Camera {
	private final float FOV;
	private final Mover mover;
	private View view;

	public Camera(int width, int height, Map map, float FOV) {
		this.FOV = FOV; //0 -> -1
		view = new View((float)map.getEntry().getX(), (float)map.getEntry().getY()+1.5f, 1, 0, 0, FOV);
		mover = new Mover(view, map);
	}
	public final View getView() {
		view = mover.getView();
		return view;
	}
	public final Mover getMover() {
		return mover;
	}
	public final float getFOV() {
		return FOV;
	}
}
