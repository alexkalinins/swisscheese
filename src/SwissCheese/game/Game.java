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
package SwissCheese.game;

import SwissCheese.map.Map;

/**
 * Main Game object. INCOMPLETE
 * 
 * @author Alex Kalinins
 * @since 2018-12-10
 * @since v0.2
 * @version v0.1
 *
 */
public class Game implements Runnable{
	private Map map;
	private int width;
	private int height;
	
	/**
	 * Game object constructor
	 */
	public Game() {
		map = new Map(20);
	}
	
	/**
	 * Prompts user for game settings before the game starts
	 */
	public void openPropt() {
		
	}
	
	/**
	 * Calls map to be generated and places Camera into map.
	 */
	public void startGame() {
		
	}
	
	/**
	 * Main game loop
	 */
	public void loop() {
		while(true) {
			
		}
	}
	
	/**
	 * Map getter
	 * @return map
	 */
	public Map getMap() {
		return map;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}
	
	
}
