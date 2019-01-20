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
package org.swisscheese.swisscheese.game;

import org.swisscheese.swisscheese.engine.camera.View;
import org.swisscheese.swisscheese.gameSaving.GameSave;
import org.swisscheese.swisscheese.gameSaving.SaveMetadata;
import org.swisscheese.swisscheese.map.DifficultyLevel;
import org.swisscheese.swisscheese.map.Map;
import org.swisscheese.swisscheese.settings.GameSettings;

/**
 * This is a wrapper class for {@link GameLoop}. When an instance of
 * {@code GameFromSettings} is created, the {@code GameLoop} is started, and the
 * game is displayed on the screen.
 * 
 * @author Alex Kalinins
 * @since 2018-12-27
 * @since v0.5
 * @version v1.0
 *
 */
public class GameFromSettings {
	@SuppressWarnings("unused")
	private final GameLoop loop;
	private static Map map;
	

	/**
	 * Private constructor for the game. (Used by public static factories).
	 * 
	 * @param settings settings of the game instance.
	 * @param metadata metadata object of the game.
	 * @param map      the maze map of the game.
	 * @param view     the view from the camera (var-args).
	 */
	private GameFromSettings(GameSettings settings, SaveMetadata metadata, Map map, View... view) {
		GameFromSettings.map = map;
		loop = new GameLoop(settings.getWindowSize().getSize(),settings.isFitToScreen(), settings.getTexturePack(), 60f, settings.getFOV(), map,
				metadata, settings.getUseRenderer(), view);
	}

	/**
	 * Public static factory from a previously saved game
	 * 
	 * @param settings settings of the game instance.
	 * @param save     a {@code GameSave} object.
	 */
	public static GameFromSettings fromGameSave(GameSettings settings, GameSave save) {
		return new GameFromSettings(settings, save.getMetadata(), save.getMap(), save.getView());
	}

	/**
	 * Public static factory for a new Game (not from previous {@code GameSave)}
	 * 
	 * @param settings settings of the game instance.
	 * @param level    difficulty of the game
	 * @param name     name of the new game
	 */
	public static GameFromSettings newGame(GameSettings settings, DifficultyLevel level, String name) {
		Map map = new Map(level.getSize());
		SaveMetadata metadata = SaveMetadata.makeMetadata(name);
		return new GameFromSettings(settings, metadata, map);
	}

	public static final Map getMap() {
		return map;
	}

}
