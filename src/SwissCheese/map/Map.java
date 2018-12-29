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
package SwissCheese.map;

import java.awt.geom.Point2D;
import java.security.SecureRandom;
import java.util.Random;

import SwissCheese.annotations.Immutable;
import SwissCheese.maze.Generator;

/**
 * Map object of the SwissCheese game
 * 
 * @author Alex Kalinins
 * @since 2018-12-10
 * @since v0.3
 * @version v0.4
 * @see SwissCheese.maze
 *
 */
@Immutable
public final class Map {
	private final int[][] map;
	private final Point2D entry;
	private final Point2D exit;
	private final int size; // this size is in walls, not cells

	/**
	 * Constructor for Map
	 * 
	 * @param size the size of the maze (measured in number of grids in CellGrid.
	 *             True size of the maze will be size*2+1).
	 */
	public Map(int size) {
		map = GenerateMaze(size);
		entry = Generator.getEntry();
		exit = Generator.getExit();

		this.size = map.length;
	}

	/**
	 * Calls maze generator to generate maze.
	 * <p>
	 * Randomizes all walls that are not entry or exit walls.
	 * 
	 * @param size the size of the maze (measured in number of grids)
	 * @return 2D Integer array of the maze.
	 * @see maze#Generator#generateMaze(size)
	 */
	private int[][] GenerateMaze(int size) {
		int[][] map = Generator.generateMaze(size);

		Point2D entry = Generator.getEntry();
		Point2D exit = Generator.getExit();
		
		Random r = new SecureRandom();
		int val;
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				val = map[i][j];
				if (val == 1) {
					val = r.nextInt(3) + 3;
				}
				map[i][j] = val;
			}
		}
		
		map[(int) entry.getX()][(int) entry.getY()] = 1;
		map[(int) exit.getX()][(int) exit.getY()] = 2;

		

		return map;
	}

	public int[][] getMap() {
		return map.clone();
	}

	public int getSize() {
		return size;
	}

	public Point2D getEntry() {
		return entry;
	}

	public Point2D getExit() {
		return exit;
	}

}
