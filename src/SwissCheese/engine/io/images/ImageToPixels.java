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

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import SwissCheese.annotations.Immutable;

/**
 * Converts an image file to a PixelImage object.
 * 
 * @author Alex Kalinins
 * @since 2018-12-14
 * @since v0.3
 * @version v0.2
 */
@Immutable
public final class ImageToPixels {

	/**
	 * Reads data from a file into a PixelImage object.
	 * 
	 * @param file source of the image.
	 * @return image from file as a PixelImage
	 */
	public static final PixelImage convert(File file) {
		try {
			BufferedImage imageBuffer = ImageIO.read(file);
			int width = imageBuffer.getWidth();
			int height = imageBuffer.getHeight();
			int pixels[] = imageBuffer.getRGB(0, 0, width, height, null, 0, width);
			return new PixelImage(pixels, width, height);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
