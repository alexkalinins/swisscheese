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
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.swisscheese.swisscheese.map.maze.Generator;
import org.swisscheese.swisscheese.math.GeomPoint2D;

/**
 * Testing that Entry and exit points of the maze are even numbers (not walls)
 * 
 * @author Alex Kalinins
 * @since 2018-12-19
 * @since v0.3
 * @version v0.3
 */
class GeneratorTest {
	private GeomPoint2D<Integer> entry;
	private GeomPoint2D<Integer> exit;
	private int[][] maze;
	private final int SIZE = 20;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	void setUp() throws Exception {
		maze = Generator.generateMaze(SIZE);
		entry = Generator.getEntry();
		exit = Generator.getExit();
	}

	/**
	 * Test method for {@link org.swisscheese.swisscheese.map.maze.Generator#getExit()}.
	 */
	@Test
	void testGetExit() {
		assertNotEquals(0, (int) exit.getX() % 2); // count starts at 0
		assertTrue(exit.getY() == maze.length - 1 || exit.getY() % 2 != 0);
		assertEquals(0, maze[(int) exit.getY()][(int) exit.getX()]);
	}

	/**
	 * Test method for {@link org.swisscheese.swisscheese.map.maze.Generator#getEntry()}.
	 */
	@Test
	void testGetEntry() {
		assertNotEquals(0, (int) entry.getX() % 2);
		assertEquals(0, (int) entry.getY());
		assertEquals(0, maze[(int) entry.getY()][(int) entry.getX()]);

	}

}
