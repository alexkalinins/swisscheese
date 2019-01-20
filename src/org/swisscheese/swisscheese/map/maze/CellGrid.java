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
package org.swisscheese.swisscheese.map.maze;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.swisscheese.swisscheese.math.GeomPoint2D;

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
 * @since v0.2
 * @since 2018-11-14
 * @version v1.0
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

		visited = new boolean[gridWidth][gridHeight];

		// fills the arrays with false
		for (int i = 0; i < gridWidth; i++) {
			Arrays.fill(northWall[i], false);
			Arrays.fill(eastWall[i], false);
			Arrays.fill(southWall[i], false);
			Arrays.fill(westWall[i], false);
			Arrays.fill(visited[i], false);

		}
	}

	public static CellGrid newCellGrid(int gridWidth, int gridHeight) {
		return new CellGrid(gridWidth, gridHeight);
	}

	/**
	 * The ENUM for each cardinal direction: NORTH, EAST, SOUTH and WEST.
	 * <p>
	 * The Direction enums contain abstract methods that involve direction, such as
	 * breaking a wall in a certain direction. Abstract methods in Direction are
	 * overloaded to work with both x and y int locations and {@code GeomPoint2D}
	 * locations.
	 * 
	 * @author Alex Kalinins
	 * @since v0.1
	 * @since 2018-11-14
	 *
	 */
	public enum Direction {
		NORTH {
			@Override
			public boolean directionAvailable(int x, int y) {
				if (y == 0)
					return false;
				return !visited[x][y - 1];
			}

			@Override
			public void breakWall(int x, int y) {
				northWall[x][y] = true;
			}

			@Override
			public GeomPoint2D<Integer> move(final int x, final int y) {
				northWall[x][y] = true;

				southWall[x][y - 1] = true;

				visited[x][y - 1] = true;
				return this.go(x, y);
			}

			@Override
			public Direction antiDirection() {
				return SOUTH;
			}

			@Override
			public GeomPoint2D<Integer> go(int x, int y) {
				point.setLocation(x, y - 1);
				return point;
			}

			@Override
			public GeomPoint2D<Integer> makeExit(int randomNumber) {
				northWall[randomNumber][0] = true;
				point.setLocation(randomNumber, 0);
				return point;
			}

		},
		SOUTH {
			@Override
			public boolean directionAvailable(int x, int y) {
				if (y == gridHeight - 1)
					return false;
				return !visited[x][y + 1];
			}

			@Override
			public void breakWall(int x, int y) {
				southWall[x][y] = true;
			}

			@Override
			public GeomPoint2D<Integer> move(final int x, final int y) {
				southWall[x][y] = true;
				northWall[x][y + 1] = true;
				visited[x][y + 1] = true;
				return this.go(x, y);
			}

			@Override
			public Direction antiDirection() {
				return NORTH;
			}

			@Override
			public GeomPoint2D<Integer> go(int x, int y) {
				point.setLocation(x, y + 1);
				return point;
			}

			@Override
			public GeomPoint2D<Integer> makeExit(int randomNumber) {
				southWall[randomNumber][gridHeight - 1] = true;
				point.setLocation(randomNumber, gridHeight - 1);
				return point;
			}

		},
		EAST {
			@Override
			public boolean directionAvailable(int x, int y) {
				if (x == gridWidth - 1)
					return false;
				return !visited[x + 1][y];
			}

			@Override
			public void breakWall(int x, int y) {
				eastWall[x][y] = true;
			}

			@Override
			public GeomPoint2D<Integer> move(final int x, final int y) {
				eastWall[x][y] = true;
				westWall[x + 1][y] = true;
				visited[x + 1][y] = true;
				return this.go(x, y);
			}

			@Override
			public Direction antiDirection() {
				return WEST;
			}

			@Override
			public GeomPoint2D<Integer> go(int x, int y) {
				point.setLocation(x + 1, y);
				return point;
			}

			@Override
			public GeomPoint2D<Integer> makeExit(int randomNumber) {
				eastWall[gridWidth - 1][randomNumber] = true;
				point.setLocation(gridWidth - 1, randomNumber);
				return point;
			}
		},
		WEST {
			@Override
			public boolean directionAvailable(int x, int y) {
				if (x == 0)
					return false;
				return !visited[x - 1][y];
			}

			@Override
			public void breakWall(int x, int y) {
				westWall[x][y] = true;

			}

			@Override
			public GeomPoint2D<Integer> move(final int x, final int y) {
				westWall[x][y] = true;
				eastWall[x - 1][y] = true;
				visited[x - 1][y] = true;
				return this.go(x, y);
			}

			@Override
			public Direction antiDirection() {
				return EAST;
			}

			@Override
			public GeomPoint2D<Integer> go(int x, int y) {
				point.setLocation(x - 1, y);
				return point;
			}

			@Override
			public GeomPoint2D<Integer> makeExit(int randomNumber) {
				westWall[0][randomNumber] = true;
				point.setLocation(0, randomNumber);
				return point;
			}

		};

		private static GeomPoint2D<Integer> point = new GeomPoint2D<>();

		/**
		 * Uses the randomNumber in the argument to pick a cell on the wall of a
		 * cardinal direction, break that wall and return the new exit point.
		 * <p>
		 * Used to make the entry and exit of the maze.
		 * 
		 * @param randomNumber random number to pick the exit point
		 * @return point of the location of the exit/entry.
		 */
		public abstract GeomPoint2D<Integer> makeExit(int randomNumber);

		/**
		 * Checks if the cell in that direction is unvisited
		 * 
		 * @param x x-location of the cell
		 * @param y y-location of the cell
		 * @return true if cell unvisited
		 */
		public abstract boolean directionAvailable(int x, int y);

		public boolean directionAvailable(GeomPoint2D<Integer> p) {
			return this.directionAvailable((int) p.getX(), (int) p.getY());
		}

		/**
		 * Breaks a wall of a specified cell <b>only! </b>To break the wall of a
		 * specified cell and the corresponding wall of an adjacent cell, use
		 * Position.move
		 * 
		 * @param x x-location
		 * @param y y-location
		 */
		public abstract void breakWall(int x, int y);

		/**
		 * Overloaded breakWall() to work with {@link GeomPoint2D}.
		 * 
		 * @param p the point of the cell whose wall is being broken.
		 * @see Direction#breakWall(int, int);
		 */
		public void breakWall(GeomPoint2D<Integer> p) {
			this.breakWall((int) p.getX(), (int) p.getY());
		}

		/**
		 * Breaks a wall of a specified wall <b>and</b> the corresponding wall of the
		 * adjacent cell. Also marks the adjacent cell side as visited
		 * 
		 * @param x x-location of the current cell
		 * @param y y-location of the current cell
		 * @return point of the current cell <b>after</b> move
		 */
		public abstract GeomPoint2D<Integer> move(final int x, final int y);

		/**
		 * Overloaded move() to work with {@link GeomPoint2D}
		 * 
		 * @param p starting position
		 * @return point of current cell after move
		 * @see Direction#move(int, int)
		 */
		public GeomPoint2D<Integer> move(GeomPoint2D<Integer> p) {
			return this.move(p.getX(), p.getY());
		}

		/**
		 * The opposite direction (180° to the current direction)
		 * 
		 * @return opposite direction
		 */
		public abstract Direction antiDirection();

		/**
		 * The point of a cell in a direction of a cell <b>without</b> breaking any
		 * walls or marking adjacent cell as visited.
		 * 
		 * @param x x-location
		 * @param y y-location
		 * @return point of an adjacent cell
		 */
		public abstract GeomPoint2D<Integer> go(int x, int y);

		/**
		 * Cell in the direction from a current cell
		 * 
		 * @param p point of current cell
		 * @return cell adjacent in the direction from p
		 * @see Direction#go(int, int)
		 */
		public GeomPoint2D<Integer> go(GeomPoint2D<Integer> p) {
			return this.go(p.getX(), p.getY());
		}
	}; // end for ENUMs

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
	 * Converts an the CellGrid object to an 2D {@code int} array. If a wall is to
	 * be drawn in that location, the Integer will have a value of 1, else value of
	 * 0.
	 * <p>
	 * Integers are used instead of booleans because the {@code int} value
	 * represents the type of wall texture used. (randomly selected by a different
	 * method).
	 * 
	 * @return CellGrid as a 2D {@code int} Array
	 */
	public synchronized int[][] gridTo2DArray() {
		List<List<Integer>> map = new ArrayList<List<Integer>>();

		List<Integer> line1 = new ArrayList<Integer>();
		List<Integer> line2 = new ArrayList<Integer>();
		for (int j = 0; j < gridHeight; j++) {
			line1 = new ArrayList<Integer>();
			line2 = new ArrayList<Integer>();

			for (int i = 0; i < gridWidth; i++) {
				/**
				 * In the format: <br>
				 * 1 N <br>
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
		line1.add(1); // corner piece

		map.add(line1);

		int[][] primitiveMap = new int[map.size()][map.size()];
		for (int i = 0; i < primitiveMap.length; i++) {
			primitiveMap[i] = map.get(i).stream().mapToInt(Integer::intValue).toArray();
		}

		return primitiveMap;
	}

	/**
	 * Checks the number of possible directions the maze generator can go by
	 * checking the visited[][] array of the adjacent cells.
	 * 
	 * @param p point of the cell being checked
	 * @return number of possible directions
	 * @see CellGrid#checkRemainingPaths(int, int)
	 */
	public int checkRemainingPaths(GeomPoint2D<Integer> p) {
		return checkRemainingPaths((int) p.getX(), (int) p.getY());
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
			// left row
			directions = 0;
			if (!visited[x][y + 1])
				directions++;
			if (!visited[x][y - 1])
				directions++;
			if (!visited[x + 1][y])
				directions++;

			return directions;

		}
		if (y == 0) {
			// top row
			directions = 0;
			if (!visited[x - 1][y])
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
			if (!visited[x][y - 1])
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
