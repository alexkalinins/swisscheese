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
package SwissCheese.engine.io;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * Converts an image file to an integer array of pixels (the rgb values of said
 * pixels).
 * 
 * @author Alex Kalinins
 */
public final class ImageToPixels {
	private File file;
	private static int width;
	private static int height;
	private static int[] pixels;

	/**
	 * Private constructor for {@code ImageToPixels}.
	 * 
	 * @param file the file of the image being converted
	 */
	private ImageToPixels(File file) {
		this.file = file;
		pixels = convert(file);
	}

	/**
	 * Creates a new instance of pixels and converts the image.
	 * 
	 * @param file the file of image file being converted
	 * @return the image as an int array
	 */
	public static synchronized int[] getPixels(File file) {
		new ImageToPixels(file);
		return pixels;
	}

	/**
	 * Private converted method
	 * 
	 * @param file file of the image
	 * @return the image as an int array
	 */
	private synchronized int[] convert(File file) {
		int[] pixels = new int[0];
		try {
			BufferedImage imageBuffer = ImageIO.read(file);
			width = imageBuffer.getWidth();
			height = imageBuffer.getHeight();
			pixels = new int[width * height];
			// breaks down imageBuffer into array of color values (RGB)
			imageBuffer.getRGB(0, 0, width, height, pixels, 0, width);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return pixels;
	}

	public static synchronized int getWidth() {
		return width;
	}

	public static synchronized int getHeight() {
		return height;
	}

}
