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
package org.swisscheese.swisscheese.devTools;

import java.io.File;

import org.swisscheese.swisscheese.gameSaving.GameSaveList;
import org.swisscheese.swisscheese.settings.GameSettings;

/**
 * This class cleans the project directory by deleting all settings and save
 * files generated by the game.
 * 
 * @author Alex Kalinins
 * @since 2018-12-31
 * @since v0.5
 *
 */
public class GameCleaner {
	/**The file location of the {@link GameSettings} file*/
	private static final File SETTINGS = new File("settings/game-settings.json");
	/**The file location of the {@link GameSaveList} file*/
	private static final File SAVES = new File("settings/user-saves/list.json");
	
	/**
	 * Checks to see if the files exist, and deletes the if they do not.
	 * @param args
	 */
	public static void main(String[] args) {
		if(SETTINGS.exists()) {
			System.out.println("Deleting settings");
			SETTINGS.delete();
		}
		if(SAVES.exists()) {
			System.out.println("Deleting saves");
			SAVES.delete();
		}

	}

}
