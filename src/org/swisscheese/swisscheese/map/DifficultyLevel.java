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
package org.swisscheese.swisscheese.map;

import org.swisscheese.swisscheese.map.maze.CellGrid;

/**
 * An <code>enum</code> for the difficulty level of the maze.
 * 
 * @author Alex Kalinins
 * @since 2018-12-27
 * @since v0.5
 * @version v1.0
 */
public enum DifficultyLevel {
	EASY(5, "Easy Peasy (11x11)"), NORMAL(10, "Up for a challange (21x21)"), HARD(20, "I'm a pro! (41x41)"),
	EXTREME(50, "Wasting my time (101x101)");

	private final int size;
	private final String desc;

	/**
	 * Private constructor for {@code DifficultyLevel} enums.
	 * 
	 * @param size The size of the {@link CellGrid} of the maze.
	 * @param desc A playful description of the {@code DifficultyLevel}.
	 */
	private DifficultyLevel(int size, String desc) {
		this.size = size;
		this.desc = desc;
	}

	public final int getSize() {
		return size;
	}

	public final String toString() {
//		return this.name(); // get name
		return desc; // get funny desc
	}

}
