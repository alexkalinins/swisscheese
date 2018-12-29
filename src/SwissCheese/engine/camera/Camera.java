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

import java.awt.Window;

import SwissCheese.annotations.ThreadSafe;
import SwissCheese.map.Map;

/**
 * Camera object. INCOMPLETE
 * 
 * @author Alex Kalinins
 * @since 2018-12-10
 * @since v0.2
 * @version v0.4
 */
@ThreadSafe
public final class Camera {
	private final float FOV;
	private final Mover mover;
	private View view;

	/**
	 * Camera constructor
	 * 
	 * @param width  width of the game {@link Window}
	 * @param height height of the game {@code Window}
	 * @param map    the map in which the player is.
	 * @param FOV    the Field of View of the player
	 * @param view   (var-arg) a {@link View} object. If multiple {@code View}
	 *               objects are passed in, only the first one is used.
	 */
	public Camera(int width, int height, Map map, float FOV, View... view) {
		this.FOV = FOV;
		if (FOV < -2.5 || FOV > -0.1)
			throw new IllegalArgumentException("FOV must be between -0.1 and -2.5");
		if (view.length == 0) {
			this.view = new View((float) map.getEntry().getX(), (float) map.getEntry().getY() + 1.5f, 1f, 0f, 0f, FOV);
		} else {
			this.view = view[0];
		}
		mover = new Mover(this.view, map);
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
