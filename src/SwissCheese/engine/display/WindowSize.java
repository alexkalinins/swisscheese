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
package SwissCheese.engine.display;

import java.awt.Dimension;

import SwissCheese.annotations.Immutable;

/**
 * This enum is for selecting the window size that SwissCheese will use.
 * 
 * @author Alex Kalinins
 * @since 2018-12-26
 * @since v0.5
 * @version v1.0
 */
@Immutable
public enum WindowSize {
	S640X480("640 x 480", new Dimension(640, 480)), S800X600("800 x 600", new Dimension(800, 600)),
	S960X720("960 x 720", new Dimension(960, 720)), S1280X800("1280 x 800", new Dimension(1280, 800)),
	S1280X960("1280 x 960", new Dimension(12680, 960));

	private final String name; // string description of the size
	private final Dimension size; // size

	/**
	 * Private constructor of the {@code WindowSize} enum.
	 * 
	 * @param name the String description of the size (easy to read)
	 * @param size the {@code Dimension} object of the size of the game.
	 */
	private WindowSize(String name, Dimension size) {
		this.name = name;
		this.size = size;
	}

	public String toString() {
		return name;
	}

	public Dimension getSize() {
		return size;
	}
}
