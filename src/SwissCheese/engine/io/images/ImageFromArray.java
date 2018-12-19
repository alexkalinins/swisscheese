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

import java.awt.Image;
import java.awt.image.BufferedImage;

import SwissCheese.annotations.Immutable;

/**
 * This class converts an array of RGB values (pixels) into an {@code Image}
 * object for rendering.
 * 
 * @author Alex Kalinins
 * @since 2018-12-15
 */
@Immutable
public class ImageFromArray {

	/**
	 * Converts pixels array of RGB values into an {@code Image}.
	 * 
	 * @param pixels {@code int} array of RGB values
	 * @param width  the target width of the {@code Image}
	 * @param height the target height of the {@code Image}
	 * @return {@code Image} from pixels array
	 */
	public static Image getImage(int[] pixels, int width, int height) {
		final BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		image.setRGB(0, 0, width, height, pixels, 0, width);
		return image;
	}

	/**
	 * Converts an {@code PixelImage} object into a {@code Image}.
	 * 
	 * @param image PixelImage being converted
	 * @return BufferedImage from PixelImage's pixels array
	 * @see SwissCheese#engine#io#PixelImage
	 */
	public static Image getImage(PixelImage image) {
		return getImage(image.getPixels(), image.getWidth(), image.getHeight());
	}
}
