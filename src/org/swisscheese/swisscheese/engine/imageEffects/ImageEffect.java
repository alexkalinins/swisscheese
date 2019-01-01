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
package org.swisscheese.swisscheese.engine.imageEffects;

/**
 * An abstract class for image effects. An {@code RBG} or {@code RGBA} color
 * value is an effect coefficient. The color value is returned with the applied
 * effect.
 * 
 * @author Alex Kalinins
 * @since 2018-12-21
 * @since v0.4
 * @version v0.1
 */
public abstract class ImageEffect {
	/**
	 * Applies an effect to {@code color} with the strength {@code coefficient}.
	 * <p>
	 * <strong>THIS METHOD MUST BE IMPLEMENTED!</strong>
	 * 
	 * @param color       an RGB or RGBA value of a color
	 * @param coefficient the effect coefficient.
	 * @return {@code color} with the applied effect
	 */
	public static int getColor(int color, final float coefficient) {
		return color;
	}
}
