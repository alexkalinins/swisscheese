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
 * Gamma changing effect. Makes an image brighter or darker.
 * 
 * @author Alex Kalinins
 * @since 2018-12-20
 * @since v0.3
 * @version v1.0
 */
public class ChangeGamma extends ImageEffect {
	
	private ChangeGamma() {
		//do nothing
	}

	/**
	 * Changes gamma of a color (RGB or RGBA)
	 * 
	 * @param color       color being effected
	 * @param coefficient by how much color is darkened (0<{@code coefficient}<1 =
	 *                    darken; 1<{@code coefficient} = brighten)
	 * @return color with the applied effect.
	 */
	public static int getColor(int color, final float coefficient) {
		if (coefficient <= 0) {
			throw new IllegalArgumentException("effect coefficient must be greater than 0");
		}

		final int MASK = 0xFF; // mask
		// breaking down color into its components
		int alpha = (color >>> 24) & MASK;
		int red = (color >>> 16) & MASK;
		int green = (color >>> 8) & MASK;
		int blue = color & MASK;

		// making darkening factor for each component
		float redDecimal = red / 255f * coefficient;
		float greenDecimal = green / 255f * coefficient;
		float blueDecimal = blue / 255f * coefficient;

		// darkening
		red = (int) (255 * redDecimal);
		green = (int) (255 * greenDecimal);
		blue = (int) (255 * blueDecimal);
		
		// assembling color
		return (alpha << 24) + (red << 16) + (green << 8) + blue;
	}
}
