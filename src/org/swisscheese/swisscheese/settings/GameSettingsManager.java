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
package org.swisscheese.swisscheese.settings;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

import org.swisscheese.swisscheese.engine.display.WindowSize;

import com.google.gson.Gson;

/**
 * This class serializes and deserializes {@link GameSettings} object to file.
 * 
 * @author Alex Kalinins
 * @since 2018-12-28
 * @since v0.5
 * @version v1.0
 */
public class GameSettingsManager {
	private final File FILE = new File("settings/game-settings.json");
	private final Gson gson = new Gson();
	private GameSettings settings;

	public static final GameSettingsManager MANAGER = new GameSettingsManager();

	private GameSettingsManager() {
		settings = FILE.exists() ? openFile() : makeGameSettings();
	}

	private GameSettings openFile() {
		try (BufferedReader reader = new BufferedReader(new FileReader(FILE))) {
			StringBuilder builder = new StringBuilder();
			String s;

			while ((s = reader.readLine()) != null) {
				builder.append(s);
			}

			s = builder.toString();
			return gson.fromJson(s, GameSettings.class);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Makes a default <code>GameSettings</code> file.
	 * <p>
	 * TODO use {@link GameSettings#DEFAULT} (doesn't work)
	 * 
	 * @return a new instance of {@code GameSettings}.
	 */
	private GameSettings makeGameSettings() {
		return new GameSettings(WindowSize.S640X480, false, -0.9f, null, 1);
	}

	/**
	 * Writes {@code settings} to file.
	 * 
	 * @param settings object being serialized.
	 */
	private void writeToFile(GameSettings settings) {
		try (PrintWriter writer = new PrintWriter(FILE)) {
			String json = gson.toJson(settings);
			writer.print(json);
		} catch (IOException e) {
			e.printStackTrace();
		}

		System.out.println("Wrote GameSavings to file");
	}

	/**
	 * Updates the settings with {@code settings}.
	 * 
	 * @param settings new {@code GameSettings} object.
	 */
	public void updateSettings(GameSettings settings) {
		this.settings = settings;
		System.out.println("Updating GameSavings");
		writeToFile(settings);
	}

	public final GameSettings getSettings() {
		return settings;
	}

	public final void setSettings(GameSettings settings) {
		this.settings = settings;
	}
}
