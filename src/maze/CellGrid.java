/**
 * Copyright (C) 2018  Alex Kalinins
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
package maze;

import java.util.*;

/**
 * The CellGrid class creates a CellGrid object necessary for the generation of
 * the maze.
 * <p>
 * The CellGrid is similar to a chess/checkers board (but larger), with each
 * cell having a wall, and when generation algorithm is creating the maze, it
 * marks the 2D boolean arrays of the wall that it is breaking as true.
 * <p>
 * CellGrid also has a method (checkRemainingPaths()) for checking the number of
 * paths that the algorithm can go (unvisited cells). If checkRemainingPaths
 * returns 0, the algorithm starts to back-track.
 * <p>
 * <b>THE SIZE AND THE GRID MUST BE AN EVEN NUMBER, OTHERWISE IT WILL NOT
 * WORK!</b>
 * 
 * @author Alex Kalinins
 * @since v0.1
 * @since 2018-11-14
 *
 */
public class CellGrid {
	private static int gridWidth;
	private static int gridHeight;
	private static boolean[][] northWall;
	private static boolean[][] eastWall;
	private static boolean[][] southWall;
	private static boolean[][] westWall;
	private static boolean[][] visited;

	/**
	 * Constructor
	 * 
	 * @param gridWidth  the width of the cell array
	 * @param gridHeight
	 */
	private CellGrid(int gridWidth, int gridHeight) {
		CellGrid.gridWidth = gridWidth;
		CellGrid.gridHeight = gridHeight;
		northWall = new boolean[gridWidth][gridHeight];
		eastWall = new boolean[gridWidth][gridHeight];
		southWall = new boolean[gridWidth][gridHeight];
		westWall = new boolean[gridWidth][gridHeight];

	}

	public static CellGrid newCellGrid(int gridWidth, int gridHeight) {
		return new CellGrid(gridWidth, gridHeight);
	}

	public enum Direction {
		NORTH {
			@Override
			public boolean directionAvailable(int x, int y) {
				if (y == 0)
					return false;
				return northWall[x][y + 1];
			}
		},
		SOUTH {
			@Override
			public boolean directionAvailable(int x, int y) {
				if (y == gridHeight - 1)
					return false;
				return southWall[x][y - 1];
			}
		},
		EAST {
			@Override
			public boolean directionAvailable(int x, int y) {
				if (x == 0)
					return false;
				return eastWall[x - 1][y];
			}
		},
		WEST {
			@Override
			public boolean directionAvailable(int x, int y) {
				if (x == gridWidth - 1)
					return false;
				return westWall[x + 1][y];
			}
		};

		public abstract boolean directionAvailable(int x, int y);
	};

	/**
	 * Marks north wall of cell as broken
	 * 
	 * @param x x-location of the cell
	 * @param y y-location of the cell
	 */
	public void goNorth(int x, int y) {
		northWall[x][y] = true;
		southWall[x][y - 1] = true;
		visited[x][y - 1] = true;
	}

	/**
	 * Marks south wall of cell as broken
	 * 
	 * @param x x-location of the cell
	 * @param y y-location of the cell
	 */
	public void goSouth(int x, int y) {
		southWall[x][y] = true;
		northWall[x][y + 1] = true;
		visited[x][y + 1] = true;
	}

	/**
	 * Marks east wall of cell as broken
	 * 
	 * @param x x-location of the cell
	 * @param y y-location of the cell
	 */
	public void goEast(int x, int y) {
		eastWall[x][y] = true;
		westWall[x - 1][y] = true;
		visited[x - 1][y] = true;
	}

	/**
	 * Marks west wall of cell as broken
	 * 
	 * @param x x-location of the cell
	 * @param y y-location of the cell
	 */
	public void goWest(int x, int y) {
		westWall[x][y] = true;
		eastWall[x + 1][y] = true;
		visited[x + 1][y] = true;
	}

	/**
	 * Marks a cell as visited
	 * 
	 * @param x x-location of the cell
	 * @param y y-location of the cell
	 */
	public void markVisited(int x, int y) {
		visited[x][y] = true;
	}

	/**
	 * If a cell has been visited
	 * 
	 * @param x x-location of the cell
	 * @param y y-location of the cell
	 * @return true if visited
	 */
	public boolean checkVisited(int x, int y) {
		return visited[x][y];
	}

	/**
	 * Checks if the direction of the Direction ENUM is unvisited.
	 * 
	 * @param x         x-location of the <b>CURRENT</b> cell
	 * @param y         y-location of the <b>CURRENT</b> cell
	 * @param direction Cardinal direction being checked (NORTH, SOUTH, EAST, WEST)
	 * @return true if Direction direction is unvisited.
	 */
	public boolean checkDirection(int x, int y, Direction direction) {
		return direction.directionAvailable(x, y);
	}

