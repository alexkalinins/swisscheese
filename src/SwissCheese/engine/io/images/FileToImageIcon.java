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

import java.awt.Dimension;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import SwissCheese.math.DimensionScaler;

/**
 * An IO tool used to input and convert a image file into a {@link ImageIcon}
 * 
 * @author Alex Kalinins
 * @since 2018-12-28
 * @since v0.5
 * @version v1.0
 *
 */
public final class FileToImageIcon {

	/**
	 * Converts {@code file} to a new instance of {@link ImageIcon}
	 * 
	 * @param file inputed file
	 * @return {@code file} as an {@code ImageIcon}.
	 */
	public static ImageIcon getIcon(File file) {
		return new ImageIcon(readImage(file));
	}

	/**
	 * Converts {@code file} to a new instance of {@code ImageIcon} with the size of
	 * {@code width} and {@code height}.
	 * <p>
	 * This method does not regard the original aspect ratio of the image. To scale
	 * an image and maintain an aspect ratio, use
	 * {@link FileToImageIcon#getScaledToBoundsIcon(File, int, int)}.
	 * 
	 * @param file   file location of the image being read.
	 * @param width  the width of the returned image.
	 * @param height the height of the returned image.
	 * @return a new {@code ImageIcon}.
	 */
	public static ImageIcon getScaledIcon(File file, int width, int height) {
		return new ImageIcon(readImage(file).getScaledInstance(width, height, Image.SCALE_SMOOTH));
	}

	/**
	 * Converts {@code file} to image and scales to fit <strong>within</strong> the
	 * bounds created by {@code width} and {@code height}.
	 * <p>
	 * This scaling method maintains the aspect ratio of the original {@code Image}.
	 * 
	 * @param file   image file being read
	 * @param width  width of the scaling bound
	 * @param height height of the scaling bound.
	 * @return scaled image
	 * 
	 * @see DimensionScaler#scale(Dimension, Dimension)
	 */
	public static ImageIcon getScaledToBoundsIcon(File file, int width, int height) {
		BufferedImage image = readImage(file);
		Dimension scaleTo = DimensionScaler.scale(new Dimension(image.getWidth(), image.getHeight()),
				new Dimension(width, height));
		return new ImageIcon(
				image.getScaledInstance((int) scaleTo.getWidth(), (int) scaleTo.getHeight(), Image.SCALE_SMOOTH));
	}

	/**
	 * Private method that converts {@code file} into a {@code BufferedImage}.
	 * 
	 * @param file file being read
	 * @return BufferedImage read from {@code file}.
	 */
	private static BufferedImage readImage(File file) {
		try {
			return ImageIO.read(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
