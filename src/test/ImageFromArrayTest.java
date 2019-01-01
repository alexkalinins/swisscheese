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
package test;

import static org.junit.Assert.assertEquals;

import java.awt.Color;
import java.awt.image.BufferedImage;

import org.junit.Before;
import org.junit.Test;
import org.swisscheese.swisscheese.engine.io.images.ImageFromArray;

/**
 * Testing to see if
 * {@link org.swisscheese.swisscheese.engine.io.images.ImageFromArray} works and
 * converts an array of pixels into a {@code BufferedImage}.
 * 
 * @author Alex Kalinins
 * @since 2018-12-18
 * @since v0.3
 * @version 1.0
 */
class ImageFromArrayTest {

	final int WIDTH = 3;
	final int HEIGHT = 2;
	BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
	int[] pixels;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	void setUp() throws Exception {
		// array of arbitrarily picked colors
		int[] colors = { Color.black.getRGB(), Color.blue.getRGB(), Color.red.getRGB(), Color.pink.getRGB(),
				Color.yellow.getRGB(), Color.cyan.getRGB() };

		// setting the image colors to be the colors array
		image.setRGB(0, 0, WIDTH, HEIGHT, colors, 0, WIDTH);

		// converting image to a pixel array.
		pixels = image.getRGB(0, 0, WIDTH, HEIGHT, null, 0, WIDTH);
	}

	/**
	 * Test method for
	 * {@link org.swisscheese.swisscheese.engine.io.images.ImageFromArray#getImage(int[], int, int)}.
	 */
	@Test
	void testGetImageIntArrayIntInt() {
		BufferedImage imageFromArray = (BufferedImage) ImageFromArray.getImage(pixels, WIDTH, HEIGHT);
		int[] pixels2 = imageFromArray.getRGB(0, 0, WIDTH, HEIGHT, null, 0, WIDTH);

		for (int i = 0; i < pixels.length; i++) {
			assertEquals(pixels[i], pixels2[i]);
		}
	}

}
