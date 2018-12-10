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
package map;

import java.awt.geom.Point2D;

import maze.Generator;

/**
 * Map object of the SwissCheese game
 * 
 * @author Alex Kalinins
 * @since 2018-12-10
 * @since v0.2
 * @version v0.1
 * @see maze
 *
 */
public class Map {
	final Integer[][] map;
	final Point2D entry;
	final Point2D exit;

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
	}

	/**
	 * Calls maze generator to generate maze.
	 * <p>
	 * Unimplemented: will randomize the texture in the maze by replacing the wall
	 * numbers (types) to make the maze less boring.
	 * 
	 * @param size the size of the maze (measured in number of grids)
	 * @return 2D Integer array of the maze.
	 * @see maze#Generator#generateMaze(size)
	 */
	private Integer[][] GenerateMaze(int size) {
		Integer[][] map = Generator.generateMaze(size);
		// TODO make code to randomize textures
		return map;
	}

	public Integer[][] getMap() {
		return map;
	}

}