	/**
	 * Converts an the CellGrid object to an 2D integer array. If a wall is to be
	 * drawn in that location, the Integer will have a value of 1, else value of 0.
	 * <p>
	 * Integers are used instead of booleans because the int value represents the
	 * type of wall texture used. (randomly selected by a different method).
	 * 
	 * @return CellGrid as a 2D Integer Array
	 */
	public Integer[][] gridTo2DArray() {
		List<List<Integer>> map = new ArrayList<List<Integer>>();

		List<Integer> line1 = new ArrayList<Integer>();
		List<Integer> line2 = new ArrayList<Integer>();
		for (int j = 0; j < gridHeight; j++) {
			line1 = new ArrayList<Integer>();
			line2 = new ArrayList<Integer>();

			for (int i = 0; i < gridWidth; i++) {
				/**
				 * In the format:
				 * <p>
				 * 1 N
				 * <p>
				 * W 0
				 */

				// 1, N
				line1.add(1);
				if (northWall[i][j])
					line1.add(0);
				else
					line1.add(1);

				// W, 0
				if (westWall[i][j])
					line2.add(0);
				else
					line2.add(1);
				line2.add(0);
			}

			line1.add(1);
			if (eastWall[gridWidth - 1][j])
				line2.add(0);
			else
				line2.add(1);

			map.add(line1);
			map.add(line2);
		}

		line1 = new ArrayList<Integer>();
		for (int i = 0; i < gridWidth; i++) {
			line1.add(1);
			if (southWall[i][gridHeight - 1])
				line1.add(0);
			else
				line1.add(1);
		}

		map.add(line1);

		Integer[][] array = new Integer[2 * gridWidth + 1][2 * gridHeight + 1];

		for (int i = 0; i < array.length; i++) {
			array[i] = map.get(i).toArray(new Integer[map.get(i).size()]);
		}

		return array;
	}

	/**
	 * Checks the number of possible directions the maze generator can go by
	 * checking the visited[][] array of the adjacent cells.
	 * 
	 * @param x x-location of the cell being checked
	 * @param y y-location of the cell being checked
	 * @return number of possible directions
	 */
	public int checkRemainingPaths(int x, int y) {
		// wrong with a capital W
		int directions = 0;
		if (x == 0 && y == 0) {
			// current cell is top left corner
			if (!visited[x][y + 1])
				directions++;
			if (!visited[x + 1][y])
				directions++;

			return directions;
		}
		if (x == 0 && y == gridHeight - 1) {
			// current cell is bottom left corner
			if (!visited[x][y - 1])
				directions++;
			if (!visited[x + 1][y])
				directions++;

			return directions;
		}
		if (x == gridWidth - 1 && y == 0) {
			// cell is top right corner
			if (!visited[x][y + 1])
				directions++;
			if (!visited[x - 1][y])
				directions++;

			return directions;

		}
		if (x == gridWidth - 1 && y == gridHeight - 1) {
			// cell is bottom right corner
			if (!visited[x][y - 1])
				directions++;
			if (!visited[x - 1][y])
				directions++;

			return directions;

		}

		if (x == 0) {
			// top row
			directions = 0;
			if (!visited[x - 1][y])
				directions++;
			if (!visited[x][y - 1])
				directions++;
			if (!visited[x + 1][y])
				directions++;

			return directions;
		}
		if (y == 0) {
			// left row
			directions = 0;
			if (!visited[x][y - 1])
				directions++;
			if (!visited[x][y + 1])
				directions++;
			if (!visited[x + 1][y])
				directions++;

			return directions;
		}
		if (y == gridHeight - 1) {
			// bottom row
			directions = 0;
			if (!visited[x - 1][y])
				directions++;
			if (!visited[x][y + 1])
				directions++;
			if (!visited[x + 1][y])
				directions++;

			return directions;
		}
		if (x == gridWidth - 1) {
			// right row
			directions = 0;
			if (!visited[x][y + 1])
				directions++;
			if (!visited[x][y - 1])
				directions++;
			if (!visited[x - 1][y])
				directions++;

			return directions;
		}

		// all the rest of the possibilities mean that a cell is adjacent to 4 others
		directions = 0;
		if (!visited[x][y + 1])
			directions++;
		if (!visited[x][y - 1])
			directions++;
		if (!visited[x + 1][y])
			directions++;
		if (!visited[x - 1][y])
			directions++;
		return directions;
	}

}
