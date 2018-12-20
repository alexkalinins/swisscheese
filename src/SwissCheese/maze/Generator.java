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
package SwissCheese.maze;

import java.awt.geom.Point2D;
import java.security.SecureRandom;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Random;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import SwissCheese.maze.CellGrid.Direction;

/**
 * The Generator class generates a maze using the CellGrid, through a variant of
 * the Randomized Depth-First Search algorithm.
 * <p>
 * The Randomized DFS algorithm picks an random arbitrary cell on the top side
 * of the grid from which it will start the maze. The algorithm then proceeds to
 * randomly pick an adjacent cell, and marks the that grid as visited and the
 * wall between the two cells as broken. From the new cell, the algorithm picks
 * another random adjacent cell, and repeats this process until it is in a
 * dead-end (where all adjacent cells are marked as visited). In that case, it
 * begins to back-track until it is able to into an unvisited cell, thus
 * creating a branch in the maze. The algorithm continues until all maze cells
 * are marked as visited.
 * <p>
 * This class uses a SecureRandom to create truly random puzzles. While
 * SecureRandom can significantly reduce performance, it is ran only once and
 * will make a once-in-a-lifetime maze.
 * 
 * @author Alex Kalinins
 * @since v0.1
 * @since 2018-11-14
 * @version v0.3
 * @see <a href="https://en.wikipedia.org/wiki/Depth-first_search"> Randomized
 *      Depth-First Search</a>
 */
public  class Generator {
	private final int size; // size of maze size*size
	private Deque<Direction> log = new ArrayDeque<Direction>();
	private CellGrid grid;
	private Random rand = new SecureRandom();
	private Point2D startingCell = new Point2D.Double();
	private Point2D currentCell = new Point2D.Double();
	private static Point2D exit = new Point2D.Double();
	private static Point2D entry = new Point2D.Double();
	private static int[][] maze;
	public static Lock lock = new ReentrantLock();

	/**
	 * Private constructor for Generator
	 * 
	 * @param size size of the CellGrid (dimensions: size*size). <b>NOTE:</b> the
	 *             size is only of the CellGrid, and NOT the final map. The size of
	 *             the map will be (size*size+1).
	 */
	private Generator(int size) {
		this.size = size;
		grid = CellGrid.newCellGrid(size, size);
		generate();
		maze = grid.gridTo2DArray();
		createRealEntryAndExit(maze);
	}

	/**
	 * This method creates a new instance of Generator (which generates the maze)
	 * and then calls CellGrid to return a 2D Integer array.
	 * 
	 * @param size size of the CellGrid (dimensions: size*size). <b>NOTE:</b> the
	 *             size is only of the CellGrid, and NOT the final map. The size of
	 * @return the newly generated maze map as a 2D Integer array.
	 */
	public static int[][] generateMaze(int size) {
		lock.lock();
		new Generator(size);
		lock.unlock();
		return Generator.maze;
	}

	/**
	 * Private method that generates the maze. Uses the CellGrid for the cell grid
	 * and follows a variant of the Randomized Depth-first search algorithm.
	 * 
	 * @see CellGrid
	 * @see <a href="https://en.wikipedia.org/wiki/Depth-first_search"> Randomized
	 *      Depth-First Search</a>
	 */
	private void generate() {
		int r = rand.nextInt(size);
		startingCell.setLocation(Direction.NORTH.makeExit(r));
		currentCell.setLocation(startingCell);
		entry.setLocation(startingCell);
		Direction thisDirection = null;

		int counter = size * size;
		int directions;

		// makes a maze with only one entrance.
		while (counter != 0) {
			thisDirection = null;
			directions = grid.checkRemainingPaths(currentCell);
			if (directions > 0) {
				if (directions == 1) {
					// if only one direction avail, for loop finds the direction
					for (int i = 0; i < 4; i++) {
						if ((thisDirection = intToDirection(i)).directionAvailable(currentCell)) {
							break;
						}
					}
				} else {
					thisDirection = randomDirection(currentCell); // stuck here
				}
				appendLog(thisDirection);
				currentCell.setLocation(thisDirection.move(currentCell));
				counter--;
			} else {
				backTrack();
			}
		}

		exit = intToDirection(rand.nextInt(3) + 1).makeExit(rand.nextInt(size - 1));
	}

	/**
	 * Sets the location of the real entry and exit {@code Point2D} from the maze
	 * array, after it has been converted from {@code CellGrid} object by
	 * {@link SwissCheese.maze.CellGrid#gridTo2DArray()}
	 * 
	 * @param maze final maze
	 * @since v0.2
	 * @since 2018-12-19
	 */
	private void createRealEntryAndExit(int[][] maze) {
		entry.setLocation(entry.getX() * 2 + 1, 0);
		if (exit.getY() != size - 1) {
			exit.setLocation((exit.getX() == 0) ? 0 : (exit.getX() * 2 + 1), exit.getY() * 2 + 1);
		} else {
			exit.setLocation(exit.getX() * 2 + 1, maze.length - 1);
		}
	}

	/**
	 * Recursively back-tracks from currentCell until reaches a first location that
	 * touches an unvisited cell.
	 */
	private void backTrack() {
		currentCell = log.removeFirst().antiDirection().go(currentCell);
		if (grid.checkRemainingPaths(currentCell) == 0) {
			backTrack();
		}
	}

	/**
	 * randomDirection overloaded to work with Point2D
	 * 
	 * @param p Point2D of the current cell
	 * @return a random direction
	 * @see Generator#randomDirection(int, int)
	 */
	private Direction randomDirection(Point2D p) {
		return randomDirection((int) p.getX(), (int) p.getY());
	}

	/**
	 * Generates am approved random Direction using SecureRandom
	 * 
	 * @param x x-location of the current cell
	 * @param x y-location of the current cell
	 * @return a random Direction
	 */
	private Direction randomDirection(int x, int y) {
		int r;
		do {
			r = rand.nextInt(4);
		} while (!(intToDirection(r).directionAvailable(x, y)));

		return intToDirection(r);
	}

	/**
	 * Returns a direction from an int:<br>
	 * NORTH = 0<br>
	 * EAST = 1<br>
	 * SOUTH = 2<br>
	 * WEST = any other number
	 * 
	 * @param r a number to pick direction
	 * @return a Direction corresponding to r
	 */
	private Direction intToDirection(int r) {
		if (r == 0)
			return Direction.NORTH;
		else if (r == 1)
			return Direction.EAST;
		else if (r == 2)
			return Direction.SOUTH;
		return Direction.WEST;
	}

	/**
	 * Adds an Direction entry to the log used for back-tracking. The log is a list
	 * of directions the algorithm took, in order to back-track, algorithm must go
	 * in the opposite direction
	 * 
	 * @see Direction#antiDirection()
	 * @see Generator#backTrack()
	 * @param d Direction to be appended to the log
	 */
	private void appendLog(Direction d) {
		log.addFirst(d);
	}

	/**
	 * Getter of the exit point of the maze. The exit point is a random location on
	 * any side (Direction) other than NORTH (starting side). Where the player
	 * should end the game
	 * 
	 * @return exit point of the maze (Point2D)
	 */
	public static Point2D getExit() {
		return exit;
	}

	/**
	 * Getter of the entry point of the maze (where the algorithm started). Where
	 * the player should start the game
	 * 
	 * @return entry point (Point2D)
	 */
	public static Point2D getEntry() {
		return entry;
	}
}
