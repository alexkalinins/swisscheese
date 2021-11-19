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

import java.security.SecureRandom;
import java.util.Random;

import org.swisscheese.swisscheese.annotations.Immutable;
import org.swisscheese.swisscheese.map.maze.Generator;
import org.swisscheese.swisscheese.math.GeomPoint2D;

/**
 * Map object of the SwissCheese game
 * 
 * @author Alex Kalinins
 * @since 2018-12-10
 * @since v0.3
 * @version v1.0
 * @see org.swisscheese.swisscheese.map.maze
 *
 */
@Immutable
public final class Map {
	private final int[][] map;
	private final GeomPoint2D<Integer> entry;
	private final GeomPoint2D<Integer> exit;
	private final int size; // this size is in walls, not cells

	/**
	 * Constructor for Map
	 * 
	 * @param size the size of the maze (measured in number of grids in CellGrid.
	 *             True size of the maze will be size*2+1).
	 */
	public Map(int size) {
		map = generateMaze(size);
		entry = Generator.getEntry();
		exit = Generator.getExit();

		this.size = map.length;

		this.printMap();
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
	private int[][] generateMaze(int size) {
		int[][] map = Generator.generateMaze(size);

		GeomPoint2D<Integer> entry = Generator.getEntry();
		GeomPoint2D<Integer> exit = Generator.getExit();
		
		Random r = new SecureRandom();
		int val;
		for (int i = 0; i < map.length; i++) {
			for (int j = 0; j < map[i].length; j++) {
				val = map[i][j];
				if (val == 1) {
					val = r.nextInt(3) + 3;
				}
				map[i][j] = val;
			}
		}
		
		map[entry.getX()][entry.getY()] = 1;
		map[exit.getX()][exit.getY()] = 2;

		

		return map;
	}

	public int[][] getMap() {
		return map.clone();
	}

	public int getSize() {
		return size;
	}

	public GeomPoint2D<Integer> getEntry() {
		return entry;
	}

	public GeomPoint2D<Integer> getExit() {
		return exit;
	}

	/**
	 * Prints the maze in console
	 */
	public void printMap() {
		System.out.println("MAP: ");
		System.out.println("(start: s; end: e)");

		for(int i = 0; i < this.size; i++){
			for(int j = 0; j < this.size; j++){
				char c = ' ';

				if(this.map[i][j] == 1){
					c = 's';
				} else if(this.map[i][j] == 2){
					c = 'e';
				} else if(this.map[i][j] != 0){
					c = '\u2588';
				}

				System.out.print(c);
			}
			System.out.print("\n");
		}
	}
}
