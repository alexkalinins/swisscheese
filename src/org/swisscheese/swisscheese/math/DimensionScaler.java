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
package org.swisscheese.swisscheese.math;

import java.awt.Dimension;

/**
 * A class for calculating a {@code Dimension} for an image to be scaled to a
 * down to a desired size while maintaining the original aspect ratio of the
 * image.
 * 
 * @author Alex Kalinins
 * @since 2018-12-29
 * @since v0.5
 * @version v1.0
 */
public class DimensionScaler {

	/**
	 * Scales down {@code original} to fit <strong>within</strong> {@code maxSize}.
	 * 
	 * @param original starting dimension of the image being scaled
	 * @param maxSize  bounds of scaling
	 * @return scaled down dimension.
	 */
	public static Dimension scale(final Dimension original, final Dimension maxSize) {
		int newWidth = (int) original.getWidth();
		int newHeight = (int) original.getHeight();

		if (newWidth > maxSize.getWidth()) {
			newWidth = (int) maxSize.getWidth();
			newHeight = (int) ((newWidth * original.getHeight()) / original.getWidth());
		}

		if (newHeight > maxSize.getHeight()) {
			newHeight = (int) maxSize.getHeight();
			newWidth = (int) ((newHeight * original.getWidth()) / original.getHeight());
		}

		return new Dimension(newWidth, newHeight);
	}
}
