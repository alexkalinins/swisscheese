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
package org.swisscheese.swisscheese.engine.io.images;

import java.io.File;

import org.swisscheese.swisscheese.annotations.Immutable;

/**
 * This object stores an image as an {@code int} array of pixel RGB values and
 * the image's width and height.
 * 
 * @author Alex Kalinins
 * @since 2018-12-15
 * @since v0.3
 * @version v0.2
 */
@Immutable
public class PixelImage {
	private final int width;
	private final int height;
	private final int[] pixels;

	/**
	 * Constructor from a pixel array (integer array or RGB values), image width and
	 * height. Since pixels is a regular array, image width and height specify the
	 * dimensions of the image and how the image would be scanned.
	 * <p>
	 * The length of the pixels array should be equal to the width multiplied by the
	 * height of the image.
	 * 
	 * @param pixels {@code int} array of RGB values
	 * @param width  the width of the image
	 * @param height the height of the image
	 */
	public PixelImage(int[] pixels, int width, int height) {
		assert pixels.length == width * height;

		this.width = width;
		this.height = height;
		this.pixels = pixels;
	}

	public PixelImage(File file) {
		this(FileToPixelImage.convert(file));
	}

	public PixelImage(PixelImage image) {
		this(image.getPixels(), image.getWidth(), image.getHeight());
	}

	public final int getWidth() {
		return width;
	}

	public final int getHeight() {
		return height;
	}

	public final int[] getPixels() {
		return pixels;
	}
}
