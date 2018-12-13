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


/**
 * A test main class to print out the maze to test the functionality of
 * Generator and CellGrid
 * 
 * @author Alex Kalinins
 * @since v0.1
 * @since 2018-11-15
 */
public strictfp class TestMain {

	public static void main(String[] args) {
		Integer[][] maze = Generator.generateMaze(60);

		String block = "";

		for (int i = 0; i < maze.length; i++) {
			for (int j = 0; j < maze[i].length; j++) {
				block = String.format("%s%c", block, ((maze[i][j] == 1) ? '\u2588' : ' '));
			}
			block = String.format("%s%n", block);
		}
		
		System.out.println(block);

	}

}
