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
package SwissCheese.engine.io.images;

import java.io.File;

/**
 * This object stores an image as an {@code int} array of pixel RGB values and
 * the image's width and height.
 * 
 * @author Alex Kalinins
 * @since 2018-12-15
 * @since v0.3
 * @version v0.1
 */
public class PixelImage {
	private int width;
	private int height;
	private int[] pixels;

	public PixelImage(int[] pixels, int width, int height) {
		super();
		this.width = width;
		this.height = height;
		this.pixels = pixels;
	}
	
	public PixelImage(File file) {
		pixels = ImageToPixels.getPixels(file);
		width = ImageToPixels.getWidth();
		height = ImageToPixels.getHeight();
	}

	public synchronized final int getWidth() {
		return width;
	}

	public synchronized final void setWidth(int width) {
		this.width = width;
	}

	public synchronized final int getHeight() {
		return height;
	}

	public synchronized final void setHeight(int height) {
		this.height = height;
	}

	public synchronized final int[] getPixels() {
		return pixels;
	}

}
